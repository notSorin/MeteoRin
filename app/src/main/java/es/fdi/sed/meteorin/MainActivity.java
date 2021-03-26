package es.fdi.sed.meteorin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

public class MainActivity extends AppCompatActivity
{
    private ColorfulRingProgressView _temperatureGauge, _humidityGauge;
    private TextView _temperatureText, _humidityText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _temperatureGauge = findViewById(R.id.TemperatureGauge);
        _temperatureText = findViewById(R.id.TemperatureText);
        _humidityGauge = findViewById(R.id.HumidityGauge);
        _humidityText = findViewById(R.id.HumidityText);

        new DataReader(this).start();
    }

    public void setData(int temperature, int humidity)
    {
        if(temperature < 0)
            temperature = 0;

        if(temperature > 100)
            temperature = 100;

        if(humidity < 0)
            humidity = 0;

        if(humidity > 100)
            humidity = 100;

        _temperatureGauge.setPercent(temperature);
        _humidityGauge.setPercent(humidity);

        String temperatureText = String.format(getResources().getString(R.string.temperature_text), temperature);
        String humidityText = String.format(getResources().getString(R.string.humidity_text), humidity);

        _temperatureText.setText(temperatureText);
        _humidityText.setText(humidityText);
    }
}