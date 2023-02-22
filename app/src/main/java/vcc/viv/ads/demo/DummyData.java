package vcc.viv.ads.demo;

import java.util.ArrayList;
import java.util.List;

public interface DummyData {
    /* **********************************************************************
     * Area : App Info
     ********************************************************************** */
    String APP_ID = "vcc.mobilenewsreader.kenh14";
    //    String APP_ID = "DemoSDKV2_Sprint1_ANDROID";
    String APP_NAME = "Ads Test In Sdk";
    String APP_VERSION = BuildConfig.VERSION_CODE + "";

    //    String DEVICE_ID = "8ae14c6bdc206623";A
    String DEVICE_ID = "0DC79D1A-0B08-4230-A26E-2D1D8F0878AA";

    /* **********************************************************************
     * Area : Advertising
     ********************************************************************** */
    int FAKE_ITEM_VIEW_COUNT = 12;

    String AD_BANNER_ID = "13450";
    String AD_POPUP_ID = "13462";
    String AD_CATFISH_ID = "13703";
    String AD_WELCOME_ID = "13676";
    String AD_NON_IN_PAGE_ID = "5225";
    String AD_IN_PAGE_ID = "5320";
    String AD_FLASH_ID = "";
    String AD_NATIVE_HOME_ID = "5425";
    String AD_NATIVE_DETAIL_ID = "5236";
    String AD_EMPTY = "13453";

    List<String> AD_BANNER_IDS = new ArrayList<String>() {
        {
            add(AD_BANNER_ID);
            add(AD_POPUP_ID);
            add(AD_IN_PAGE_ID);
            add(AD_NON_IN_PAGE_ID);
            add(AD_CATFISH_ID);
            add(AD_EMPTY);
        }
    };
    List<String> AD_NATIVE_IDS = new ArrayList<String>() {
        {
            add("");
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
}
