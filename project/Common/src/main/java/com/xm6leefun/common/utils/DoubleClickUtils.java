package com.xm6leefun.common.utils;


/**
 * 多次点击控制  工具类
 */

public class DoubleClickUtils {
    private static long lastClickTime;
    private static long lastClickTime2;
    private final static int SPACE_TIME = 500;

    public static void initLastClickTime() {
        lastClickTime = 0;
    }

    //    boolean netWorkAvailable = HelpUtils.isNetWorkAvailable(LoginActivity.this);
    public synchronized static boolean isNoDoubleClick() {
//    boolean networkable = HelpUtils.isNetworkable();
//    if (!networkable)
//    {
//        Tip.getDialog(AppManager.getAppManager().currentActivity(),"请检查网络设置");
//        return false;
//    }
        long currentTime = System.currentTimeMillis();
//        long lastClickTime=0;
        long time = currentTime - lastClickTime;
        lastClickTime = currentTime;
        if (time > SPACE_TIME) {
            return true;
//            if (HelpUtils.isNetWorkAvailable())
//            {
//                return true;
////                有网络
//            }
//            else
//            {
//                Tip.getDialog(AppManager.getAppManager().currentActivity(),CommonParameter.ERROR);
//                return false;
//            }
        } else {
//            ToastUtil.show("点击太频繁，请稍后");
//        Tip.getDialog(AppManager.getAppManager().currentActivity(),"点击太频繁，请稍后");
            return false;
        }
    }

    public synchronized static boolean isNoDoubleClickNoToast() {  // 不进行提示
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastClickTime;
        lastClickTime = currentTime;
        if (time > SPACE_TIME) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 可以传入时间
     *
     * @param spaceTime
     * @return
     */
    public synchronized static boolean isSetTimeForAbbidenActionNoToast(long spaceTime) {  // 不进行提示
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastClickTime2;
        lastClickTime2 = currentTime;
        LogUtil.d(lastClickTime2 + "=============" + time + "=============" + currentTime);
        if (time > spaceTime) {
            return true;
        } else {
            return false;
        }
    }
}
