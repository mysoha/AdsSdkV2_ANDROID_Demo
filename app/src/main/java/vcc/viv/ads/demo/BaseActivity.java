package vcc.viv.ads.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private boolean doubleBackToExitPressedOnce = false;
    private Context context;

    /* **********************************************************************
     * Area : Override
     ********************************************************************** */
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
