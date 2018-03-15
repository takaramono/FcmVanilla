package vanilla.fcm.test.com.fcmvanilla;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by leochien on 3/15/18.
 */

public class MyTokenReceiver extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        Log.d("TOKEN", "Refresh");
    }
}
