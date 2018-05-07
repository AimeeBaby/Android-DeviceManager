package com.nd.adhoc.dmsdk.demo.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.nd.adhoc.dmsdk.demo.model.impl.DeviceControlModel;
import com.nd.adhoc.dmsdk.demo.presenter.BasePresenter;
import com.nd.adhoc.dmsdk.demo.presenter.IDeviceControlPresenter;
import com.nd.adhoc.dmsdk.demo.utils.RxJavaUtils;
import com.nd.adhoc.dmsdk.demo.view.DeviceControlView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class DeviceControlPresenter extends BasePresenter<DeviceControlView,DeviceControlModel> implements IDeviceControlPresenter {


    public DeviceControlPresenter(Context context, DeviceControlView view) {
        super(context, view);
    }

    /**
     * 获取参数列表
     */
    public void getDeviceControlList(){

        Observable.create(new Observable.OnSubscribe<List>() {
            @Override
            public void call(Subscriber<? super List> subscriber) {
                List list=modle.getList();
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).compose(RxJavaUtils.<List>applyDefaultSchedulers()).subscribe(new Subscriber<List>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List list) {

                view.showList(list);
            }


        });
    }

    @Override
    public void onClick(final int position) {

        if(position==1){
            modle.update(position,true);
            view.updateView(position);
            view.jumpHome();
            return;
        }
        //从本地读取数据操作
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(modle.updateStatus(position));
                subscriber.onCompleted();
            }
        }).compose(RxJavaUtils.<Boolean>applyDefaultSchedulers()).subscribe(new Subscriber<Boolean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Boolean success) {
                if(success) {
                    modle.update(position,true);
                    view.updateView(position);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        modle=null;
        super.onDestroy();
    }
}
