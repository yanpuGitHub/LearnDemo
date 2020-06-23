package com.yp.learndemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yp.baseframworklib.utils.DensityUtils;
import com.yp.networklib.bean.ExtDataBean;
import com.yp.networklib.log.Logger;
import com.yp.networklib.manager.BaseManager;
import com.yp.pic.ImageProxy;
import com.yp.pic.ImageUtils;

public class MainActivity extends AppCompatActivity implements BaseManager.Callback<MoveiBeean> {

    private MovieManager movieManager;
    private TextView tvText;
    private TextView tvTop;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarHelper.setStatusBarLightMode(this);
        tvTop = (TextView) findViewById(R.id.tv_top);
        applyInsets(tvTop, false);

        ivImage = (ImageView) findViewById(R.id.iv_image);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvTop.getLayoutParams();
        params.height = getStatusBarHeight() + DensityUtils.dip2px(this, 50);// + MScreenUtils.dp2px(44);
        tvTop.setLayoutParams(params);
//        tvTop.setLayoutParams(params);
        StatusBarHelper.translucent(this, 0x00000000);

        tvText = (TextView) findViewById(R.id.tv_text);
        movieManager = new MovieManager();

    }

    public void onClick(View view) {
        movieManager.dateBoxOffice(this);

        ImageUtils.with_clipType(this, ImageProxy.SizeType.CUSTOM_SIZE)
                .view(ivImage)
                .load("http://piaoshen.oss-cn-beijing.aliyuncs.com/movie/images/2020/01/10/A23F401F76C88C4E56A8.jpg")
                .showload();

    }

    @Override
    public void onSuccess(@Nullable MoveiBeean bean, @Nullable ExtDataBean extBean) {
        Logger.w(bean);
        Log.e("TAG", "onSuccess: " + bean.getCurPage());
        tvText.setText(bean.toString());
    }

    @Override
    public void onError(int code, String str, @Nullable ExtDataBean extBean) {
        Logger.w(str);
    }

    /**
     * 适配沉浸式状态栏，为view预留出状态拦的高度
     *
     * @param view
     * @param changeHeight 改变view的高度来达到适配
     */
    public void applyInsets(View view, final boolean changeHeight) {
        if (null != view) {
            ViewCompat.requestApplyInsets(view);
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() {
                @Override
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    Object applied = v.getTag(R.id.view_tag_apply_insets);
                    if (null == applied) {
                        v.setTag(R.id.view_tag_apply_insets, true);
                        if (changeHeight) {
                            v.getLayoutParams().height += insets.getSystemWindowInsetTop();
                        }
                        v.setPadding(v.getPaddingLeft(), v.getPaddingTop() + insets.getSystemWindowInsetTop(),
                                v.getPaddingRight(), v.getPaddingBottom() + insets.getSystemWindowInsetBottom());
                    }
                    return insets;
                }
            });
        }
    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = this.getResources().getDimensionPixelSize(resourceId);
        }
        Logger.w("get status bar height is " + statusBarHeight);
        return statusBarHeight;
    }
}
