package es.fdi.sed.meteorin;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataReader
{
    private final MainActivity MAIN_ACTIVITY;
    private final String HC06_MAC = "00:14:03:05:59:38";
    private SimpleBluetoothDeviceInterface deviceInterface;

    public DataReader(MainActivity ma)
    {
        MAIN_ACTIVITY = ma;

        BluetoothManager bluetoothManager = BluetoothManager.getInstance();
        bluetoothManager.openSerialDevice(HC06_MAC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }

    private void onConnected(BluetoothSerialDevice connectedDevice)
    {
        // You are now connected to this device!
        // Here you may want to retain an instance to your device:
        deviceInterface = connectedDevice.toSimpleDeviceInterface();

        // Listen to bluetooth events
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);

        // Let's send a message:
        //deviceInterface.sendMessage("Hello world!");
    }

    private void onMessageSent(String message)
    {
        // We sent a message! Handle it here.
        //Toast.makeText(context, "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
    }

    private void onMessageReceived(String message)
    {
        // We received a message! Handle it here.
        String[] splits = message.split("x");

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