package com.example.testacceleration;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerationActivity extends Activity {
  private TextView resultX, resultY, resultZ;
  private SensorManager sensorManager;
  private Sensor sensor;
  private float x, y, z;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_acceleration);

    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);

    resultX = (TextView) findViewById(R.id.txvX);
    resultY = (TextView) findViewById(R.id.txvY);
    resultZ = (TextView) findViewById(R.id.txvZ);
  }

  private void refreshDisplay() {
    resultX.setText(Float.toString(x));
    resultY.setText(Float.toString(y));
    resultZ.setText(Float.toString(z));
  }

  @Override
  protected void onResume() {

    super.onResume();
    sensorManager.registerListener(
        accelerationListener,
        sensor,
        SensorManager.SENSOR_DELAY_NORMAL);
  }

  @Override
  protected void onStop() {
    sensorManager.unregisterListener(accelerationListener);
    super.onStop();
  }

  private SensorEventListener accelerationListener = new SensorEventListener() {
    @Override
    public void onAccuracyChanged(Sensor sensor, int acc) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
      x = event.values[0];
      y = event.values[1];
      z = event.values[2];
      
      refreshDisplay();
    }
  };

  /*
   * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
   * menu; this adds items to the action bar if it is present.
   * getMenuInflater().inflate(R.menu.acceleration, menu); return true; }
   */

}
