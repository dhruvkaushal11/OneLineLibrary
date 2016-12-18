package onelinelibrary.com.onelinelibrary.main_module.parseJson;

/**
 * Created by my pc on 05-09-2016.
 */


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import onelinelibrary.com.onelinelibrary.main_module.RequestQueue;
import onelinelibrary.com.onelinelibrary.main_module.Response;
import onelinelibrary.com.onelinelibrary.main_module.VolleyError;
import onelinelibrary.com.onelinelibrary.main_module.toolbox.OkHttpStack;
import onelinelibrary.com.onelinelibrary.main_module.toolbox.StringRequest;
import onelinelibrary.com.onelinelibrary.main_module.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyView extends LinearLayout {

    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    private static ViewGroup v1;
    private static Integer v12;



    private static String URL1;
    private static String[] tagnames1;
    private static String[] tagtypes1;
    private static String arrayname1;
    private static Integer taglength;
    static ArrayList<String> a1=new ArrayList<String>();
    static ArrayList<String> a2=new ArrayList<String>();
    static ArrayList<String> a3=new ArrayList<String>();
    static ArrayList<String> a4=new ArrayList<String>();
    static ArrayList<String> a5=new ArrayList<String>();
    static ArrayList<String> a6=new ArrayList<String>();
    static ArrayList<String> a7=new ArrayList<String>();
    static ArrayList<String> a8=new ArrayList<String>();
    static ArrayList<String> a9=new ArrayList<String>();
    static ArrayList<String> a10=new ArrayList<String>();


    static Context c1;
    public MyView(Context context) {
        super(context);
        c1=context;
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c1=context;
    }


   public void setinputdata(Context context, RecyclerView recyclerview, String url, String arrayname, String[] tagnames, String[] tagtypes, Integer v){
       recyclerView=recyclerview;
       URL1=url;
       tagnames1=tagnames;
       arrayname1=arrayname;
       taglength=tagnames1.length;
       tagtypes1=tagtypes;
       v12=v;
       getData();
       c1=context;
   }



    public  void getData() {
        /**
         *
         * VOLLEY WITH OKHTTP
         *
         */

        StringRequest stringRequest = new StringRequest("http://resolvefinance.co.uk/Whatsapp/json3.txt",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response returned is","-"+response);
                        try{
                            parseJSON(response);
                        }
                        catch (Exception e){
                            Toast.makeText(c1,"Please check your Internet Connection",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //                 Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("Volley ka error", "" + error);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(c1, new OkHttpStack(new com.squareup.okhttp.OkHttpClient()));
        requestQueue.add(stringRequest);

    }
   /* public  void getData(){
        class GetData extends AsyncTask<Void,Void,String>{
         //   ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
             //   progressDialog = ProgressDialog.show(context, "Fetching Data", "Please wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            //    progressDialog.dismiss();
                try{
                    parseJSON(s);
                }
                catch (Exception e){
                    Toast.makeText(c1,"Please check your Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    Log.i("checkurl",URL1);
                    URL url = new URL(URL1);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }*/
   public void showData(){
       if(taglength==1) {
           adapter = new CardAdapter1(a1,tagtypes1,v12,c1);

       }
       if(taglength==2) {
           adapter = new CardAdapter2(a1,a2,tagtypes1,v12,c1);

       }
       if(taglength==3) {
           adapter = new CardAdapter3(a1,a2,a3,tagtypes1,v12,c1);
          }
      if(taglength==4) {
           adapter = new CardAdapter4(a1,a2,a3,a4,tagtypes1,v12,c1);
      }
       if(taglength==5) {
           adapter = new CardAdapter5(a1,a2,a3,a4,a5,tagtypes1,v12,c1);

       }
       recyclerView.setLayoutManager(new LinearLayoutManager(c1));
       //       mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
       recyclerView.setHasFixedSize(true);
       recyclerView.setAdapter(adapter);
   }


    public  void parseJSON(String json){
        try {
            JSONObject jsonObject=null;
            JSONArray array=null;
            try {
                jsonObject = new JSONObject(json);
                Log.i("jsonarrayr", "is-" + json);
               array = jsonObject.getJSONArray(arrayname1);

            }
            catch (Exception e){
                Log.i("ERROR","INTERNET NOT AVAILABLE");
            }


            for(int i=0; i<array.length(); i++){
                    JSONObject j = array.getJSONObject(i);
                    if(taglength==1) {

                        a1.add(j.getString(tagnames1[0]));
                    }
                if(taglength==2) {
                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                }

                if(taglength==3) {


                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                    a3.add(j.getString(tagnames1[2]));

                }

                if(taglength==4) {

                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                    a3.add(j.getString(tagnames1[2]));
                    a4.add(j.getString(tagnames1[3]));


                }
                if(taglength==5) {

                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                    a3.add(j.getString(tagnames1[2]));
                    a4.add(j.getString(tagnames1[3]));
                    a5.add(j.getString(tagnames1[4]));

                }
                if(taglength==6) {

                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                    a3.add(j.getString(tagnames1[2]));
                    a4.add(j.getString(tagnames1[3]));
                    a5.add(j.getString(tagnames1[4]));
                    a6.add(j.getString(tagnames1[5]));

                }
                if(taglength==7) {

                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                    a3.add(j.getString(tagnames1[2]));
                    a4.add(j.getString(tagnames1[3]));
                    a5.add(j.getString(tagnames1[4]));
                    a6.add(j.getString(tagnames1[5]));
                    a7.add(j.getString(tagnames1[6]));



                }
                if(taglength==8) {


                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                    a3.add(j.getString(tagnames1[2]));
                    a4.add(j.getString(tagnames1[3]));
                    a5.add(j.getString(tagnames1[4]));
                    a6.add(j.getString(tagnames1[5]));
                    a7.add(j.getString(tagnames1[6]));
                    a8.add(j.getString(tagnames1[7]));

        }
                if(taglength==9) {

                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                    a3.add(j.getString(tagnames1[2]));
                    a4.add(j.getString(tagnames1[3]));
                    a5.add(j.getString(tagnames1[4]));
                    a6.add(j.getString(tagnames1[5]));
                    a7.add(j.getString(tagnames1[6]));
                    a8.add(j.getString(tagnames1[7]));
                    a9.add(j.getString(tagnames1[8]));


                }
                if(taglength==10) {

                    a1.add(j.getString(tagnames1[0]));
                    a2.add(j.getString(tagnames1[1]));
                    a3.add(j.getString(tagnames1[2]));
                    a4.add(j.getString(tagnames1[3]));
                    a5.add(j.getString(tagnames1[4]));
                    a6.add(j.getString(tagnames1[5]));
                    a7.add(j.getString(tagnames1[6]));
                    a8.add(j.getString(tagnames1[7]));
                    a9.add(j.getString(tagnames1[8]));
                    a10.add(j.getString(tagnames1[9]));


                }



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        showData();

    }


}
