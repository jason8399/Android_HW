package jasonpan.lab1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Activity2 extends ActionBarActivity {

    private Button Finish;
    private TextView string;
    private Button TextSizeInc;
    private Button TextSizeDec;
    private float TextSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        Finish = (Button) findViewById(R.id.button2);
        string = (TextView) findViewById(R.id.textView4);
        TextSizeInc = (Button) findViewById(R.id.button3);
        TextSizeDec = (Button) findViewById(R.id.button4);

        Bundle bundle;
        bundle = this.getIntent().getExtras();
        string.setText(bundle.getString("KEY_TEXT"));
        TextSize = string.getTextSize();
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextSizeInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextSize <= 300)
                    string.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize += 10);
            }
        });

        TextSizeDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextSize > 0)
                    string.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize -= 10);
            }
        });
    }
}
