package com.release.zerochallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class ReduceActivity extends AppCompatActivity{

    private static final int[] mReduceTag = {Type.REDUCE_COFFEE , Type.REDUCE_MEALS, Type.REDUCE_ALCOHOL, Type.REDUCE_TRANSPORTATION, Type.REDUCE_ETC, Type.REDUCE_ALL};
    int reduce = 0;

    LinearLayout category;
    LinearLayout successView;
    ImageView iv[];
    TextView tv_sub;
    MaterialCardView ct[];
    TextView ct_tv[];

    TextView tv[];
    TextView tv_main;

    MaterialCardView success;
    MaterialCardView fail;
    TextView tv_success;
    TextView tv_fail;
    MaterialCardView back;
    MaterialCardView next;
    Day day;

    RelativeLayout btnView;
    TextView tv_next;

    ImageView season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduce);

        Intent intent = getIntent();
        day = (Day) intent.getSerializableExtra("day");
        reduce = 0;

        success = findViewById(R.id.success);
        fail = findViewById(R.id.fail);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        category = findViewById(R.id.category);
        tv_sub = findViewById(R.id.tv_succ);
        successView = findViewById(R.id.successview);
        tv_main = findViewById(R.id.main);
        btnView = findViewById(R.id.btnview);
        TextView tv_date = findViewById(R.id.date);
        tv_next = findViewById(R.id.tv_next);
        tv_success = findViewById(R.id.tv_success);
        tv_fail = findViewById(R.id.tv_fail);
        season = findViewById(R.id.season);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, day.year);
        calendar.set(Calendar.MONTH, day.month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day.day);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.KOREAN);
        tv_date.setText(format.format(date));
        setImage(day.month);

        ct = new MaterialCardView[6];
        tv = new TextView[6];
        iv = new ImageView[6];
        ct_tv = new TextView[6];

        {
            ct[0] = findViewById(R.id.ct_1);
            ct[1] = findViewById(R.id.ct_2);
            ct[2] = findViewById(R.id.ct_3);
            ct[3] = findViewById(R.id.ct_4);
            ct[4] = findViewById(R.id.ct_5);
            ct[5] = findViewById(R.id.ct_6);

            ct_tv[0] = findViewById(R.id.ct_tv1);
            ct_tv[1] = findViewById(R.id.ct_tv2);
            ct_tv[2] = findViewById(R.id.ct_tv3);
            ct_tv[3] = findViewById(R.id.ct_tv4);
            ct_tv[4] = findViewById(R.id.ct_tv5);
            ct_tv[5] = findViewById(R.id.ct_tv6);

            tv[0] = findViewById(R.id.tv_1);
            tv[1] = findViewById(R.id.tv_2);
            tv[2] = findViewById(R.id.tv_3);
            tv[3] = findViewById(R.id.tv_4);
            tv[4] = findViewById(R.id.tv_5);
            tv[5] = findViewById(R.id.tv_6);

            iv[0] = findViewById(R.id.img_1);
            iv[1] = findViewById(R.id.img_2);
            iv[2] = findViewById(R.id.img_3);
            iv[3] = findViewById(R.id.img_4);
            iv[4] = findViewById(R.id.img_5);
            iv[5] = findViewById(R.id.img_6);
        }

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reduce = 1;
                tv_main.setText("나는 오늘 0원 챌린지 ‘줄이기’ 에 성공 했어");
                tv_next.setTextColor(Color.WHITE);
                next.setCardBackgroundColor(Color.parseColor("#786557"));
                next.setStrokeWidth(0);

                clearBtn();
                tv_success.setTextColor(Color.WHITE);
                success.setCardBackgroundColor(Color.parseColor("#786557"));
                success.setStrokeWidth(0);
            }
        });

        fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reduce = 2;
                tv_main.setText("나는 오늘 0원 챌린지 ‘줄이기’ 에 실패 했어");
                tv_next.setTextColor(Color.WHITE);
                next.setCardBackgroundColor(Color.parseColor("#786557"));
                next.setStrokeWidth(0);

                clearBtn();
                tv_fail.setTextColor(Color.WHITE);
                fail.setCardBackgroundColor(Color.parseColor("#786557"));
                fail.setStrokeWidth(0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(reduce == 0){
                    Toast.makeText(ReduceActivity.this, "성공 / 실패 여부를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (reduce == 1) {
                    //성공
                    tv_next.setTextColor(Color.parseColor("#9C5353"));
                    next.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
                    next.setStrokeWidth(6);
                    next.setStrokeColor(Color.parseColor("#786557"));
                    show();
                    reduce = 3;
                }else if (reduce == 3) {
                    if(day.reduce.isEmpty()){
                        Toast.makeText(ReduceActivity.this, "성공한 카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        //통과
                        Intent intent = new Intent(ReduceActivity.this, SaveActivity.class);
                        intent.putExtra("day", day);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    //실패
                    day.reduce.clear();
                    Intent intent = new Intent(ReduceActivity.this, SaveActivity.class);
                    intent.putExtra("day", day);
                    startActivity(intent);
                    finish();

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(reduce != 3){
                    finish();
                 }else{
                    hide();
                    btnView.setVisibility(View.VISIBLE);
                    reduce = 0;
                 }
            }
        });


        if(!day.reduce.isEmpty()){
            reduce = 3;
            tv_main.setText("나는 오늘 0원 챌린지 ‘줄이기’ 에 성공 했어");
            tv_next.setTextColor(Color.WHITE);
            next.setCardBackgroundColor(Color.parseColor("#786557"));
            next.setStrokeWidth(0);

            clearBtn();
            tv_success.setTextColor(Color.WHITE);
            success.setCardBackgroundColor(Color.parseColor("#786557"));
            success.setStrokeWidth(0);
            show();
        }else{
            hide();
        }

    }

    void clear(){
        int size = Type.REDUCE_COUNT;
        for(int i = 0; i < size; i++){
            tv[i].setTextColor(Color.parseColor("#9C5353"));
            ct[i].setCardBackgroundColor(Color.parseColor("#FFFBF3"));
            ct[i].setStrokeWidth(4);
            ct[i].setStrokeColor(Color.parseColor("#786557"));
            iv[i].setVisibility(View.INVISIBLE);
            ct[i].setTag(mReduceTag[i]);
        }
    }

    void show(){
        clear();
        btnView.setVisibility(View.GONE);

        HashMap<String, Boolean> type = day.reduce;

        category.setVisibility(View.VISIBLE);
        tv_sub.setVisibility(View.VISIBLE);
        successView.setVisibility(View.VISIBLE);

        for (String s : type.keySet()) {
            int tag = Integer.parseInt(s);
            tv[tag].setTextColor(Color.WHITE);
            ct[tag].setCardBackgroundColor(Color.parseColor("#786557"));
            ct[tag].setStrokeWidth(0);
            iv[tag].setVisibility(View.VISIBLE);
        }
    }

    void hide(){
        category.setVisibility(View.GONE);
        tv_sub.setVisibility(View.GONE);
        successView.setVisibility(View.GONE);
        clearBtn();
        tv_next.setTextColor(Color.parseColor("#9C5353"));
        next.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
        next.setStrokeWidth(6);
        next.setStrokeColor(Color.parseColor("#786557"));
    }

    void clearBtn(){
        tv_success.setTextColor(Color.parseColor("#9C5353"));
        success.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
        success.setStrokeWidth(6);
        success.setStrokeColor(Color.parseColor("#786557"));
        tv_fail.setTextColor(Color.parseColor("#9C5353"));
        fail.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
        fail.setStrokeWidth(6);
        fail.setStrokeColor(Color.parseColor("#786557"));
    }

    @Override
    public void onBackPressed() {
        if(reduce != 3){
            super.onBackPressed();
            finish();
        }else{
            hide();
            btnView.setVisibility(View.VISIBLE);
            reduce = 0;
        }
    }

    public void onClick(View view) {
        int tag = (int) view.getTag();
        String key = String.valueOf(tag);
        HashMap<String, Boolean> type = day.reduce;

        if(type.containsKey(key)) {
            type.remove(key);
            tv[tag].setTextColor(Color.parseColor("#9C5353"));
            ct[tag].setCardBackgroundColor(Color.parseColor("#FFFBF3"));
            ct[tag].setStrokeWidth(4);
            ct[tag].setStrokeColor(Color.parseColor("#786557"));
            iv[tag].setVisibility(View.INVISIBLE);
            if(type.isEmpty()){
                tv_next.setTextColor(Color.parseColor("#9C5353"));
                next.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
                next.setStrokeWidth(6);
                next.setStrokeColor(Color.parseColor("#786557"));
            }
        }else {
            if(type.size() == Type.REDUCE_COUNT-2){
                clear();
                type.clear();
                type.put(String.valueOf(Type.REDUCE_ALL), true);
                tv[Type.REDUCE_ALL].setTextColor(Color.WHITE);
                ct[Type.REDUCE_ALL].setCardBackgroundColor(Color.parseColor("#786557"));
                ct[Type.REDUCE_ALL].setStrokeWidth(0);
                iv[Type.REDUCE_ALL].setVisibility(View.VISIBLE);
            }else{
                if(tag == Type.REDUCE_ALL){
                    clear();
                    type.clear();
                }else{
                    type.remove(String.valueOf(Type.REDUCE_ALL));
                    tv[Type.REDUCE_ALL].setTextColor(Color.parseColor("#9C5353"));
                    ct[Type.REDUCE_ALL].setCardBackgroundColor(Color.parseColor("#FFFBF3"));
                    ct[Type.REDUCE_ALL].setStrokeWidth(4);
                    ct[Type.REDUCE_ALL].setStrokeColor(Color.parseColor("#786557"));
                    iv[Type.REDUCE_ALL].setVisibility(View.INVISIBLE);
                }
                type.put(key, true);
                tv[tag].setTextColor(Color.WHITE);
                ct[tag].setCardBackgroundColor(Color.parseColor("#786557"));
                ct[tag].setStrokeWidth(0);
                iv[tag].setVisibility(View.VISIBLE);
            }
            tv_next.setTextColor(Color.WHITE);
            next.setCardBackgroundColor(Color.parseColor("#786557"));
            next.setStrokeWidth(0);
        }
        System.out.println(type);
    }



    public void setImage(int month){
        if(month <= 2 || month == 12){
            season.setImageResource(R.drawable.winter);
        }
        else if(month <= 5){
            season.setImageResource(R.drawable.spring);
        } else if (month <= 8) {
            season.setImageResource(R.drawable.summer);
        }else if (month <= 11) {
            season.setImageResource(R.drawable.fall);
        }
    }



}