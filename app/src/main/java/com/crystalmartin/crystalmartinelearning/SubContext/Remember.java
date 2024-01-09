package com.crystalmartin.crystalmartinelearning.SubContext;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import static android.content.Context.MODE_PRIVATE;

public class Remember {

    // TOKEN_AND_ID SHARED PREFERENCES
    static String SHARED_PREF_NAME_1 = "TOKEN_AND_ID";
    static String KEY_TOKEN = "Token", KEY_ID = "1", KEY_USERNAME,KEY_EPF_NO="45",KEY_IMAGE="img",KEY_PRO_NAME="name";

    public static void SaveLoginCredentials(Activity activity, String token, String id, String username,String epfNo,String imageUrl,String profileName) {

        SharedPreferences sp = activity.getSharedPreferences(SHARED_PREF_NAME_1, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        e.putString(KEY_TOKEN, token);
        e.putString(KEY_ID, id);
        e.putString(KEY_USERNAME, username);
        e.putString(KEY_EPF_NO, epfNo);
        e.putString(KEY_IMAGE, imageUrl);
        e.putString(KEY_PRO_NAME, profileName);
        e.apply();
    }

    public static String[] DisplayLoginCredentials(Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences(SHARED_PREF_NAME_1, MODE_PRIVATE);
        String savedName = sp.getString(KEY_TOKEN, null);
        String savedId = sp.getString(KEY_ID, null);
        String savedUsername = sp.getString(KEY_USERNAME, null);
        String savedEpfNo = sp.getString(KEY_EPF_NO, null);
        String savedImageUrl = sp.getString(KEY_IMAGE, null);
        String savedProfName = sp.getString(KEY_PRO_NAME, null);

        return new String[]{savedName, savedId,savedUsername,savedEpfNo,savedImageUrl,savedProfName};
    }

    public static void DeleteLoginCredentials(@NonNull Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences(SHARED_PREF_NAME_1, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.remove(KEY_TOKEN);
        e.remove(KEY_ID);
        e.remove(KEY_USERNAME);
        e.remove(KEY_EPF_NO);
        e.remove(KEY_IMAGE);
        e.remove(KEY_PRO_NAME);
        e.apply();
    }

    //----------------------------------------------------------------------------------------------

    // SELECTED_LANGUAGE PREFERENCES
    static String SHARED_PREF_NAME_2 = "SELECTED_LANGUAGE";
    static String LANGUAGE = "ENGLISH";

    public static void SaveSelectedLanguage(Activity activity, String language) {

        SharedPreferences sp = activity.getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        e.putString(LANGUAGE, language);
        e.apply();
    }

    public static String[] DisplaySelectedLanguage(Activity activity) {

        SharedPreferences sp = activity.getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String selectedLanguage = sp.getString(LANGUAGE, null);

        return new String[]{selectedLanguage};
    }

    public static void DeleteSelectedLanguage(@NonNull Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.remove(LANGUAGE);
        e.apply();
    }
}
