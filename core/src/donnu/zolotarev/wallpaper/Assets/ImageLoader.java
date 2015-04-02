package donnu.zolotarev.wallpaper.Assets;

import com.badlogic.gdx.math.MathUtils;

public class ImageLoader implements IImageLoader {


    private static final int CUSTOM_IMAGE_ID = -2;
    private final TextureAssets textureAssets;
    private int lastIndex;
    private int lastUnload;
    private String customImage;

    public ImageLoader() {
        textureAssets =  TextureAssets.getTextureAssets();
    }

    @Override
    public void setCustomImage(String customImage) {
        if (!customImage.equals(this.customImage)) {
            if (this.customImage != null && textureAssets.isLoaded(this.customImage)) {
                textureAssets.unload(customImage);
            }
        }
        this.customImage = customImage;
    }

    @Override
    public void getNext(IImageLoaded callback) {
        if (customImage == null || (customImage.isEmpty())) {
            String fileName = textureAssets.getImagesNames()[getNextRandom()].path();
            textureAssets.load(fileName, callback);
        }else{
            lastIndex = CUSTOM_IMAGE_ID;
            if (!textureAssets.isLoaded(customImage)) {
                textureAssets.load(customImage, callback);
            }
        }
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
        if (lastUnload != -1) {
            textureAssets.unload(textureAssets.getImagesNames()[lastUnload].path());
        }else if (lastUnload == CUSTOM_IMAGE_ID){
            if (textureAssets.isLoaded(customImage)) {
                textureAssets.unload(customImage);
            }
        }
            lastUnload = -1;
    }
}
