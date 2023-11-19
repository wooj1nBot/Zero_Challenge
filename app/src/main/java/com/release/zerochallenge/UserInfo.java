package com.release.zerochallenge;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserInfo {

    public static String getID(Context context){
        SharedPreferences preferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return preferences.getString("uid", "");
    }

    public static void saveUser(Context context, String uid, String name){
        SharedPreferences.Editor editor = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit();
        editor.putString("uid", uid);
        editor.putString("name", name);
        editor.apply();
    }

    public static void logout(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit();
        editor.remove("uid");
        editor.apply();
        logoutKakao();
        logoutGoogle(context);

    }

    private static void logoutKakao(){ UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() { @Override public Unit invoke(Throwable throwable) { return null; } }); }

    private static void logoutGoogle(Context context){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mGoogleSignInClient.signOut();
    }
}
