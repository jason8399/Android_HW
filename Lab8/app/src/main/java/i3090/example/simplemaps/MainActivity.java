package i3090.example.simplemaps;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {  
	private EditText edit;
	private EditText edit2;
	private Button recordbtn;
	private Button startbtn;
	String from;
	String to;
	private static final String MAP_URL = "file:///android_asset/map_v3.html";
	private WebView webView;
  @Override
  /** Called when the activity is first created. */
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.map_v3);
      setupWebView();

      edit = (EditText) findViewById(R.id.EditText01);
      edit2 = (EditText) findViewById(R.id.EditText02);
      recordbtn = (Button) findViewById(R.id.Button01);
      startbtn = (Button) findViewById(R.id.Button02);;
		
      startbtn.setOnClickListener( new OnClickListener() {
			
			public void onClick(View v) {
				from=edit.getText().toString();
				to=edit2.getText().toString();
				webView.loadUrl( "javascript:route()");
			}
		});
      recordbtn.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
	    		intent.setClass(MainActivity.this, IntentTest2.class);
	    		Bundle bundle = new Bundle();
	    		bundle.putString("KEY_TEXT1", edit.getText().toString());
	    		bundle.putString("KEY_TEXT2", edit2.getText().toString());
	    		intent.putExtras(bundle);
	    		startActivity(intent);
			}
		});
	}
  @JavascriptInterface
  public String getFrom()
  {
	  return from;
  }
  @JavascriptInterface
  public String getTo()
  {
	  return to;
  }
  @SuppressLint("JavascriptInterface")
  private void setupWebView(){
      webView = (WebView) findViewById(R.id.webview);
      webView.getSettings().setJavaScriptEnabled(true);//�ҥ�Webview��JavaScript�\��
      webView.addJavascriptInterface(this ,"Android");
      webView.loadUrl(MAP_URL);
  }
}