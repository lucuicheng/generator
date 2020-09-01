package com.lucuicheng.plugin.utils;

public class StringUtils {

    /**
     * 下划线后一个字符大写
     * @param str
     * @return
     */
    public static String upcaseUnderlineNext(String str) {
        str = str.toLowerCase();//先统一小写

        //如果下划线在最后,去掉最后的下划线
        if("_".equals(str.substring(str.length() - 1, str.length()))) {
            str = str.substring(0, str.length() - 1);
        }

        //替换下划线，用后一个字母的大写
        int start = str.indexOf("_");
        while(!(start < 0)) {//
            String old_char = String.valueOf(str.charAt(start + 1));
            String new_char = old_char.toUpperCase();
            str = str.replaceFirst("_" + old_char, new_char);

            start = str.indexOf("_");
        }

        return str;
    }

    /**
     * 第一个字符大写
     * @param str
     * @return
     */
    public static String upcaseFirst(String str) {
        str = str.toLowerCase();//先统一小写
        String first = str.substring(0, 1).toUpperCase();
        str = first + str.substring(1, str.length());
        return str;
    }
}
