package com.xe.lzh.rfid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xe.lzh.rfid.Model.DeatailModel;
import com.xe.lzh.rfid.R;
import com.xe.lzh.rfid.Utils.RFIDUtils;
import com.xe.lzh.rfid.Utils.SpUtils;
import com.xe.lzh.rfid.Utils.ZhuangTaiUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/31 0031.
 * 展示扫码得到的EPC具体信息
 */


@ContentView(R.layout.activity_xiangqing)
public class XiangqingActivity extends BaseActivity {
    @ViewInject(R.id.tv_kuhao)
    TextView kuhao;
    @ViewInject(R.id.tv_danghao)
    TextView danghao;
    @ViewInject(R.id.tv_zhuangtai)
    TextView zhuangtai;
    @ViewInject(R.id.tv_jieyueren)
    TextView jieyueren;
    @ViewInject(R.id.tv_shijian)
    TextView shijian;
    @ViewInject(R.id.tv_ztm)
    TextView tv_ztm;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.ll_jyr)
    LinearLayout ll_jyr;
    String loginUser_id;
    String loginUser_name;
    private String EPC;
    private DeatailModel deatailModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

//        EPC = getIntent().getStringExtra("EPC");
//        EPC = "2015-DQ11-Y-3,211832,218688,";
//        Intent intent = this.getIntent();
        EPC = getIntent().getStringExtra("EPC");

//        this.EPC = (String) intent.getSerializableExtra("EPC");

        loginUser_id = (String) SpUtils.get(this, "userid", "1");
        loginUser_name = (String) SpUtils.get(this, "username", "操作人");
        tv_name.setText("欢迎您，"+loginUser_name);

        doNet();
    }

    @Override
    protected void onResume() {
        super.onResume();
        settext("详情");

    }

    //    @Event(value = {R.id.bt_guanbi})
//    private void Click(View v) {
//        switch (v.getId()) {
//            case R.id.bt_guanbi:
//                finish();
//                break;
//        }
//    }
    private void doNet() {
        System.out.println("EPC "+EPC);
        RequestParams params = new RequestParams(RFIDUtils.FINDDETAIL);
        params.addBodyParameter("EPC", "2015-DQ11-Y-3,5164396,341849,");
        doNetWork(params, 0);
    }

    @Override
    public void setdata(String data, int requestcode) {
        switch (requestcode) {
            case 0:

                try {
                    JSONObject js = new JSONObject(data);
                    danghao.setText(js.getString("DH"));
                    System.out.println(js.toString());
                    if (!js.getString("state").equals("1")) {
                        jieyueren.setText(js.getString("jyrname"));
                    } else {
                        ll_jyr.setVisibility(View.GONE);
                    }
                    zhuangtai.setText(ZhuangTaiUtils.transOrderStatusCode(Integer.parseInt(js.getString("state"))));
                    kuhao.setText(String.valueOf(js.get("GROUPID")));
                    tv_ztm.setText(js.getString("ZTM"));
                    String strdate = js.getString("UPDATETIME");
//                    System.out.println("sds"+strdate);
//                    Date date = RFIDUtils.sdf1.parse(strdate);
//                    String format = RFIDUtils.sdf.format(date);
                    System.out.println(strdate.substring(0, strdate.length() - 2));
                    shijian.setText(strdate.substring(0, strdate.length() - 2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}