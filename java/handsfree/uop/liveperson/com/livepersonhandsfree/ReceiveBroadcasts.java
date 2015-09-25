package handsfree.uop.liveperson.com.livepersonhandsfree;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import handsfree.uop.liveperson.com.livepersonhandsfree.companyContent.CompanyContent;

public class ReceiveBroadcasts extends BroadcastReceiver {
    public static final String TAG = CompanyContent.class.getSimpleName();
    public static boolean  success;

    public ReceiveBroadcasts() {}

    @Override
    public void onReceive(Context context, Intent intent) {

        // get status from chatService class
        receiveChatconnectivityStatus(intent);

    }

    private void receiveChatconnectivityStatus(Intent intent) {
        String action = intent.getAction();
        Log.v(TAG, "++ action  in RecieveBroadcasts ++ :" + action);
        if(action.equals("handsfree.uop.liveperson.com.livepersonhandsfree")){
            success = intent.getExtras().getBoolean("success");
            Log.v(TAG, "++ Success status in RecieveBroadcasts ++ :" + success);
        }
    }
}
