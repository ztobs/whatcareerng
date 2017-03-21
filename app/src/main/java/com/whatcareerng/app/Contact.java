package com.whatcareerng.app;




import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whatcareerng.app.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Contact extends AppCompatActivity implements View.OnClickListener {

    private EditText name, email, subject, message;
    private Button sendBtn;
    private static final String URL = "http://whatcareerng.com/android/sendContact.php";

    private ProgressDialog loading;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);



        actionBar = getSupportActionBar();
        actionBar.setTitle("Contact Form");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff009688));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.name_contact);
        email = (EditText) findViewById(R.id.email_contact);
        subject = (EditText) findViewById(R.id.subject_contact);
        message = (EditText) findViewById(R.id.message_contact);
        sendBtn = (Button) findViewById(R.id.submit_btn_contact);

        loading = new ProgressDialog(this);

        sendBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {


        Boolean err = false;
        if(name.getText().toString().trim().isEmpty())
        {
            name.setError("Empty Field", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            name.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
            err = true;
        }
        if(email.getText().toString().trim().isEmpty())
        {
            email.setError("Empty Field", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            email.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
            err = true;
        }
        else if(!isValidEmailAddress(email.getText().toString().trim()))
        {
            email.setError("Invalid", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            email.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
            err = true;
        }
        if(subject.getText().toString().trim().isEmpty())
        {
            subject.setError("Empty Field", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            subject.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
            err = true;
        }
        if(message.getText().toString().trim().length() <= 15)
        {
            message.setError("Too short", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            message.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
            err = true;
        }

        if(err == true)
        {
            Toast.makeText(this, "Fill the form correctly, all fields are required", Toast.LENGTH_LONG).show();
        }
        else
        {
            clearError();
            loading.setMessage("sending");
            loading.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            try {
                String url2go = URL+"?name="+ URLEncoder.encode(name.getText().toString().trim(), "UTF-8")+"&email="+URLEncoder.encode(email.getText().toString().trim(), "UTF-8")+"&subject="+URLEncoder.encode(subject.getText().toString().trim(), "UTF-8")+"&message="+URLEncoder.encode(message.getText().toString().trim(), "UTF-8");

                StringRequest stringRequest = new StringRequest(url2go, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        if(response.equals("ok"))
                        {
                            Toast.makeText(Contact.this, "Sent Successfully", Toast.LENGTH_LONG).show();
                            cleanFields();
                        }
                        else
                        {
                            Toast.makeText(Contact.this, "An error occurred", Toast.LENGTH_LONG).show();

                        }


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loading.dismiss();
                                Toast.makeText(Contact.this, "Please check your network and retry", Toast.LENGTH_LONG).show();
                            }
                        });
                requestQueue.add(stringRequest);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }


    public void cleanFields()
    {
        name.setText("");
        email.setText("");
        subject.setText("");
        message.setText("");
    }

    public void clearError()
    {
        name.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
        email.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
        subject.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
        message.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
    }



    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
