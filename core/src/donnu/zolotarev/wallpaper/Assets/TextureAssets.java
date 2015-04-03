package donnu.zolotarev.wallpaper.Assets;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class TextureAssets extends AssetManager{

    private static TextureAssets textureAssets;

    private static final String IMAGE_FOLDER = "backgrounds/";


    private boolean isFinished = false;
    private IImageLoader.IImageLoaded callback;
    private String lastInLoad;


    public TextureAssets() {
        super();
        textureAssets = this;

        setErrorListener(new AssetErrorListener() {
            @Override
            public void error(AssetDescriptor asset, Throwable throwable) {

            }
        });
    }

    public static TextureAssets getTextureAssets() {
        return textureAssets;
    }

    @Override
    public synchronized boolean update() {
        boolean status = super.update();
        if (callback != null && isLoaded(lastInLoad)) {
            callback.onCompleate(get(lastInLoad,Texture.class));
            callback = null;
        }
        return status;
    }

    public FileHandle[] getImagesNames(){
        return Gdx.files.getFileHandle(IMAGE_FOLDER, Files.FileType.Internal).list();
    }

    public void load(String fileName, IImageLoader.IImageLoaded callback) {
        lastInLoad = fileName;
        this.callback = callback;
        load(fileName, Texture.class);
    }

    @Override
    public synchronized void unload(String fileName) {
        if (fileName != null && isLoaded(fileName)) {
            super.unload(fileName);
        }
    }
}
