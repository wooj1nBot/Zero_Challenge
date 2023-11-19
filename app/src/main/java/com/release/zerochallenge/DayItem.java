package com.release.zerochallenge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DayItem extends LinearLayout {

    int day;
    //1. 커피 2.밥값 3.교통비 4.술값 5.기타 6.모든 곳
    int[] icons = {R.drawable.more_horiz_48px, R.drawable.more_horiz_48px, R.drawable.more_horiz_48px,R.drawable.more_horiz_48px, R.drawable.more_horiz_48px,R.drawable.more_horiz_48px};
    ArrayList<Integer> iconList;
    String etc;
    boolean isToday;
    TextView tv_day;
    ImageView iv1;
    ImageView iv2;
    ImageView more;
    View mother;

    public DayItem(Context context) {
        super(context);
        mother = init(context,  null);
    }

    public DayItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mother = init(context, attrs);
    }

    public DayItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mother = init(context, attrs);
    }

    private View init(Context context, AttributeSet attrs){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (attrs != null) {
            //attrs.xml에 정의한 스타일을 가져온다 즉 (attrs.xml에서 발생된 selected 속성이
            // 발생되어 private void setSelected(int select, boolean force)를 호출하게 된다.
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DayItem);
            day = a.getInteger(R.styleable.DayItem_day, 1);
            a.recycle(); // 이용이 끝났으면 recycle() 호출
        }
        return inflater.inflate(R.layout.item_day_layout,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_day = findViewById(R.id.day);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        more = findViewById(R.id.more);
        if(isToday){
            mother.setBackgroundColor(Color.parseColor("#FCEAEA"));
        }else {
            mother.setBackgroundColor(Color.parseColor("#FFFBF3"));
        }

    }

    public void setIconList(ArrayList<Integer> iconList) {
        this.iconList = iconList;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

}
