package com.share.jack.friendcircledemo.publish;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.share.jack.cygtool.http.callback.CygSubscriberApi;
import com.share.jack.cygtool.util.BitmapUtil;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.cygtool.util.CygStringUtil;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.login.model.UserSession;
import com.share.jack.friendcircledemo.publish.loader.GlideLoader;
import com.share.jack.friendcircledemo.publish.model.PublishModel;
import com.share.jack.friendcircledemo.widget.CustomToolbar;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class PublishActivity extends BaseActivity {

    @Bind(R.id.ap_select_image)
    ImageView apSelectImage;
    @Bind(R.id.publish_titlebar)
    CustomToolbar toolbar;
    @Bind(R.id.ap_et_content)
    EditText apEtContent;

    public static final int REQUEST_CODE = 0x001;
    private List<String> pathList;

    private boolean isSelectPic;

    public static void start(Context context) {
        CygActivity.start(context, PublishActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);

        toolbar.setMainTitle("发动态");
        toolbar.setMainTitleLeftText("返回");
        toolbar.setMainTitleLeftDrawable(R.mipmap.icon_back);
        toolbar.setTvMainTitleLeftOnClick(thisActivity());
        toolbar.setMainTitleRightText("提交");
        toolbar.setTvMainTitleRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CygStringUtil.getEditTextContent(apEtContent).isEmpty()) {
                    submitPublish();
                } else {
                    Toast.makeText(thisActivity(), "内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        apSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelectPic) {
                    ImageConfig imageConfig = new ImageConfig.Builder(new GlideLoader())
                            .titleBgColor(ContextCompat.getColor(thisActivity(), R.color.title_bar_background))
                            .singleSelect()
                            .showCamera()
                            .crop()
                            .filePath("/FriendCircle/images")
                            .requestCode(REQUEST_CODE)
                            .build();
                    ImageSelector.open(thisActivity(), imageConfig);
                    isSelectPic = true;
                }
            }
        });
    }

    private void submitPublish() {
        String content = CygStringUtil.getEditTextContent(apEtContent);
        String image = "";
        if (pathList != null && pathList.size() > 0) {
            image = BitmapUtil.bitmapToBase64(BitmapFactory.decodeFile(pathList.get(0)));
        }
        Map<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(UserSession.getUserProfile().getId()));
        params.put("content", content);
        params.put("image", image);
        PublishModel.getInstance().execute(params, new CygSubscriberApi<Void>(thisActivity(), true) {
            @Override
            protected void onBaseNext(Void data) {
                Toast.makeText(thisActivity(), "发布成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (pathList != null) {
                Glide.with(thisActivity())
                        .load(new File(pathList.get(0)))
                        .into(apSelectImage);
            } else {
                Toast.makeText(thisActivity(), "选择图片出错", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
