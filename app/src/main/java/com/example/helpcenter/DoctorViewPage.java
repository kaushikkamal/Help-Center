package com.example.helpcenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DoctorViewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_page);

        Intent i = getIntent();
        String from = i.getStringExtra("from");
        if (from.equals("ent")) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, this));
            ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
            fragmentCardShadowTransformer.enableScaling(true);

            viewPager.setAdapter(pagerAdapter);
            viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
            viewPager.setOffscreenPageLimit(3);
        }
        else if (from.equals("bone")) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            CardFragmentPagerAdapterBone pagerAdapter = new CardFragmentPagerAdapterBone(getSupportFragmentManager(), dpToPixels(2, this));
            ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
            fragmentCardShadowTransformer.enableScaling(true);

            viewPager.setAdapter(pagerAdapter);
            viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
            viewPager.setOffscreenPageLimit(3);
        }
        else if (from.equals("nerve")) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            CardFragmentPagerAdapterNerve pagerAdapter = new CardFragmentPagerAdapterNerve(getSupportFragmentManager(), dpToPixels(2, this));
            ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
            fragmentCardShadowTransformer.enableScaling(true);

            viewPager.setAdapter(pagerAdapter);
            viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
            viewPager.setOffscreenPageLimit(3);
        }
        else if (from.equals("heart")) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            CardFragmentPagerAdapterHeart pagerAdapter = new CardFragmentPagerAdapterHeart(getSupportFragmentManager(), dpToPixels(2, this));
            ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
            fragmentCardShadowTransformer.enableScaling(true);

            viewPager.setAdapter(pagerAdapter);
            viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
            viewPager.setOffscreenPageLimit(3);
        }
        else if (from.equals("skin")) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            CardFragmentPagerAdapterSkin pagerAdapter = new CardFragmentPagerAdapterSkin(getSupportFragmentManager(), dpToPixels(2, this));
            ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
            fragmentCardShadowTransformer.enableScaling(true);

            viewPager.setAdapter(pagerAdapter);
            viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
            viewPager.setOffscreenPageLimit(3);
        }
    }
    /**
     * Change value in dp to pixels
     * @param dp
     * @param context
     */
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}