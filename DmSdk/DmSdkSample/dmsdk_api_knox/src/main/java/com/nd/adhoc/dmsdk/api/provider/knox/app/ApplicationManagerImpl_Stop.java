package com.nd.adhoc.dmsdk.api.provider.knox.app;

import android.app.admin.DevicePolicyManager;
import android.app.enterprise.ApplicationPolicy;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;

import com.nd.adhoc.dmsdk.api.BaseManager;
import com.nd.adhoc.dmsdk.api.exception.DeviceManagerSecurityException;
import com.nd.adhoc.dmsdk.api.exception.ErrorCode;
import com.nd.adhoc.dmsdk.api.manager.app.IApplicationManager_Run;
import com.nd.adhoc.dmsdk.api.manager.app.IApplicationManager_Stop;
import com.nd.adhoc.dmsdk.api.provider.knox.KnoxDeviceManagerFactory;

public class ApplicationManagerImpl_Stop extends BaseManager implements IApplicationManager_Stop {

    public ApplicationManagerImpl_Stop(@NonNull DevicePolicyManager devicePolicyManager, @NonNull ComponentName componentName) {
        super(devicePolicyManager, componentName);
    }

    public ApplicationManagerImpl_Stop(@NonNull DevicePolicyManager devicePolicyManager) {
        super(devicePolicyManager);
    }

    @Override
    public void release(@NonNull Context context) {

    }

    @Override
    public void stopApp(@NonNull Context context,String packageName) throws DeviceManagerSecurityException {

        ApplicationPolicy applicationPolicy=KnoxDeviceManagerFactory.getInstance().getApplicationPolicy(context);
        if(applicationPolicy==null){
            throw  new DeviceManagerSecurityException(ErrorCode.ERROR_CODE_CONSTRUCT_NO_INSTANCE);
        }
        //TODO zyb 此处最高异常待定，需要核对API
        try {
            boolean isSuccess= applicationPolicy.stopApp(packageName);
            if(!isSuccess){
                throw  new DeviceManagerSecurityException(ErrorCode.ERROR_CODE_CONSTRUCT_NO_INSTANCE);
            }
        }catch (Exception e){
            throw  new DeviceManagerSecurityException(ErrorCode.ERROR_CODE_CONSTRUCT_NO_INSTANCE);
        }
    }
}