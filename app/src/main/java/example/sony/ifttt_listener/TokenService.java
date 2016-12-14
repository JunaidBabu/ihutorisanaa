package example.sony.ifttt_listener;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by junaid on 16/10/06.
 */
public class TokenService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.w("notification", refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {

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