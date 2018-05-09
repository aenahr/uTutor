package ututor.edu.csulb.ututor;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;

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
     * Sets default hostname and protocol to the server's URL and POST
     * Default Request Method will be POST
     */
    public ServerRequester() {
        serverurl = "http://ututor-server.us-west-1.elasticbeanstalk.com/";
        method = "POST";
    }

    /**
     * Sets hostname and protocol to parameter input.
     * Do not put the pagename as the input
     * Good Example: http://google.com/
     * Bad Example: http://google.com/somepage.html
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
     *                    http://192.158.3.355/
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
     *              2. Web Token: Basic login authentication variable acquired when logging in, leave an blank string if unimplemented
     *              </REQUIRED>
     *              <OPTIONAL>
     *              3.  Key 1:   Name of the first $_POST variable
     *              Ex. 'username' or 'password' or 'email'
     *              4.  Value 1:    Value of the first $_POST variable
     *              Ex. 'Lance' or '12345' or 'lance@gmail.com'
     *              5.  Key 2: Second $_POST variable name
     *              6.  Value 2: Second $_POST variable value
     *              7.  (...)
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
            //Check if there is an odd number of parameters, all requests need a url, token, and several key/value pairs
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
            urlConnection.setConnectTimeout(5000);
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

            //Response is actually made here, any response is thrown into the reader
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            //Response from server terminates when a null value is read
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //Closes the Input Stream
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
