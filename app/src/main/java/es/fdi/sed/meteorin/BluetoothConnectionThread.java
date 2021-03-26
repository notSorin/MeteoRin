package es.fdi.sed.meteorin;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnectionThread extends Thread
{
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothAdapter _adapter;
    private BluetoothSocket _socket;
    private BluetoothDevice _device;

    public BluetoothConnectionThread()
    {
        _adapter = BluetoothAdapter.getDefaultAdapter();
        _socket = null;
        _device = null;

        if(_adapter != null)
        {
            Set<BluetoothDevice> pairedDevices = _adapter.getBondedDevices();

            if(pairedDevices.size() > 0)
            {
                for(BluetoothDevice device : pairedDevices)
                {
                    _device = device;
                }
            }

            if(_device != null)
            {
                try
                {
                    _socket = _device.createRfcommSocketToServiceRecord(MY_UUID);
                }
                catch(IOException ignored)
                {
                }
            }
        }
    }

    @Override
    public void run()
    {
        if(_adapter != null && _socket != null)
        {
            _adapter.cancelDiscovery();

            try
            {
                _socket.connect();
            }
            catch(IOException ignored)
            {
                try
                {
                    _socket.close();
                }
                catch(IOException ignored1)
                {
                }
            }
        }
    }

    public void cancel()
    {
        if(_socket != null)
        {
            try
            {
                _socket.close();
            }
            catch(IOException ignored)
            {
            }
        }
    }
}