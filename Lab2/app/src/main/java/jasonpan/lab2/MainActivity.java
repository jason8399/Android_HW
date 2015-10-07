package jasonpan.lab2;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    Button playRawBtn, playSDBtn, playURLBtn, stopBtn, volUpBtn, volDownBtn;
    MediaPlayer player;
    ProgressBar progressBar;
    ListView fileListView;
    File mediaDiPath;
    File[] mediaInDir;
    FilenameFilter mediaFileFilter;
    String[] filter = {".mp3", ".ogg"};
    ArrayList<String> fileList;
    ArrayAdapter<String> listAdapter;
    int progressState = 0, length;

    private void init() {
        playRawBtn = (Button)findViewById(R.id.button);
        playSDBtn = (Button)findViewById(R.id.button2);
        playURLBtn = (Button)findViewById(R.id.button3);
        stopBtn = (Button)findViewById(R.id.button4);
        volUpBtn = (Button)findViewById(R.id.button5);
        volDownBtn = (Button)findViewById(R.id.button6);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        fileListView = (ListView)findViewById(R.id.listView);
        player = new MediaPlayer();
    }

    private void setFileList(){
        mediaFileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                for(String filterString: filter){
                    if(filename.contains(filterString)) return true;
                }
                return false;
            }
        };
        mediaDiPath = new File(Environment.getExternalStorageDirectory() + "/Lab2_music");
        if(!mediaDiPath.exists()){
            mediaDiPath.mkdir();
        }
        mediaInDir = mediaDiPath.listFiles(mediaFileFilter);
        fileList = new ArrayList<>();
        for(File file: mediaInDir){
           fileList.add(file.getName());
        }
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        init();
        setFileList();
        fileListView.setAdapter(listAdapter);

        playRawBtn.setEnabled(true);
        playSDBtn.setEnabled(true);
        stopBtn.setEnabled(false);

        playRawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = MediaPlayer.create(MainActivity.this, R.raw.sample);
                try {
                    progressBar.setProgress(0);
                    length = player.getDuration();
                    progressBar.setMax(length / 1000);
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                    playRawBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });

        playSDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressBar.setProgress(0);
                    player = new MediaPlayer();
                    player.setDataSource("/sdcard/test.mp3");
                    player.prepare();
                    length = player.getDuration();
                    progressBar.setMax(length / 1000);
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                    playSDBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });


        playURLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressBar.setProgress(0);
                    player = new MediaPlayer();
                    player.setDataSource("http://www.vorbis.com/music/Epoq-Lepidoptera.ogg");
                    player.prepare();
                    length = player.getDuration();
                    progressBar.setMax(length / 1000);
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                    playURLBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    player.stop();
                    playRawBtn.setEnabled(true);
                    playSDBtn.setEnabled(true);
                    playURLBtn.setEnabled(true);
                    stopBtn.setEnabled(false);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });

        volUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, 5);
            }
        });

        volDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, 5);
            }
        });
    }

    class ProcessBarRefresh implements Runnable {
        @Override
        public void run() {
            while (player.isPlaying()){
                progressState = player.getCurrentPosition()/1000;
                progressBar.setProgress(progressState);
            }
        }
    }
}
