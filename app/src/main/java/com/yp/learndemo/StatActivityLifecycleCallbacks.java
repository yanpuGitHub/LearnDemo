package com.yp.learndemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author senrsl
 * @ClassName: StatActivityLifecycleCallbacks
 * @Package: com.jydata.monitor.stat
 * @CreateTime: 2019/12/2 4:43 PM
 */
public class StatActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
//        new Handler().post(() -> {
//            String pageId = getPageId(activity);
//            if (null != pageId) TalkingDataUtils.onVisible(activity, pageId);
//        });
    }

    @Override
    public void onActivityPaused(Activity activity) {
//        new Handler().post(() -> {
//            String pageId = getPageId(activity);
//            if (null != pageId) TalkingDataUtils.onInvisible(activity, pageId);
//        });
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    private String getPageId(Activity activity) {
        String pageId = null;

//        if (activity instanceof PublisherActivity) {
//            pageId = ((PublisherActivity) activity).getPageId();
//        } else if (activity instanceof LogonPublisherActivity) {
//            pageId = StatContext.PAGE_LOGON;
//        } else if (activity instanceof PlanSelfHelpActivity) {
//            pageId = StatContext.PAGE_PLAN_MANUAL_CONFIG;
//        } else if (activity instanceof PlanCinemaActivity) {
//            if (DISPLAY_TYPE_MAP == ((PlanCinemaActivity) activity).getDisplayType())
//                pageId = ((PlanCinemaActivity) activity).getSourceType() == PLAN_GENERATE_TYPE_MANUAL ? StatContext.PAGE_PLAN_MANUAL_CINEMA_MAP : StatContext.PAGE_PLAN_AROUND_MAP;
//
//        } else if (activity instanceof PlanPutDetailActivity) {
//            pageId = StatContext.PAGE_PLAN_MANUAL_PREVIEW;
//        } else if (activity instanceof PlanCreateSuccessActivity) {
//            pageId = StatContext.PAGE_PLAN_CREATED;
//        } else if (activity instanceof AutoPlanActivity) {
//            pageId = StatContext.PAGE_PLAN_AUTO_DESC;
//        } else if (activity instanceof AutoPutAreaActivity) {
//            pageId = StatContext.PAGE_PLAN_AUTO_AREA;
//        } else if (activity instanceof AutoPutIndustryActivity) {
//            pageId = StatContext.PAGE_PLAN_AUTO_INDUSTRY;
//        } else if (activity instanceof ReleasedCalendarActivity) {
//            pageId = StatContext.PAGE_PLAN_SCHEDULE_CONFIG;
//        } else if (activity instanceof OrderListActivity) {
//            pageId = StatContext.TAB_ORDER_LIST;
//        } else if (activity instanceof OrderDetailActivity) {
//            pageId = StatContext.PAGE_ORDER_DETAIL;
//        } else if (activity instanceof WalletDetailActivity) {
//            pageId = StatContext.PAGE_WALLET_DETAIL;
//        } else if (activity instanceof CashierDeskActivity) {
//            pageId = StatContext.PAGE_PAY_SELECTION;
//        } else if (activity instanceof SubmitVoucherSuccessActivity) {
//            pageId = StatContext.PAGE_PAY_SUSS;
//        } else if (activity instanceof PollPayInfoActivity) {
//            pageId = StatContext.PAGE_PAY_SUSS_THRID;
//        } else if (activity instanceof AdListActivity) {
//            pageId = StatContext.PAGE_AD_LIST;
//        } else if (activity instanceof UploadAdModeActivity) {
//            pageId = StatContext.PAGE_AD_UPLOAD;
//        } else if (activity instanceof MessageListActivity) {
//            pageId = StatContext.PAGE_MESSAGE_UPLOAD;
//
//            //yanpu
//        } else if (activity instanceof CitySelectActivity) {
//            pageId = StatContext.PAGE_CITY_SELECT;
//        } else if (activity instanceof CalendarSelectActivity) {
//            pageId = StatContext.PAGE_CALENDAR_SELECT;
//        } else if (activity instanceof CinemaFilterActivity) {
//            pageId = StatContext.PAGE_CIENMA_FILTER;
//        } else if (activity instanceof RelatedAdListActivity) {
//            pageId = StatContext.PAGE_ORDER_RELATED_AD;
//        } else if (activity instanceof ReportCinemaListActivity) {
//            pageId = StatContext.PAGE_REPORT_CINEMA_LIST;
//        } else if (activity instanceof ReportMovieListActivity) {
//            pageId = StatContext.PAGE_REPORT_MOVIE_LIST;
//        } else if (activity instanceof ReportCityListActivity) {
//            pageId = StatContext.PAGE_REPORT_CITY_LIST;
//        } else if (activity instanceof ReportExposureListActivity) {
//            pageId = StatContext.PAGE_REPORT_EXPOSURE_LIST;
//        } else if (activity instanceof AboutActivity) {
//            pageId = StatContext.PAGE_ABOUT_US;
//        } else if (activity instanceof SettingActivity) {
//            pageId = StatContext.PAGE_SETTING;
//        } else if (activity instanceof UploadLocalVideoActivity) {
//            pageId = StatContext.PAGE_UPLOAD_LOCAL_VIEDO;
//        } else if (activity instanceof UploadNetdiscVideoActivity) {
//            pageId = StatContext.PAGE_UPLOAD_NETDISC_VIDEO;
//        } else if (activity instanceof RechargeActivity) {
//            pageId = StatContext.PAGE_RECHARGE;
//        } else if (activity instanceof TransferListActivity) {
//            pageId = StatContext.PAGE_TRANSFER_LIST;
//
//            //situation
//        } else if (activity instanceof SituationActivity) {
//            pageId = StatContext.PAGE_SITUATION;
//        } else if (activity instanceof HeatSongTabActivity) {
//            pageId = StatContext.PAGE_HEAT_SONG_LIST;
//        } else if (activity instanceof BrandHeatListActivity) {
//            pageId = StatContext.PAGE_HEAT_BRAND_LIST;
//        } else if (activity instanceof MovieHeatListActivity) {
//            pageId = StatContext.PAGE_HEAT_MOVIE_LIST;
//        } else if (activity instanceof TvHeatListActivity) {
//            pageId = StatContext.PAGE_HEAT_TV_LIST;
//        } else if (activity instanceof ActorHeatListActivity) {
//            pageId = StatContext.PAGE_HEAT_ACOTR_LIST;
//        } else if (activity instanceof UserCenterActivity) {
//            pageId = StatContext.PAGE_MINE;
//        }
//
//        Logger.w(getClass().getSimpleName(), TAG_STAT, pageId);

        return pageId;
    }
}
