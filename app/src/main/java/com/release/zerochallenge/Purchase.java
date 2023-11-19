package com.release.zerochallenge;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class Purchase {

    public static void addKey(StoreActivity.GridViewAdapter adapter, LoadingView loadingView, int diff, Context context){
        String uid = UserInfo.getID(context);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).update("key", FieldValue.increment(diff)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingView.stop();
                if(task.isSuccessful()){
                    Toast.makeText(context, "열쇠 결제를 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    adapter.change();
                }
            }
        });
    }

    public static void addIcon(StoreActivity.GridViewAdapter adapter, LoadingView loadingView, String icon, Context context){
        String uid = UserInfo.getID(context);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    ZeroUser user = task.getResult().toObject(ZeroUser.class);
                    UserIcons userIcons = user.userIcons;
                    userIcons.purchaseIcon.add(icon);
                    db.collection("users").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            loadingView.stop();
                            if(task.isSuccessful()){
                                Toast.makeText(context, "아이콘 결제를 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                adapter.change();
                            }
                        }
                    });
                }
            }
        });
    }

    public static void setAdOff(StoreActivity.GridViewAdapter adapter, LoadingView loadingView, Context context){
        String uid = UserInfo.getID(context);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).update("isRemoveAdd", true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingView.stop();
                if(task.isSuccessful()){
                    Toast.makeText(context, "광고 제거가 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    adapter.change();
                }
            }
        });
    }



    public static void purchase(String productId, LoadingView loadingView, Context context, StoreActivity.GridViewAdapter adapter){
         if(productId.equals(Icons.ADS_ICONS[0].id)){
             setAdOff(adapter, loadingView, context);
         }
         else{
             for(Icons.Icon icon : Icons.KEY_ICONS){
                 if(icon.id.equals(productId)){
                     addKey(adapter, loadingView, Integer.parseInt(icon.name), context);
                     return;
                 }
             }
             addIcon(adapter, loadingView, productId, context);
         }
    }





}
