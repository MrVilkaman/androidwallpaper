package donnu.zolotarev.wallpaper.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.InjectView;
import donnu.zolotarev.wallpaper.android.R;
import donnu.zolotarev.wallpaper.android.adapters.TempAdapter;
import donnu.zolotarev.wallpaper.android.models.ICallback;
import donnu.zolotarev.wallpaper.android.models.ListViewItems;

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

            }
        }));
        items.add(new ListViewItems(R.string.main_list_rate, new ICallback() {
            @Override
            public void execute() {

            }
        }));
        items.add(new ListViewItems(R.string.main_list_share, new ICallback() {
            @Override
            public void execute() {

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

            }
        }));
        tempAdapter = new TempAdapter(getActivity(), items);
    }
}
