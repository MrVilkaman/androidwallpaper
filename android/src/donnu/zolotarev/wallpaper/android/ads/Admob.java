package donnu.zolotarev.wallpaper.android.ads;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ironsource.mobilcore.MobileCore;

import donnu.zolotarev.wallpaper.android.utils.Constants;

public class Admob implements IAds {

    private InterstitialAd interstitial;
    private Activity context;
    private AdView adView;
    private boolean needInterstitial;


    @Override
    public boolean showBigBanner() {

        if (Constants.NEED_ADS) {
            boolean adMod = interstitial.isLoaded();
            boolean mobiapp = MobileCore.isInterstitialReady();

            if (mobiapp) {
                MobileCore.showInterstitial(context, null);
            }else if (adMod) {
                interstitial.show();
            }else{
                needInterstitial = true;
                loadAdMob();
                return false;
            }
        }
        return true;
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
    public void showVideo() {
        if (Constants.NEED_ADS) {
            Chartboost.showRewardedVideo(CBLocation.LOCATION_GAMEOVER);
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
            Chartboost.onResume(context);
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
            Chartboost.onPause(context);
        }
    }

    @Override
    public void onStart() {
        if (adView != null) {
            Chartboost.onStart(context);
        }
    }

    @Override
    public void onStop() {
        if (adView != null) {
            Chartboost.onStop(context);
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
            Chartboost.onDestroy(context);
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
    public void setContext(Activity context) {
        this.context = context;
        prelaodAds();
    }

    private void prelaodAds(){
        if (Constants.NEED_ADS) {
            interstitial = new InterstitialAd(context.getApplicationContext());
            interstitial.setAdUnitId(Constants.ADMOB_DS_ID);
            interstitial.setAdListener(adMobListener);
            loadAdMob();

            Chartboost.startWithAppId(context, Constants.CHARTBOOST_APP_ID, Constants.CHARTBOOST_APP_SIGNATURE);
    /* Optional: If you want to program responses to Chartboost events, supply a delegate object here and see step (10) for more information */
            //Chartboost.setDelegate(delegate);
            Chartboost.onCreate(context);
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
                boolean mobiapp = MobileCore.isInterstitialReady();
                if (mobiapp) {
                    MobileCore.showInterstitial(context, null);
                }else {
                    interstitial.show();
                }
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
