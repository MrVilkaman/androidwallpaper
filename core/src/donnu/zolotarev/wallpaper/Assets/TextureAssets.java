package donnu.zolotarev.wallpaper.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureAssets extends AssetManager{


    private static final String IMAGE_1 = "image.jpg";

    private final boolean isFinished = false;
    private Texture image;

    public TextureAssets() {
        super();
        image = new Texture(1,1, Pixmap.Format.RGBA4444);

        load();
    }

    public void load(){
        load(IMAGE_1,Texture.class);
    }

    @Override
    public synchronized boolean update() {
        boolean status = super.update();
        if (status) {
            if (!isFinished) {
               image = get(IMAGE_1,Texture.class);
            }
        }
        return status;
    }

    public Texture getImage() {
        return image;
    }
}
