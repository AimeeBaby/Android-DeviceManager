package com.nd.adhoc.dmsdk.api.provider.knox.hardware;
import android.app.enterprise.RestrictionPolicy;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import com.nd.adhoc.dmsdk.api.exception.DeviceManagerSecurityException;
import com.nd.adhoc.dmsdk.api.exception.ErrorCode;
import com.nd.adhoc.dmsdk.api.manager.hardware.IBluetoothManager;
import com.nd.adhoc.dmsdk.api.provider.knox.KnoxDeviceManagerFactory;

public class BluetoothManagerImpl implements IBluetoothManager {

    @Override
    public void open(@NonNull Context context) throws DeviceManagerSecurityException {
        turnOff(context,true);
        enableBluetooth();
    }

    @Override
    public void close(@NonNull Context context) throws DeviceManagerSecurityException {
        turnOff(context,false);
    }

    @Override
    public boolean isOpen(@NonNull Context context) throws DeviceManagerSecurityException {
        RestrictionPolicy restrictionPolicy= verifyIsNull(context);
        return restrictionPolicy.isBluetoothEnabled(true);
    }

    @Override
    public void release(@NonNull Context context) {

    }


    private void turnOff(@NonNull Context context,boolean isOpen) throws DeviceManagerSecurityException {
        RestrictionPolicy restrictionPolicy= verifyIsNull(context);
        try {
            boolean isSuccess=restrictionPolicy.allowBluetooth(isOpen);
            if(!isSuccess){
                //TODO zyb 此处需要定义ErrorCode中的枚举值为开启失败
                throw  new DeviceManagerSecurityException(ErrorCode.ERROR_CODE_CONSTRUCT_NO_INSTANCE);
            }
        }catch (SecurityException e){
            //TODO zyb 此处需要定义ErrorCode中的枚举值为开启失败
            throw new DeviceManagerSecurityException(ErrorCode.ERROR_CODE_CONSTRUCT_NO_INSTANCE);
        }
    }

    /**
     * 校验从工厂中获取到数值是否为空
     * @return
     * @throws DeviceManagerSecurityException
     */
    private RestrictionPolicy verifyIsNull(@NonNull Context context) throws DeviceManagerSecurityException {
        RestrictionPolicy restrictionPolicy= KnoxDeviceManagerFactory.getInstance().getRestrictionPolicy(context);
        if(restrictionPolicy==null){
            throw  new DeviceManagerSecurityException(ErrorCode.ERROR_CODE_CONSTRUCT_NO_INSTANCE);
        }
        return restrictionPolicy;
    }

    /**
     * 开启蓝牙开关
     */
    private void enableBluetooth() {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null)
        {
            bluetoothAdapter.enable();
        }
    }
}
