package es.fdi.sed.meteorin;

import java.util.Random;

public class DataReader extends Thread
{
    private boolean _running;
    private final MainActivity MAIN_ACTIVITY;
    private int _temperature, _humidity;
    private final Random RNG;

    private final Runnable RUNNABLE = new Runnable()
    {
        @Override
        public void run()
        {
            MAIN_ACTIVITY.setData(_temperature, _humidity);
        }
    };

    public DataReader(MainActivity ma)
    {
        _running = false;
        MAIN_ACTIVITY = ma;
        _temperature = 0;
        _humidity = 0;
        RNG = new Random();
    }

    @Override
    public void run()
    {
        _running = true;

        while(_running)
        {
            try
            {
                _temperature = RNG.nextInt(100);
                _humidity = RNG.nextInt(100);
                MAIN_ACTIVITY.runOnUiThread(RUNNABLE);
                sleep(1000);
            }
            catch(InterruptedException ignored)
            {
            }
        }
    }
}