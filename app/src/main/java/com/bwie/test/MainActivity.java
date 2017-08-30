package com.bwie.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.test.adapter.MyXListAdapter;
import com.bwie.test.api.API;
import com.bwie.test.bean.Bean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.xlv_list) XListView xlv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        RequestParams params=new RequestParams(API.URL_POST);
        params.addBodyParameter("key",API.KEY);
        params.addBodyParameter("type",API.TYPE);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //请求成功   打印json串
                System.out.println("result============="+result);
                //解析json
                getJson(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析json串
     * @param result
     */
    private void getJson(String result) {
        Gson gson=new Gson();
        Bean bean = gson.fromJson(result, Bean.class);
        List<Bean.ResultBean.DataBean> list = bean.getResult().getData();
        for (Bean.ResultBean.DataBean b:list) {
            System.out.println("bean============="+b.getTitle()+"  "+b.getThumbnail_pic_s()+"  "+b.getCategory()+"  "+b.getDate());
        }

        //添加适配器
        MyXListAdapter myAdapter=new MyXListAdapter(this,list);
        xlv_list.setAdapter(myAdapter);
    }
}
