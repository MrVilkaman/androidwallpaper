package donnu.zolotarev.wallpaper.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import donnu.zolotarev.wallpaper.android.R;


public abstract class SingleFragmentActivity extends ActionBarActivity {
    protected abstract android.support.v4.app.Fragment createFragment();

    protected int getContainerID(){
        return R.id.fragmentContainer;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContent();
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack){
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(getContainerID(), fragment).commit();
    }

    public void popBackStack(){
        getFragmentManager().popBackStack();
    }
}
