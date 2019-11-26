package com.example.helpcenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapterHeart extends FragmentStatePagerAdapter implements CardAdapter {

    private List<CardFragmentHeart> fragments;
    private float baseElevation;

    public CardFragmentPagerAdapterHeart(FragmentManager fm, float baseElevation) {
        super(fm);
        fragments = new ArrayList<CardFragmentHeart>();
        this.baseElevation = baseElevation;

        for(int i = 0; i< 3; i++){
            addCardFragment(new CardFragmentHeart());
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
        return CardFragmentHeart.getInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (CardFragmentHeart) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragmentHeart fragment) {
        fragments.add(fragment);
    }

}
