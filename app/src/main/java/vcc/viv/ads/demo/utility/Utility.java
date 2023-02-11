package vcc.viv.ads.demo.utility;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.TypedValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private final static String REGEX = "/game/";
    private final static String INVALID_REGEX = "[^a-zA-Z0-9\\\\-]";
    private final static String REPLACE = "-";

    /* **********************************************************************
     * Area : Function - convert
     ********************************************************************** */
    public static float dpToPx(Context context, float dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return px;
    }

    /* **********************************************************************
     * Area : Function - Dynamic
     ********************************************************************** */
    public static boolean isDynamicLink(String url) {
        String[] params = url.split("\\?");
        for (String param : params) {
            if (param.contains("dynamiclink")) {
                String value = param.split("=")[1];
                if (value.equals("true")) return true;
            }
        }
        return false;
    }

    public static String createDynamicLink(String host, String url) {
        Pattern patternRegex = Pattern.compile(REGEX);
        Matcher matcherRegex = patternRegex.matcher(url);
        StringBuffer game = new StringBuffer();
        if (matcherRegex.find()) {
            matcherRegex.replaceFirst("");
            matcherRegex.appendTail(game);
        }

        Pattern patternInvalidRegex = Pattern.compile(INVALID_REGEX);
        Matcher matcherInvalidRegex = patternInvalidRegex.matcher(game);
        StringBuffer gameRegex = new StringBuffer();
        while (matcherInvalidRegex.find()) {
            matcherInvalidRegex.appendReplacement(gameRegex, REPLACE);
        }
        matcherInvalidRegex.appendTail(gameRegex);

        return String.format("%s%s?l=%s", host, gameRegex, url);
    }

    /* **********************************************************************
     * Area : Function - browser
     ********************************************************************** */
    public static void browserDefault(Context context, String url) {
        try {
            android.content.Intent defaultBrowser = new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(defaultBrowser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
