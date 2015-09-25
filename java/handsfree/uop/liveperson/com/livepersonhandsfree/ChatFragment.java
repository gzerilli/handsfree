package handsfree.uop.liveperson.com.livepersonhandsfree;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import handsfree.uop.liveperson.com.livepersonhandsfree.companyContent.CompanyContent;

public class ChatFragment extends Fragment implements View.OnClickListener {

    public  static final String TAG = ChatFragment.class.getSimpleName();

    protected ImageButton sendButton;
    protected ImageButton voiceButton;
    protected EditText textInput;
    protected ListView listView;
    ChatAdapter mChatAdapter ;
    boolean position = false;

    ArrayList<String> textData = new ArrayList<>();
    ArrayList<String> voiceData = new ArrayList<>();

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1;

    /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";

  /**
   * The companyContent content this fragment is presenting.
   */
  private CompanyContent.CompanyItem mItem;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public ChatFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments().containsKey(ARG_ITEM_ID)) {
      // Load content specified by the fragment
        mItem = CompanyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

        }
  }

    @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

    // Show the companyContent content as text in a TextView.
    if (mItem != null) {


        sendButton  = (ImageButton)rootView.findViewById(R.id.sendButton);
        voiceButton = (ImageButton)rootView.findViewById(R.id.voiceButton);
        textInput   = (EditText) rootView.findViewById(R.id.textinput);
        listView    = (ListView)rootView.findViewById(R.id.chat_list_view);

        mChatAdapter = new ChatAdapter(getActivity(),R.id.singleMessage);
        listView.setAdapter(mChatAdapter);
        //make listView scrollable
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        //when user tap a message. it should appear on the bottom of the view
        mChatAdapter.registerDataSetObserver(new DataSetObserver() {

            @Override
            public void onChanged() {
                super.onChanged();
                // set the listview current position
                listView.setSelection(mChatAdapter.getCount() - 1);

            }
        });

        voiceButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);

    }
        return rootView;
  }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.voiceButton:

                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //determine what language is being spoken
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Listening...");
                startActivityForResult(speechIntent, VOICE_RECOGNITION_REQUEST_CODE);
                break;
            case R.id.sendButton:

                mChatAdapter.add(new MessageData(position,textInput.getText().toString()));

                // after sending the first message, we need to change the position
                position = !position;
                textInput.setText("");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            voiceData = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            listView.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,voiceData));

            //Result code for various error.
        } else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
            showToastMessage("Audio Error");
        } else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
            showToastMessage("Client Error");
        } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
            showToastMessage("Network Error");
        } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
            showToastMessage("Server Error");
        }
    }

    void showToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
