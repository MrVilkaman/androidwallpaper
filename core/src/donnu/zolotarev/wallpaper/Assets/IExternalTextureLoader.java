package donnu.zolotarev.wallpaper.Assets;

public interface IExternalTextureLoader {

    boolean load(String file,IImageLoader.IImageLoaded callback);
    void unload(String file);
}
