package com.huangying.leftmoveview;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

//    private int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
//            R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine,
//            R.drawable.ten, R.drawable.eleven, R.drawable.twelve, R.drawable.thirteen, R.drawable.fourteen,
//            R.drawable.fifteen, R.drawable.sixteen, R.drawable.seventeen, R.drawable.eighteen,
//            R.drawable.ninteen, R.drawable.twenty};

    private List<Integer> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lv = (ListView) View.inflate(this, R.layout.activity_main, null);
        setContentView(lv);
        initImages();
        lv.setAdapter(new MoveViewAdapter());
    }

    private void initImages() {
        imgs.add(R.drawable.one);
        imgs.add(R.drawable.two);
        imgs.add(R.drawable.three);
        imgs.add(R.drawable.four);
        imgs.add(R.drawable.five);
        imgs.add(R.drawable.six);
        imgs.add(R.drawable.seven);
        imgs.add(R.drawable.eight);
        imgs.add(R.drawable.nine);
        imgs.add(R.drawable.ten);
        imgs.add(R.drawable.eleven);
        imgs.add(R.drawable.twelve);
        imgs.add(R.drawable.thirteen);
        imgs.add(R.drawable.fourteen);
        imgs.add(R.drawable.fifteen);
        imgs.add(R.drawable.sixteen);
        imgs.add(R.drawable.seventeen);
        imgs.add(R.drawable.eighteen);
        imgs.add(R.drawable.ninteen);
        imgs.add(R.drawable.twenty);
    }

    class MoveViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(MainActivity.this, R.layout.list_item, null);
                viewHolder.mv = (MoveView) convertView.findViewById(R.id.mv);
                viewHolder.my_top = (ImageView) convertView.findViewById(R.id.my_top);
                viewHolder.my_delete = (ImageView) convertView.findViewById(R.id.my_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ImageView iv = new ImageView(MainActivity.this);

            iv.setImageResource(imgs.get(position));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    -1, dip2px(200));
            viewHolder.mv.removeAllViews();
            viewHolder.mv.setLayoutParams(params);
            viewHolder.mv.addView(iv);
            final View finalConvertView = convertView;
            viewHolder.my_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TranslateAnimation ta = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, -1.0f,
                            Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 0);
                    ta.setDuration(200);
                    finalConvertView.startAnimation(ta);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            imgs.remove(position);
                            notifyDataSetChanged();
                        }
                    }, 200);
                }
            });
            viewHolder.my_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TranslateAnimation ta = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, -1.0f);
                    ta.setDuration(200);
                    viewHolder.mv.startAnimation(ta);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            imgs.add(0, imgs.remove(position));
                            notifyDataSetChanged();
                        }
                    }, 200);
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        MoveView mv;
        ImageView my_top;
        ImageView my_delete;

    }

    private int dip2px(int dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
