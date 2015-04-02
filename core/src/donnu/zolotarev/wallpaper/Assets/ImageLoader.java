package donnu.zolotarev.wallpaper.Assets;

import com.badlogic.gdx.math.MathUtils;

public class ImageLoader implements IImageLoader {


    private final TextureAssets textureAssets;
    private int lastIndex;
    private int lastUnload;

    public ImageLoader() {
        textureAssets =  TextureAssets.getTextureAssets();
    }

    @Override
    public void getNext(IImageLoaded callback) {
        String fileName = textureAssets.getImagesNames()[getNextRandom()].path();
        textureAssets.load(fileName,callback);
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
            lastUnload = -1;
        }
    }
}
