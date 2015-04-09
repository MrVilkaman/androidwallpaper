package donnu.zolotarev.wallpaper.Assets;

import com.badlogic.gdx.math.MathUtils;

public class ImageLoader implements IImageLoader {


    private static final int CUSTOM_IMAGE_ID = -2;
    private final TextureAssets textureAssets;
    private final IExternalTextureLoader externalTextureLoader;
    private int lastIndex;
    private int lastUnload;
    private String customImage;
    private String oldCustomImage;

    public ImageLoader() {
        textureAssets =  TextureAssets.getTextureAssets();
        externalTextureLoader = new ExternalTextureLoader();
    }

    @Override
    public boolean setCustomImage(String customImage) {
        /*if (!customImage.equals(this.customImage)) {
            textureAssets.unload(customImage);
        }*/
        oldCustomImage = this.customImage;
        this.customImage = customImage;

        return !customImage.equals(oldCustomImage);
    }

    @Override
    public void getNext(final IImageLoaded callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (customImage == null || (customImage.isEmpty())) {
                    String fileName = textureAssets.getImagesNames()[getNextRandom()].path();
                    textureAssets.load(fileName, callback);
                }else{
                    int i = getCustomImage();
                    if (i == -1) {
                        lastIndex = CUSTOM_IMAGE_ID;
                        if (!externalTextureLoader.load(customImage, callback)) {
                            oldCustomImage = "";
                        }
                    }else{
                        String fileName = textureAssets.getImagesNames()[i].path();
                        textureAssets.load(fileName, callback);
                    }

                }
            }
        }).run();

    }

    private int getCustomImage() {

        if (customImage.charAt(0) == '#') {
            String s = customImage.substring(1, customImage.length());
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
            }
        }
        return -1;
    }

    private int getNextRandom() {
        int index;
        int maxDefImages = textureAssets.getImagesNames().length - 1;
        do{
            index = MathUtils.random(maxDefImages);
        }while (index == lastIndex);
        lastUnload = lastIndex;
        lastIndex = index;
        return lastIndex;
    }

    @Override
    public void unloadLast() {
         if (lastUnload == CUSTOM_IMAGE_ID){
             if (!oldCustomImage.equals(customImage)) {
                 if (!oldCustomImage.isEmpty()) {
                     externalTextureLoader.unload(oldCustomImage);
                 }
             }
        }else if (lastUnload != -1) {
             String fileName = textureAssets.getImagesNames()[lastUnload].path();
             textureAssets.unload(fileName);
         }
            lastUnload = -1;
    }
}
