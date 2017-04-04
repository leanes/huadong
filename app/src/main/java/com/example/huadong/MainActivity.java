package com.example.huadong;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.huadong.adapter.CategoryAdapter;
import com.example.huadong.bean.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
    private ViewPager mViewPager;	//下方的可横向拖动的控件
    private ArrayList<View> mViews;//用来存放下方滚动的layout(layout_1,layout_2,layout_3)

    LocalActivityManager manager = null;

    private RadioGroup myRadioGroup;

    private int _id = 1000;

    private LinearLayout layout,titleLayout;

    private TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        getTitleInfo();
        initGroup();
        iniListener();
        iniVariable();

        mViewPager.setCurrentItem(0);

    }
   // private List<Map<String, Object>> titleList = new ArrayList<Map<String,Object>>();
    private List<HashMap<String , Category>> categories = new ArrayList<HashMap<String, Category>>() ;
    private void getTitleInfo(){
        String[] categoryArray = getResources().getStringArray(R.array.categories) ;
        for (int i = 0 ; i < categoryArray.length ; i ++) {
            String[] temp = categoryArray[i].split("\\|");
            if (temp.length == 3) {
                int cid = Integer.parseInt(temp[0]);
                String name = temp[1];
                String title = temp[2];
                Category type = new Category(cid, name, title);
                HashMap<String, Category> hashMap = new HashMap<String, Category>();
                hashMap.put("category_title", type);
                categories.add(hashMap);
            }
        }

    }

    private void initGroup(){
        titleLayout = (LinearLayout) findViewById(R.id.title_lay);
        layout = (LinearLayout) findViewById(R.id.lay);

        //mImageView = new ImageView(this);

        mImageView = (ImageView)findViewById(R.id.img1);
        mHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);

        mViewPager = (ViewPager)findViewById(R.id.pager);

        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams( new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(myRadioGroup);
        for (int i = 0; i <categories.size(); i++) {
            HashMap<String, Category> map = categories.get(i);
            RadioButton radio = new RadioButton(this);

            radio.setBackgroundResource(R.drawable.radiobtn_selector);
            radio.setButtonDrawable(android.R.color.transparent);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setGravity(Gravity.CENTER);
            radio.setPadding(30, 20, 30, 18);
            //radio.setPadding(left, top, right, bottom)
            radio.setId(_id+i);
            radio.setText(map.get("category_title")+"");
            radio.setTextColor(Color.WHITE);
            radio.setTextSize(20);
            if (i == 0) {
                radio.setChecked(true);
                int itemWidth = (int) radio.getPaint().measureText(map.get("category_title")+"");
                mImageView.setLayoutParams(new  LinearLayout.LayoutParams(itemWidth+radio.getPaddingLeft()+radio.getPaddingRight(),4));
            }
            myRadioGroup.addView(radio);
        }
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Map<String, Object> map = (Map<String, Object>) group.getChildAt(checkedId).getTag();
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                  RadioButton rb = (RadioButton)findViewById(radioButtonId);
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation;
                translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setDuration(300);

                mImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
                mViewPager.setCurrentItem(radioButtonId-_id);//让下方ViewPager跟随上面的HorizontalScrollView切换
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前蓝色横条距离左边的距离
                mHorizontalScrollView.smoothScrollTo((int)mCurrentCheckedRadioLeft-(int)getResources().getDimension(R.dimen.rdo2), 0);

                mImageView.setLayoutParams(new  LinearLayout.LayoutParams(rb.getRight()-rb.getLeft(),4));

            }
        });
    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }


    private void iniVariable() {
        mViews = new ArrayList<View>();
        for (int i = 0; i < categories.size(); i++) {
            Intent intent1 = new Intent(this,Categoryaivity.class);
            intent1.putExtra("newsType", categories.get(i).get("category_title").getName());
            mViews.add(getView("View"+i, intent1));
        }
        mViewPager.setAdapter(new MyPagerAdapter());//设置ViewPager的适配器
    }




    private void iniListener() {
        // TODO Auto-generated method stub
        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
    }


    /**
     * ViewPager的适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View v, int position, Object obj) {
            // TODO Auto-generated method stub
            ((ViewPager)v).removeView(mViews.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mViews.size();
        }

        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager)v).addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub
        }

    }
    /**
     * ViewPager的PageChangeListener(页面改变的监听器)
     */
    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }
        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            RadioButton radioButton = (RadioButton) findViewById(_id+position);
            radioButton.performClick();
        }
    }

}
