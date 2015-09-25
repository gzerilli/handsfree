package handsfree.uop.liveperson.com.livepersonhandsfree.companyContent;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import handsfree.uop.liveperson.com.livepersonhandsfree.R;

public class CompanyContent {

    public static final String TAG = CompanyContent.class.getSimpleName();

    public static List<CompanyItem> ITEMS = new ArrayList<>();
    public static Map<String, CompanyItem> ITEM_MAP = new HashMap<>();

  public static void setContext(Context context) {

     Resources res = context.getResources();

     String[] companies = res.getStringArray(R.array.companies);
     TypedArray images = res.obtainTypedArray(R.array.images);
     String[] siteNumbers = res.getStringArray(R.array.companyID);
     String[] urls = res.getStringArray(R.array.urls);

     for (int i = 0; i < companies.length; i++) {

         // read from the arrays. dynamically Add items to hash map
         addItem(new CompanyItem(String.valueOf(i),images.getResourceId(i,0),companies[i], siteNumbers[i], urls[i]));
         Log.v(TAG, "++ Company Items ++ :" + companies[i]);
     }
      //return allocated memory
      images.recycle();
  }


    private static void addItem(CompanyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

  public CompanyItem getCompany(String siteid) {
    return ITEM_MAP.get(siteid);
  }

  public  static class CompanyItem {

        public int imageId;
        public String siteName;
        public String companyID;
        public String siteUrl;
        public String id;

        public CompanyItem(String id, int imageId, String companyName, String companyID, String companyUrl) {

            this.id = id;
            this.imageId = imageId;
            this.siteName = companyName;
            this.companyID = companyID;
            this.siteUrl = companyUrl;

        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getCompanyID() {
            return companyID;
        }

        public void setCompanyID(String companyID) {
            this.companyID = companyID;
        }

        public String getSiteUrl() {
            return siteUrl;
        }

        public void setSiteUrl(String title) {
            this.siteUrl = siteUrl;
        }

        @Override
        public String toString() {
            return siteName + "\n";
        }
    }
}
