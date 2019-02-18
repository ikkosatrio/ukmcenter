package cakcode.com.ukmapp.Global;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import cakcode.com.ukmapp.Model.Member;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "CakDeveloper";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_CURRENT_MEMBER = "CurrentMember";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void setCurrentMember(String member) {
        editor.putString(KEY_CURRENT_MEMBER, member);
        // commit changes
        editor.commit();
        Log.d(TAG, "Member login session modified!");
    }

    public Member getCurrentMember() {
        Member mMember = gson.fromJson(pref.getString(KEY_CURRENT_MEMBER,""),Member.class);
        return mMember;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
