package cakcode.com.ukmapp.Global;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cakcode.com.ukmapp.BuildConfig;


//import com.google.android.gms.maps.model.BitmapDescriptor;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by azlan on 8/29/14.
 */
public class Cak {
    public final static String PROPERTY_APP_VERSION = "appVersion";
    public final static String CLASS_NAME = "Cak";
    public final static String REGISTER_ID = "register_id";
    public final static String FIRST_RUN = "first_run";

    public static String DATE_FORMAT_NICE = "d MMM yyyy";
    public static String DATE_FORMAT_DB = "yyyy-MM-dd";

    public final static int MIN_PASSWORD_LENGTH = 8;

    public static String urlPrefix(){
        return "https";
    }



    public static String myCurrencyFormat(String money){
        String result = money;
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrency(Currency.getInstance(new Locale("in", "ID")));
        dfs.setCurrencySymbol("Rp.");
        dfs.setGroupingSeparator('.');
        dfs.setMonetaryDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(dfs);
        try
        {
            result = df.format(Double.valueOf(money));
            Log.d("CAK", "myCurrencyFormat: "+result);
            return result;
        }
        catch (Exception e)
        {
            return "";
        }

    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    public static String myCurrencyFormatDouble(double money){
        String result = String.valueOf(money);
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("Rp.");
        dfs.setGroupingSeparator('.');
        dfs.setMonetaryDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(dfs);
        result = df.format(Double.valueOf(money));
        return result;
    }

    public static Double currencyStringToDouble(String currencyString){
        try {
            DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
            //format.setParseBigDecimal(true);
            Number number = format.parse(currencyString);
            return number.doubleValue();
        }
        catch (ParseException pe){
            pe.printStackTrace();
        }
        return null;
    }

    public static String myDecimalFormat(Double number){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String s = nf.format(number);
        return s;
    }

    public static String niceNumberFormat(Double number){
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator('.');
        dfs.setMonetaryDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);
        df.setDecimalFormatSymbols(dfs);
        String result = df.format(Double.valueOf(number));
        return result;
    }

    public static String getFormatedDate(String date, String formatDate){
        String result = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date inputdate = sdf.parse(date);
            sdf = new SimpleDateFormat(formatDate);
            result = sdf.format(inputdate);
            return result;
        }catch (ParseException e){
            e.printStackTrace();
        }

