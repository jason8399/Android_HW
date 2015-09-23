package jasonpan.lab1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    private Button Switch;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch = (Button) findViewById(R.id.button);
        edit = (EditText) findViewById(R.id.editText);

        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("KEY_TEXT", edit.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
