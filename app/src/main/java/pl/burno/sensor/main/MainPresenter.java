package pl.burno.sensor.main;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import pl.burno.sensor.util.RotationHelper;

public class MainPresenter
{
    private final int MAX_SENSOR = 250;
    private SensorManager mSensorManager;
    private MainView mMainView;
    public enum MySensor {
        LIGHT, ROTATION, ACCELEROMETER
    }
    private double rotationSpeed = 1;

    private SensorEventListener mSensorEventListener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent)
        {
            switch (sensorEvent.sensor.getType())
            {
                case Sensor.TYPE_LIGHT:
                    float value = sensorEvent.values[0];
                    value = value > MAX_SENSOR ? MAX_SENSOR : value;

                    int color = (int) (1 - (value / MAX_SENSOR) * 0xff) * 0x010101;
                    mMainView.setBackground(color);
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    int rotation = RotationHelper.rotationInDegrees(sensorEvent.values);
                    int color2 = (int) (((double) (180 + rotation) / 360) * 0xff + 0x60000000);
                    mMainView.rotateInfoBoxes((int) rotationSpeed, rotation);
                    mMainView.setTransparentBackground(color2);
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    float v[] = sensorEvent.values;
                    rotationSpeed = Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i)
        {

        }
    };

    public MainPresenter(MainView mainView)
    {
        mSensorManager = (SensorManager) mainView.getSystemService(Context.SENSOR_SERVICE);

        final Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor == null)
            mainView.showErrorNoSensor(MySensor.LIGHT);
        else
            mSensorManager.registerListener(mSensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);

        final Sensor rotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        if(rotationSensor == null)
            mainView.showErrorNoSensor(MySensor.ROTATION);
        else
            mSensorManager.registerListener(mSensorEventListener, rotationSensor, SensorManager.SENSOR_DELAY_FASTEST);

        final Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(lightSensor == null)
            mainView.showErrorNoSensor(MySensor.ACCELEROMETER);
        else
            mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);

        mMainView = mainView;
    }


}
