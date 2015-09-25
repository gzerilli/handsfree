package handsfree.uop.liveperson.com.livepersonhandsfree;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import java.util.List;

public class ChatApi {

    // Define variables for URL
    private static final String API_URL = "https://api.liveperson.net";
    private static final String APIVERSION = "v=1";
    static String id, chatAvailability,
            chatAvailableSlots,
            chatEstimatedWaitTime,
            chatRequest,
            prechatSurvey,
            chatOfflineSurvey;

    // Creates a list of links returned by the API
    static class Account {
        @ElementList(inline=true)
        private List<Link> list;

        @Attribute private String id;

        public List getProperties() {
            return list;
        }
    }

    // Assign values to the elements of the list
    static class Link {
        @Attribute(name = "href")
        String href;
        @Attribute(name = "rel")
        String rel;
    }

    //Creates the interface for the Companies
    interface LivePerson {
        @GET("/api/account/{accountID}?" + APIVERSION)
        Account getResources(@Path("accountID") String accountID);
    }

    // Start main
    public static void main(String... args) {

        // Creates an interceptor that adds the header to every request needed
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "LivePerson appKey=909386d0b49f49908e311a783aeef607");
            }
        };

        // Creates the builder with ENDPOINT, type of CONVERTER and Interceptor
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setConverter(new SimpleXMLConverter())
                .setRequestInterceptor(requestInterceptor)
                .build();

        // Create an instance of our LivePerson API interface.
        LivePerson livePerson = restAdapter.create(LivePerson.class);

        // Get the list of LP links.
        List<Link> links = livePerson.getResources("P73373771").getProperties();
        for (Link link : links) {
            if (link.rel.equals("self")) {
                id = link.href;
            }else if (link.rel.equals("chat-availability")) {
                chatAvailability = link.href;
            }else if (link.rel.equals("chat-available-slots")) {
                chatAvailableSlots = link.href;
            }else if (link.rel.equals("chat-estimatedWaitTime")) {
                chatEstimatedWaitTime = link.href;
            }else if (link.rel.equals("chat-request")) {
                chatRequest = link.href;
            }else if (link.rel.equals("prechat-survey")) {
                prechatSurvey = link.href;
            }else if (link.rel.equals("chat-offline-survey")) {
                chatOfflineSurvey = link.href;
            }
        }
    }
}