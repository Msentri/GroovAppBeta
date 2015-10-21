package com.example.sandilemazibuko.groovappbeta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by sandilemazibuko on 15/09/17.
 * @version V 0.1
 */
public class LocalStorage {

    SharedPreferences sharedPreferences;

    public LocalStorage(Context context){
        sharedPreferences = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
    }

    /**
     * THESE FUNCTION STORES INFORMATION RETRIEVED FROM ONLINE DATABASE
     * AND ACCESS IN VIA A CLASS CALLED USER AND STORE IN IN SHARED PREFERENCES
     * THO BE ACCESSED LOCAL BY AN APPLICATION TO KEEP TRACK OF LOGIN IN AND OUT OF A USER
     * */
    public void storeUserDetailsOnPreference(User user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id",user.user_id);
        editor.putString("user_name",user.name);
        editor.putString("user_surname",user.surname);
        editor.putString("user_picture",user.profile_pic);
        editor.putString("user_email",user.email);
        editor.putString("user_dob",user.dob);
        editor.putString("user_membership_type",user.membership_type);
        editor.commit();
    }

    /**
     * THE FUNCTION CHECKS LOGGED IN USER BY REFERENCING BY ID OF A USER
     * */
    public boolean isLoggedin(){

        String Default = "N/A";
        String id = sharedPreferences.getString("user_id",Default);
        if(!id.equals("N/A")){
            return true;
        }else{
            return false;

        }
    }

    /**
     * THESE FUNCTION CLEAR THE LOCAL STORED USER INFORMATION LOCALLY
     * */
    public void clearUserData(){
        SharedPreferences.Editor userDatabase = sharedPreferences.edit();
        userDatabase.clear();
        userDatabase.commit();

    }
}
