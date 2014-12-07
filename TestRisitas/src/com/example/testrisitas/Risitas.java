package com.example.testrisitas;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class Risitas extends Activity {
  private SensorManager objSensorManager;
  private Sensor objSensor;
  private TextView resultXYZ;
  private float xPresent, yPresent, zPresent;
  private float xPast, yPast, zPast;
  private SoundPool objSoundPool;
  private int idSoundPool;
  float difference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_risitas);

    this.objSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    this.objSensor = objSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);

    this.resultXYZ = (TextView) findViewById(R.id.txvXYZ);
    
    objSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
  }

  @Override
  protected void onResume() {
    
    xPresent = yPresent = zPresent = 0;
    xPast = yPast = zPast = 0;
    
    super.onResume();
    objSensorManager.registerListener(accelerationListener, objSensor,
        SensorManager.SENSOR_DELAY_NORMAL);
  }

  private SensorEventListener accelerationListener = new SensorEventListener() {
    @Override
    public void onAccuracyChanged(Sensor sensor, int acc) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
      xPresent = event.values[0];
      yPresent = event.values[1];
      zPresent = event.values[2];

      refreshAceleration();
    }
  };
  
  @Override
  protected void onStop() {
    objSensorManager.unregisterListener(accelerationListener);
    super.onStop();
  }
  
  private boolean isBetween(int x, int lower, int upper) {
    return lower <= x && x <= upper;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.risitas, menu);
    return true;
  }
  
  private void refreshAceleration() {
    
    resultXYZ.setText(String.format("%s, %s, %s\n(%s, %s, %s)\n[Difference: %s]",
        Float.toString(this.xPresent),
        Float.toString(this.yPresent),
        Float.toString(this.zPresent),
        Float.toString(this.xPast),
        Float.toString(this.yPast),
        Float.toString(this.zPast),
        Float.toString(difference)));
    
    difference = this.xPresent - this.xPast;
    difference = (difference < 0) ? -difference : difference;
    
    idSoundPool = 0;
    
    int caso = (int) difference;
    
    if (this.isBetween(caso, 3, 4)) {
      idSoundPool = objSoundPool.load(this, R.raw.l11, 1);
    }
    else if (this.isBetween(caso, 5, 6)) {
      idSoundPool = objSoundPool.load(this, R.raw.l12, 1);
    }
    else if (this.isBetween(caso, 7, 8)) {
      idSoundPool = objSoundPool.load(this, R.raw.l21, 1);
    }
    else if (caso > 9) {
      idSoundPool = objSoundPool.load(this, R.raw.l22, 1);
    }
    
    if (idSoundPool != 0) {
      objSoundPool.play(idSoundPool, 1, 1, 0, 0, 1);
      
      this.xPast = this.xPresent;
      this.yPast = this.yPresent;
      this.zPast = this.zPresent;
    }
    
    /*if (difference > 4) {
      idSoundPool = objSoundPool.load(this, R.raw.l11, 1);
      
      if (idSoundPool != 0) {
        objSoundPool.play(idSoundPool, 1, 1, 0, 0, 1);
      }
      
      this.xPast = this.xPresent;
      this.yPast = this.yPresent;
      this.zPast = this.zPresent;
    }
    else if (difference > 7) {
      idSoundPool = objSoundPool.load(this, R.raw.l23, 1);
      
      if (idSoundPool != 0) {
        objSoundPool.play(idSoundPool, 1, 1, 0, 0, 1);
      }
      
      this.xPast = this.xPresent;
      this.yPast = this.yPresent;
      this.zPast = this.zPresent;
    }*/
  }
}
