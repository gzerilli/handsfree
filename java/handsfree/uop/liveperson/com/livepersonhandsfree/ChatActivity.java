package handsfree.uop.liveperson.com.livepersonhandsfree;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class ChatActivity extends ActionBarActivity {

    public static final String TAG = ChatActivity.class.getSimpleName();

    ChatService mService;
    boolean mBound = false;
    ProgressDialog statusDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chat);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008040")));
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Create the chat fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ChatFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ChatFragment.ARG_ITEM_ID));
            ChatFragment fragment = new ChatFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.company_detail_container, fragment)
                    .commit();
            // first check Network connectivity.
            if (isNetworkAvailable()) {
                // if connected, dismiss the progress dialog, else show a failure dialog

                    // show spinner dialog while waiting connection to LivePerson.
                    statusDialog = statusProgressDialog("Connecting to Chat Service", "Please wait...");

            }
            else{
                statusProgressDialog("Network isn't Available","please turn on your wifi");
            }
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Bind to ChatService. bindService() method starts the service!!!!
        Intent intent = new Intent(this, ChatService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);


    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service

            unbindService(mConnection);
            mBound = false;
       
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName className,IBinder service) {
            // bind to LocalService, cast the IBinder and get LocalService instance
            ChatService.LocalBinder binder = (ChatService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            statusDialog.dismiss();
            Toast.makeText(ChatActivity.this," ChatService Connected",Toast.LENGTH_LONG).show();
            Log.d(TAG,"+++ChatService Connected to ChatActivity+++");
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mBound = false;

            Toast.makeText(ChatActivity.this,"Disconnected",Toast.LENGTH_LONG).show();
            Log.d(TAG,"Service disconnected from ChatActivity");
        }
    };

    // check whether Network connectivity available.
    private  boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private ProgressDialog  statusProgressDialog(String title, String message) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {

      NavUtils.navigateUpTo(this, new Intent(this, CompanyListActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }


}
