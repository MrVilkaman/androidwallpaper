package donnu.zolotarev.wallpaper.android.ads;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import donnu.zolotarev.wallpaper.android.utils.Constants;

public class Admob implements IAds {

    private InterstitialAd interstitial;
    private Context context;
    private AdView adView;
    private boolean needInterstitial;


    @Override
    public void showBigBanner() {
        if (Constants.NEED_ADS) {
            boolean adMod = interstitial.isLoaded();
            if (adMod) {
                interstitial.show();
            }else{
                needInterstitial = true;
                loadAdMob();
            }
        }
    }

    @Override
    public void showBanner() {
        if (Constants.NEED_ADS) {
            if (adView != null) {
                adView.setVisibility(View.VISIBLE);
                adView.resume();
            }
        }
    }


    @Override
    public void hileBanner() {
        if (Constants.NEED_ADS) {
            if (adView != null) {
                adView.setVisibility(View.GONE);
                adView.pause();
            }
        }
    }


    @Override
    public void onResume(){
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
    }

    @Override
    public View getBannerView() {
        if (Constants.NEED_ADS) {
            if (adView != null ){
                adView.pause();
                adView.destroy();
            }
                adView = new AdView(context.getApplicationContext());
                adView.setAdSize(AdSize.SMART_BANNER);
                adView.setAdUnitId(Constants.BANNED_ID);
                AdRequest adRequest = new AdRequest.Builder()
                                        .addTestDevice("0941BB9226FB875E4646CDF71CF1D248")

                        .build();
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (adView != null) {
                        adView.setVisibility(View.VISIBLE);
                    }
                }
            });
            adView.setVisibility(View.GONE);
            adView.loadAd(adRequest);
        }

        return adView;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
        prelaodAds();
    }

    private void prelaodAds(){
        if (Constants.NEED_ADS) {
            interstitial = new InterstitialAd(context.getApplicationContext());
            interstitial.setAdUnitId(Constants.ADMOB_DS_ID);
            interstitial.setAdListener(adMobListener);
            loadAdMob();
        }
    }

    private void loadAdMob() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("0941BB9226FB875E4646CDF71CF1D248")
                .build();
        interstitial.loadAd(adRequest);
    }

    private AdListener adMobListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            if (needInterstitial) {
                interstitial.show();
                needInterstitial = false;
            }
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            Log.d("TAG",admodErrorIntToStr(errorCode));
        }
    };

    private String admodErrorIntToStr(int errorCode) {
        switch (errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                return "ERROR_CODE_INTERNAL_ERROR";
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                return "ERROR_CODE_INVALID_REQUEST";
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                return "ERROR_CODE_NETWORK_ERROR";
            case AdRequest.ERROR_CODE_NO_FILL:
                return "ERROR_CODE_NO_FILL";
        }
        return "ADMOD_ERROR_OTHER";
    }
}
