package cakcode.com.ukmapp.Helper;

import org.json.JSONException;
import org.json.JSONObject;

public class API {

    //BASE
    public static final String BASE_URL = "https://cakcode.id/";
    public static final String JSON_META = "Meta";
    public static final String PARAM_MESSAGE_CODE = "Code";
    public static final String JSON_MESSAGE = "Message";
    public static final String JSON_DATA = "Data";
    public static final String CLIENT_ID = "12345";

    //Member
    public static final String MEMBER_REGISTRATION = BASE_URL+"MemberApi/registration";
    public static final String MEMBER_LOGIN= BASE_URL+"MemberApi/login";

    //GLOBAL
    public static final String GLOBAL_SLIDE = BASE_URL+"GlobalApi/slide";

    //STORE
    public static final String STORE_ADD = BASE_URL+"StoreApi/add";
    public static final String STORE_ADD_DETAIL1 = BASE_URL+"StoreApi/adddetail1";
    public static final String STORE_ADD_DETAIL2 = BASE_URL+"StoreApi/adddetail2";

    //PRODUCT
    public static final String PRODUCT_NEWER = BASE_URL+"ProductApi/newer";

    //Category
    public static final String CATEGORY = BASE_URL+"CategoryApi/home";

    public static boolean isResponseSuccess(JSONObject jsonResponse) {
        try {
            if (jsonResponse.getJSONObject(JSON_META).getInt(PARAM_MESSAGE_CODE) == 200) {
                return true;
            }
        } catch (JSONException je) {
            je.printStackTrace();
            return false;
        }
        return false;
    }

    public static final String getResponseMessage(JSONObject jsonResponse) {
        try {
            return jsonResponse.getJSONObject(JSON_META).getString(JSON_MESSAGE);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return "Parsing failed";
    }
}
