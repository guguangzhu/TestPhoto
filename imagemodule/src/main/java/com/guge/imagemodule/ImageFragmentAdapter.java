package com.guge.imagemodule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

/**
 * fragment适配器
 * Created by Administrator on 2017/3/30.
 */

public class ImageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private FragmentManager fragmentManager;

    public ImageFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentManager=fm;
        this.mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }



    public void removeAllFragment(){

        List<Fragment> fragments=fragmentManager.getFragments();
        if(fragments==null||fragments.size()==0)
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            fragmentTransaction.remove(fragments.get(i));
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        notifyDataSetChanged();

    }
}
