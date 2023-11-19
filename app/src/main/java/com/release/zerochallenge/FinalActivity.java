package com.release.zerochallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FinalActivity extends AppCompatActivity {

    TextView tv_main;
    TextView tv_sub;
    ImageView season;

    private FirebaseFirestore db;

    private ZeroUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();

        Day day = (Day) intent.getSerializableExtra("day");
        tv_main = findViewById(R.id.main);
        TextView tv_date = findViewById(R.id.date);
        tv_sub = findViewById(R.id.main2);
        MaterialCardView back = findViewById(R.id.back);
        MaterialCardView next = findViewById(R.id.next);


        setText(day);
        season = findViewById(R.id.season);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, day.year);
        calendar.set(Calendar.MONTH, day.month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day.day);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.KOREAN);
        tv_date.setText(format.format(date));
        setImage(day.month);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        getUser();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null){
                    save(calendar, day);
                }
            }
        });

    }

    void save(Calendar calendar, Day day){
        String y = String.valueOf(calendar.get(Calendar.YEAR));
        String m = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String d = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        Year year;
        Month month;

        if(user.years.containsKey(y)){
            year = user.years.get(y);
            if(year.months.containsKey(m)){
                month = year.months.get(m);
            }else{
                month = new Month();
            }
        }else{
            year = new Year();
            month = new Month();
        }
        month.addDay(d, day);
        year.addMonth(m, month);
        user.addYear(y, year);
        update(user, day);
        LoadingView loadingView = new LoadingView(FinalActivity.this);
        loadingView.show("저장 중..");

        db.collection("users").document(UserInfo.getID(FinalActivity.this)).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingView.stop();
                finish();
            }
        });
    }

    public void update(ZeroUser user, Day day){

        user.achieve_count++;
        for(String s : day.reduce.keySet()){
            int type = Integer.parseInt(s);
            if(type == Type.REDUCE_ALL){
                for(int i = 0; i <= Type.REDUCE_ALL; i++){
                    user.addReduce(i);
                }
            }else{
                user.addReduce(type);
            }
        }


        for(String s : day.save.keySet()){
            int type = Integer.parseInt(s);
            if(type == Type.SAVE_ALL){
                for(int i = 0; i <= Type.SAVE_ALL; i++){
                    user.addSave(i);
                }
            }else{
                user.addSave(type);
            }
        }
    }

    public void getUser(){
        db.collection("users").document(UserInfo.getID(FinalActivity.this)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(ZeroUser.class);
            }
        });
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


    void setText(Day day){

        ArrayList<String> success = new ArrayList<>();
        success.add("너는 역시 대단한 사람이구나!\n너의 미래가 기대 돼");
        success.add("이제 부자가 되기까지\n정말 한발자국 남았네");
        success.add("뿌듯하지?\n나도 뿌듯해");
        success.add("내일도 오늘처럼만!\n힘내보자");
        success.add("성장한 나 자신이 느껴지지 않아?\n키도 쑥쑥 크는 것 같아ㅎ");

        ArrayList<String> fail = new ArrayList<>();
        fail.add("괜찮아. 오늘도 넌 수고했고,\n내일의 너가 더 잘할거야.");
        fail.add("오늘은 따듯한 물로 샤워하고\n포근한 이불로 덮어보자");
        fail.add("조금만 더 힘내보자");
        fail.add("부자가 되는 너를 상상해보자");
        fail.add("사랑해 나 자신.\n내일은 조금 더 힘내보자");

        int random = new Random().nextInt(5);

        if(day.reduce.isEmpty() && day.save.isEmpty()){
            tv_main.setText(fail.get(random));
            tv_sub.setText("오늘은 챌린지를 성공하지 못했어요.");
        }else{
            tv_main.setText(success.get(random));
            if(!day.reduce.isEmpty() && !day.save.isEmpty()){
                tv_sub.setText("오늘은 2개 중 2개의 챌린지를\n완료했어요");
            }else{
                tv_sub.setText("오늘은 2개 중 1개의 챌린지를\n완료했어요");
            }
        }
    }
}