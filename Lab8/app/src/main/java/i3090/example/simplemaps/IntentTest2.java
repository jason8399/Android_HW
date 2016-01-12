package i3090.example.simplemaps;

import android.os.Bundle;
import i3090.example.simplemaps.R;
import android.app.ListActivity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class IntentTest2 extends ListActivity {
    private EditText edit;
    private Button recordbtn;
    private Button deletetbtn;
    TextView showfrom;
    TextView showto;
    String from;
    String to;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        //Tell the list view which view to display when the list is empty
        getListView().setEmptyView(findViewById(R.id.TextView01));
        setAdapter();
        Bundle bundle = this.getIntent().getExtras();
        showfrom=(TextView) findViewById(R.id.TextViewFrom);
        showto=(TextView) findViewById(R.id.TextViewTo);
        from=bundle.getString("KEY_TEXT1");
        to=bundle.getString("KEY_TEXT2");
        showfrom.setText(from);
        showto.setText(to);

        deletetbtn=(Button) findViewById(R.id.Button02);
        recordbtn=(Button) findViewById(R.id.Button01);
        edit = (EditText) findViewById(R.id.EditText01);

        recordbtn.setOnClickListener( new OnClickListener() {

            public void onClick(View v) {
                String noteName = "  從  "+ from + "  到  "+ to;
                mDbHelper.create(noteName);
                fillData();
            }
        });

        deletetbtn.setOnClickListener( new OnClickListener() {

            public void onClick(View v) {
                mDbHelper.delete(Integer.valueOf(edit.getText().toString()));
                edit.setText("");
                fillData();
            }
        });



    }

    private DB mDbHelper;
    private Cursor mNotesCursor;

    private void setAdapter() {
        mDbHelper = new DB(this);
        mDbHelper.open();
        fillData();
    }

    private void fillData() {
        mNotesCursor = mDbHelper.getAll();
        startManagingCursor(mNotesCursor);

        String[] from = new String[]{DB.KEY_NOTE};
        int[] to = new int[]{android.R.id.text1};

        // Now create a simple cursor adapter
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, mNotesCursor, from, to);
        setListAdapter(adapter);
    }

    private int mNoteNumber = 1;
    protected static final int MENU_INSERT = Menu.FIRST;
    protected static final int MENU_DELETE = Menu.FIRST+1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, MENU_INSERT, 0, "新增");
        menu.add(0, MENU_DELETE, 0, "刪除");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()) {
            case MENU_INSERT:
                String noteName = "Note " + mNoteNumber++;
                mDbHelper.create(noteName);
                fillData();
            case MENU_DELETE:
                mDbHelper.delete(getListView().getSelectedItemId());
                fillData();
        }

        return super.onOptionsItemSelected(item);
    }
}
