package donnu.zolotarev.wallpaper.Assets;

public interface IExternalTextureLoader {

    void load(String file,IImageLoader.IImageLoaded callback);
    void unload(String file);
}
