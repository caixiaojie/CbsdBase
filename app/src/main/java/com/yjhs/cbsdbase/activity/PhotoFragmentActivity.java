package com.yjhs.cbsdbase.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yjhs.cbsd.mvp.BaseActivity;
import com.yjhs.cbsdbase.R;
import com.yjhs.cbsdbase.fragment.PhotoFragment;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.Nullable;

/**
 * author: Administrator
 * date: 2019-11-13
 * desc:
 */
public class PhotoFragmentActivity extends BaseActivity {
    private PhotoFragment fragment;
    @Override
    public int getLayout() {
        return R.layout.activity_simp;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void start() {

    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
// 在部分低端手机，调用单独拍照时内存不足时会导致activity被回收，所以不重复创建fragment
        if (savedInstanceState == null) {
            // 添加显示第一个fragment
            fragment = new PhotoFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.tab_content, fragment,
                    PictureConfig.FC_TAG).show(fragment)
                    .commit();
        } else {
            fragment = (PhotoFragment) getSupportFragmentManager()
                    .findFragmentByTag(PictureConfig.FC_TAG);
        }
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(PhotoFragmentActivity.this,
                            PictureMimeType.ofImage());
                } else {
                    Toast.makeText(PhotoFragmentActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
