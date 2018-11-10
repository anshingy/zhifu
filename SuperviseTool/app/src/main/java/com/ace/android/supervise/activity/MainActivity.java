package com.ace.android.supervise.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ace.android.supervise.R;
import com.ace.android.supervise.module.model.bean.AlipayAmountEvent;
import com.ace.android.supervise.module.presenter.MainPresenter;
import com.ace.android.supervise.module.view.IMainView;
import com.ace.android.supervise.utils.DialogUtils;
import com.ace.android.supervise.utils.SnackbarUtil;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView {
    @BindView(R.id.tv_info)
    TextView mTextView;
    @BindView(R.id.btn_service)
    Button mServiceButton;
    @BindView(R.id.edit_amount)
    TextInputEditText mAmountEditText;
    @BindView(R.id.btn_submit)
    Button mSubmitButton;
    private MainPresenter mMainPresenter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(this);
        // check runtime permissions
        mMainPresenter.checkPermissions(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(!checkAliPayInstalled(this)) {
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.btn_service,R.id.btn_submit,R.id.btn_notification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_service:
                openAccessibilityServiceSettings();
                break;
            case R.id.btn_submit:

                for(int i=1;i<5000;i++){
                    final BigDecimal amount = new BigDecimal(i+"");
                    EventBus.getDefault().postSticky(new AlipayAmountEvent(amount));
                }
               /* final BigDecimal amount = new BigDecimal(mAmountEditText.getText().toString().trim());
                EventBus.getDefault().postSticky(new AlipayAmountEvent(amount));*/
                break;
            case R.id.btn_notification:
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showOpenServiceDialog() {
        // 显示打开辅助服务的对话框
        DialogUtils.showMessage(this, R.string.use_tip, R.string.open_additional_function_service,
        true, R.string.open_function, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAccessibilityServiceSettings();
            }
        });
    }

    @Override
    public void checkService() {
        // check accessibilityservice
        mMainPresenter.checkService();
    }

    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

        @Override
    public void showErrorMessage(String errorMsg) {
        SnackbarUtil.showSnackbar(mTextView, errorMsg, Snackbar.LENGTH_SHORT);
    }

    // 打开辅助服务的设置
    private void openAccessibilityServiceSettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
