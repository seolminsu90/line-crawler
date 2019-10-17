package com.crawler.line.util;

public class CommonUtil {
    public static Integer getPureNumber(String str) throws NumberFormatException {
        String replace = str.replaceAll("\\D+", "");
        if ("".equals(replace)) {
            return 0;
        } else {
            return Integer.parseInt(replace);
        }
    }
}
