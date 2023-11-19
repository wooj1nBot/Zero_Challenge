package com.release.zerochallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

public class StoreActivity extends AppCompatActivity {
    private GridView gridview;
    CardView[] buttons;
    TextView[] textViews;
    private String uid;
    private FirebaseFirestore db;
    private ZeroUser user;
    private final int TYPE_KEY = 129;
    private final int TYPE_ADDS = 131;

    private final int[] buttonType = {Type.REDUCE_COFFEE, Type.REDUCE_MEALS, Type.REDUCE_ALCOHOL, Type.REDUCE_TRANSPORTATION, Type.SAVE_RESALE, Type.SAVE_APP, Type.SAVE_PIGGY, Type.SAVE_ETC, TYPE_KEY, TYPE_ADDS};
    private final boolean[] IsReduces = {true, true, true, true, false, false, false, false, false, false};
    private BillingEntireManager billingEntireManager;
    private boolean isKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        buttons = new CardView[10];
        textViews = new TextView[10];
        gridview = findViewById(R.id.grid);
        db = FirebaseFirestore.getInstance();
        uid = UserInfo.getID(StoreActivity.this);
        billingEntireManager = new BillingEntireManager(this);
        Intent intent = getIntent();
        isKey = intent.getBooleanExtra("key", false);

        {
            buttons[0] = findViewById(R.id.btn1);
            buttons[1] = findViewById(R.id.btn2);
            buttons[2] = findViewById(R.id.btn3);
            buttons[3] = findViewById(R.id.btn4);
            buttons[4] = findViewById(R.id.btn5);
            buttons[5] = findViewById(R.id.btn6);
            buttons[6] = findViewById(R.id.btn7);
            buttons[7] = findViewById(R.id.btn8);
            buttons[8] = findViewById(R.id.btn9);
            buttons[9] = findViewById(R.id.btn10);

            textViews[0] = findViewById(R.id.tv1);
            textViews[1] = findViewById(R.id.tv2);
            textViews[2] = findViewById(R.id.tv3);
            textViews[3] = findViewById(R.id.tv4);
            textViews[4] = findViewById(R.id.tv5);
            textViews[5] = findViewById(R.id.tv6);
            textViews[6] = findViewById(R.id.tv7);
            textViews[7] = findViewById(R.id.tv8);
            textViews[8] = findViewById(R.id.tv9);
            textViews[9] = findViewById(R.id.tv10);

        }

