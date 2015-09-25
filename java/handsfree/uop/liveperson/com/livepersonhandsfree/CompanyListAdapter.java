package handsfree.uop.liveperson.com.livepersonhandsfree;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import handsfree.uop.liveperson.com.livepersonhandsfree.companyContent.CompanyContent;

/**
 * Created by adamdebbagh on 2/18/15.
 */
public class CompanyListAdapter extends ArrayAdapter<CompanyContent.CompanyItem> {

    Context context;

    public CompanyListAdapter(Context context, int resourceId, List<CompanyContent.CompanyItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }


    private class ViewHolder {
        ImageView imageView;
        TextView siteName;
        TextView companyID;
        TextView siteUrl;



    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        CompanyContent.CompanyItem companyItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.single_company, null);
            holder = new ViewHolder();

            holder.imageView  = (ImageView) convertView.findViewById(R.id.image);
            holder.siteName   = (TextView) convertView.findViewById(R.id.siteName);
            holder.companyID = (TextView) convertView.findViewById(R.id.companyID);
            holder.siteUrl    = (TextView) convertView.findViewById(R.id.siteUrl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(companyItem.getImageId());
        holder.siteName.setText(companyItem.getSiteName());
        holder.siteUrl.setText(companyItem.getSiteUrl());
        holder.companyID.setText(companyItem.getCompanyID());


        return convertView;
    }
}