        return result;
    }

    public static String getFormatedDate(String date, String formatDate, String formatBefore){
        String result = date;
        SimpleDateFormat sdf = new SimpleDateFormat(formatBefore);
        try{
            Date inputdate = sdf.parse(date);
            sdf = new SimpleDateFormat(formatDate);
            result = sdf.format(inputdate);
            return result;
        }catch (ParseException e){
            e.printStackTrace();
        }

        return result;
    }

    public static String myDateFormat(String date){
        String result = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date inputdate = sdf.parse(date);
            sdf = new SimpleDateFormat("d MMMM yyyy");
            result = sdf.format(inputdate);
            return result;
        }catch (ParseException e){
            e.printStackTrace();
        }

        return result;
    }

    public static Date myStringToDate(String date){
        String dtStart = date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date rldate = format.parse(dtStart);
            return rldate;
        }
        catch (ParseException pe){
            pe.printStackTrace();
        }
        return null;
    }

    public static Date myStringToDate(String date, String formatDate){
        String dtStart = date;
        SimpleDateFormat format = new SimpleDateFormat(formatDate);
        try{
            Date rldate = format.parse(dtStart);
            return rldate;
        }
        catch (ParseException pe){
            pe.printStackTrace();
        }
        return null;
    }

    public static String currentDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return format.format(currentDate);
    }

    public static String currentDate(String dateFormat){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date currentDate = new Date();
        return format.format(currentDate);
    }

    public static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static double getPeriod(Date start, Date end){
        long period = end.getTime() - start.getTime();
        return period;
    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static BigDecimal doubleToDecimal(double number){
        return new BigDecimal(number);
    }

    public static boolean isDataExpired(Date start, Date end, double maxTime){
        Log.d("Period", ""+(getPeriod(start, end) / (60 * 60 * 1000)) + " | " + maxTime);
        if((getPeriod(start, end) / (60 * 60 * 1000)) > maxTime){
            return true;
        }
        return false;
    };


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isUsernameValid(String username) {
        boolean isValid = false;

        String expression = "^[a-z0-9_-]{3,15}$";
        CharSequence inputStr = username;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static String getRealPathFromUriAPIBelow11(Context context, Uri uri){
        Log.d("Result Post", uri.toString());
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    public static Matrix getRotateMatrix(String path){
        int rotate = 0;
        try{
            File imageFile = new File(path);
            ExifInterface ei = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            Log.d("Result Post", "Exif Orientation : " + orientation);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return matrix;
    };

    public static boolean isDebugable(ApplicationInfo appInfo){
        return 0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE);
    }

    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static int getAppVersion(Context context){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }catch (PackageManager.NameNotFoundException nnfe){
            throw new RuntimeException("Could not package name " + nnfe);
        }
    }

    public static void deleteSharedPreference(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean isNumeric(String number){
        try {
            double d = Double.parseDouble(number);
        }
        catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }

    static public SharedPreferences getSharedLocalData(Context context){
        return context.getSharedPreferences(CLASS_NAME, Context.MODE_PRIVATE);
    }

    static public void storeRegisterIdToLocal(Context context, String registerId){
        final SharedPreferences prefs = getSharedLocalData(context);
        int appVersion = Cak.getAppVersion(context);
        Log.d("Result Post", "Saving notif on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REGISTER_ID, registerId);
        editor.putInt(Cak.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    static public String getRegisterIdFromPref(Context context){
        SharedPreferences preferences = getSharedLocalData(context);
        String registerId = preferences.getString(REGISTER_ID, "");
        if(registerId.isEmpty()){
            return "";
        }

        int registrationVersion = preferences.getInt(Cak.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = Cak.getAppVersion(context);
        if(registrationVersion != currentVersion){
            return "";
        }
        return registerId;
    }

    static public void storeFirstRunToLocal(Context context, boolean isFirstRun){
        final SharedPreferences prefs = getSharedLocalData(context);
        int appVersion = Cak.getAppVersion(context);
        Log.d("Result Post", "Saving notif on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_RUN, isFirstRun);
        editor.putInt(Cak.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    static public boolean isFirstRunFromPref(Context context){
        SharedPreferences preferences = getSharedLocalData(context);
        int registrationVersion = preferences.getInt(Cak.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = Cak.getAppVersion(context);
        if(registrationVersion != currentVersion){
            return false;
        }

        boolean isFirstRun = preferences.getBoolean(FIRST_RUN, true);
        return isFirstRun;
    }


    public static String getStringLocaleSetting(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if(!prefs.getString("language", "").isEmpty()){
            return prefs.getString("language", "");
        }
        return "id";
    }

    public static String getOtherStringLocale(String locale){
        if(locale.equals("id")){
            return "in";
        }
        return locale;
    }

    public static int getActionBarHeight(Context context){
        TypedValue tv = new TypedValue();
        int actionBarHeight;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            return actionBarHeight;
        }
        return 0;
    }

    public static boolean isStringParamValid(JSONObject json, String param){
        try {
            if(!json.isNull(param) && !json.getString(param).isEmpty()
                    && json.getString(param).trim().length() > 0
                    && !json.getString(param).trim().equals("null")){
                return true;
            }
        }
        catch (JSONException je){
            je.printStackTrace();
        }
        return false;
    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }

    public static void log(String tag, String content){
        if (BuildConfig.DEBUG) {
            Log.d(tag, content);
        }
    }
    public static void logCursor (String tag , Cursor cursor, String[] columns){
        if(BuildConfig.DEBUG){
            String msg = "| ";
            for(int a = 0; a<columns.length ; a++){
//                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_MEMBER_ID)
                msg = msg+ cursor.getString(cursor.getColumnIndex(String.valueOf(columns[a])))+ "  |  ";

            }
            Log.d(tag, msg);

        }

    }
    public static String getUrlWithoutPrefix(String url){
        String fixUrl = "";
        if (url.contains("https://")){
            fixUrl = url.replace("https://", "");
        }
        else if(url.contains("http://")){
            fixUrl = url.replace("http://", "");
        }
        else{
            fixUrl = url;
        }
        return fixUrl;
    }

    public static Intent getEmailIntent(String emailTo, String subject, String content){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent;
    }

    public static String getUniqueDeviceId(Context context){
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }
}
