package example.sony.ifttt_listener;


import android.content.Context;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by junaid on 16/10/06.
 *
 */
public class FCMMessageReceiverService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.i("fcm", "received notification "+remoteMessage.getData().toString());

            PowerManager.WakeLock screenLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
            screenLock.acquire();
            screenLock.release();
//        try {
//            if (remoteMessage.getData().get("power").contains("off")) {
//                goToSleep(SystemClock.uptimeMillis(), 1, 1);
//            }
//            if (remoteMessage.getData().get("power").contains("on")) {
//                wakeUp(SystemClock.uptimeMillis());
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        AudioManager audio = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
//        try {
//            audio.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.parseInt(remoteMessage.getData().get("volume")), AudioManager.FLAG_SHOW_UI);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

    }




    public void goToSleep(long time, int reason, int flags){
        String TAG_NAME = "Sleep Tag";
        Log.d(TAG_NAME, "in goToSleep");
        Log.d(TAG_NAME, "reason : " + reason);

        // Go to sleep
        Exception occurredExcept = null;
        try {
            PowerManager pm_ = (PowerManager) getSystemService(Context.POWER_SERVICE);

            Method goToSleep = pm_.getClass()
                    .getMethod("goToSleep", long.class, int.class, int.class);
            goToSleep.invoke(pm_, time, reason, flags);
        } catch (NoSuchMethodException e) {
            occurredExcept = e;
        } catch (IllegalAccessException e) {
            occurredExcept = e;
        } catch (IllegalArgumentException e) {
            occurredExcept = e;
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof Exception) {
                occurredExcept = (Exception) e.getCause();
            } else {
                Log.w(TAG_NAME, "Exception detail was lost");
                occurredExcept = e;
            }
        } finally {
            if (occurredExcept != null) {
                Log.e(TAG_NAME, "Could not call goToSleep("
                        + occurredExcept.getMessage() + ")");
                occurredExcept.printStackTrace();
                return;
            }
        }

        Log.d(TAG_NAME, "out goToSleep");
    }

    public void wakeUp(long time) {
        String TAG_NAME = "wakeuptag";
        Log.d(TAG_NAME, "in wakeUp");

        // Wake up
        Exception occurredExcept = null;
        try {
            PowerManager pm_ = (PowerManager) getSystemService(Context.POWER_SERVICE);
            Method wakeup = pm_.getClass().getMethod("wakeUp", long.class);
            wakeup.invoke(pm_, time);
        } catch (NoSuchMethodException e) {
            occurredExcept = e;
        } catch (IllegalAccessException e) {
            occurredExcept = e;
        } catch (IllegalArgumentException e) {
            occurredExcept = e;
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof Exception) {
                occurredExcept = (Exception) e.getCause();
            } else {
                Log.w(TAG_NAME, "Exception detail was lost");
                occurredExcept = e;
            }
        } finally {
            if (occurredExcept != null) {
                Log.e(TAG_NAME, "Could not called wakeUp("
                        + occurredExcept.getMessage() + ")");
                occurredExcept.printStackTrace();
                return;
            }
        }

        Log.d(TAG_NAME, "out wakeUp");
    }
}
