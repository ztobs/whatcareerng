package com.tobilukan.whatcareerng;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private String postRequestUrl = "http://whatcareerng.com/android/getFeaturedPost.php";
    private ProgressDialog loading;


    public static final String TAG = "JSON";

    private List<Post> posts;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        initData();
        initializeAdapter();

        rv.post(new Runnable() {
            @Override
            public void run() {
                getData();
                initializeAdapter();
            }
        });

        rv.postInvalidate();




    }

    private void initializeData(JSONArray response){
        posts = new ArrayList<>();


        try {

            for(int i=0; i<response.length(); i++)
            {
                JSONObject pt = response.getJSONObject(i);
                String postTitleString = pt.getString("post_title");
                String postContentString = pt.getString("post_content").substring(0, 80)+"...";
                String postUrlString = pt.getString("url");
                String postImageUrlString = pt.getString("post_image");
                String postDateString = pt.getString("post_date");

                //Toast.makeText(MainActivity.this, postImageUrlString, Toast.LENGTH_SHORT).show();

                posts.add(new Post(postTitleString, postContentString, postUrlString, postImageUrlString, postDateString));
            }
        } catch (JSONException e) {
            Log.i(TAG, e.toString());
        }

    }


    private void initData()
    {
        posts = new ArrayList<>();
        posts.add(new Post("", "", "", "", ""));
    }


    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(posts);
        rv.setAdapter(adapter);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ask_a) {
            Intent intent = new Intent(this, Ask.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ask_counselor) {
            Intent ask_c = new Intent(this, Ask.class);
            startActivity(ask_c);
        } else if (id == R.id.ask_counselor) {
            Intent ask_c = new Intent(this, Ask.class);
            startActivity(ask_c);
        } else if (id == R.id.personality_test) {
            String url = "http://www.mba-institute.org/personality-test/Myers-Briggs-MBTI-Personality-Test-Start.php";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (id == R.id.contact) {
            Intent contact = new Intent(this, Contact.class);
            startActivity(contact);
        } else if (id == R.id.about) {
            Intent contact = new Intent(this, About.class);
            startActivity(contact);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getData()
    {

        loading = ProgressDialog.show(this, "Please Wait", "Loading Blog Posts", false, false);

        JsonArrayRequest req = new JsonArrayRequest(postRequestUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                initializeData(response);
                loading.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Check Your Network", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        Log.i(TAG, error.toString());
                    }
                });
        // Instantiate the cache
        //Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        //Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        //RequestQueue requestQueue = new RequestQueue(cache, network);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }


    @Override
    public void onClick(View v) {
        //Toast.makeText(MainActivity.this, "Hey, im working", Toast.LENGTH_SHORT).show();
        getData();
        initializeAdapter();
    }




}


