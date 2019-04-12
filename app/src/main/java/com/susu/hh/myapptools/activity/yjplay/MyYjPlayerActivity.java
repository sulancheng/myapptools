package com.susu.hh.myapptools.activity.yjplay;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.activity.BaseActivity;
import com.susu.hh.myapptools.bean.MediaItem;
import com.susu.hh.myapptools.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

public class MyYjPlayerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yj_player);
        zhifubtest();
        initview();
        initData();
    }

    private void initview() {
//        VideoPlayerView VideoPlayerView =  findViewById(R.id.exo_play_context_id);
//        VideoPlayerView.setVerticalFullScreen(false);
    }

    private void initData() {
        //得到播放地址
        Uri uri = getIntent().getData();//文件夹，图片浏览器，QQ空间
        List<MediaItem> mediaItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videolist");
        int position = getIntent().getIntExtra("position", 0);
        MyLog.i("playermyinitData", "===" + position);
        if (mediaItems != null && mediaItems.size() > 0) {
            MediaItem mediaItem = mediaItems.get(position);
//            mVideoView.setVideoPath(mediaItem.getData());
        } else if (uri != null) {
            //boolean isNetUri = CommenUtils.isNetUri(uri.toString());
//            mVideoView.setVideoURI(uri);
        } else {
            Toast.makeText(this, "没有传递数据", Toast.LENGTH_SHORT).show();
        }
    }
    private void zhifubtest() {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "", "", "json", "", "", "RSA2");
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo("");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //单纯的签名
//        AlipaySignature.rsaSign("contnet");
//        AlipaySignature.rsaSign()
    }
}
