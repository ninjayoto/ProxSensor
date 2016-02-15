package app.com.ninja.android.proxsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

// The activity implements SensorEventListner to allow us to register a listner
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);

        //we get a reference for the sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //we get a reference to the sensor we will be listening to
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    protected void onResume() {
        super.onResume();
        //we call refgisterListner method of the SensorEventListner interface
        //we give it a context, sensor object and the sensor response delay
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        //we make sure that the sesnsor service is unregistered when the app is paused -> stopped
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    //we handle the events using onSensorChange (in this case the sensor detects an object in it's range)
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) { //the distance in cm (some sensors only have binary values, e.g. everything closer than 6 cm is near)
            //we are changing the background color, by first calling the getWindow method getDecorView
            //then set it to the color we have defined at R.id.near
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.near));
            text.setText(R.string.tooclose);

        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.far));
                text.setText(R.string.closer);


        }
    }
}
