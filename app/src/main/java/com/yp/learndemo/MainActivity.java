package com.yp.learndemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yp.networklib.bean.ExtDataBean;
import com.yp.networklib.log.Logger;
import com.yp.networklib.manager.BaseManager;

public class MainActivity extends AppCompatActivity implements BaseManager.Callback<MoveiBeean> {

    private MovieManager movieManager;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = (TextView) findViewById(R.id.tv_text);
        movieManager = new MovieManager();

    }

    public void onClick(View view) {
        movieManager.dateBoxOffice(this);

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
}
