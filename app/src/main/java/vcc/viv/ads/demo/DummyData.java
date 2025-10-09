package vcc.viv.ads.demo;

import java.util.ArrayList;
import java.util.List;

public interface DummyData {
    /* **********************************************************************
     * Area : App Info
     ********************************************************************** */
    String APP_ID = "";
    //    String APP_ID = "DemoSDKV2_Sprint1_ANDROID";
    String APP_NAME = "Ads Test In Sdk";
    String APP_VERSION = "";

    String DEVICE_ID = "";

    /* **********************************************************************
     * Area : Advertising
     ********************************************************************** */
    int FAKE_ITEM_VIEW_COUNT = 12;

    String AD_BANNER_ID = "";
    String AD_POPUP_ID = "";
    String AD_CATFISH_ID = "";
    String AD_WELCOME_ID = "";
    String AD_NON_IN_PAGE_ID = "";
    String AD_IN_PAGE_ID = "";
    String AD_FLASH_ID = "";
    String AD_NATIVE_HOME_ID = "";
    String AD_NATIVE_DETAIL_ID = "";
    String AD_EMPTY = "";

    String AD_STREAM_ID = "";
    String AD_BANNER_SCROLLABLE_ID = "";

    List<String> AD_BANNER_IDS = new ArrayList<String>() {
        {
            add(AD_BANNER_ID);
            add(AD_POPUP_ID);
            add(AD_IN_PAGE_ID);
            add(AD_STREAM_ID);
            add(AD_NON_IN_PAGE_ID);
            add(AD_EMPTY);
            add(AD_CATFISH_ID);
        }
    };

    List<String> adMixedIds = new ArrayList<String>() {
        {
            add(AD_BANNER_ID);
            add(AD_POPUP_ID);
            add(AD_CATFISH_ID);
            add(AD_WELCOME_ID);
            add(AD_IN_PAGE_ID);
            add(AD_NON_IN_PAGE_ID);
            add(AD_FLASH_ID);
        }
    };

    List<String> AD_SCROLLABLE_IDS = new ArrayList<String>() {
        {
            add(AD_BANNER_SCROLLABLE_ID);
            add(AD_STREAM_ID);
            add(AD_BANNER_SCROLLABLE_ID);
        }
    };
}