        for(int i = 0; i < buttons.length; i++){
            buttons[i].setTag(i);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    clearBtn();
                    textViews[pos].setTextColor(Color.WHITE);
                    buttons[pos].setCardBackgroundColor(Color.parseColor("#786557"));

                    if(user != null){
                        GridViewAdapter adapter;
                        if(buttonType[pos] == TYPE_KEY){
                            adapter = new GridViewAdapter(buttonType[pos], Icons.KEY_ICONS, user.userIcons);
                        } else if (buttonType[pos] == TYPE_ADDS) {
                            adapter = new GridViewAdapter(buttonType[pos], Icons.ADS_ICONS, user.userIcons);
                        } else {
                            if (IsReduces[pos]) {
                                adapter = new GridViewAdapter(buttonType[pos], Icons.REDUCE_ICON[buttonType[pos]], user.userIcons.getIcon(buttonType[pos], true).id, user.userIcons);
                            } else {
                                adapter = new GridViewAdapter(buttonType[pos], Icons.SAVE_ICON[buttonType[pos]], user.userIcons.getIcon(buttonType[pos], false).id, user.userIcons);
                            }
                        }
                        gridview.setAdapter(adapter);

                    }
                }
            });
        }

        getData();

        ImageView store = findViewById(R.id.star);
        ImageView back = findViewById(R.id.back);

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, AchieveActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, AchieveActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getData(){

        db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful() && task.getResult().exists()){
                    user = task.getResult().toObject(ZeroUser.class);
                    if(isKey){
                        textViews[8].setTextColor(Color.WHITE);
                        buttons[8].setCardBackgroundColor(Color.parseColor("#786557"));
                        GridViewAdapter adapter = new GridViewAdapter(TYPE_KEY, Icons.KEY_ICONS, user.userIcons);
                        gridview.setAdapter(adapter);
                    }else {
                        textViews[0].setTextColor(Color.WHITE);
                        buttons[0].setCardBackgroundColor(Color.parseColor("#786557"));
                        GridViewAdapter adapter = new GridViewAdapter(Type.REDUCE_COFFEE, Icons.REDUCE_ICON[0], user.userIcons.getIcon(Type.REDUCE_COFFEE, true).id, user.userIcons);
                        gridview.setAdapter(adapter);
                    }
                }
            }
        });
    }



    public void clearBtn(){
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setCardBackgroundColor(Color.parseColor("#FFFBF3"));
            textViews[i].setTextColor(Color.parseColor("#786557"));
        }
    }

    class GridViewAdapter extends BaseAdapter {
        Icons.Icon[] icons;
        View[][] buttons;
        UserIcons userIcons;
        String selectId;

        int type;

        GridViewAdapter(int type, Icons.Icon[] icons, String selectId, UserIcons userIcons){
            this.type = type;
            this.icons = icons;
            this.selectId = selectId;
            this.userIcons = userIcons;
            buttons = new View[icons.length][2];
        }

        GridViewAdapter(int type, Icons.Icon[] icons, UserIcons userIcons){
            this.type = type;
            this.icons = icons;
            this.userIcons = userIcons;
        }

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return icons[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final Icons.Icon icon = icons[position];

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if(type == TYPE_KEY) {
                    convertView = inflater.inflate(R.layout.purchase_key_layout, viewGroup, false);
                }
                else if(type == TYPE_ADDS){
                    convertView = inflater.inflate(R.layout.purchase_removead_layout, viewGroup, false);
                }
                else{
                    convertView = inflater.inflate(R.layout.icon_item_layout, viewGroup, false);
                }

                ImageView imageView = convertView.findViewById(R.id.icon);
                TextView tv_name = convertView.findViewById(R.id.iconname);
                TextView tv_text = convertView.findViewById(R.id.text);

                MaterialCardView button = convertView.findViewById(R.id.button);
                TextView btn_text = convertView.findViewById(R.id.btn_text);

                button.setTag(icon);
                convertView.setTag(icon);

                if(type == TYPE_KEY) {
                    tv_name.setText("x" + icon.name);
                    tv_text.setText(icon.text);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Icons.Icon icon = (Icons.Icon) view.getTag();
                            billingEntireManager.purchase(icon.id, StoreActivity.this, GridViewAdapter.this);
                        }
                    });

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Icons.Icon icon = (Icons.Icon) view.getTag();
                            billingEntireManager.purchase(icon.id, StoreActivity.this, GridViewAdapter.this);
                        }
                    });
                }
                else if(type == TYPE_ADDS){
                    tv_name.setText(icon.name);
                    tv_text.setText(icon.text);

                    if(user.isRemoveAdd){
                        button.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
                        button.setStrokeWidth(4);
                        button.setStrokeColor(Color.parseColor("#786557"));
                        btn_text.setText("사용중");
                        btn_text.setTextColor(Color.parseColor("#786557"));
                    }else{
                        btn_text.setTextColor(Color.WHITE);
                        btn_text.setText("구매하기");
                        button.setCardBackgroundColor(Color.parseColor("#842D2D"));
                        button.setStrokeWidth(0);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Icons.Icon icon = (Icons.Icon) view.getTag();
                                Log.d("adsdsd", icon.id);
                                billingEntireManager.purchase(icon.id, StoreActivity.this, GridViewAdapter.this);
                            }
                        });

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Icons.Icon icon = (Icons.Icon) view.getTag();
                                billingEntireManager.purchase(icon.id, StoreActivity.this, GridViewAdapter.this);
                            }
                        });
                    }

                }
                else{
                    buttons[position][0] = button;
                    buttons[position][1] = btn_text;

                    Glide.with(StoreActivity.this).load(icon.resId).into(imageView);
                    tv_name.setText(icon.name);
                    if (!icon.text.equals("")) {
                        tv_text.setText(icon.text);
                        tv_text.setVisibility(View.VISIBLE);
                    } else {
                        tv_text.setVisibility(View.GONE);
                    }

                    if (icon.id.equals(selectId)) {
                        button.setCardBackgroundColor(Color.parseColor("#FFFBF3"));
                        button.setStrokeWidth(4);
                        button.setStrokeColor(Color.parseColor("#786557"));
                        btn_text.setText("사용중");
                        btn_text.setTextColor(Color.parseColor("#786557"));
                    } else {
                        if (icon.isPaid) {
                            if (userIcons.purchaseIcon.contains(icon.id)) {
                                btn_text.setTextColor(Color.WHITE);
                                btn_text.setText("교체하기");
                                button.setCardBackgroundColor(Color.parseColor("#786557"));
                                button.setStrokeWidth(0);
                            } else {
                                btn_text.setTextColor(Color.WHITE);
                                btn_text.setText("구매하기");
                                button.setCardBackgroundColor(Color.parseColor("#842D2D"));
                                button.setStrokeWidth(0);
                            }
                        } else {
                            btn_text.setTextColor(Color.WHITE);
                            btn_text.setText("교체하기");
                            button.setCardBackgroundColor(Color.parseColor("#786557"));
                            button.setStrokeWidth(0);
                        }
                    }

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Icons.Icon icon = (Icons.Icon) view.getTag();
                            if (!icon.id.equals(selectId)) {
                                if (icon.isPaid) {
                                    if (userIcons.purchaseIcon.contains(icon.id)) {
                                        //교체
                                        changeIcon(icon, type);
                                    } else {
                                        //구메
                                        billingEntireManager.purchase(icon.id, StoreActivity.this, GridViewAdapter.this);
                                    }
                                } else {
                                    //교체
                                    changeIcon(icon, type);
                                }
                            }
                        }
                    });

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Icons.Icon icon = (Icons.Icon) view.getTag();
                            if (!icon.id.equals(selectId)) {
                                if (icon.isPaid) {
                                    if (userIcons.purchaseIcon.contains(icon.id)) {
                                        //교체
                                        changeIcon(icon, type);
                                    } else {
                                        //구메
                                        billingEntireManager.purchase(icon.id, StoreActivity.this, GridViewAdapter.this);
                                    }
                                } else {
                                    //교체
                                    changeIcon(icon, type);
                                }
                            }
                        }
                    });
                }

            }

            return convertView;  //뷰 객체 반환
        }


        public void changeIcon(Icons.Icon icon, int type){
            if(icon.isReduce){
                userIcons.reduceIcon.set(type, icon.id);
            }else{
                userIcons.saveIcon.set(type, icon.id);
            }
            selectId = icon.id;
            db.collection("users").document(UserInfo.getID(StoreActivity.this)).set(user);

            GridViewAdapter adapter = new GridViewAdapter(type, icons, selectId, user.userIcons);
            gridview.setAdapter(adapter);
            Toast.makeText(StoreActivity.this, "아이콘을 성공적으로 교체했습니다.", Toast.LENGTH_SHORT).show();
        }


        public void change(){
            db.collection("users").document(UserInfo.getID(StoreActivity.this)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful() && task.getResult().exists()){
                        user = task.getResult().toObject(ZeroUser.class);
                        GridViewAdapter adapter = new GridViewAdapter(type, icons, selectId, user.userIcons);
                        gridview.setAdapter(adapter);
                    }
                }
            });
        }



    }

}