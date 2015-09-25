package handsfree.uop.liveperson.com.livepersonhandsfree;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by adamdebbagh on 2/25/15.
 */
public class ChatService extends Service {
    public static final String TAG = ChatService.class.getSimpleName();
    private final IBinder  mBinder = new LocalBinder() ;
    protected String companyID;

    public ChatService() {}


    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    public class LocalBinder extends Binder {
        ChatService getService(){
            return ChatService.this;
        }
    }

    //needs to be implemented
    public void requestChat(String siteId) {

    }

    //"checkAvailability" returns whether an agent is available for an account.(needs to be implemented)

    public boolean checkAvailability() {

        // get the current companyID
        Intent intent = new Intent();
        Bundle extra = intent.getExtras();
        if( extra != null ) {
            companyID = extra.getString("companyID");
            Log.v(TAG, "++ companyID in Service ++ :" + companyID);
        }
        return true;
    }




}
