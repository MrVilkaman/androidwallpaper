package donnu.zolotarev.wallpaper.android.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import donnu.zolotarev.wallpaper.android.activity.SingleFragmentActivity;
import donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility;
import donnu.zolotarev.wallpaper.android.utils.TypefaceSpan;

public class BaseFragment extends Fragment{

    private static final String TAG = "BaseFragment";

    protected static final int ACTION_BAR_HIDE = -1;

    private static final String FONTS_FOLDER = "fonts/";
    protected static final String FONT_ROBOTO_THIN = FONTS_FOLDER+"Roboto-Thin.ttf";
    protected static final String FONT_ROBOTO_LIGHT = FONTS_FOLDER+"Roboto-Light.ttf";

    public View injectView(int res, LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(res, container, false);
        ButterKnife.inject(this, view);

        loadFonts(view);
        return view;
    }

    private void loadFonts(View view) {
        try {
            Typeface type = Typeface.createFromAsset(getActivity().getAssets(),FONT_ROBOTO_THIN);
            AndroidTypefaceUtility.SetTypefaceOfViewGroup((ViewGroup)view,type);
        } catch (Exception e) {
        }
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
            SpannableString s = new SpannableString(getString(resId));
            s.setSpan(new TypefaceSpan(getActivity(), FONT_ROBOTO_LIGHT), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            bar.setTitle(s);
        }

    }


    protected void toast(int messageId) {
        Activity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, messageId, Toast.LENGTH_SHORT).show();
        }
    }

    protected void toast(String message) {
        Activity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }
}
