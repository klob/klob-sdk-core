package com.diandi.klob.sdk.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class RuleUtil {
    RuleUtil() {
    }

    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) return false;
        //String strPattern = "^//s*//w+(?://.{0,1}[//w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*//.[a-zA-Z]+//s*$";
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) return false;
        //Pattern pattern = Pattern.compile("1[0-9]{10}");
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[6-8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isURL(String url){
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
        Pattern patt = Pattern. compile(regex );
        Matcher matcher = patt.matcher(url);
        return matcher.matches();
    }

    public static String formatString(String str) {
        String res = String.format("%2s", str);
        res = res.replaceAll("\\s", "0");
        return res;
    }

    public static String formatString(String weishu, String str) {
        String res = String.format("%" + weishu + "s", str);
        res = res.replaceAll("\\s", "0");
        return res;
    }


}
