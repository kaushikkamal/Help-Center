package com.example.helpcenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapterNerve extends FragmentStatePagerAdapter implements CardAdapter {

    private List<CardFragmentNerve> fragments;
    private float baseElevation;

    public CardFragmentPagerAdapterNerve(FragmentManager fm, float baseElevation) {
        super(fm);
        fragments = new ArrayList<CardFragmentNerve>();
        this.baseElevation = baseElevation;

        for(int i = 0; i< 2; i++){
            addCardFragment(new CardFragmentNerve());
        }
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragmentNerve.getInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (CardFragmentNerve) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragmentNerve fragment) {
        fragments.add(fragment);
    }

}
