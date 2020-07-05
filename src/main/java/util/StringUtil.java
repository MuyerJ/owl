package util;

import java.util.Optional;

public class StringUtil {
    public static String getNotNullStr(String str) {
        if ("null".equals(str)){
            return "";
        }
        return Optional.ofNullable(str).orElse("");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.equals("");
    }
}
