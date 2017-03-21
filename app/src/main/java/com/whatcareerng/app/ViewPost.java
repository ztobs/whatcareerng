package com.whatcareerng.app;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.whatcareerng.app.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewPost extends AppCompatActivity {


    private static final String JSON_URL = "http://whatcareerng.com/android/getPost.php?id=";
    TextView tvPostContent;
    TextView tvPostTitle;
    ActionBar actionBar;
    ProgressDialog loading;
    LinearLayout networkError;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;
    String customPostUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvPostContent = (TextView) findViewById(R.id.view_post_content);
        tvPostTitle = (TextView) findViewById(R.id.my_post_title);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout) ;

        fab = (FloatingActionButton) findViewById(R.id.fab);


        Bundle extras = getIntent().getExtras();
        String postID = extras.getString("postID");

        networkError = (LinearLayout) findViewById(R.id.network_error_view_post);

        loading = new ProgressDialog(this);
        loading.setMessage("loading");
        loading.show();
        getData(postID);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void getData(String id)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JSON_URL + id, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(JSONObject response) {
                try {


                    ImageRequest imageRequest = new ImageRequest(response.getString("post_image"), new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), response);
                            collapsingToolbarLayout.setBackground(bitmapDrawable);
                        }
                    }, 0, 0, ImageView.ScaleType.FIT_CENTER, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    VolleySingleton.getInstance(ViewPost.this).addToRequestQueue(imageRequest);


                    tvPostTitle.setText(response.getString("post_title"));
                    //actionBar.setTitle(response.getString("post_title"));
                    tvPostContent.setText(Html.fromHtml(response.getString("post_content")));
                    customPostUrl = response.getString("url");

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(customPostUrl));
                            startActivity(i);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
                networkError.setVisibility(View.INVISIBLE);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(ViewPost.this, "Network Error", Toast.LENGTH_SHORT).show();
                        networkError.setVisibility(View.VISIBLE);
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(ViewPost.this);
        requestQueue.add(jsonObjectRequest);


    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
