package com.onelinelibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.OneLineLibrary;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.Priority;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.error.ANError;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.interfaces.BitmapRequestListener;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.interfaces.JSONArrayRequestListener;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.interfaces.JSONObjectRequestListener;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.interfaces.UploadProgressListener;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.interfaces.progressListner;
import onelinelibrary.com.onelinelibrary.main_module.parseJson.MyView;

import static com.onelinelibrary.R.id.button5;


public class MainActivity extends ActionBarActivity {
    long startTime;
    float bandwidth=0;
    int req_count=0;
    ProgressDialog progress;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String URL_IMAGE = "http://i.imgur.com/2M7Hasn.png";
    private static final String URL_IMAGE_LOADER = "http://i.imgur.com/52md06W.jpg";
    RecyclerView recyclerview;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar t=(Toolbar)findViewById(R.id.toolbar);
        t.setTitle("One Line Library");

        /*
        context=getApplicationContext();
        recyclerview=(RecyclerView)findViewById(R.id.recycler);
        String[] tagnames={"country","population","flag","rank"};
        String[] tagtype={"text","text","image","text"};
        String arrayname="worldpopulation";


        */
        MyView mv=new MyView(getApplicationContext());

        //mv.setinputdata(context, recyclerview, "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt", arrayname, tagnames, tagtype, R.layout.list_card_view);

        /**
         *
         * Post Request
         *
         */
        Button button_post=(Button)findViewById(R.id.button4);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_REQUEST();
                Log.d("MAJOR","POST REQUEST MADEE");
            }
        });
        /**
         *
         * ImageUpload
         *
         */
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_UPLOAD();
                Log.d("MAJOR","IMAGE_REQUEST MADE");
            }
        });

        /**
         *
         * ImageDownload
         *
         */
        Button button3=(Button)findViewById(button5);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageDirect(view);
                Log.d("MAJOR","IMAGE_REQUEST MADE1");
            }
        });
        Button button4=(Button)findViewById(R.id.button_network);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check_Network();
                Log.d("MAJOR","CHECKNETWORK STATE");
            }
        });

        Button button5=(Button)findViewById(R.id.button7);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req_count=0;
                makeRequests();
            }
        });
        Button button9=(Button)findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OneLineLibrary.cancelAll();
            }
        });
    }

    private void post_REQUEST() {

        OneLineLibrary.post("http://jiittute.esy.es/MAJOR/post_file.php")
                .addBodyParameter("name", "DHRUV")
                .addBodyParameter("number", "56789")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                        public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d(TAG,"POST_OUTPUT1"+response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d(TAG,"POST_OUTPUT2"+error.toString());
                    }
                });
    }
