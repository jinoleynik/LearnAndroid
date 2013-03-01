package com.activity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.learn.R;

public class WispActivity extends BaseActivity implements SensorEventListener{

 
    private SensorManager sensorManager;   
    private TextView mColorFirst;
    private TextView mColorSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisp);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);       
        mColorFirst=(TextView)findViewById(R.id.color_first);
        mColorSecond=(TextView)findViewById(R.id.color_second);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
       int start = 255;     
       float xf = event.values[0];
       float yf = event.values[1];
       float zf = event.values[2];
       int X1 = (int) ((xf+10f)*start/20);
       int Y1 = (int) ((yf+10f)*start/20);
       int Z1 = (int) ((zf+10f)*start/20);
       int X2 = (int) (Math.abs(xf)*start/20);
       int Y2 = (int) (Math.abs(yf)*start/20);
       int Z2 = (int) (Math.abs(zf)*start/20);
   
        mColorFirst.setText(X1+ "  "+Y1 +"   "+ Z1);
        mColorSecond.setText(X2+ "  "+Y2 +"   "+ Z2);
        mColorFirst.setBackgroundColor(Color.rgb(X1, Y1, Z1));
        mColorSecond.setBackgroundColor(Color.rgb(X2, Y2, Z2));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        
    }

   

}
