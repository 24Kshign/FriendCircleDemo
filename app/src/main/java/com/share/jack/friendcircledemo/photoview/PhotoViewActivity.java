package com.share.jack.friendcircledemo.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.cygtool.util.CygStringUtil;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 *
 */
public class PhotoViewActivity extends BaseActivity {

    @Bind(R.id.ah_pv_photo)
    PhotoView ahPvPhoto;

    public static void start(Context context, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        CygActivity.start(context, PhotoViewActivity.class, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        String url = getIntent().getStringExtra("url");
        if (!CygStringUtil.isEmpty(url)) {
            loadNetImage(url);
        } else {
            loadLocalImage();
        }
    }

    /**
     * 加载本地图片
     */
    private void loadLocalImage() {
//       加载本地图片，缩放处理
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_splash);
        ahPvPhoto.setImageBitmap(bm);
    }

    /**
     * 加载网络图片
     */
    private void loadNetImage(String url) {
        Glide.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ahPvPhoto);
        ahPvPhoto.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                Toast.makeText(thisActivity(), "图片被点击了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOutsidePhotoTap() {
                finish();
            }
        });

    }

}
