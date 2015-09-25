package handsfree.uop.liveperson.com.livepersonhandsfree;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.Locale;

import handsfree.uop.liveperson.com.livepersonhandsfree.companyContent.CompanyContent;


public class CompanyListActivity extends FragmentActivity
        implements CompanyListFragment.Callbacks {
    public static final String TAG = CompanyContent.class.getSimpleName();



  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  private boolean mTwoPane;
  protected String welcomeText = String.format("How can I make your day better?");
  protected TextToSpeech tts;
  protected Intent serviceIntent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_company_list);


    if (findViewById(R.id.company_detail_container) != null) {

      mTwoPane = true;

      // In two-pane mode, list items should be given the
      // 'activated' state when touched.
      ((CompanyListFragment) getSupportFragmentManager()
              .findFragmentById(R.id.company_list))
              .setActivateOnItemClick(true);
    }
      tts = new TextToSpeech(this,new TextToSpeech.OnInitListener() {
          @Override
          public void onInit(int status) {

              if (status != TextToSpeech.ERROR){
                  tts.setLanguage(Locale.US);
              }
          }
      });

  }

  @Override
  public void onItemSelected(String id) {
    if (mTwoPane) {
      // In two-pane mode, show the detail view in this activity by
      // adding or replacing the detail fragment using a
      // fragment transaction.
      Bundle arguments = new Bundle();
      arguments.putString(ChatFragment.ARG_ITEM_ID, id);
      ChatFragment fragment = new ChatFragment();
      fragment.setArguments(arguments);
      getSupportFragmentManager().beginTransaction()
              .replace(R.id.company_detail_container, fragment)
              .commit();

        //open conversation with one company
        openConversation(id);
        //open ChatService with one company
        openChatService(id);
        // A welcoming voice, on site click
        tts.speak(welcomeText, TextToSpeech.QUEUE_FLUSH,null);


    } else {
                // Single-pane mode
      // start the chat activity for the selected item ID.

        //open conversation with one company
        openConversation(id);
        //open ChatService with one company
        openChatService(id);
        // A welcoming voice, on site click
        tts.speak(welcomeText, TextToSpeech.QUEUE_FLUSH,null);
      
    }
  }

    private void openChatService(String id) {
        Intent siteDataIntent = new Intent(this,ChatService.class);
        // send out, the corresponding site id and site url to the ChatService class
        String siteId = CompanyContent.ITEM_MAP.get(id).getCompanyID();
        String url = CompanyContent.ITEM_MAP.get(id).getSiteUrl();
        Log.v(TAG, "++ companyID in CompanyList ++ :" + siteId);
        Log.v(TAG, "++ siteUrl in CompanyList ++ :" + url);
        siteDataIntent.putExtra("companyID", siteId);
        siteDataIntent.putExtra("siteUrl", url);
        startService(siteDataIntent);
    }

    private void openConversation(String id) {
        Intent chatIntent = new Intent(this, ChatActivity.class);
        chatIntent.putExtra(ChatFragment.ARG_ITEM_ID, id);
        startActivity(chatIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
