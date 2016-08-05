package com.huangying.leftmoveview;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
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

    private List<Integer> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.listview);
        initImages();
        lv.setAdapter(new MoveViewAdapter());
    }

    private void initImages() {
        TypedArray taypedArray = getResources().obtainTypedArray(R.array.pic_drawables);
        for (int i = 0; i < taypedArray.length(); i++) {
            imgs.add(taypedArray.getResourceId(i, -1));
        }
        taypedArray.recycle();
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
                    doDeletAnim(finalConvertView, position);
                }
            });
            viewHolder.my_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doTopAnim(viewHolder, position);
                }
            });
            return convertView;
        }

        private void doTopAnim(ViewHolder viewHolder, final int position) {
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

        private void doDeletAnim(View finalConvertView, final int position) {
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
