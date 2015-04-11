package donnu.zolotarev.wallpaper.android.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import donnu.zolotarev.wallpaper.android.R;
import donnu.zolotarev.wallpaper.android.activity.SingleFragmentActivity;
import donnu.zolotarev.wallpaper.android.ads.IAds;
import donnu.zolotarev.wallpaper.android.utils.TypefaceSpan;

import static donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility.FONT_ROBOTO_LIGHT;
import static donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility.FONT_ROBOTO_THIN;
import static donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility.getFont;
import static donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility.setTypefaceOfViewGroup;

public class BaseFragment extends Fragment{

    private static final String TAG = "BaseFragment";

    protected static final int ACTION_BAR_HIDE = -1;
    protected IAds ads;


    public View injectView(int res, LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(res, container, false);
        ButterKnife.inject(this, view);

      //  loadFonts(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (ads == null) {
            ads = loadAds();
        }
        FrameLayout layout = ButterKnife.findById(view, R.id.adslayout);
        View viewAds = ads.getBannerView();
        layout.addView(viewAds);
    }

    private void loadFonts(View view) {
        try {
            setTypefaceOfViewGroup(getActivity(), (ViewGroup) view, FONT_ROBOTO_THIN);
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
            Typeface font = getFont(getActivity(), FONT_ROBOTO_LIGHT);
            s.setSpan(new TypefaceSpan(font), 0, s.length(),
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

    private IAds loadAds(){

         StoreFragment retainedWorkerFragment =
                (StoreFragment) getFragmentManager().findFragmentByTag(StoreFragment.TAG);

        if (retainedWorkerFragment == null) {
            retainedWorkerFragment = new StoreFragment();

            getFragmentManager().beginTransaction()
                    .add(retainedWorkerFragment, StoreFragment.TAG)
                    .commit();

        }
        IAds iAds = retainedWorkerFragment.getAds();
        iAds.setContext(getActivity().getApplicationContext());
        return iAds;
    }

    @Override
    public void onResume() {
        super.onResume();
        ads.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        ads.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ads.onDestroy();
    }
}
