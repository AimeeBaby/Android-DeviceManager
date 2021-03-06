package com.nd.adhoc.dmsdk.api.manager.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nd.adhoc.dmsdk.api.IDeviceManager;
import com.nd.adhoc.dmsdk.api.exception.DeviceManagerSecurityException;

/**
 * 应用数据清理
 */
public interface IApplicationManager_WipeData extends IDeviceManager {
    /**
     * 移除数据
     * @param packageName 包名
     */
    void clearData(@NonNull Context context, String packageName) throws DeviceManagerSecurityException;
}
