package com.ridhs.mydigicatlog;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

class InfiniteViewPager extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    int[] mResources;
    String[] title;

    public InfiniteViewPager(Context context, int[] mResources, String[] title) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mResources = mResources;
        this.title = title;
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.5f;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_viewpager_item, container, false);
        Typeface normal_bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/CenturySchoolbook-Bold.otf");

        final TextView textView = (TextView) itemView.findViewById(R.id.imageView);
        textView.setBackgroundResource(mResources[position]);
        textView.setText(title[position]);
        textView.setTypeface(normal_bold);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position == 0) {
                    //Toast.makeText(mContext,"Click Working",Toast.LENGTH_LONG).show();

                    String Title = "Metallic Ring";
                    String Ruppees = "5,000/-";
                    int Image = R.drawable.ring_1;
                    String Feature1 = "Gold Purity: 18kt";
                    String Feature2 = "Platinium Purity: NA";
                    String Feature3 = "Setting: Prong";
                    String SellerName = "Kalyan Jewelers";
                    String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";

                    int demo_img1 = R.drawable.thumbnail_ring1;
                    int demo_img2 = R.drawable.thumbnail_ring1;
                    int demo_img3 = R.drawable.thumbnail_ring1;
                    int demo_img4 = R.drawable.thumbnail_ring1;

                   /* Intent intent = new Intent(mContext.getApplicationContext(),JewelDesc.class);

                    intent.;
*/
                    mContext.startActivity(new Intent(mContext, JewelDesc.class).putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                            .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                            .putExtra("positionoflightboximg", position)
                            .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc).putExtra("anim id in", R.anim.down_in).putExtra("anim id out", R.anim.down_out)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    ((Activity)mContext).overridePendingTransition(R.anim.up_in, R.anim.up_out);
                }

                if (position == 1) {
                  //  Toast.makeText(mContext,"Click Working",Toast.LENGTH_LONG).show();

                    String Title = "Engagement Ring";
                    String Ruppees = "15,000/-";
                    int Image = R.drawable.ring_2;
                    String Feature1 = "Gold Purity: 15kt";
                    String Feature2 = "Platinium Purity: NA";
                    String Feature3 = "Setting: Prong";
                    String SellerName = "Chintamani";
                    int demo_img1 = R.drawable.thumbnail_ring2;
                    int demo_img2 = R.drawable.thumbnail_ring2;
                    int demo_img3 = R.drawable.thumbnail_ring2;
                    int demo_img4 = R.drawable.thumbnail_ring2;
                    String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";

                   /* Intent intent = new Intent(mContext.getApplicationContext(),JewelDesc.class);

                    intent.;
*/
                    mContext.startActivity(new Intent(mContext, JewelDesc.class).putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                            .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                            .putExtra("positionoflightboximg",position)
                            .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc).putExtra("anim id in",R.anim.down_in).putExtra("anim id out",R.anim.down_out)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    ((Activity)mContext).overridePendingTransition(R.anim.up_in, R.anim.up_out);
                }

                if (position == 2) {
                 //   Toast.makeText(mContext,"Click Working",Toast.LENGTH_LONG).show();

                    String Title = "Wedding Ring";
                    String Ruppees = "17,000/-";
                    int Image = R.drawable.ring_3;
                    String Feature1 = "Gold Purity: 5kt";
                    String Feature2 = "Platinium Purity: 20%";
                    String Feature3 = "Setting: Prong";
                    String SellerName = "Chintamani";
                    int demo_img1 = R.drawable.thumbnail_ring3;
                    int demo_img2 = R.drawable.thumbnail_ring3;
                    int demo_img3 = R.drawable.thumbnail_ring3;
                    int demo_img4 = R.drawable.thumbnail_ring3;
                    String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                   /* Intent intent = new Intent(mContext.getApplicationContext(),JewelDesc.class);

                    intent.;
*/
                    mContext.startActivity(new Intent(mContext, JewelDesc.class).putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                            .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                            .putExtra("positionoflightboximg",position)
                            .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc).putExtra("anim id in",R.anim.down_in).putExtra("anim id out",R.anim.down_out)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    ((Activity)mContext).overridePendingTransition(R.anim.up_in, R.anim.up_out);
                }

                if (position == 3) {
                 //   Toast.makeText(mContext,"Click Working",Toast.LENGTH_LONG).show();

                    String Title = "Knuckle Ring";
                    String Ruppees = "3,999/-";
                    int Image = R.drawable.ring_4;
                    String Feature1 = "Gold Purity: 5kt";
                    String Feature2 = "Platinium Purity: 80%";
                    String Feature3 = "Setting: Prong";
                    String SellerName = "Chintamani";
                    String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                    int demo_img1 = R.drawable.thumbnail_ring4;
                    int demo_img2 = R.drawable.thumbnail_ring4;
                    int demo_img3 = R.drawable.thumbnail_ring4;
                    int demo_img4 = R.drawable.thumbnail_ring4;


                   /* Intent intent = new Intent(mContext.getApplicationContext(),JewelDesc.class);

                    intent.;
*/
                    mContext.startActivity(new Intent(mContext, JewelDesc.class).putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                            .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                            .putExtra("positionoflightboximg",position)
                            .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc).putExtra("anim id in",R.anim.down_in).putExtra("anim id out",R.anim.down_out)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    ((Activity)mContext).overridePendingTransition(R.anim.up_in, R.anim.up_out);
                }


                if (position == 4) {
                  //  Toast.makeText(mContext,"Click Working",Toast.LENGTH_LONG).show();

                    String Title = "Mourning Ring";
                    String Ruppees = "3,999/-";
                    int Image = R.drawable.ring_5;
                    String Feature1 = "Gold Purity: 5kt";
                    String Feature2 = "Platinium Purity: 80%";
                    String Feature3 = "Setting: Prong";
                    String SellerName = "Chintamani";
                    int demo_img1 = R.drawable.thumbnail_ring5;
                    int demo_img2 = R.drawable.thumbnail_ring5;
                    int demo_img3 = R.drawable.thumbnail_ring5;
                    int demo_img4 = R.drawable.thumbnail_ring5;
                    String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";



                   /* Intent intent = new Intent(mContext.getApplicationContext(),JewelDesc.class);

                    intent.;
*/
                    mContext.startActivity(new Intent(mContext, JewelDesc.class).putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                            .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                            .putExtra("positionoflightboximg", position)
                            .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc).putExtra("anim id in", R.anim.down_in).putExtra("anim id out", R.anim.down_out)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    ((Activity)mContext).overridePendingTransition(R.anim.up_in, R.anim.up_out);


                }

                if (position == 5) {
                    String Title = "Birthstone Ring";
                    String Ruppees = "7,999/-";
                    int Image = R.drawable.ring_6;
                    String Feature1 = "Gold Purity: 5kt";
                    String Feature2 = "Platinium Purity: 80%";
                    String Feature3 = "Setting: Prong";
                    String SellerName = "Chintamani";
                    int demo_img1 = R.drawable.thumbnail_ring6;
                    int demo_img2 = R.drawable.thumbnail_ring6;
                    int demo_img3 = R.drawable.thumbnail_ring6;
                    int demo_img4 = R.drawable.thumbnail_ring6;
                    String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";



                   /* Intent intent = new Intent(mContext.getApplicationContext(),JewelDesc.class);

                    intent.;
*/
                    mContext.startActivity(new Intent(mContext, JewelDesc.class).putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                            .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                            .putExtra("positionoflightboximg",position)
                            .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc).putExtra("anim id in",R.anim.down_in).putExtra("anim id out",R.anim.down_out)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    ((Activity)mContext).overridePendingTransition(R.anim.up_in, R.anim.up_out);
                }




            }
        });


       /* textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}