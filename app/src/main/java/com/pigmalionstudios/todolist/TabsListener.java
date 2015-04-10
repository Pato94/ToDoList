package com.pigmalionstudios.todolist;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

/**
 * Created by pigmalionstudios on 4/6/15.
 */
public class TabsListener<T extends Fragment> implements ActionBar.TabListener {

    private Fragment mFragment;
    private Class<T> mCls;
    private final String mTag;
    private Activity mActivity;

    public TabsListener(Activity activity, String tag, Class<T> cls) {
        this.mActivity = activity;
        this.mTag = tag;
        this.mCls = cls;
        mFragment = Fragment.instantiate(activity, mCls.getName());

        ((ListaFragment) mFragment).setCosas((tag == activity.getString(R.string.tag_pendientes)) ? ListaFragment.Tipo.PENDIENTES : ListaFragment.Tipo.HECHAS);
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(android.R.id.content, mFragment, (((ListaFragment)mFragment).tipo==ListaFragment.Tipo.PENDIENTES)?"Pendientes":"Hechas");
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(mFragment);
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

}