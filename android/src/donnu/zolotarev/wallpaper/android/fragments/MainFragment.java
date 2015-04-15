package donnu.zolotarev.wallpaper.android.fragments;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.wallpaper.android.AndroidLauncher;
import donnu.zolotarev.wallpaper.android.R;
import donnu.zolotarev.wallpaper.android.adapters.TempAdapter;
import donnu.zolotarev.wallpaper.android.models.ICallback;
import donnu.zolotarev.wallpaper.android.models.ListViewItems;
import donnu.zolotarev.wallpaper.android.utils.Constants;
import donnu.zolotarev.wallpaper.android.utils.Utils;

import static donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility.FONT_ROBOTO_BOLD;
import static donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility.setTypefaceOfView;

public class MainFragment extends BaseFragment {

    private TempAdapter tempAdapter;

    @InjectView(R.id.list)
    ListView list;
    private boolean firstload = true;


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
                if (position != parent.getAdapter().getCount() - 1) {
                    ((TempAdapter) ((HeaderViewListAdapter) parent.getAdapter()).getWrappedAdapter()).click(position);
                }
            }
        });
        addedFooter();
        return view;
    }

    private void loadFont() {
        try {
     /*       setTypefaceOfView(getActivity(), list.getRootView(), FONT_ROBOTO_THIN);
            setTypefaceOfView(getActivity(), ButterKnife.findById(getView(), R.id.main_settings), FONT_ROBOTO_LIGHT);
            setTypefaceOfView(getActivity(), ButterKnife.findById(getView(), R.id.main_set_wallpaper), FONT_ROBOTO_LIGHT);*/
            setTypefaceOfView(getActivity(), ButterKnife.findById(getView(), R.id.title_layout), FONT_ROBOTO_BOLD);
            Log.d("TAG", "loadFont - success");
        } catch (Exception e) {
            Log.d("TAG","loadFont",e);
        }
    }

    private void addedFooter() {
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.footer_main_view, list, false);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        headerView.findViewById(R.id.footer_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.open(getActivity(), Constants.GROUP_FACEBOOK);
            }
        });
        headerView.findViewById(R.id.footer_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Utils.open(getActivity(), Constants.GROUP_GOOGLE);
            }
        });
        headerView.findViewById(R.id.footer_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.open(getActivity(), Constants.GROUP_TWITTER);
            }
        });
        headerView.findViewById(R.id.footer_youtube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.open(getActivity(), Constants.GROUP_YOUTUBE);
            }
        });
        list.addFooterView(headerView);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (firstload) {
            firstload = ads.showBigBanner();
        }

        setTitle(ACTION_BAR_HIDE);
        list.setAdapter(tempAdapter);
        loadFont();

    }

    private void initAdapter() {
        ArrayList<ListViewItems> items = new ArrayList<ListViewItems>(6);
        items.add(new ListViewItems(R.string.main_list_gift, R.drawable.ic_gift, new ICallback() {
            @Override
            public void execute() {

            }
        }));
        items.add(new ListViewItems(R.string.main_list_more_wallpaper, R.drawable.ic_more_wallpepr, new ICallback() {
            @Override
            public void execute() {
                Utils.open(getActivity(), Constants.MORE_APPS);
            }
        }));
        items.add(new ListViewItems(R.string.main_list_rate, R.drawable.ic_rate, new ICallback() {
            @Override
            public void execute() {
                Utils.open(getActivity(), Constants.APP_PNAME);
            }
        }));
        items.add(new ListViewItems(R.string.main_list_share, R.drawable.ic_share, new ICallback() {
            @Override
            public void execute() {
                Utils.share(getActivity(), getString(R.string.share_text));
            }
        }));
        items.add(new ListViewItems(R.string.main_list_amazing_apps, R.drawable.ic_amazing_wallpaper, new ICallback() {
            @Override
            public void execute() {

            }
        }));
        items.add(new ListViewItems(R.string.main_list_contact, R.drawable.ic_contact_developer, new ICallback() {
            @Override
            public void execute() {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DEV_LINK)));
                Utils.open(getActivity(), Constants.DEV_LINK);
            }
        }));
        tempAdapter = new TempAdapter(getActivity(), items);
    }

    @OnClick(R.id.main_settings)
    void onSetting(){
        showFragment(new SettingFragment(),true);
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
