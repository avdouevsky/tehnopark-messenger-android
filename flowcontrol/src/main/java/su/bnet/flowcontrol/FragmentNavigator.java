package su.bnet.flowcontrol;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by andrey on 25.11.2016.
 */

abstract public class FragmentNavigator<T extends Enum<?>, E extends BundleCommand> extends Navigator<T, E> {
    private static final String TAG = FragmentNavigator.class.toString();

    private FragmentManager fragmentManager;
    private int containerId;

    public FragmentNavigator(Router<T, E> router, FragmentManager fragmentManager, int containerId) {
        super(router);

        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @Override
    public void forwardTo(T state, Bundle bundle) {
        super.forwardTo(state, bundle);

        fragmentManager
                .beginTransaction()
                .replace(containerId, createFragment(state, router.get(state).getData()))
                .addToBackStack(state.toString())
                .commit();
    }

    @Override
    public boolean back() {
        if(fragmentManager.getBackStackEntryCount() > 1){
            Log.v(TAG, "back");
            super.back();
            fragmentManager.popBackStackImmediate();
            return true;
        }
        return false;
    }

    @Override
    public void replaceTo(T state, Bundle bundle) {
        Log.v(TAG, "replaceTo " + state);
        super.back();
        super.forwardTo(state, bundle);

        Log.e(TAG, "ss: " + fragmentManager.getBackStackEntryCount());
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
//            fragmentManager
//                    .beginTransaction()
//                    .replace(containerId, createFragment(state, router.get(state).getData()))
//                    .addToBackStack(state.toString())
//                    .commit();
        }/* else*/ {
            fragmentManager
                    .beginTransaction()
                    .replace(containerId, createFragment(state, router.get(state).getData()))
                    .addToBackStack(state.toString())
                    .commit();
        }
        //fragmentManager.executePendingTransactions();
    }

    @Override
    public void backTo(T state) {
        super.backTo(state);

        boolean hasScreen = false;
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            if (state.toString().equals(fragmentManager.getBackStackEntryAt(i).getName())) {
                fragmentManager.popBackStackImmediate(state.toString(), 0);
                hasScreen = true;
                break;
            }
        }
//        if (!hasScreen) {
//            replaceTo(state);
//        }
    }

//    public void backToRoot() {
//        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
//            fragmentManager.popBackStack();
//        }
//        fragmentManager.executePendingTransactions();
//    }

    @Nullable
    protected abstract Fragment createFragment(T screenKey, Bundle data);
}
