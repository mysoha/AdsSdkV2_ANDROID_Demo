package vcc.viv.ads.demo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import vcc.viv.ads.transport.lifecycle.VccLifeCycleObserver;

public class MyApplication extends Application {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private VccLifeCycleObserver observer;

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @Override
    public void onCreate() {
        super.onCreate();

        observer = new DemoLifeCycleObserver();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(observer);
    }

    /* **********************************************************************
     * Area : Function
     ********************************************************************** */
    public VccLifeCycleObserver getObserver() {
        return observer;
    }

    /* **********************************************************************
     * Area : Inner Class
     ********************************************************************** */
    private static class DemoLifeCycleObserver extends VccLifeCycleObserver {
        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            super.onStateChanged(source, event);
        }
    }
}
