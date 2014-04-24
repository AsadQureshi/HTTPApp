package com.metikulous.httpapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
public class MainActivity extends Activity {
 
    EditText etResponse;
    TextView tvIsConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
 
        // get reference to the views
        
        
        etResponse = (EditText) findViewById(R.id.etResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
 
        // check if you are connected or not
        if(isConnected()){
           tvIsConnected.setBackgroundColor(0xFF00CC00);
           tvIsConnected.setText("You are conncted");
        }
        else{
          tvIsConnected.setText("You are NOT conncted");
        }
 
        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute("http://www.quandl.com/api/v1/datasets/GOOG/NYSE_IBM.json?&trim_start=2012-03-11&trim_end=2014-02-13&sort_order=desc");
    }
    

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
 
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
 
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                return false;  
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            return GET(urls[0]);
        }
        
        
      
        
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            try {
            	JSONObject json = new JSONObject(result);

            	String str = "";
            	String str1 ="";

            	org.json.JSONArray articles = json.getJSONArray("column_names");
            	str += "articles length = "+json.getJSONArray("column_names").length();
            	str += "\n--------\n";
            	org.json.JSONArray dataArray = json.getJSONArray("data").getJSONArray(0);
            	str1 += "DataArray length= "+ json.getJSONArray("data").length();
            	str1 += "\n--------\n";
            	//str1 += "Data in index 0 = " + json.getJSONArray("data").optJSONArray(0);
            	str1 += "Date = " + json.getJSONArray("data").getJSONArray(0).getString(0);
            	str1 += "\n--------\n";
            	str1 += "Open = " + json.getJSONArray("data").getJSONArray(0).getString(1);
            	str1 += "\n--------\n";
            	str1 += "High = " + json.getJSONArray("data").getJSONArray(0).getString(2);
            	str1 += "\n--------\n";
            	str1 += "Low = " + json.getJSONArray("data").getJSONArray(0).getString(3);
            	str1 += "\n--------\n";
            	str1 += "Close = " + json.getJSONArray("data").getJSONArray(0).getString(4);
            	str1 += "\n--------\n";
            	str1 += "Volume = " + json.getJSONArray("data").getJSONArray(0).getString(5);
            	str1 += "\n--------\n";
            	str1 += "Date = " + json.getJSONArray("data").getJSONArray(480).getString(0);
            	str1 += "\n--------\n";
            	str1 += "Open = " + json.getJSONArray("data").getJSONArray(480).getString(1);
            	str1 += "\n--------\n";
            	str1 += "High = " + json.getJSONArray("data").getJSONArray(480).getString(2);
            	str1 += "\n--------\n";
            	str1 += "Low = " + json.getJSONArray("data").getJSONArray(480).getString(3);
            	str1 += "\n--------\n";
            	str1 += "Close = " + json.getJSONArray("data").getJSONArray(480).getString(4);
            	str1 += "\n--------\n";
            	str1 += "Volume = " + json.getJSONArray("data").getJSONArray(480).getString(5);
            	str1 += "\n--------\n";
            	
            	
            	
             	//org.json.JSONArray dataArray = json.getJSONArray("data");
            	//str += "Start :" + articles.getJSONObject(0).getString("start");
            	//str += "names: "+arts.getJSONicleObject(0).names();
            	//str += "\n--------\n";
//            	//str += "name: "+articles.getJSONObject(0).getString("source_code");
//            	articles.length(); // --> 2
//              articles.getJSONObject(0); // get first article in the array
//              articles.getJSONObject(0).names(); // get first article keys [Date","Open","High","Low","Close","Volume"]
//              articles.getJSONObject(0).getString("Date"); // return an article url
//              articles.getJSONObject(0).getString("Open");
//              articles.getJSONObject(0).getString("High");
//              articles.getJSONObject(0).getString("Low");
//              articles.getJSONObject(0).getString("Close");
//              articles.getJSONObject(0).getString("Volume");

            	//etResponse.setText(str);
            	etResponse.setText(str1);
            	//etResponse.setText(json.toString(1));

            	} catch (JSONException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
            	}
           //etResponse.setText(result);
//            JSONObject json = new JSONObject(result); // convert String to JSONObject
//            JSONArray articles= json.getJSONArray("errors"); // get articles array
//            articles.length(); // --> 2
//            articles.getJSONObject(0); // get first article in the array
//            articles.getJSONObject(0).names(); // get first article keys [Date","Open","High","Low","Close","Volume"]
//            articles.getJSONObject(0).getString("Date"); // return an article url
//            articles.getJSONObject(0).getString("Open");
//            articles.getJSONObject(0).getString("High");
//            articles.getJSONObject(0).getString("Low");
//            articles.getJSONObject(0).getString("Close");
//            articles.getJSONObject(0).getString("Volume");
//            JSONObject json = new JSONObject(result); // convert String to JSONObject
//            etResponse.setText(json.toString());
//            JSONArray articles = json.getJSONArray("articleList"); // get articles array
//            articles.length(); 
//            articles.getJSONObject(1); 
//            articles.getJSONObject(1).names() ;
//            articles.getJSONObject(1).getString("");
            
       }
        GraphViewSeries example = new GraphViewSeries(new GraphViewData[] {
        	    new GraphViewData(1, 2.0d),
        	    new GraphViewData(2, 1.5d),
        	    new GraphViewData(3, 2.5d),
        	    new GraphViewData(4, 1.0d),
        	    new GraphViewData(5, 3.0d),
        	    new GraphViewData(6, 4.75d)
        	});
        GraphView graphView = new MyGraphView.LineGraphView(this,"GraphViewDemo");
        	   
        	graphView.addSeries(example); // data
        	 
        	LinearLayout layout = (LinearLayout) findViewById(R.layout.main);
        	layout.addView(MyGraphView);
        	
        	
//        	private int getJSONArray(int i) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
        
		
   
}
}

