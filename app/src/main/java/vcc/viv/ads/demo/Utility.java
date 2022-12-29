package vcc.viv.ads.demo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.TypedValue;

/**
 * Created by trungdunghoang125 on 12/29/2022.
 */
public class Utility {
    public static float dpToPx(Context context, float dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return px;
    }

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

    public static void browserDefault(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
