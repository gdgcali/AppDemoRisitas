package com.example.testsound;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ReproducirSonido extends Activity {

  Button btnPlay;
  SoundPool objSoundPool;
  int idSoundPool;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reproducir_sonido);

    objSoundPool = new SoundPool(9, AudioManager.STREAM_MUSIC, 0);
    idSoundPool = objSoundPool.load(this, R.raw.laugh, 1);
  }

  public void onClick(View v) {
    if (v.getId() == R.id.btnPlay) {
      if (idSoundPool != 0) {
        objSoundPool.play(idSoundPool, 1, 1, 0, 0, 1);
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.reproducir_sonido, menu);
    return true;
  }

}
