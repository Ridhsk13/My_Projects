package com.noesis.bhurmalaidanmal;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by noesisimac on 9/23/16.
 */
public class FragGridViewAdapter extends BaseAdapter {

    private Context context;
    private  final String[] text;
    private final int[] imageId;


    // ,int[] imageid to add in adapter constructor
    public FragGridViewAdapter(Context c,String[] text,int[] imageid)
    {
        context=c;
        this.text=text;
        this.imageId=imageid;
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(context);
            Typeface normal = Typeface.createFromAsset(context.getAssets(), "fonts/CenturySchoolbook.otf");
            Typeface normal_bold = Typeface.createFromAsset(context.getAssets(), "fonts/CenturySchoolbook-Bold.otf");

            grid = inflater.inflate(R.layout.fragment_grid_item_layout, null);
            TextView textView = (TextView) grid.findViewById(R.id.frag_grid_text);
            Button viewMore_btn=(Button) grid.findViewById(R.id.frag_grid_button);
            viewMore_btn.setTypeface(normal);
            textView.setText(text[i]);
            textView.setTypeface(normal_bold);
           // textView.setCompoundDrawablesWithIntrinsicBounds(0,imageId[i],0,0);
            textView.setBackgroundResource(imageId[i]);
/*            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_img);
            textView.setText(text[i]);
            imageView.setImageResource(imageId[i]);*/
        } else {
            grid = (View) view;
        }

        return grid;    }
}
