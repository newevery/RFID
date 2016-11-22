package com.xe.lzh.rfid.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xe.lzh.rfid.R;
import com.xe.lzh.rfid.Utils.HttpUtils;
import com.xe.lzh.rfid.Utils.RFIDUtils;
import com.xe.lzh.rfid.Utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public abstract class BaseActivity extends FragmentActivity implements HttpUtils.PostCallBack {
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_name)
    TextView tb_name;
    @ViewInject(R.id.tv_time)
    TextView tv_time;
    Context content;

    public void back(View view) {
        finish();
    }

    public void setting(View view) {
    }

    public void settext(String str) {

        Date date = new Date();
        String datestr = RFIDUtils.sdf.format(date);
        System.out.println(str + datestr);
        tv_time.setText(datestr);
        tv_title.setText(str);
    }

    public void doNetWork(RequestParams params, int requestcode) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.setPostcallback(this);
        httpUtils.dopost(params, requestcode, this);
        List<KeyValue> bodyParams = params.getBodyParams();
        for (KeyValue k : bodyParams) {
            System.out.println(" BaseActivity " + k.getValueStr() + "  " + k.key);
        }
        System.out.println("doNetWork " + params.toString());
    }

    @Override
    public void onSuccess(String s, int requestcode) {
        try {
            if (s!=null&&!"".equals(s)){
                JSONObject jsonObject = new JSONObject(s);
                String resultcode = jsonObject.getString("resultcode");
                if (resultcode.equals("200")) {
                    String data = jsonObject.getString("data");
                    setdata(data, requestcode);
                }
            }else {
                Toast.makeText(this,"服务器无数据",Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailture(Throwable throwable, boolean b, int requestcode) {

    }

    public abstract void setdata(String data, int requestcode);
}
