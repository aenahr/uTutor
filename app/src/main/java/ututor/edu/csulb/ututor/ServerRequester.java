package ututor.edu.csulb.ututor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

public class ServerRequester extends AsyncTask<String, Void, JSONObject>{

    static InputStream is = null;
    static JSONObject jObj = null;

    // constructor
    public ServerRequester() {

    }
    @Override
    protected JSONObject doInBackground(String... pagenames) {
        HttpURLConnection urlConnection = null;
        JSONObject jsonResponse = null;
        String method = "POST";
        // Making HTTP request

        try {

            URL url = new URL("http://25.85.175.237/jsonTest.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            // check for request method

            if (method.equals("POST")) {
                System.out.println("Ya got here kiddo");
                // request method is POST

                urlConnection.setRequestMethod("POST");
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                // InputStreamReader isr = new
                // InputStreamReader(urlConnection.getInputStream());
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                jsonResponse = new JSONObject(response.toString());

            } else if (method == "GET") {
                // TOUGH LUCK KIDDO
				/*
				 * request method is GET DefaultHttpClient httpClient = new
				 * DefaultHttpClient(); String paramString =
				 * URLEncodedUtils.format(params, "utf-8"); url += "?" +
				 * paramString; HttpGet httpGet = new HttpGet(url);
				 *
				 * HttpResponse httpResponse = httpClient.execute(httpGet);
				 * HttpEntity httpEntity = httpResponse.getEntity(); is =
				 * httpEntity.getContent();
				 */
            } else {
                System.out.println("No Method?!?!");
                return null;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }finally{
            urlConnection.disconnect();
        }
        return jsonResponse;
    }



    // function get json from url
    // by making HTTP POST or GET method

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            jsonObject=this.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    /*
	 * public JSONObject getJSONFromUrl(final String url) {
	 *
	 * // Making HTTP request try { // Construct the client and the HTTP
	 * request. DefaultHttpClient httpClient = new DefaultHttpClient(); HttpPost
	 * httpPost = new HttpPost(url);
	 *
	 * // Execute the POST request and store the response locally. HttpResponse
	 * httpResponse = httpClient.execute(httpPost); // Extract data from the
	 * response. HttpEntity httpEntity = httpResponse.getEntity(); // Open an
	 * inputStream with the data content. is = httpEntity.getContent();
	 *
	 * } catch (UnsupportedEncodingException e) { e.printStackTrace(); } catch
	 * (ClientProtocolException e) { e.printStackTrace(); } catch (IOException
	 * e) { e.printStackTrace(); }
	 *
	 * try { // Create a BufferedReader to parse through the inputStream.
	 * BufferedReader reader = new BufferedReader(new InputStreamReader( is,
	 * "iso-8859-1"), 8); // Declare a string builder to help with the parsing.
	 * StringBuilder sb = new StringBuilder(); // Declare a string to store the
	 * JSON object data in string form. String line = null;
	 *
	 * // Build the string until null. while ((line = reader.readLine()) !=
	 * null) { sb.append(line + "\n"); }
	 *
	 * // Close the input stream. is.close(); // Convert the string builder data
	 * to an actual string. json = sb.toString(); } catch (Exception e) {
	 * Log.e("Buffer Error", "Error converting result " + e.toString()); }
	 *
	 * // Try to parse the string to a JSON object try { jObj = new
	 * JSONObject(json); } catch (JSONException e) { Log.e("JSON Parser",
	 * "Error parsing data " + e.toString()); }
	 *
	 * // Return the JSON Object. return jObj;
	 */
}
