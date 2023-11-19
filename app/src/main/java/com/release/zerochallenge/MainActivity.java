package com.release.zerochallenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private GridView gridview = null;
    private String uid;
    private FirebaseFirestore db;
    private ZeroUser user;

    private TextView tv_big_month, tv_month_str, tv_year, tv_year_sum, tv_month, tv_month_sum;

    private Calendar current;

    private IconView[] iconView;
    private ImageView[] icons;
    private CardView iconBubble;
    private CardView bigBubble;
    private AdView mAdView;

    private CardView achieve_view;
    private ImageView iv_achieve;
    private TextView tv_title;
    private TextView tv_subtitle;

    public static final int RESULT_CHALLENGE = 1276;
    public static final int REQUEST_CHALLENGE = 1279;
    public boolean isAdOpen = false;

    private Uri uri;

    private Dialog dialog;
    private AdView adView;
    private MaterialCardView cancel;
    private MaterialCardView quit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = UserInfo.getID(MainActivity.this);
        if(uid.equals("")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.activity_main);
            tv_big_month = findViewById(R.id.textView);
            tv_month_str = findViewById(R.id.tv_month_str);
            tv_year = findViewById(R.id.tv_year);
            tv_year_sum = findViewById(R.id.tv_year_sum);
            tv_month = findViewById(R.id.tv_month);
            tv_month_sum = findViewById(R.id.tv_month_sum);

            db = FirebaseFirestore.getInstance();
            gridview = findViewById(R.id.gridview);

            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            mAdView = findViewById(R.id.adView);

            ImageView prev = findViewById(R.id.prev);
            ImageView next = findViewById(R.id.next);

            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    current.add(Calendar.MONTH, -1);
                    setDate(current);
                    iconBubble.setVisibility(View.GONE);
                    bigBubble.setVisibility(View.GONE);
                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    current.add(Calendar.MONTH, 1);
                    setDate(current);
                    iconBubble.setVisibility(View.GONE);
                    bigBubble.setVisibility(View.GONE);
                }
            });

            iconView = new IconView[8];
            icons = new ImageView[8];

            iconView[0] = new IconView(findViewById(R.id.icon1), findViewById(R.id.tv1), true, Type.REDUCE_COFFEE);
            iconView[1] = new IconView(findViewById(R.id.icon2), findViewById(R.id.tv2), true, Type.REDUCE_MEALS);
            iconView[2] = new IconView(findViewById(R.id.icon3), findViewById(R.id.tv3), true, Type.REDUCE_ALCOHOL);
            iconView[3] = new IconView(findViewById(R.id.icon4), findViewById(R.id.tv4), true, Type.REDUCE_TRANSPORTATION);
            iconView[4] = new IconView(findViewById(R.id.icon5), findViewById(R.id.tv5), false, Type.SAVE_RESALE);
            iconView[5] = new IconView(findViewById(R.id.icon6), findViewById(R.id.tv6), false, Type.SAVE_APP);
            iconView[6] = new IconView(findViewById(R.id.icon7), findViewById(R.id.tv7), false, Type.SAVE_PIGGY);
            iconView[7] = new IconView(findViewById(R.id.icon8), findViewById(R.id.tv8), false, Type.SAVE_ETC);

            icons[0] = findViewById(R.id.iv1);
            icons[1] = findViewById(R.id.iv2);
            icons[2] = findViewById(R.id.iv3);
            icons[3] = findViewById(R.id.iv4);
            icons[4] = findViewById(R.id.iv5);
            icons[5] = findViewById(R.id.iv6);
            icons[6] = findViewById(R.id.iv7);
            icons[7] = findViewById(R.id.iv8);

            bigBubble = findViewById(R.id.big_bubble);
            Button current_btn = findViewById(R.id.button);
            current_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bigBubble.getVisibility() == View.VISIBLE){
                        bigBubble.setVisibility(View.GONE);
                        view.setTag(false);
                    }else{
                        bigBubble.setVisibility(View.VISIBLE);
                        view.setTag(true);
                    }
                    iconBubble.setVisibility(View.GONE);
                }
            });
            iconBubble = findViewById(R.id.bubble);

            Button award = findViewById(R.id.award);
            award.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AchieveActivity.class);
                    startActivity(intent);
                }
            });

            achieve_view = findViewById(R.id.achieve);
            iv_achieve = findViewById(R.id.image);
            tv_title = findViewById(R.id.title);
            tv_subtitle = findViewById(R.id.subtitle);

            makeDialog();

            current = Calendar.getInstance();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        uid = UserInfo.getID(MainActivity.this);
        if(uid.equals("")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            if (preferences.getBoolean("isFirst", true)) {
                showHelpDialog();
            }
            getData();
        }
    }

    @Override
    public void onBackPressed() {
        if(user.isRemoveAdd){
            showQuitDialog(true);
        }else{
            showQuitDialog(isAdOpen);
        }
    }

    public void getData(){
        setDate(current);

        db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if(task.isSuccessful() && task.getResult().exists()){
                      user = task.getResult().toObject(ZeroUser.class);
                      setDate(current);
                      checkAchieve();
                      if(user.isRemoveAdd){
                          achieve_view.setVisibility(View.VISIBLE);
                          mAdView.setVisibility(View.GONE);
                          setAchieve();
                      }else{
                          achieve_view.setVisibility(View.GONE);
                          mAdView.setVisibility(View.VISIBLE);
                          AdRequest adRequest = new AdRequest.Builder().build();
                          mAdView.loadAd(adRequest);
                      }
                 }
            }
        });
    }



    public void setAchieve(){
        Map<String, Boolean> map = user.achieve;
        if(map.isEmpty()){
            tv_title.setText("해결한 도전과제 없음");
            tv_subtitle.setText("도전과제를 해결해보세요!");
            iv_achieve.setImageResource(R.drawable.box);
            achieve_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AchieveActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Random random = new Random();
            int count = random.nextInt(map.size()) + 1;
            Iterator<String> key = map.keySet().iterator();
            String s = "";
            for(int i = 0; i < count; i++){
                s = key.next();
            }
            Achieve.Item item = Achieve.getItem(s);
            String format;

            if(item.type == Type.TYPE_ALL){
                format = String.format("무지출 %d회 달성", item.number);
            }else {
                if(item.isReduce){
                    format = String.format("%s 줄이기 %d회 달성", Type.REDUCE_NAME[item.type], item.number);
                }else{
                    format = String.format("%s 모으기 %d회 달성", Type.SAVE_NAME[item.type], item.number);
                }
            }
            achieve_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Achieve.Item> items = new ArrayList<>();
                    items.add(item);
                    showDialog(items, 0);
                }
            });

            tv_title.setText(item.name);
            tv_subtitle.setText(format);
            iv_achieve.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(MainActivity.this).load(item.resID).into(iv_achieve);
        }
    }

    public void setDate(Calendar c){
        Month month = null;
        Year year = null;

        for(int j = 0; j < 8; j++){
            iconView[j].reset();
            if(user != null) {
                iconView[j].setImage();
            }
        }

        if(user != null) {
            for (String key : user.achieve_reduce.keySet()) {
                int k = Integer.parseInt(key);
                int count = user.achieve_reduce.get(key);
                if(k == Type.REDUCE_ALL) continue;
                if (k == Type.REDUCE_ETC) {
                    iconView[7].setCount(count);
                } else {
                    iconView[k].setCount(count);
                }
            }
            for (String key : user.achieve_save.keySet()) {
                int k = Integer.parseInt(key);
                int count = user.achieve_save.get(key);
                if(k == Type.SAVE_ALL) continue;
                if (k == Type.SAVE_ETC) {
                    iconView[7].setCount(count);
                } else {
                    iconView[k + 4].setCount(count);
                }
            }
        }


        ArrayList<Day> days = new ArrayList<>();
        String y = String.valueOf(c.get(Calendar.YEAR));
        tv_year.setText(y);

        if(user != null && user.years.containsKey(y)){
            year = user.years.get(y);
            int sum = 0;
            for(Month m : year.months.values()){
                for(Day d : m.days.values()){
                    sum += d.saveMoney;
                }
            }
            tv_year_sum.setText(String.valueOf(sum));
        }else{
            tv_year_sum.setText("0");
        }

        int today = c.get(Calendar.DAY_OF_MONTH);

        c.set(Calendar.DAY_OF_MONTH, 1);
        int start = c.get(Calendar.DAY_OF_WEEK);

        c.add(Calendar.MONTH, -1);
        int prev = c.getActualMaximum(Calendar.DAY_OF_MONTH) - (start - 2);
        int m = c.get(Calendar.MONTH) + 1;

        y = String.valueOf(c.get(Calendar.YEAR));
        if(user != null && user.years.containsKey(y)) {
            year = user.years.get(y);
        }else{
            year = null;
        }

        if(year != null && year.months.containsKey(String.valueOf(m))){
            month = year.months.get(String.valueOf(m));
        }else{
            month = null;
        }

        for(int i = prev; i <= c.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            if(month != null && month.days.containsKey(String.valueOf(i))){
                Day day = month.days.get(String.valueOf(i));
                days.add(day);
            }else{
                days.add(new Day(c.get(Calendar.YEAR), m, i));
            }
        }

        c.add(Calendar.MONTH, 1);
        m = c.get(Calendar.MONTH) + 1;
        c.set(Calendar.DAY_OF_MONTH, today);
        y = String.valueOf(c.get(Calendar.YEAR));

        if(user != null && user.years.containsKey(y)) {
            year = user.years.get(y);
        }else{
            year = null;
        }

        if(year != null && year.months.containsKey(String.valueOf(m))){
            month = year.months.get(String.valueOf(m));
        }else{
            month = null;
        }

        String mMonth = "";
        if(m > 9){
            mMonth = String.valueOf(m);
        }else{
            mMonth = "0" + m;
        }
        tv_month.setText(mMonth + "월");
        tv_big_month.setText(mMonth);
        tv_month_str.setText(getMonth(m));


        if(year != null && year.months.containsKey(String.valueOf(m))){
            month = year.months.get(String.valueOf(m));
            int sum = 0;
            for(Day d : month.days.values()){
                sum += d.saveMoney;
            }
            tv_month_sum.setText(String.valueOf(sum));
        }else{
            tv_month_sum.setText("0");
        }


        for(int i = 1; i <= c.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            if(month != null && month.days.containsKey(String.valueOf(i))){
                Day day = month.days.get(String.valueOf(i));
                days.add(day);
            }else{
                days.add(new Day(c.get(Calendar.YEAR), m, i));
            }
        }

        int size = c.getActualMaximum(Calendar.DAY_OF_MONTH) + (start-1);
        int next;
        if(size > 35) {
            next = 42 - size;
        }else{
            next = 35 - size;
        }

        c.add(Calendar.MONTH, 1);
        m = c.get(Calendar.MONTH) + 1;

        y = String.valueOf(c.get(Calendar.YEAR));
        if(user != null && user.years.containsKey(y)) {
            year = user.years.get(y);
        }else{
            year = null;
        }

        if(year != null && year.months.containsKey(String.valueOf(m))){
            month = year.months.get(String.valueOf(m));
        }else{
            month = null;
        }

        for(int i = 1; i <= next; i++){
            if(month != null && month.days.containsKey(String.valueOf(i))){
                days.add(month.days.get(String.valueOf(i)));
            }else{
                days.add(new Day(c.get(Calendar.YEAR), m, i));
            }
        }


        GridViewAdapter adapter = new GridViewAdapter(days);
        gridview.setAdapter(adapter);
        c.add(Calendar.MONTH, -1);
    }

    private String getMonth(int m){
        switch (m){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";

        }
        return "January";
    }

    public void onClick(View v){
        if(v.getId() == R.id.parent){
            if(iconBubble.getVisibility() == View.VISIBLE){
                iconBubble.setVisibility(View.GONE);
            }
            if(bigBubble.getVisibility() == View.VISIBLE){
                bigBubble.setVisibility(View.GONE);
            }
        }
    }

    public void checkAchieve(){
        ArrayList<Achieve.Item> list = Achieve.check(user);
        if(list.size() > 0){
            showDialog(list, 0);
        }
    }

    public void showHelpDialog(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mdialog = inflater.inflate(R.layout.help_dialog, null);
        AlertDialog.Builder buider = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        buider.setView(mdialog);
        buider.setCancelable(true);
        Dialog dialog = buider.create();
        dialog.show();
        MaterialCardView start = mdialog.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirst", false);
                editor.apply();
            }
        });
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void makeDialog(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mdialog = inflater.inflate(R.layout.app_quit_dialog, null);
        AlertDialog.Builder buider = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        buider.setView(mdialog);
        buider.setCancelable(true);
        dialog = buider.create();
        adView = mdialog.findViewById(R.id.adView);
        cancel = mdialog.findViewById(R.id.cancel);
        quit = mdialog.findViewById(R.id.quit);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void showQuitDialog(boolean isShow){
        dialog.show();

        if(isShow){
            adView.setVisibility(View.GONE);
        }else{
            adView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            isAdOpen = true;
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void showDialog(ArrayList<Achieve.Item> list, int pos){
        uri = null;

        Achieve.Item item = list.get(pos);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mdialog = inflater.inflate(R.layout.achieve_dialog, null);
        AlertDialog.Builder buider = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        buider.setView(mdialog);
        buider.setCancelable(true);
        Dialog dialog = buider.create();
        dialog.show();

        LottieAnimationView animationView = mdialog.findViewById(R.id.animation_view);
        TextView tv_key = mdialog.findViewById(R.id.tv);
        TextView tv_title = mdialog.findViewById(R.id.title);
        TextView tv_subtitle = mdialog.findViewById(R.id.subtitle);
        TextView tv_hint = mdialog.findViewById(R.id.subtitle2);
        TextView tv_cong = mdialog.findViewById(R.id.title2);
        ImageView iv_key = mdialog.findViewById(R.id.imageView2);
        ImageView close = mdialog.findViewById(R.id.close);
        ImageView image = mdialog.findViewById(R.id.image);
        MaterialCardView key = mdialog.findViewById(R.id.key);
        MaterialCardView save = mdialog.findViewById(R.id.save);
        MaterialCardView share = mdialog.findViewById(R.id.share);


        tv_key.setVisibility(View.GONE);
        iv_key.setVisibility(View.GONE);
        tv_cong.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();

        tv_title.setText(item.name);

        String format;

        if(item.type == Type.TYPE_ALL){
            format = String.format("무지출 %d회 달성", item.number);
        }else {
            if(item.isReduce){
                format = String.format("%s 줄이기 %d회 달성", Type.REDUCE_NAME[item.type], item.number);
            }else{
                format = String.format("%s 모으기 %d회 달성", Type.SAVE_NAME[item.type], item.number);
            }
        }

        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(MainActivity.this).load(item.resID).into(image);
        tv_hint.setText(item.text);
        save.setVisibility(View.VISIBLE);
        share.setVisibility(View.VISIBLE);
        key.setVisibility(View.GONE);

        tv_subtitle.setText(format);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                user.achieve.put(item.name, true);
                db.collection("users").document(UserInfo.getID(MainActivity.this)).set(user);
                if(pos < list.size()-1){
                    showDialog(list, pos+1);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = viewToBitmap(mdialog);
                        uri = saveFile(bitmap, item.name);
                    }
                }, 500);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri != null){
                    share(uri);
                }else{
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = viewToBitmap(mdialog);
                            uri = saveFile(bitmap, item.name);
                            share(uri);
                        }
                    }, 500);
                }
            }
        });

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public Bitmap viewToBitmap(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private Uri saveFile(Bitmap bitmap, String name) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, String.format("challenge_%s.jpg", name));
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        ContentResolver contentResolver = getContentResolver();
        Uri item = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            ParcelFileDescriptor pdf = contentResolver.openFileDescriptor(item, "w", null);

            if (pdf == null) {
                Log.d("asdf", "null");
            } else {
                FileOutputStream fos = new FileOutputStream(pdf.getFileDescriptor());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear();
                    values.put(MediaStore.Images.Media.IS_PENDING, 0);
                    contentResolver.update(item, values, null, null);
                }
                Toast.makeText(MainActivity.this, "저장이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return item;
    }

    public void share(Uri uri){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpg");
        startActivity(Intent.createChooser(shareIntent, "이미지 공유"));
    }

    class GridViewAdapter extends BaseAdapter {
        ArrayList<Day> items;

        GridViewAdapter(ArrayList<Day> items){
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Day item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final Day day = items.get(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_day_layout, viewGroup, false);
                convertView.setTag(day);
                TextView tv_day = convertView.findViewById(R.id.day);
                LinearLayout layout = convertView.findViewById(R.id.item_day_layout);
                ImageView iv1 = convertView.findViewById(R.id.iv1);
                ImageView iv2 = convertView.findViewById(R.id.iv2);
                ImageView more = convertView.findViewById(R.id.more);
                more.setVisibility(View.INVISIBLE);
                tv_day.setText(String.valueOf(day.day));
                Calendar calendar = Calendar.getInstance();



                if(calendar.get(Calendar.YEAR) == day.year && calendar.get(Calendar.MONTH)+1 == day.month && calendar.get(Calendar.DAY_OF_MONTH) == day.day){
                    convertView.setBackgroundColor(Color.parseColor("#FCE4EC"));
                }else{
                    convertView.setBackgroundColor(Color.TRANSPARENT);
                }


                ArrayList<int[]> types = new ArrayList<>();

                Set<String> key = day.reduce.keySet();
                for(String k : key){
                    if(Integer.parseInt(k) == Type.REDUCE_ALL){
                        for(int i = 0; i < Type.REDUCE_ALL; i++){
                            types.add(new int[]{0, i});
                        }
                    }else{
                        types.add(new int[]{0, Integer.parseInt(k)});
                    }
                }
                Set<String> key2 = day.save.keySet();
                for(String k : key2){
                    if(Integer.parseInt(k) == Type.SAVE_ALL){
                        for(int i = 0; i < Type.SAVE_ALL; i++){
                            types.add(new int[]{1, i});
                        }
                    }else{
                        types.add(new int[]{1, Integer.parseInt(k)});
                    }
                }
                Object[] ob = new Object[]{convertView, types};

                more.setTag(ob);

                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(bigBubble.getVisibility() == View.VISIBLE || iconBubble.getVisibility() == View.VISIBLE){
                            bigBubble.setVisibility(View.GONE);
                            iconBubble.setVisibility(View.GONE);
                            return;
                        }
                        Object[] ob = (Object[]) view.getTag();
                        View parent = (View) ob[0];
                        ArrayList<int[]> list = (ArrayList<int[]>) ob[1];
                        for(int i = 0; i < 8; i++){
                            if(i >= list.size()){
                                icons[i].setVisibility(View.GONE);
                            }else{
                                int[] item = list.get(i);
                                icons[i].setVisibility(View.VISIBLE);
                                int resId;
                                if(item[0] == 0){
                                    resId = user.userIcons.getIcon(item[1], true).resId;
                                }else{
                                    resId = user.userIcons.getIcon(item[1], false).resId;
                                }
                                icons[i].setImageResource(resId);
                            }
                        }
                        iconBubble.setVisibility(View.VISIBLE);

                        iconBubble.post(new Runnable() {
                            @Override
                            public void run() {
                                Display display = getWindowManager().getDefaultDisplay();
                                Point point = new Point();
                                display.getSize(point);

                                float x = parent.getX() + gridview.getX();
                                float y = parent.getY() + gridview.getY();

                                float center = x + parent.getWidth()/2f;
                                float posX = center - iconBubble.getWidth()/2f;
                                System.out.println(point);
                                if(posX <= 0){
                                    iconBubble.setX(50);
                                } else if (posX + iconBubble.getWidth() >= point.x) {
                                    iconBubble.setX(point.x - iconBubble.getWidth() - 50);
                                }else{
                                    iconBubble.setX(posX);
                                }

                                
                                
                                iconBubble.setY(y - iconBubble.getHeight());
                                
                                System.out.println(iconBubble.getWidth() + "," + iconBubble.getHeight());
                            }
                        });

                    }
                });

                Collections.shuffle(types);

                if(types.size() >= 2){
                    ImageView[] iv = new ImageView[]{iv1, iv2};
                    for(int i = 0; i < 2; i++){
                        int[] item = types.get(i);
                        int resId;
                        if(item[0] == 0){
                            resId = user.userIcons.getIcon(item[1], true).resId;
                        }else{
                            resId = user.userIcons.getIcon(item[1], false).resId;
                        }
                        iv[i].setImageResource(resId);
                        iv[i].setVisibility(View.VISIBLE);
                    }
                    if(types.size() > 2){
                        more.setVisibility(View.VISIBLE);
                    }
                }else if(types.size() == 1){
                    iv1.setVisibility(View.VISIBLE);
                    iv2.setVisibility(View.GONE);
                    int[] item = types.get(0);
                    int resId;
                    if(item[0] == 0){
                        resId = user.userIcons.getIcon(item[1], true).resId;
                    }else{
                        resId = user.userIcons.getIcon(item[1], false).resId;
                    }
                    iv1.setImageResource(resId);
                }else{
                    iv1.setVisibility(View.GONE);
                    iv2.setVisibility(View.GONE);
                }


                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(bigBubble.getVisibility() == View.VISIBLE || iconBubble.getVisibility() == View.VISIBLE){
                            bigBubble.setVisibility(View.GONE);
                            iconBubble.setVisibility(View.GONE);
                        }else{
                            Day day = (Day) view.getTag();
                            Intent intent = new Intent(MainActivity.this, ReduceActivity.class);
                            intent.putExtra("day", day);
                            startActivity(intent);
                        }
                    }
                });

            } else {
                View view = new View(context);
                view = (View) convertView;
            }


            return convertView;  //뷰 객체 반환
        }
    }

    class IconView{
        ImageView imageView;
        TextView textView;

        boolean isReduce;

        int tag;

        IconView(ImageView imageView, TextView textView, boolean isReduce, int tag){
            this.imageView = imageView;
            this.textView = textView;
            this.isReduce = isReduce;
            this.tag = tag;
            textView.setText(String.format("x %2d", 0));
        }

        void setImage(){
            int resId;

            if(isReduce){
                resId = user.userIcons.getIcon(tag, true).resId;
            }else{
                resId = user.userIcons.getIcon(tag, false).resId;
            }

            imageView.setImageResource(resId);
        }

        void setCount(int cnt){
            textView.setText(String.format("x %02d", cnt));
        }

        void reset(){
            textView.setText(String.format("x %02d", 0));
        }


    }
}