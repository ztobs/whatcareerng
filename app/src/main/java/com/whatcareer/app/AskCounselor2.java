package com.whatcareer.app;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class AskCounselor2 extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener{

    ActionBar actionBar;
    private static final String TAG = "myDebug";
    private static final String URL = "http://whatcareer.ng/android/askQuestion.php";

    @NotEmpty
    EditText name, phone, ambition, curiousity, good_at, personality;

    @Email
    @NotEmpty
    EditText email;

    @Length(min = 10, message = "Enter atleast 10 characters.")
    EditText message;

    @Select
    Spinner gender, age, level, referal;
    Button sendBtn;

    ProgressDialog loading;
    Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_counselor2);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Talk To A Career Counselor");
        actionBar.setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.name_c);
        email = (EditText) findViewById(R.id.email_c);
        phone = (EditText) findViewById(R.id.phone_c);
        ambition = (EditText) findViewById(R.id.ambition_c);
        curiousity = (EditText) findViewById(R.id.curious_c);
        good_at = (EditText) findViewById(R.id.good_at_c);
        personality = (EditText) findViewById(R.id.personality_c);
        message = (EditText) findViewById(R.id.message_c);
        gender = (Spinner) findViewById(R.id.gender_c);
        age = (Spinner) findViewById(R.id.age_c);
        level = (Spinner) findViewById(R.id.level_c);
        referal = (Spinner) findViewById(R.id.referal_c);
        sendBtn = (Button) findViewById(R.id.submit_btn_c);

        validator = new Validator(this);
        validator.setValidationListener(this);

        loading = new ProgressDialog(this);

        sendBtn.setOnClickListener(this);

    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onValidationSucceeded() {

        loading.setMessage("submitting");
        loading.show();

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("VOLLEY", response);
                    //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

                    loading.dismiss();
                    if(response.equals("ok"))
                    {
                        Toast.makeText(getApplicationContext(), "Sent Successfully", Toast.LENGTH_LONG).show();
                        cleanFields();
                    }
                    else if(response.equals("dup"))
                    {
                        Toast.makeText(getApplicationContext(), "Ignored, already sent before", Toast.LENGTH_LONG).show();
                        cleanFields();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();

                    params.put("name", name.getText().toString().trim());
                    params.put("email", email.getText().toString().trim());
                    params.put("phone",phone.getText().toString().trim());
                    params.put("gender",gender.getSelectedItem().toString());
                    params.put("age",age.getSelectedItem().toString());
                    params.put("message",message.getText().toString().trim());
                    params.put("referal",referal.getSelectedItem().toString().trim());
                    params.put("level",level.getSelectedItem().toString().trim());
                    params.put("ambition",ambition.getText().toString().trim());
                    params.put("personality",personality.getText().toString().trim());
                    params.put("curiousity", curiousity.getText().toString().trim());
                    params.put("good_at", good_at.getText().toString().trim());
                    params.put("type","ask-a-coun");

                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = "All fields are required !!";

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        Button clicked = (Button) view;


        if(clicked.getText().toString().equals("SUBMIT"))
        {
            validator.validate(); // Sapripaar now handles next events
        }

    }



    public void cleanFields()
    {
        name.setText("");
        email.setText("");
        phone.setText("");
        age.setSelection(0);
        gender.setSelection(0);
        message.setText("");
        referal.setSelection(0);
        level.setSelection(0);
        ambition.setText("");
        personality.setText("");
        curiousity.setText("");
        good_at.setText("");
    }




}
