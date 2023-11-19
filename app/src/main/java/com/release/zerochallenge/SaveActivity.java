package com.release.zerochallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Locale;

public class SaveActivity extends AppCompatActivity {
    private static final int[] mSaveTag = {Type.SAVE_RESALE , Type.SAVE_APP, Type.SAVE_PIGGY, Type.SAVE_ETC, Type.SAVE_ALL};
    int save = 0;

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
    TextView tv_subtitle;
    LinearLayout saveView;
    EditText ed_save;

    ImageView season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Intent intent = getIntent();
        day = (Day) intent.getSerializableExtra("day");
        save = 0;

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
        tv_subtitle = findViewById(R.id.main2);
        saveView = findViewById(R.id.saveview);
        ed_save = findViewById(R.id.ed_save);
        season = findViewById(R.id.season);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, day.year);
        calendar.set(Calendar.MONTH, day.month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day.day);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.KOREAN);
        tv_date.setText(format.format(date));
        setImage(day.month);

        ct = new MaterialCardView[Type.SAVE_COUNT];
        tv = new TextView[Type.SAVE_COUNT];
        iv = new ImageView[Type.SAVE_COUNT];
        ct_tv = new TextView[Type.SAVE_COUNT];

        {
            ct[0] = findViewById(R.id.ct_1);
            ct[1] = findViewById(R.id.ct_2);
            ct[2] = findViewById(R.id.ct_3);
            ct[3] = findViewById(R.id.ct_4);
            ct[4] = findViewById(R.id.ct_5);

            ct_tv[0] = findViewById(R.id.ct_tv1);
            ct_tv[1] = findViewById(R.id.ct_tv2);
            ct_tv[2] = findViewById(R.id.ct_tv3);
            ct_tv[3] = findViewById(R.id.ct_tv4);
            ct_tv[4] = findViewById(R.id.ct_tv5);

            tv[0] = findViewById(R.id.tv_1);
            tv[1] = findViewById(R.id.tv_2);
            tv[2] = findViewById(R.id.tv_3);
            tv[3] = findViewById(R.id.tv_4);
            tv[4] = findViewById(R.id.tv_5);

            iv[0] = findViewById(R.id.img_1);
            iv[1] = findViewById(R.id.img_2);
            iv[2] = findViewById(R.id.img_3);
            iv[3] = findViewById(R.id.img_4);
            iv[4] = findViewById(R.id.img_5);
        }

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save = 1;
                tv_main.setText("나는 오늘 0원 챌린지 ‘모으기’ 에 성공 했어");
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
                save = 2;
                tv_main.setText("나는 오늘 0원 챌린지 ‘모으기’ 에 실패 했어");
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

                if(save == 0){
                    Toast.makeText(SaveActivity.this, "성공 / 실패 여부를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (save == 1) {
                    //성공
                    tv_next.setTextColor(Color.parseColor("#9C5353"));
                    next.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
                    next.setStrokeWidth(6);
                    next.setStrokeColor(Color.parseColor("#786557"));
                    show();
                    save = 3;
                }else if (save == 3) {
                    if(day.save.isEmpty()){
                        Toast.makeText(SaveActivity.this, "성공한 카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }else if(ed_save.getText().length() == 0){
                        Toast.makeText(SaveActivity.this, "모은 금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        //통과
                        day.saveMoney = Integer.parseInt(ed_save.getText().toString());
                        Intent intent = new Intent(SaveActivity.this, FinalActivity.class);
                        intent.putExtra("day", day);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    //실패
                    day.save.clear();
                    Intent intent = new Intent(SaveActivity.this, FinalActivity.class);
                    intent.putExtra("day", day);
                    startActivity(intent);
                    finish();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(save != 3){
                    Intent intent = new Intent(SaveActivity.this, ReduceActivity.class);
                    intent.putExtra("day", day);
                    startActivity(intent);
                    finish();
                }else{
                    hide();
                    btnView.setVisibility(View.VISIBLE);
                    tv_subtitle.setVisibility(View.VISIBLE);
                    save = 0;
                }
            }
        });


        if(!day.save.isEmpty()){
            save = 3;
            tv_main.setText("나는 오늘 0원 챌린지 ‘모으기’ 에 성공 했어");
            tv_next.setTextColor(Color.WHITE);
            next.setCardBackgroundColor(Color.parseColor("#786557"));
            next.setStrokeWidth(0);
            ed_save.setText(day.saveMoney+"");

            clearBtn();
            tv_success.setTextColor(Color.WHITE);
            success.setCardBackgroundColor(Color.parseColor("#786557"));
            success.setStrokeWidth(0);
            show();
        }else{
            hide();
        }

        ed_save.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String num = editable.toString();
                if(num.length() > 0 && !day.save.isEmpty()){
                    tv_next.setTextColor(Color.WHITE);
                    next.setCardBackgroundColor(Color.parseColor("#786557"));
                    next.setStrokeWidth(0);
                }else{
                    tv_next.setTextColor(Color.parseColor("#9C5353"));
                    next.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
                    next.setStrokeWidth(6);
                    next.setStrokeColor(Color.parseColor("#786557"));
                }
            }
        });
    }

    void clear(){
        int size = Type.SAVE_COUNT;

        for(int i = 0; i < size; i++){
            tv[i].setTextColor(Color.parseColor("#9C5353"));
            ct[i].setCardBackgroundColor(Color.parseColor("#FFFBF3"));
            ct[i].setStrokeWidth(4);
            ct[i].setStrokeColor(Color.parseColor("#786557"));
            iv[i].setVisibility(View.INVISIBLE);
            ct[i].setTag(mSaveTag[i]);
        }
    }

    void show(){
        clear();
        btnView.setVisibility(View.GONE);
        tv_subtitle.setVisibility(View.INVISIBLE);

        HashMap<String, Boolean> type = day.save;

        category.setVisibility(View.VISIBLE);
        tv_sub.setVisibility(View.VISIBLE);
        successView.setVisibility(View.VISIBLE);
        saveView.setVisibility(View.VISIBLE);

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
        saveView.setVisibility(View.GONE);

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
        if(save != 3){
            super.onBackPressed();
            Intent intent = new Intent(SaveActivity.this, ReduceActivity.class);
            intent.putExtra("day", day);
            startActivity(intent);
            finish();
        }else{
            hide();
            btnView.setVisibility(View.VISIBLE);
            save = 0;
        }
    }


    public void onClick(View view) {
        int tag = (int) view.getTag();
        String key = String.valueOf(tag);
        HashMap<String, Boolean> type = day.save;

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
            if(type.size() == Type.SAVE_COUNT-2){
                clear();
                type.clear();
                type.put(String.valueOf(Type.SAVE_ALL), true);
                tv[Type.SAVE_ALL].setTextColor(Color.WHITE);
                ct[Type.SAVE_ALL].setCardBackgroundColor(Color.parseColor("#786557"));
                ct[Type.SAVE_ALL].setStrokeWidth(0);
                iv[Type.SAVE_ALL].setVisibility(View.VISIBLE);
            }else{
                if(tag == Type.SAVE_ALL){
                    clear();
                    type.clear();
                }else{
                    type.remove(String.valueOf(Type.SAVE_ALL));
                    tv[Type.SAVE_ALL].setTextColor(Color.parseColor("#9C5353"));
                    ct[Type.SAVE_ALL].setCardBackgroundColor(Color.parseColor("#FFFBF3"));
                    ct[Type.SAVE_ALL].setStrokeWidth(4);
                    ct[Type.SAVE_ALL].setStrokeColor(Color.parseColor("#786557"));
                    iv[Type.SAVE_ALL].setVisibility(View.INVISIBLE);
                }
                type.put(key, true);
                tv[tag].setTextColor(Color.WHITE);
                ct[tag].setCardBackgroundColor(Color.parseColor("#786557"));
                ct[tag].setStrokeWidth(0);
                iv[tag].setVisibility(View.VISIBLE);
            }

            if(ed_save.getText().length() > 0) {
                tv_next.setTextColor(Color.WHITE);
                next.setCardBackgroundColor(Color.parseColor("#786557"));
                next.setStrokeWidth(0);
            }
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