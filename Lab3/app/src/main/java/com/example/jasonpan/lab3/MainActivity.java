package com.example.jasonpan.lab3;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity implements MediaPlayer.OnPreparedListener {

    VideoView videoView;
    MediaController mediaController;
    int REQUEST_CODE = 1;
    String TAG = "MainActivity";
    Intent FileSelect;
    TextView videoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView)findViewById(R.id.videoView);
        videoName = (TextView)findViewById(R.id.textView);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(this);

        FileSelect = new Intent(Intent.ACTION_GET_CONTENT);
        FileSelect.setType("video/*");
        this.startActivityForResult(FileSelect, REQUEST_CODE);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                FileSelect = new Intent(Intent.ACTION_GET_CONTENT);
                FileSelect.setType("video/*");
                startActivityForResult(FileSelect, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            Log.e(TAG, "error on reading file");
            return;
        }
        if (requestCode != REQUEST_CODE) {
            Log.d(TAG, "not reading file");
            return;
        }
        Uri uri = data.getData();
        Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        videoName.setText(returnCursor.getString(nameIndex));
        videoView.setVideoURI(uri);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "get file path");
        videoView.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                break;
            case KeyEvent.KEYCODE_BACK:
                if(videoView.isPlaying()) {
                    videoView.suspend();
                    FileSelect = new Intent(Intent.ACTION_GET_CONTENT);
                    FileSelect.setType("video/*");
                    this.startActivityForResult(FileSelect, REQUEST_CODE);
                    Toast.makeText(this,
                            "Pause and Press Back 2 times to exit", Toast.LENGTH_SHORT).show();
                }
                break;
            case KeyEvent.KEYCODE_MENU:
                break;
            case KeyEvent.KEYCODE_HOME:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
