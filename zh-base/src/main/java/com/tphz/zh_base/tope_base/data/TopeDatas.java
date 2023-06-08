package com.tphz.zh_base.tope_base.data;


import com.tphz.zh_base.common.Constants;
import com.tphz.zh_base.common.LogUtil;

/**
 * 桌面相关数据，方便其他app获取
 */
public class TopeDatas {
    public static final String TAG = "TopeDatas";
    public static final String TOKEN = "token";
    public static final String USER_ID = "userId";
    public static final String CHILDREN_ID = "childrenId";
    public static final String ACCOUNT_ID = "accountId";
    public static final String CHILDREN_NAME = "childrenName";
    public static final String DEVICE_ID = "deviceId";
    public static final String CDN_KEY = "CDN_KEY";
    public static final String LAUNCHER_PACKAGE = "com.tope.launcher";

    public static void setCdnKey(String value) {
        TopeDataManager.getInstance(Constants.getContext()).putKey(CDN_KEY, value);
    }

    public static String getCdnKey() {
        return TopeDataManager.getInstance(Constants.getContext()).getKey(CDN_KEY);
    }

    public static String getToken() {
        return TopeDataManager.getInstance(Constants.getContext()).getKey(TOKEN);
    }

    public static void setToken(String token) {
        TopeDataManager.getInstance(Constants.getContext()).putKey(TOKEN, token);
    }

    public static String getAccountId() {
        return TopeDataManager.getInstance(Constants.getContext()).getKey(ACCOUNT_ID);
    }

    public static void setAccountId(String accountId) {
        if (chekPackageName()) {
            TopeDataManager.getInstance(Constants.getContext()).putKey(ACCOUNT_ID, accountId);
        }
    }

    public static String getChildrenId() {
        return TopeDataManager.getInstance(Constants.getContext()).getKey(CHILDREN_ID);
    }

    public static void setChildrenId(String childrenId) {
        if (chekPackageName()) {
            TopeDataManager.getInstance(Constants.getContext()).putKey(CHILDREN_ID, childrenId);
        }
    }

    public static String getChildrenName() {
        return TopeDataManager.getInstance(Constants.getContext()).getKey(CHILDREN_NAME);
    }

    public static void setChildrenName(String childrenName) {
        if (chekPackageName()) {
            TopeDataManager.getInstance(Constants.getContext()).putKey(CHILDREN_NAME, childrenName);
        }
    }

    public static String getUserId() {
        return TopeDataManager.getInstance(Constants.getContext()).getKey(USER_ID);
    }

    public static void setUserId(String userId) {
        if (chekPackageName()) {
            TopeDataManager.getInstance(Constants.getContext()).putKey(USER_ID, userId);
        }
    }

    public static String getDeviceId() {
        return TopeDataManager.getInstance(Constants.getContext()).getKey(DEVICE_ID);
    }

    public static void setDeviceId(String userId) {
        if (chekPackageName()) {
            TopeDataManager.getInstance(Constants.getContext()).putKey(DEVICE_ID, userId);
        }
    }

    private static boolean chekPackageName() {
        String packName = Constants.getContext().getPackageName();
        if (LAUNCHER_PACKAGE.equals(packName)) {
            LogUtil.d(TAG, "allow " + packName + " write");
            return true;
        } else {
            LogUtil.d(TAG, "not allow " + packName + " write");
            return false;
        }

    }
}
