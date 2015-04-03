package donnu.zolotarev.wallpaper.android.fragments;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.wallpaper.android.AndroidLauncher;
import donnu.zolotarev.wallpaper.android.R;
import donnu.zolotarev.wallpaper.android.adapters.TempAdapter;
import donnu.zolotarev.wallpaper.android.models.ICallback;
import donnu.zolotarev.wallpaper.android.models.ListViewItems;
import donnu.zolotarev.wallpaper.android.utils.Constants;
import donnu.zolotarev.wallpaper.android.utils.Utils;

public class MainFragment extends BaseFragment {

    private TempAdapter tempAdapter;

    @InjectView(R.id.list)
    ListView list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = injectView(R.layout.fragment_main,inflater, container);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TempAdapter)parent.getAdapter()).click(position);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().getActionBar().hide();
        list.setAdapter(tempAdapter);
    }

    private void initAdapter() {
        ArrayList<ListViewItems> items = new ArrayList<ListViewItems>(6);
        items.add(new ListViewItems(R.string.main_list_gift, new ICallback() {
            @Override
            public void execute() {

            }
        }));
        items.add(new ListViewItems(R.string.main_list_more_wallpaper, new ICallback() {
            @Override
            public void execute() {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MORE_APPS)));
            }
        }));
        items.add(new ListViewItems(R.string.main_list_rate, new ICallback() {
            @Override
            public void execute() {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_PNAME)));
            }
        }));
        items.add(new ListViewItems(R.string.main_list_share, new ICallback() {
            @Override
            public void execute() {
                Utils.share(getActivity(), getString(R.string.share_text));
            }
        }));
        items.add(new ListViewItems(R.string.main_list_amazing_apps, new ICallback() {
            @Override
            public void execute() {

            }
        }));
        items.add(new ListViewItems(R.string.main_list_contact, new ICallback() {
            @Override
            public void execute() {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DEV_LINK)));
            }
        }));
        tempAdapter = new TempAdapter(getActivity(), items);
    }

    @OnClick(R.id.main_set_wallpaper)
    void onSetWallpaper(){
        Intent i = new Intent();
        if (Build.VERSION.SDK_INT > 15) {
            i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);

            String p = AndroidLauncher.class.getPackage().getName();
            String c = AndroidLauncher.class.getCanonicalName();
            i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(p, c));
        } else {
            i.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }
        startActivityForResult(i, 0);

    }
}
