package donnu.zolotarev.wallpaper.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class ExternalTextureLoader implements IExternalTextureLoader {

    private HashMap<String,Texture> textureHashMap;

    public ExternalTextureLoader() {
        textureHashMap = new HashMap<String,Texture>(2);
    }

    @Override
    public boolean load(String file, IImageLoader.IImageLoaded callback) {
        if (!textureHashMap.containsKey(file)) {
            Texture texture =  new Texture(Gdx.files.absolute(file));
            textureHashMap.put(file,texture);
            callback.onCompleate(texture);
            return true;
        }
        return false;
    }

    @Override
    public void unload(String file) {
        Texture texture =  textureHashMap.get(file);
        if (texture != null) {
            texture.dispose();
            textureHashMap.remove(file);
        }
    }
}
