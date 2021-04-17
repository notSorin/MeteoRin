package es.fdi.sed.meteorin;

import android.annotation.SuppressLint;
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataReader
{
    private final MainActivity MAIN_ACTIVITY;
    private final String HC06_MAC = "00:14:03:05:59:38";
    private SimpleBluetoothDeviceInterface _deviceInterface;

    @SuppressLint("CheckResult")
    public DataReader(MainActivity ma)
    {
        MAIN_ACTIVITY = ma;

        BluetoothManager bluetoothManager = BluetoothManager.getInstance();

        bluetoothManager.openSerialDevice(HC06_MAC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }

    // You are now connected to this device!
    private void onConnected(BluetoothSerialDevice connectedDevice)
    {
        // Here you may want to retain an instance to your device:
        _deviceInterface = connectedDevice.toSimpleDeviceInterface();

        // Listen to bluetooth events
        _deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);
    }

    // We sent a message! Handle it here.
    private void onMessageSent(String message)
    {
    }

    // We received a message! Handle it here.
    private void onMessageReceived(String message)
    {
        String[] splits = message.split(" ");

        if(splits.length == 2)
        {
            try
            {
                int temperature = Integer.parseInt(splits[0]);
                int humidity = Integer.parseInt(splits[1]);

                MAIN_ACTIVITY.setData(temperature, humidity);
            }
            catch (NumberFormatException ignored)
            {
            }
        }
    }

    private void onError(Throwable error)
    {
    }
}