package donnu.zolotarev.wallpaper.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import donnu.zolotarev.wallpaper.android.R;

public class MainFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = injectView(R.layout.fragment_main,inflater, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().getActionBar().hide();
    }
}
