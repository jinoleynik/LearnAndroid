package com.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;


public abstract class BaseFragment<A, S> extends Fragment implements OnClickListener {
    protected A mApp;
    protected S mSettings;
    
    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Activity activity) {
        mApp = (A) activity.getApplication();
        if ( mApp == null ) {
            throw new NullPointerException("Application is null");
        }
//        mSettings =  mApp.getSettings();
//        if ( mSettings == null ) {
//            throw new NullPointerException("Settings is null");
//        }
        super.onAttach(activity);
    }
    
    protected void startFragment(int resId, Fragment fragment, 
            boolean addToStack, String stackTag ) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();      
        ft.replace(resId, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if ( addToStack ) {
            ft.addToBackStack(stackTag);
        }
        ft.commit();
    }
    
    protected void registerOnClickListener(int[] is, View inview) {
        for (int i : is) {
            final View view = inview.findViewById(i);
            if ( view == null ) throw new NullPointerException("Can't get view with id "+i);
            view.setOnClickListener(this);
        }
    }
}
