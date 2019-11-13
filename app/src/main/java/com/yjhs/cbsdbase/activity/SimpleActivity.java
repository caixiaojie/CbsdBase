package com.yjhs.cbsdbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.yjhs.cbsd.mvp.BaseActivity;
import com.yjhs.cbsdbase.R;
import org.jetbrains.annotations.Nullable;

/**
 * author: Administrator
 * date: 2019-11-13
 * desc:
 */
public class SimpleActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_activity, btn_fragment;
    @Override
    public int getLayout() {
        return R.layout.activity_other;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        btn_activity = findViewById(R.id.btn_activity);
        btn_fragment = findViewById(R.id.btn_fragment);
        btn_activity.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_activity:
                intent = new Intent(SimpleActivity.this, PhotoMainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_fragment:
                intent = new Intent(SimpleActivity.this, PhotoFragmentActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
