package donnu.zolotarev.wallpaper.android.models;

public class ListViewItems implements ICallback {



    private int title;
    private ICallback callback;

    public ListViewItems(int title, ICallback callback) {
        this.title = title;
        this.callback = callback;
    }

    public int getTitle() {
        return title;
    }

    @Override
    public void execute() {
        callback.execute();
    }

}
