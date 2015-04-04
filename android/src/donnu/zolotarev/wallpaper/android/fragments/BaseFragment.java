package donnu.zolotarev.wallpaper.android.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import donnu.zolotarev.wallpaper.android.activity.SingleFragmentActivity;

public class BaseFragment extends Fragment{

    protected static final int ACTION_BAR_HIDE = -1;

    public View injectView(int res, LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(res, container, false);
        ButterKnife.inject(this,view);
        return view;
    }

    protected SingleFragmentActivity getMainActivity(){
        Activity activity = getActivity();
        if(activity != null && activity instanceof SingleFragmentActivity){
            return (SingleFragmentActivity) activity;
        }

        return null;
    }

    protected void showFragment(BaseFragment fragment, boolean addToBackStack) {
        SingleFragmentActivity activity = getMainActivity();
        if(activity != null){
            activity.loadRootFragment(fragment, addToBackStack);
        }
    }

    protected void setTitle(int resId){
        ActionBar bar = getMainActivity().getSupportActionBar();
        if (resId == ACTION_BAR_HIDE) {
            bar.hide();
        }else{
            bar.show();
            bar.setTitle(resId);
        }
    }
}
