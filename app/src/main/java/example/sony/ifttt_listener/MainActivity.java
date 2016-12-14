package example.sony.ifttt_listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView)findViewById(R.id.textView2);
        t1.setText("Test\n");
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms")));
        registerReceiver(uiUpdated, new IntentFilter("UPDATED"));
        final String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            Log.w("gcm token", token);
            t1.setText("Token :" +token);

            Thread thread = new Thread() {
                @Override
                public void run() {

                    URL url = null;
                    try {
                        url = new URL("https://api.telegram.org/bot183643621:AAGTElVAsBv2ked6HLCDv6k1kqtjCn0-Ld0/sendMessage?text="+token+"&chat_id=16795984");
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.getInputStream();
                        //readStream(in);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();


        }
    }

    private BroadcastReceiver uiUpdated= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            t1.setText(t1.getText()+"\n"+intent.getExtras().getString("Key"));

        }
    };

    public void onPause() {
        super.onPause();

        unregisterReceiver(uiUpdated);
    }
}
