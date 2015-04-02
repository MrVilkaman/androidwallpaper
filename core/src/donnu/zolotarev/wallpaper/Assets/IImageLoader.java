package donnu.zolotarev.wallpaper.Assets;

import com.badlogic.gdx.graphics.Texture;

public interface IImageLoader {


    interface IImageLoaded{
        public void onCompleate(Texture texture);
    }

    void setCustomImage(String customImage);
    void getNext(IImageLoaded callback);
    void unloadLast();
}
