package edu.brynmawr.buildingaccessibility;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import edu.brynmawr.buildingaccessibility.adapters.FormAdapter;
import edu.brynmawr.buildingaccessibility.models.Form;


public class ViewPostsActivity extends Activity {
    private static final String TAG = "ViewPostsActivity";
    protected String message;
    protected String jsonString;
    List<Form> listForms;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewposts);
        Log.e(TAG, "Start  onCreate");
        RecyclerView rvForms = findViewById(R.id.rvForms);

        listForms = new ArrayList<>();

        //Create the adapter
        FormAdapter formAdapter = new FormAdapter(this,listForms);
        //Set the adapter on the recycler view
        rvForms.setAdapter(formAdapter);
        //Set a layout manager
        rvForms.setLayoutManager(new LinearLayoutManager(this));

        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"
                            //http://10.0.2.2:3000/test

                            String baseURL = "http://165.106.115.234:3000/androidGetForms";
                            URL url = new URL(baseURL);

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.connect();
                            Log.e(TAG, "Connected NOW");
                            Scanner in = new Scanner(url.openStream());

                            while(in.hasNext()){
                                String line = in.nextLine();
                                jsonString += line;
                            }
                            in.close();
                            Log.e(TAG, "Response: " + jsonString);
                            jsonString = jsonString.substring(4);
                            Log.e(TAG, "Response: " + jsonString);
                            //String response = in.nextLine();
                           // JSONObject jo = new JSONObject(response);
                            try{
                                JSONObject jo = new JSONObject(jsonString);
                                message = jo.getString("status");
                                Log.e(TAG, "status: " + message);
                                try{
                                    JSONArray forms = jo.getJSONArray("forms");
                                    Log.i(TAG, "Forms" + forms.toString());
                                    listForms.addAll(Form.fromJsonArray(forms));
                                    formAdapter.notifyDataSetChanged();
                                    Log.i(TAG, "number of forms" + listForms.size());
                                } catch(JSONException e){
                                    Log.e(TAG, "Hit json exception", e);
                                }
                            }catch (Throwable t){
                                Log.e(TAG, "Couldnt parse JSON");
                            }
                            // need to set the instance variable in the Activity object
                            // because we cannot directly access the TextView from here

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            message = e.toString();
                        }
                    }
            );

            // this waits for up to 2 seconds
            // it's a bit of a hack because it's not truly asynchronous
            // but it should be okay for our purposes (and is a lot easier)
            executor.awaitTermination(2, TimeUnit.SECONDS);

            // now we can set the status in the TextView
            //tv.setText(message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            // uh oh
            e.printStackTrace();
            //tv.setText(e.toString());
            Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
        }










    }



//    public void onwhat(View v) {
//
//        //TextView tv = findViewById(R.id.statusField);
//        EditText building_name = findViewById(R.id.building_name);
//        EditText username = findViewById(R.id.name);
//        EditText description = findViewById(R.id.description);
//
//        try {
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            executor.execute( () -> {
//                        try {
//                            // assumes that there is a server running on the AVD's host on port 3000
//                            // and that it has a /test endpoint that returns a JSON object with
//                            // a field called "message"
//                            //http://10.0.2.2:3000/test
//
//                            String baseURL = "http://165.106.115.234:3000/androidAddForms?building_name=";
//                            baseURL += building_name.getText().toString() + "&name=" + username.getText().toString() + "&description=" + description.getText().toString();
//                            Log.v("URL", baseURL);
//
//                            URL url = new URL(baseURL);
//
//                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                            conn.setRequestMethod("GET");
//                            conn.connect();
//
//                            Scanner in = new Scanner(url.openStream());
//                            String response = in.nextLine();
//
//                            JSONObject jo = new JSONObject(response);
//
//                            // need to set the instance variable in the Activity object
//                            // because we cannot directly access the TextView from here
//                            message = jo.getString("message");
//
//                        }
//                        catch (Exception e) {
//                            e.printStackTrace();
//                            message = e.toString();
//                        }
//                    }
//            );
//
//            // this waits for up to 2 seconds
//            // it's a bit of a hack because it's not truly asynchronous
//            // but it should be okay for our purposes (and is a lot easier)
//            executor.awaitTermination(2, TimeUnit.SECONDS);
//
//            // now we can set the status in the TextView
//            //tv.setText(message);
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//        }
//        catch (Exception e) {
//            // uh oh
//            e.printStackTrace();
//            //tv.setText(e.toString());
//            Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
}
