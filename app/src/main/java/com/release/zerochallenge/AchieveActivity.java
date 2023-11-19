package com.release.zerochallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AchieveActivity extends AppCompatActivity {
    private String uid;
    private FirebaseFirestore db;
    private ZeroUser user;
    private RecyclerView rc;
    private boolean isReduce = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
        uid = UserInfo.getID(AchieveActivity.this);
        db = FirebaseFirestore.getInstance();
        rc = findViewById(R.id.rc);
        RadioGroup group = findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                 if(i == R.id.r1){
                     isReduce = true;
                 }else{
                     isReduce = false;
                 }
                 setList(isReduce);
            }
        });
        rc.setLayoutManager(new LinearLayoutManager(this));

        ImageView store = findViewById(R.id.star);
        ImageView back = findViewById(R.id.back);
        ImageView logout = findViewById(R.id.logout);

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AchieveActivity.this, StoreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog();
            }
        });

    }

    public void makeDialog(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mdialog = inflater.inflate(R.layout.app_quit_dialog, null);
        AlertDialog.Builder buider = new AlertDialog.Builder(AchieveActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        buider.setView(mdialog);
        buider.setCancelable(true);
        Dialog dialog = buider.create();
        MaterialCardView cancel = mdialog.findViewById(R.id.cancel);
        MaterialCardView quit = mdialog.findViewById(R.id.quit);
        TextView tv_title = mdialog.findViewById(R.id.title);
        AdView adView = mdialog.findViewById(R.id.adView);
        adView.setVisibility(View.GONE);

        tv_title.setText("로그아웃 하시겠습니까?");
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
                UserInfo.logout(AchieveActivity.this);
                finish();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }




    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    public void getData(){
        db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful() && task.getResult().exists()){
                    user = task.getResult().toObject(ZeroUser.class);
                    setList(isReduce);
                }
            }
        });
    }

    public void setList(boolean isReduce){
        List<Achieve.Item> list;
        if(isReduce){
            list = Achieve.reduceItems;
        }else{
            list = Achieve.saveItems;
        }
        if(user != null) {
            AchieveAdopter achieveAdopter = new AchieveAdopter(list);
            rc.setAdapter(achieveAdopter);
        }
    }

    public boolean check(Achieve.Item item){
        return user.achieve.containsKey(item.name);
    }



    public class AchieveAdopter extends RecyclerView.Adapter<AchieveAdopter.ViewHolder>{

        List<Achieve.Item> list;
        Context context;

        Uri uri;

        AchieveAdopter(List<Achieve.Item> list){
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
            View view = inflater.inflate(R.layout.achieve_list_layout, parent, false) ;
            return new AchieveAdopter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Achieve.Item item = list.get(position);
            holder.tv_title.setText(item.name);
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

            holder.tv_subtitle.setText(format);

            if(check(item)){
                holder.tv_subtitle.setVisibility(View.VISIBLE);
                Glide.with(AchieveActivity.this).load(item.resID).into(holder.image);
            }else{
                if(user.achieve_open.containsKey(item.name)){
                    holder.tv_subtitle.setVisibility(View.VISIBLE);
                }else{
                    holder.tv_subtitle.setVisibility(View.GONE);
                }
                holder.image.setImageResource(R.drawable.box);
            }
        }



        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            TextView tv_subtitle;
            ImageView image;

            ViewHolder(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.title);
                tv_subtitle = itemView.findViewById(R.id.subtitle);
                image = itemView.findViewById(R.id.image);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        Achieve.Item item = list.get(pos);
                        if (pos != RecyclerView.NO_POSITION) {
                            showDialog(item);
                        }
                    }
                });
            }
        }

        public void openHint(Achieve.Item item){
            user.key--;
            user.achieve_open.put(item.name, true);
            showDialog(item);
            db.collection("users").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    getData();
                }
            });
        }



        public void showDialog(Achieve.Item item){
            uri = null;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mdialog = inflater.inflate(R.layout.achieve_dialog, null);
            AlertDialog.Builder buider = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
            buider.setView(mdialog);
            buider.setCancelable(true);
            Dialog dialog = buider.create();
            dialog.show();
            TextView tv_key = mdialog.findViewById(R.id.tv);
            TextView tv_title = mdialog.findViewById(R.id.title);
            TextView tv_subtitle = mdialog.findViewById(R.id.subtitle);
            TextView tv_hint = mdialog.findViewById(R.id.subtitle2);
            ImageView close = mdialog.findViewById(R.id.close);
            ImageView image = mdialog.findViewById(R.id.image);
            MaterialCardView key = mdialog.findViewById(R.id.key);
            MaterialCardView save = mdialog.findViewById(R.id.save);
            MaterialCardView share = mdialog.findViewById(R.id.share);

            tv_key.setText(String.format("x %02d", user.key));
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

            if(check(item)){
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(AchieveActivity.this).load(item.resID).into(image);
                tv_hint.setText(item.text);
                save.setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);
                key.setVisibility(View.GONE);
            }else{
                if(user.achieve_open.containsKey(item.name)){
                    tv_subtitle.setVisibility(View.VISIBLE);
                    key.setVisibility(View.GONE);
                }else{
                    tv_subtitle.setVisibility(View.GONE);

                    key.setVisibility(View.VISIBLE);
                    key.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(user.key >= 1){
                                dialog.dismiss();
                                openHint(item);
                            }else{
                                Toast.makeText(AchieveActivity.this, "열쇠가 부족합니다. 열쇠를 구매해주세요!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(AchieveActivity.this, StoreActivity.class);
                                intent.putExtra("key", true);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                image.setImageResource(R.drawable.box);
                tv_hint.setText("Hint : " + item.hint);
                save.setVisibility(View.GONE);
                share.setVisibility(View.GONE);



            }

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


            tv_subtitle.setText(format);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
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
                    Toast.makeText(AchieveActivity.this, "저장이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

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


    }

}