package ututor.edu.csulb.ututor;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Primary AsyncTask class that handles server requests
 * Input will consist of Strings which will be translated to Page Names and Request variables
 * Output will be in the form of a JSON object containing information that the server returned
 */
public class ServerRequester extends AsyncTask<String, Void, JSONObject> {
    private final String serverurl;
    private final String method;
    static InputStream is = null;

    /**
     * Sets default hostname and protocol to
     * http://25.85.175.237/
     * Default Request Method will be POST
     */
    public ServerRequester() {
        serverurl = "http://25.85.175.237/";
        method = "POST";
    }

    /**
     * Sets hostname and protocol to parameter input.
     * Do not put the pagename as the input
     * Bad Example: http://google.com/somepage.html
     * Good Example: http://google.com/
     * <p>
     * Sets Default Method to POST
     *
     * @param hostaddress String of the protocol and url combo
     *                    Expects a string in the form of:
     *                    http://yahoo.com/
     *                    or
     *                    https://192.158.3.355/
     */
    public ServerRequester(String hostaddress) {
        serverurl = hostaddress;
        method = "POST";
    }

    /**
     * Sets hostname, protocol and method to parameter input
     *
     * @param hostaddress String of the protocol and url combo
     *                    <p>
     *                    Expects a string in the form of:
     *                    http://yahoo.com/
     *                    or
     *                    https://192.158.3.355/
     * @param meth        Method of the request, case insensitive
     *                    Expects usable method such as:
     *                    POST, GET, HEAD, OPTIONS, PUT, DELETE, or TRACE
     */
    public ServerRequester(String hostaddress, String meth) {
        serverurl = hostaddress;
        method = meth.toUpperCase();
    }

    /**
     * Takes in proper string inputs, appends the first string into a proper URL, the rest will be $POST name value pairs
     * Any response from the server will be in JSON format holding any name value pairs
     *
     * @param param Variable String Input. Expects String input in the following order:
     *              <REQUIRED>
     *              1. Page Name: Will be appended to the server url to direct the request to the proper page
     *              Ex. login.php or userlist.html etc
     *              2. Web Token: Basic login authentication variable acquired when logging in, leave blank if unimplemented
     *              </REQUIRED>
     *              <OPTIONAL>
     *              3.  Key 1:   Name of the first $POST variable
     *              Ex. 'username' or 'password' or 'email'
     *              4.  Value 1:    Value of the first $POST variable
     *              Ex. 'Lance' or '12345' or 'lance@gmail.com'
     *              5.  Key 2: Second $POST variable name
     *              6.  Value 2: Second $POST variable value
     *              </OPTIONAL>
     * @return JSON object holding any server response
     */
    @Override
    protected JSONObject doInBackground(String... param) {
        HttpURLConnection urlConnection = null;
        JSONObject jsonResponse = null;
        try {
            //Check if there is at least 2 String

            if (param.length < 2) {
                jsonResponse.accumulate("Error", "1");
                return jsonResponse;
            }
            //Check if there is an odd number of parameters
            if (param.length % 2 == 1) {
                jsonResponse.accumulate("Error", "1");
                return jsonResponse;
            }
            //Appends the pagename to the server url, full URL is formed here
            URL url = new URL(serverurl + param[0]);
            //Opens the connection to the URL, does not actually connect
            System.out.println("Full URL: " + url.toString());
            StringBuilder parameters = new StringBuilder();
            parameters.append("token=" + param[1]);
            for (int i = 2; i < param.length; i += 2) {
                parameters.append("&" + param[i] + "=" + param[i + 1]);
            }
            String request = parameters.toString();
            System.out.println("Request: " + request);
            urlConnection = (HttpURLConnection) url.openConnection();
            System.out.println("Connection Opened");
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter write = new OutputStreamWriter(urlConnection.getOutputStream());
            write.write(request);
            write.flush();
            write.close();

            //Connection is actually made here, any response is thrown into the reader
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            //Response from server terminates when a null value is read
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                response.append(inputLine);
            }
            //TODO Get a better solution than deleting the Square Brackets from both ends of the JSON
            if (response.charAt(0) == '[') {
                response.deleteCharAt(0);
            }
            if (response.charAt(response.length() - 1) ==']') {
                response.deleteCharAt(response.length() - 1);
            }            //Closes the Input Stream
            in.close();
            //Creates the JSON object from the response that the server gave
            System.out.println("FINAL Response: " + response.toString());
            jsonResponse = new JSONObject(response.toString());
            System.out.println("FINAL JSON: " + jsonResponse.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Closes the connection to the server (Might not be necessary)
            urlConnection.disconnect();
        }
        //Returns the final JSON response
        return jsonResponse;
    }

    //TODO Check if necessary
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            jsonObject = this.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
