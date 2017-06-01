package com.example.uksbraziq.latlong;

import android.content.ClipData;
import com.example.uksbraziq.latlong.JSONParser;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uksbraziq.bean.UserBean;
import com.example.uksbraziq.bean.UserListBean;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpRequestHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import static com.example.uksbraziq.latlong.login_new.KEY_APPNUMBER;
import static android.R.attr.name;
import static com.example.uksbraziq.latlong.login_new.KEY_USERID;
import static com.example.uksbraziq.latlong.login_new.KEY_EMAIL;



public class profile extends AppCompatActivity {

    public static String KEY_USER_ID = "user_id";
    public static String KEY_APPNO = "appNumber";
    public static String KEY_APPFNAME = "appFirstName";
    public static String KEY_APPLNAME = "app_lastname";
    public static String KEY_APPDOCNO = "app_docno";

    private TextView textView;
    private ListView listView;
    private TextView nationality;
    //private List<UserBean> user;

    private String id;
    private String appNumber;
    private String fname;
    private String app_lastname;
    private String appDocNo;
    private String jsonResponse;
    private List<UserBean> appList = new ArrayList<UserBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = (TextView) findViewById(R.id.tag);
        listView = (ListView) findViewById(R.id.list_item);
        id = getIntent().getStringExtra(String.valueOf(login_new.KEY_USERID));

        KEY_APPFNAME = fname;

        String THE_URL = "http://192.168.1.164:8080/myaidservices/webapi/locationtag/fetchDetailInfo/" + id;
        StringRequest request = new StringRequest(THE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String datas) {
                datas.toString();
                System.out.println("user Id "+ id+" AND THE JSON IS : "+datas);

                try{
                    textView.setText(id);

                    JSONObject object = new JSONObject(datas);
                    JSONArray array = object.getJSONArray("listVisaApplication");
                    String[] data = new String[array.length()];

                    /*
                    for (int i = 0; i < array.length(); i++) {





                        JSONObject obj = array.getJSONObject(i);
                        obj = array.getJSONObject(i);
                        data[i] = obj.getString("appFirstName");

                        KEY_APPFNAME = obj.getString("appFirstName");
                        String appno = obj.getString("appNumber");

                        System.out.println("First Name is " + KEY_APPFNAME);
                        System.out.println("Application number is >> "+ appno);

                       String fname = KEY_APPFNAME;



                    }
                    */

                    showlistview(array);

                }catch (Exception e){
                    System.out.println("ERROR ::  " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_APPFNAME, fname);
                //map.put(PASSWORD, password);

                return map;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(profile.this);
        rQueue.add(request);
    }


    private void showlistview(JSONArray array){

        String F = KEY_APPFNAME;
        Intent intent = new Intent();
        intent.putExtra(KEY_APPFNAME,fname);
        System.out.println("first name >>>> "+ F);
        Bundle bundle = new Bundle();
            //if(bundle != null) {
               ArrayList<String> ar = new ArrayList<String>();

            for(int i = 0; i<array.length(); i++){

                JSONObject obj = null;
                try {
                    obj = array.getJSONObject(i);




                    System.out.println("GETEXTRA "+ intent.getStringExtra(KEY_APPFNAME));
                    ar.add(obj.getString("appFirstName") + " "+obj.getString("appLastName")+" - "+obj.getString("appNumber"));
                    System.out.println("Show ListView appNumber is "+ bundle.getString("appNumber"));
                    System.out.println("fname is " + obj.getString("appFirstName"));
                    ListView myLV = (ListView)findViewById(R.id.list_item);
                    myLV.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ar));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                }
           // }
        }
}
//System.out.println("USER id at profile is "+ getIntent().getStringExtra(String.valueOf(login_new.KEY_USERID)));
//        textView.setText(getIntent().getStringExtra(String.valueOf(login_new.KEY_USERID)));
//        System.out.println("Doc number name is " + getIntent().getStringExtra(this.KEY_APPDOCNO));
//
//
// Intent intent = getIntent();
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            String app_number = KEY_APPNUMBER;
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(profile.this,application.class);
//                intent.putExtra(KEY_APPNUMBER, app_number);
//                System.out.println("Selected app number : "+ app_number);
//                startActivity(intent);
//
//            }
//
//        });
        //int userId = KEY_USER_ID;
        //KEY_USER_ID = Integer.toString(userId);
        //String userId = Integer.toString(KEY_USER_ID );
        //textView.setText(intent.getIntExtra(login_new.KEY_USERID));
//        System.out.println("This is full name : " + intent.getStringExtra(login_new.KEY_FULLNAME));
//        System.out.println("KEY_NATIONALITY at profile class " + intent.getStringExtra(login_new.KEY_NATIONALITY));
        //System.out.println("User id is :" + intent.getStringExtra(String.valueOf(login_new.KEY_USERID)));

