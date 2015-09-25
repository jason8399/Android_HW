package jasonpan.lab1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private Button Switch;
    private EditText edit;
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private TextView progressText;
    private TextView rText, gText, bText;
    private int color;
    private static final String TAG = "MainActivity";

    private void init(){
        Switch = (Button) findViewById(R.id.button);
        edit = (EditText) findViewById(R.id.editText);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        progressText = (TextView) findViewById(R.id.textView5);
        rText = (TextView) findViewById(R.id.textView6);
        gText = (TextView) findViewById(R.id.textView7);
        bText = (TextView) findViewById(R.id.textView8);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        seekBar1.setOnSeekBarChangeListener(new RSeekBarChange());
        seekBar2.setOnSeekBarChangeListener(new GSeekBarChange());
        seekBar3.setOnSeekBarChangeListener(new BSeekBarChange());

        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("KEY_TEXT", edit.getText().toString());
                bundle.putInt("KEY_COLOR", color);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    class RSeekBarChange implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int r, g, b;
            rText.setText("R " + seekBar1.getProgress() + "/" + seekBar1.getMax());
            r = seekBar1.getProgress() << 16;
            g = seekBar2.getProgress() << 8;
            b = seekBar3.getProgress();
            color = (r & 0x00FF0000) +
                    (g & 0x0000FF00) +
                    (b & 0x000000FF) +
                    0xFF000000;
            progressText.setTextColor(color);
            Log.d(TAG, "color=" + color);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class GSeekBarChange implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int r, g, b;
            gText.setText("G " + seekBar2.getProgress() + "/" + seekBar2.getMax());
            r = seekBar1.getProgress() << 16;
            g = seekBar2.getProgress() << 8;
            b = seekBar3.getProgress();
            color = (r & 0x00FF0000) +
                    (g & 0x0000FF00) +
                    (b & 0x000000FF) +
                    0xFF000000;
            progressText.setTextColor(color);
            Log.d(TAG, "color=" + color);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class BSeekBarChange implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int r, g, b;
            bText.setText("B " + seekBar3.getProgress() + "/" + seekBar3.getMax());
            r = seekBar1.getProgress() << 16;
            g = seekBar2.getProgress() << 8;
            b = seekBar3.getProgress();
            color = (r & 0x00FF0000) +
                    (g & 0x0000FF00) +
                    (b & 0x000000FF) +
                    0xFF000000;
            progressText.setTextColor(color);
            Log.d(TAG, "color=" + color);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
