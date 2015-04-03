package donnu.zolotarev.wallpaper.android.fragments;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment{

    public View injectView(int res, LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(res, container, false);
        ButterKnife.inject(this,view);
        return view;
    }
}
