package com.noesis.bhurmalaidanmal;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by noesisimac on 9/19/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Banner3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.banner3,container,false);

        return rootView;
    }
}
