package donnu.zolotarev.wallpaper.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoUtils {

    public static final int IN_GALLERY = 1991;
    public static final String TEMP_NAME = "TEMP_NAME.jpg";

    private static final int HEIGHT =  640;
    private static final int WIGTH =   480;

    public static final String TEMP_PHOTO_DIR_NAME = "tempPhotos";
    public static final String DEFAULT_PHOTO_NAME = "Photo";
    private static String lastPhotoPath = "";
    private static String lastPhotoName;


    public static Bitmap decodeSampledBitmapFromResource(String res) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFile(res, options);

        options.inSampleSize = calculateInSampleSize(options, HEIGHT, WIGTH);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(res, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static void writeImageToFile(String filePath,Bitmap finalBmp){
        File file = new File (filePath);
        try {
        if(!file.exists()){
            file.createNewFile();
        }

            FileOutputStream out = new FileOutputStream(file);
            finalBmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importCamera(Fragment frag, int resultId,String fileName){
        lastPhotoPath = getPathToPhoto(fileName);
        lastPhotoName = fileName;
        File originalFile = new File(lastPhotoPath);
        originalFile.getParentFile().mkdirs();
        Uri imageFileUri = Uri.fromFile(originalFile);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        frag.startActivityForResult(cameraIntent, resultId);
    }

    public static BitmapDrawable getScaledPicture(Activity activity, String path) {

        File f = new File(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        float outHeight = options.outHeight;
        float outWidth = options.outWidth;

        int inSampleSize = 1;
        if (outHeight > HEIGHT || outWidth > WIGTH){
            if (outHeight> outWidth ){
                inSampleSize = Math.round(outHeight / HEIGHT);
            } else {
                inSampleSize = Math.round(outWidth / WIGTH);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = null; //BitmapFactory.decodeFile(path, options);
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int orientation = 1;
        orientation = neededRotation(path);
        if (orientation != 0){
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }
        return new BitmapDrawable(activity.getResources(), bitmap);
    }

    public static void clearImageView(ImageView imageView) {
        if (! (imageView.getDrawable() instanceof BitmapDrawable)){
            return;
        }
        BitmapDrawable drawable =(BitmapDrawable) imageView.getDrawable();
        if (drawable.getBitmap() != null){
            drawable.getBitmap().recycle();
        }
        imageView.setImageDrawable(null);
    }

    public static int neededRotation(String path){
        try{
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            if (orientation == ExifInterface.ORIENTATION_ROTATE_270){
                return 270;
            }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180){
                return 180;
            }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90){
                return 90;
            }
            return 0;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static String getLastPhotoPath() {
        return lastPhotoPath;
    }

    public static String getPathToPhoto(String fileName) {
        return getPathToPhotoDir()  + fileName+".jpg";
    }

    public static String getPathToPhotoDir() {
        return "" + File.separator;
    }

    public static void clearTempPhotoDir(){
        File dir = new File(TEMP_PHOTO_DIR_NAME);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    public static void importInGalery(Fragment fragment, int resultId, String fileName) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, resultId);
    }

    public static String getLastPhotoName() {
        return lastPhotoName;
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IN_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    if (resultCode == Activity.RESULT_OK) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        if (cursor != null){
                            if (cursor.moveToFirst()) {
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                lastPhotoPath = cursor.getString(columnIndex);
                            }
                            cursor.close();
                        }
                    }
                }
                break;

        }
    }

    public static void clearLastPhotoPath() {
        lastPhotoPath = "";
    }
}
