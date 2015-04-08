package donnu.zolotarev.wallpaper.android.models;

public class ListViewItems implements ICallback {



    private int title;
    private int imageId;
    private ICallback callback;

    public ListViewItems(int title, int imageId, ICallback callback) {
        this.title = title;
        this.imageId = imageId;
        this.callback = callback;
    }

    public int getImageId() {
        return imageId;
    }

    public int getTitle() {
        return title;
    }

    @Override
    public void execute() {
        callback.execute();
    }

}
