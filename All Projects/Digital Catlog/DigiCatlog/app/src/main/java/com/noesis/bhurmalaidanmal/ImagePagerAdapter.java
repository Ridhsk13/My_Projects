package com.noesis.bhurmalaidanmal;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by noesisimac on 9/20/16.
 */
public class ImagePagerAdapter extends PagerAdapter {


    Activity activity;
    int imageArray[];
    String[] stringArray;

    public ImagePagerAdapter(Activity act, int[] imgArra) {
        imageArray = imgArra;
        activity = act;
    //    stringArray = stringArra;
    }

    public int getCount() {
        return imageArray.length;}

    public Object instantiateItem(View collection, int position) {
        LayoutInflater inflater = (LayoutInflater)collection.getContext
                ().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_pager, null);

        ImageView im=(ImageView) layout.findViewById(R.id.myimage);
        im.setImageResource(imageArray[position]);



        ((ViewPager) collection).addView(layout, 0);
        return layout;   }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);}

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);}

    @Override
    public Parcelable saveState() {
        return null; }}