package bo.umsa.soundpool.soundpool;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private SoundPool soundPool;
    private int soundId;
    boolean plays = false, loaded = false ;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;
    int counter;
    int misoundId;
    int [] idSound = {R.raw.beep, R.raw.sonido};
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Audio Manager settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume =(float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume =(float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        //Hardware buttons settings to adjust the media sound
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //the counter will help us recognize the stream id of the sound played now
        counter = 0;

        //Loaded the sounds
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
        soundId = soundPool.load(this,R.raw.beep, 1);
        misoundId = soundPool.load(this,R.raw.sonido, 1);
    }


    //Methods

    public void playSound(View view){
        //Is the sound loaded does it already play?
        if(loaded && !plays){
            if(position == 0){
                soundPool.play(idSound[0],volume,volume,1,0,1f);
                counter = counter++;
            } else if(position == 1){
                soundPool.play(idSound[1],volume,volume,1,0,1f);
                counter = counter++;
            }

            Toast.makeText(this,"Play sound", Toast.LENGTH_SHORT).show();
            plays = true;
        }
    }
    public void pauseSound(View view) {
        // Is the sound loaded already?
        if (plays) {
            soundPool.pause(idSound[0]);
            soundId = soundPool.load(this, R.raw.beep, counter);
            Toast.makeText(this, "Pause sound", Toast.LENGTH_SHORT).show();
            plays = false;
        }
    }
    public void playLoop(View view) {
        // Is the sound loaded does it already play?
        if (loaded && !plays) {

            // the sound will play for ever if we put the loop parameter -1
            soundPool.play(soundId, volume, volume, 1, -1, 1f);
            counter = counter++;
            Toast.makeText(this, "Plays loop", Toast.LENGTH_SHORT).show();
            plays = true;
        }
    }
    public void stopSound(View view) {
        // Is the sound loaded already?
        if (plays) {
            soundPool.stop(soundId);
            soundId = soundPool.load(this, R.raw.beep, counter);
            Toast.makeText(this, "Stop sound", Toast.LENGTH_SHORT).show();
            plays = false;
        }
    }

    public void nextSound(View view){
        if(plays || !plays){
            if(position == 0){

                soundPool.stop(idSound[position]);
                soundId = soundPool.load(this, R.raw.beep, counter);
                position = 1;
                soundPool.play(idSound[position], volume, volume, 1, -1, 1f);
                Toast.makeText(this,"Next sound", Toast.LENGTH_SHORT).show();
                plays = false;
            } else if(position == 1){
                soundPool.stop(idSound[position]);
                soundId = soundPool.load(this, R.raw.beep, counter);
                position = 0;
                soundPool.play(idSound[position], volume, volume, 1, -1, 1f);
                Toast.makeText(this,"Next sound", Toast.LENGTH_SHORT).show();
                plays = false;
            }

        }
    }

}
