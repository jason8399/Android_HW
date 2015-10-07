package jasonpan.lab2;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private static final String ACTIVITY_TAG="Main";
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/Lab2_music/";
    Button playRawBtn, playSDBtn, playURLBtn, stopBtn, volUpBtn, volDownBtn, pause, nextBtn, forwardBtn;
    MediaPlayer player;
    ProgressBar progressBar;
    ListView fileListView;
    File mediaDiPath;
    File[] mediaInDir;
    FilenameFilter mediaFileFilter;
    String[] filter = {".mp3", ".ogg", ".wav"};
    ArrayList<String> fileList;
    ArrayAdapter<String> listAdapter;
    int progressState = 0, length, numTrack, nowPlaying;

    private void init() {
        playRawBtn = (Button)findViewById(R.id.button);
        playSDBtn = (Button)findViewById(R.id.button2);
        playURLBtn = (Button)findViewById(R.id.button3);
        stopBtn = (Button)findViewById(R.id.button4);
        volUpBtn = (Button)findViewById(R.id.button5);
        volDownBtn = (Button)findViewById(R.id.button6);
        pause = (Button)findViewById(R.id.button7);
        nextBtn = (Button)findViewById(R.id.button8);
        forwardBtn = (Button)findViewById(R.id.button9);
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
        mediaDiPath = new File(FILE_PATH);
        if(!mediaDiPath.exists()){
            mediaDiPath.mkdir();
        }
        mediaInDir = mediaDiPath.listFiles(mediaFileFilter);
        fileList = new ArrayList<>();
        for(File file: mediaInDir){
           fileList.add(file.getName());
        }
        numTrack = fileList.size();
        Log.d(ACTIVITY_TAG, numTrack+"");
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
        pause.setEnabled(false);
        nextBtn.setEnabled(false);
        forwardBtn.setEnabled(false);

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
                    pause.setEnabled(true);
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
                    player.reset();
                    player.setDataSource("/sdcard/test.mp3");
                    player.prepare();
                    length = player.getDuration();
                    progressBar.setMax(length / 1000);
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                    playSDBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                    pause.setEnabled(true);
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
                    player.reset();
                    player.setDataSource("http://www.vorbis.com/music/Epoq-Lepidoptera.ogg");
                    player.prepare();
                    length = player.getDuration();
                    progressBar.setMax(length / 1000);
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                    playURLBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                    pause.setEnabled(true);
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
                    pause.setText("暫停");
                    pause.setEnabled(false);
                    nextBtn.setEnabled(false);
                    forwardBtn.setEnabled(false);
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

        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (player.isPlaying()) {
                    player.stop();
                }
                try {
                    player.reset();
                    progressBar.setProgress(0);
                    Log.d(ACTIVITY_TAG, FILE_PATH + fileList.get(position));
                    player.setDataSource(FILE_PATH + fileList.get(position));
                    nowPlaying = position;
                    player.prepare();
                    length = player.getDuration();
                    progressBar.setMax(length / 1000);
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                    playSDBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                    pause.setEnabled(true);
                    nextBtn.setEnabled(true);
                    forwardBtn.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.pause();
                    pause.setText("繼續");
                } else {
                    pause.setText("暫停");
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                }
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (++nowPlaying == numTrack)
                    nowPlaying = 0;
                try {
                    mp.reset();
                    mp.setDataSource(FILE_PATH + fileList.get(nowPlaying));
                    mp.prepare();
                    length = mp.getDuration();
                    progressBar.setMax(length / 1000);
                    mp.start();
                    new Thread(new ProcessBarRefresh()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (++nowPlaying == numTrack)
                    nowPlaying = 0;
                try {
                    player.reset();
                    player.setDataSource(FILE_PATH + fileList.get(nowPlaying));
                    player.prepare();
                    length = player.getDuration();
                    progressBar.setMax(length / 1000);
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (--nowPlaying < 0)
                    nowPlaying = numTrack - 1;
                try {
                    player.reset();
                    player.setDataSource(FILE_PATH + fileList.get(nowPlaying));
                    player.prepare();
                    length = player.getDuration();
                    progressBar.setMax(length / 1000);
                    player.start();
                    new Thread(new ProcessBarRefresh()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
