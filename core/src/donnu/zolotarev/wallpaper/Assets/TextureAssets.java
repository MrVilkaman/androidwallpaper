package donnu.zolotarev.wallpaper.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureAssets extends AssetManager{



    public interface ITextureAssetsListener {
        public void loaded();
    }

    private static TextureAssets textureAssets;

    private static final String IMAGE_1 = "image.jpg";
    private static final String IMAGE_2 = "image2.jpeg";

    private boolean isFinished = false;

    private Texture image;
    private Texture image2;

    private ITextureAssetsListener listener;

    public TextureAssets() {
        super();
        if (textureAssets == null) {
            textureAssets = this;
        }else{
            throw new RuntimeException("textureAssets = null");
        }
        image = image2 = new Texture(1,1, Pixmap.Format.RGBA4444);
    }

    public static TextureAssets getTextureAssets() {
        return textureAssets;
    }

    public void load(ITextureAssetsListener listener){
        this.listener = listener;
        load(IMAGE_1,Texture.class);
        load(IMAGE_2,Texture.class);
    }

    @Override
    public synchronized boolean update() {
        boolean status = super.update();
        if (status) {
            if (!isFinished) {
               image = get(IMAGE_1,Texture.class);
               image2 = get(IMAGE_2,Texture.class);
                isFinished = true;
                listener.loaded();
            }
        }
        return status;
    }

    public Texture getImage() {
        return image;
    }

    public Texture getImage2() {
        return image2;
    }
}
