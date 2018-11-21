package ng.com.techdepo.esidemtest.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class AppFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private static final String ENGAGE_TOPIC = "engage";


    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM Token: " + token);
        FirebaseMessaging.getInstance().subscribeToTopic(ENGAGE_TOPIC);
    }
}
