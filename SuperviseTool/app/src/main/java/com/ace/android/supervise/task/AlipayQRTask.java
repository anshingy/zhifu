package com.ace.android.supervise.task;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.ace.android.supervise.consts.AppConst;
import com.ace.android.supervise.module.model.bean.ExpandAccessibilityNodeInfo;
import com.ace.android.supervise.module.model.bean.QRCodeBean;
import com.ace.android.supervise.utils.AccessibilityServiceHelper;
import com.ace.android.supervise.utils.LogUtil;

/**
 * Created by ace on 2018/10/30.
 */
public class AlipayQRTask {

    private static final long SLEEP_TIME = 500L;

    /**
     * 因为支付宝首页更多菜单（“+”）与收钱菜单冲突（resId一样），单独拧出来处理
     * 处理方法是先找到惟一redId的子控件按钮“更多操作”，因为按钮不可点击，所以点击事件传递到父控件
     * @param info
     */
    public void clickMoreMenu(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            String desc = info.getContentDescription() == null ? null : info.getContentDescription().toString();
            if ("更多操作".equals(desc)) {
                AccessibilityServiceHelper.performClick(info.getParent());
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    clickMoreMenu(info.getChild(i));
                }
            }
        }
    }

    /**
     * 模拟点击方法
     * @param target
     */
    public void clickTargetView(ExpandAccessibilityNodeInfo target) {
        AccessibilityNodeInfo accessibilityNodeInfo = target.getAccessibilityNodeInfo();
        if (accessibilityNodeInfo != null) {
            LogUtil.i("点击了“" + target.getId() + "”按钮...");
            // 模拟点击
            AccessibilityServiceHelper.performClick(accessibilityNodeInfo);
            sleep(SLEEP_TIME);
        }
    }

    /**
     * 模拟输入收款金额和收款理由
     *
     * @param context
     * @param mCodeBean
     * @param target
     * @param nodeInfo
     */

    public void inputQRCodeInfo(Context context, QRCodeBean mCodeBean,
                                ExpandAccessibilityNodeInfo target, AccessibilityNodeInfo nodeInfo) {
        AccessibilityNodeInfo reasonInput = AccessibilityServiceHelper
                .findAccessibilityNodeInfosById(nodeInfo, AppConst.REASON_RELATIVELAYOUT_ID);
        // get amount edittext
        AccessibilityNodeInfo amountInput = AccessibilityServiceHelper
                .findAccessibilityNodeInfosById(nodeInfo, AppConst.MONEY_RELATIVELAYOUT_ID);
        AccessibilityNodeInfo submitButton = AccessibilityServiceHelper
                .findAccessibilityNodeInfosById(nodeInfo, AppConst.SUBMIT_ID);
        if (reasonInput == null || submitButton == null || amountInput == null) {
            return;
        }
        // 模拟输入金额
        AccessibilityServiceHelper.performInput(context, amountInput.getChild(1),"amount", mCodeBean.getAmount());
        // thread sleep
        sleep(SLEEP_TIME);
        // 模拟输入理由
        AccessibilityServiceHelper.performInput(context, reasonInput.getChild(1),"associatedCode", mCodeBean.getAssociatedCode());
        // thread sleep
        sleep(SLEEP_TIME);
        // 模拟点击确定按钮
        AccessibilityServiceHelper.performClick(submitButton);
    }

    /**
     * 模拟输入帐号密码进行登录
     * @param context
     * @param target
     * @param nodeInfo
     */
    public void inputLoginAlipay(Context context, ExpandAccessibilityNodeInfo target, AccessibilityNodeInfo nodeInfo){
        // get amount edittext
        AccessibilityNodeInfo amountInput = AccessibilityServiceHelper.findAccessibilityNodeInfosById(nodeInfo, AppConst.LOGIN_ALIPAY_ACCOUNT_ID);
        AccessibilityNodeInfo submitButton = AccessibilityServiceHelper.findAccessibilityNodeInfosById(nodeInfo, AppConst.SUBMIT_ID);
        if ( submitButton == null || amountInput == null) {
            return;
        }
        // 模拟输入金额
        AccessibilityServiceHelper.performInput(context, amountInput.getChild(1),"amount", "18574406231");
        // thread sleep
        sleep(SLEEP_TIME);
    }

    /**
     * 模拟点击返回键
     *
     * @param service
     */
    public void goGlobalBack(AccessibilityService service) {
        AccessibilityServiceHelper.performBack(service);
        sleep(SLEEP_TIME);
    }

    /**
     * 每一次点击后停顿一段时间
     *
     * @param time
     */

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
