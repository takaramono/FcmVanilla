package vanilla.fcm.test.com.fcmvanilla;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


/**
 * Created by yuanwan on 2/1/18.
 */

public class FCMServer {
  private String TAG = "FCMDemo";

  private String endpoint = "https://fcm.googleapis.com/fcm/send";
  private String apiKey = "AAAATXJ0bTI:APA91bF_oqDjLC4zkbMlEY-DY-9zmHGkz9c-dgrw8rISP7P9L64zXrJ4PpibWPzdC5ZkZ03xFrlhf2PRx0h9M7fvVTMcgPPDMWgK1lTNsL8vuYceeIKMbNNAXtTm3gc3ton5DZcsyjK8";

  public void sendMessage(FCMData data, String token) throws Exception {
    JSONObject dataMessage = new JSONObject();
    if (data.getDataMaps() != null && data.getDataMaps().size() > 0) {
      for (String key : data.getDataMaps().keySet()) {
        dataMessage.put(key, data.getDataMaps().get(key));
      }
    }

    JSONObject notificationMessage = new JSONObject();

    if (data.getTitle() != null && !data.getTitle().isEmpty()) {
      notificationMessage.put("title", data.getTitle());
    }

    if (data.getContent() != null && !data.getContent().isEmpty()) {
      notificationMessage.put("body", data.getContent());
    }

    JSONObject postData = new JSONObject();
    postData.put("to", token);
    if (notificationMessage.length() != 0) {
      postData.put("notification", notificationMessage);
    }
    if (dataMessage.length() != 0) {
      postData.put("data", dataMessage);
    }

    //POST
    URL url = new URL(endpoint);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");

    //Set Header
    connection.setRequestProperty("Authorization", "key=" + apiKey);
    connection.setRequestProperty("Content-Type", "application/json");

    connection.setDoOutput(true); //for POST
    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
    outputStream.write(postData.toString().getBytes("utf8"));
//        outputStream.writeBytes(postData.toString());
    outputStream.flush();
    outputStream.close();

    int statusCode = connection.getResponseCode();

    Log.i("FCMDemo-Send", "Post data is : " + postData.toString());
    Log.i("FCMDemo-Send", "Status Code : " + statusCode);

    BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    Log.i("FCMDemo-Send", "Response : " + response.toString());

  }
}