/*
    private void check_Network() {




        startTime = System.currentTimeMillis();
        final ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar4);

        class abc extends AsyncTask<Void,Void,Void>{
            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Snackbar.make(findViewById(android.R.id.content), "Bandwidth is "+bandwidth, Snackbar.LENGTH_LONG)
                        .show();
                pb.setVisibility(View.INVISIBLE);

                super.onPostExecute(aVoid);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                long endTime = 0;
                BufferedHttpEntity bufHttpEntity = null;
                try {
                    HttpGet httpRequest = new HttpGet(new URL("http://speedtest.ftp.otenet.gr/files/test1Mb.db").toURI());
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse response = (HttpResponse) httpClient.execute(httpRequest);
                    endTime = System.currentTimeMillis();

                    HttpEntity entity = response.getEntity();
                    bufHttpEntity = new BufferedHttpEntity(entity);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //You can re-check the size of your file
                final long contentLength = bufHttpEntity.getContentLength();
                Log.d("DHRUV", "Length"+contentLength);

// Log
                Log.d("DHRUV", "Download time :"+(endTime-startTime)+" ms");

// Bandwidth : size(KB)/time(s)
                 bandwidth = (contentLength*1024) / ((endTime-startTime) *1000);
                Log.d("DHRUV", "Bandwidth"+bandwidth);
                return null;
            }

        }

        abc bc=new abc();
        bc.execute();




       *//* ConnectionQuality connectionQuality = OneLineLibrary.getCurrentConnectionQuality();
        if(connectionQuality == ConnectionQuality.EXCELLENT) {
            // do something
            Toast.makeText(getApplicationContext(),"Excellent",Toast.LENGTH_SHORT).show();
        } else if (connectionQuality == ConnectionQuality.POOR) {
            Toast.makeText(getApplicationContext(),"Poor",Toast.LENGTH_SHORT).show();

            // do something
        } else if (connectionQuality == ConnectionQuality.UNKNOWN) {
            // do something
            Toast.makeText(getApplicationContext(),"Unknown",Toast.LENGTH_SHORT).show();

        }*//*
    }*/

    public void image_UPLOAD(){
        progress = new ProgressDialog(this);
        progress.setMessage("Uploading File ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(0);
        File file=new File(Environment.getExternalStorageDirectory().getPath()+"/MAJOR/screens.jpg");
        OneLineLibrary.upload("http://jiittute.esy.es/MAJOR/postFile.php")
                .parameter("pic",file)
                .setTag("uploadTest")
                .start()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        float p1= Float.parseFloat(""+bytesUploaded);
                        float p2= Float.parseFloat(""+totalBytes);
                        float uploaded=(p1/p2)*100;
                        Log.d("MAJOR","IMAGE_REQUEST MADE"+ Math.round(uploaded));
                        progress.setProgress(Math.round(uploaded));
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("MAJOR","IMAGE_REQUEST MADE"+response.toString());
                        progress.dismiss();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("MAJOR","IMAGE_REQUEST MADE"+error.getMessage());
                        progress.dismiss();
                    }
                });
        progress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OneLineLibrary.cancel(this);
                progress.dismiss();
                Toast.makeText(getApplicationContext(),"Uploading Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        progress.show();

    }
    /*public  void getData() {
        *//**
         *
         * VOLLEY WITH OKHTTP
         *
         */

    /*
        StringRequest stringRequest = new StringRequest("http://resolvefinance.co.uk/Whatsapp/json3.txt",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response returned is","-"+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //                 Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("Volley ka error", "" + error);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this, new OkHttpStack(new com.squareup.okhttp.OkHttpClient()));
        requestQueue.add(stringRequest);

    }*/
    private void makeJSONArrayRequest(final int i) {
        /**
         *
         * JSON ARRAY REQUEST
         *
         */
        OneLineLibrary.load("http://resolvefinance.co.uk/Whatsapp/json10_1.txt")
                .setTag(this)
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .setPriority(Priority.LOW)
                .start()
                .progress(new progressListner() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse array : " + response.toString());
                        req_count++;
                        TextView req=(TextView)findViewById(R.id.textView4);
                        req.setText("Request Made :"+req_count);
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });
    }
    private void makeJSONObjectRequest() {
        /**
         *
         * JSON OBJECT REQUEST
         *
         *
         */
        OneLineLibrary.load("http://resolvefinance.co.uk/Whatsapp/json3.txt")
                .setTag(this)
                .addPathParameter("userId", "1")
                .setPriority(Priority.HIGH)
                .start()
                .progress(new progressListner() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse object : " + response.toString());
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });
    }
    public void makeRequests() {
        /**
         *
         * JSON 10 REQUEST REQUEST
         *
         */


        for (int i = 0; i < 20; i++) {
            makeJSONArrayRequest(i);
        }
    }
    public void cancelAllRequests(View view) {
        /**
         *
         * CANCEL REQUEST
         *
         */

        OneLineLibrary.cancel(this);
    }
    public void loadImageDirect(View view) {
        /**
         *
         * IMAGEVIEW REQUEST
         *
         */
        Log.d(TAG, " timeTakenInMillis ");
        progress = new ProgressDialog(this);
        progress.setMessage("Downloading File ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);

        progress.show();


        OneLineLibrary.load("http://jiittute.esy.es/MAJOR/uploads/screens.jpg")
                .setTag("imageRequestTag")
                .setPriority(Priority.LOW)
                .setImageScaleType(null)
                .setBitmapMaxHeight(500)
                .setBitmapMaxWidth(500)
                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                .start()
                .progress(new progressListner() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        // do anything with progress

                    }
                })
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.d(TAG, "onResponse Bitmap");
                        //imageView.setImageBitmap(response);
                        ImageView image=(ImageView)findViewById(R.id.imageView);
                        image.setImageBitmap(response);
                        progress.dismiss();
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });





/*

        OneLineLibrary.get("https://fierce-cove-29863.herokuapp.com/getAllUsers?page={pageNumber}")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


        OneLineLibrary.post("https://fierce-cove-29863.herokuapp.com/createAnUser")
                .addBodyParameter("firstname", "Amit")
                .addBodyParameter("lastname", "Shekhar")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


//AddingJSON object

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", "Rohit");
            jsonObject.put("lastname", "Kumar");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OneLineLibrary.post("https://fierce-cove-29863.herokuapp.com/createUser")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

        //Adding JSON File
        OneLineLibrary.post("https://fierce-cove-29863.herokuapp.com/postFile")
                //.addFileBody(file) // posting any type of file
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


*/

//JSON object
        /*
        AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsParsed(new TypeToken<List<User>>() {}, new ParsedRequestListener<List<User>>() {
                    @Override
                    public void onResponse(List<User> users) {
                        // do anything with response
                        Log.d(TAG, "userList size : " + users.size());
                        for (User user : users) {
                            Log.d(TAG, "id : " + user.id);
                            Log.d(TAG, "firstname : " + user.firstname);
                            Log.d(TAG, "lastname : " + user.lastname);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        // handle error
                    }
                });

        */


//Downloading file
/*
        AndroidNetworking.download(url,dirPath,fileName)
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

*/


//Uploading File
/*
        AndroidNetworking.upload(url)
                .addMultipartFile("image",file)
                .addMultipartParameter("key","value")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


*/
        /**
         *
         * HOW GOOD IS NETWORK
         *
         */

//Getting response and completion
        /*
        AndroidNetworking.upload(url)
                .addMultipartFile("image",file)
                .addMultipartParameter("key","value")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setExecutor(Executors.newSingleThreadExecutor()) // setting an executor to get response or completion on that executor thread
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // below code will be executed in the executor provided
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

*/

//Canceling Request
      /*  OneLineLibrary.cancel("tag"); // All the requests with the given tag will be cancelled.
        OneLineLibrary.forceCancel("tag");  // All the requests with the given tag will be cancelled , even if any percent threshold is
        // set , it will be cancelled forcefully.
        OneLineLibrary.cancelAll(); // All the requests will be cancelled.
        OneLineLibrary.forceCancelAll(); // All the requests will be cancelled , even if any percent threshold is
        // set , it will be cancelled forcefully.


*/



    }



}
