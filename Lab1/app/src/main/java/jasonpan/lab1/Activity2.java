package jasonpan.lab1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity2 extends ActionBarActivity {

    private Button Finish;
    private TextView string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        Finish = (Button) findViewById(R.id.button2);
        string = (TextView) findViewById(R.id.textView4);

        Bundle bundle;
        bundle = this.getIntent().getExtras();
        string.setText(bundle.getString("KEY_TEXT"));
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
