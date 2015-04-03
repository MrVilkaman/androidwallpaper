package donnu.zolotarev.wallpaper.android.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import donnu.zolotarev.wallpaper.android.R;


public abstract class SingleFragmentActivity extends Activity {
    protected abstract Fragment createFragment();

    protected int getContainerID(){
        return R.id.fragmentContainer;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContent();
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getFragmentManager();
        Fragment myFragment = fm.findFragmentById(getContainerID());

        if (myFragment == null){
            myFragment = createFragment();
            fm.beginTransaction()
                    .add(getContainerID(), myFragment)
                    .commit();
        }
    }

    protected void beforeSetContent() {
    }

    public void loadRootFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (addToBackStack){
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(getContainerID(), fragment).commit();
    }

    public void popBackStack(){
        getFragmentManager().popBackStack();
    }
}
