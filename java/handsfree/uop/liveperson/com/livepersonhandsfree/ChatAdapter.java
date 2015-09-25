package handsfree.uop.liveperson.com.livepersonhandsfree;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamdebbagh on 2/21/15.
 */

public class ChatAdapter extends ArrayAdapter<MessageData> {

    private List<MessageData> chat_list = new ArrayList<MessageData>();
    private TextView chatText;
    Context context;

    public ChatAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }


    @Override
    public void add(MessageData textMessage) {

        chat_list.add(textMessage);
        super.add(textMessage);

    }
    //check the number of objects in the arrayList

    @Override
    public int getCount() {
        return chat_list.size();
    }
    // look at the current item on the list
    @Override
    public MessageData getItem(int position) {
        return chat_list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.single_message,parent ,false);
        }

        chatText = (TextView)convertView.findViewById(R.id.singleMessage);

        String message;
        boolean messagePosition;

        MessageData provider = getItem(position);
        message = provider.message;
        messagePosition = provider.position;

        chatText.setText(message);
        // depending on message position, set text background and gravity of the text message
         chatText.setTextColor(Color.parseColor(messagePosition ? "#ff0080" : "#0000ff"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        if (!messagePosition) {
            params.gravity = Gravity.RIGHT;
        }
        else {
            params.gravity = Gravity.LEFT;
        }

        chatText.setLayoutParams(params);
        return convertView;
    }
}

