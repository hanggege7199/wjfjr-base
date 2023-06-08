package com.tope.tope_base.scheme

import android.content.Context
import com.tphz.zh_base.tope_base.scheme.SchemeHandler
import com.tphz.zh_base.tope_base.scheme.SchemeManager

object TopeScheme {
    /**
     * TopeScheme.init(getApplicationContext(), "hzlauncher", new SchemeHandler() {
     * @Override
     * public void handler(String path, Map<String></String>, String> map) {
     * if ("mdm".equals(path)) {
     * LogUtil.e(TAG,"map:"+map.toString());
     * MdmActivity.launch(getApplicationContext());
     * }
     * }
     * });
     * @param context
     * @param schemeName
     * @param schemeHandler
     */
    @JvmStatic
    fun init(schemeName: String?, schemeHandler: SchemeHandler?) {
        SchemeManager.getInstance().setSchemeName(schemeName)
        SchemeManager.getInstance().setSchemeHandler(schemeHandler)
    }

    /**
     * TopeScheme.go("hzlauncher://mdm?key=aa");
     * @param scheme
     */
    @JvmStatic
    @Throws(Exception::class)
    fun go(context: Context?, scheme: String?) {
        SchemeManager.getInstance().go(context, scheme)
    }

    /**
     * 外部
     * TopeScheme.goFormOther("hzlauncher://mdm?key=aa");
     */
    @JvmStatic
    @Throws(Exception::class)
    fun goFormOther(context: Context?, scheme: String?) {
        SchemeManager.getInstance().goFormOther(context, scheme)
    }
}