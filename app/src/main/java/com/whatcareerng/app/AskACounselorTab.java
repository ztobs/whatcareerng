package com.whatcareerng.app;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whatcareerng.app.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class AskACounselorTab extends Fragment{

    private Toast toast;
    View view;
    EditText name, email, phone, subject, message;
    Spinner gender, age, referal;
    private static final String URL = "http://whatcareerng.com/android/askQuestion.php";
    ProgressDialog loading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.ask_a_counselor_frag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btn = (Button) getActivity().findViewById(R.id.submit_btn_c);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitResponse();
            }
        });
    }

    public void submitResponse()
    {

        name = (EditText) getActivity().findViewById(R.id.name_c);
        email = (EditText) getActivity().findViewById(R.id.email_c);
        phone = (EditText) getActivity().findViewById(R.id.phone_c);
        subject = (EditText) getActivity().findViewById(R.id.subject_c);
        message = (EditText) getActivity().findViewById(R.id.message_c);
        gender = (Spinner) getActivity().findViewById(R.id.gender_c);
        age = (Spinner) getActivity().findViewById(R.id.age_c);
        referal = (Spinner) getActivity().findViewById(R.id.referal_c);

        loading = new ProgressDialog(getContext());
        loading.setMessage("submitting");

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
        if(phone.getText().toString().trim().isEmpty())
        {
            phone.setError("Empty Field", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            phone.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
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
        if(age.getSelectedItem().equals("Age"))
        {
            TextView errorText = (TextView)age.getSelectedView();
            errorText.setError("Please select", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            age.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
            err = true;
        }
        if(gender.getSelectedItem().equals("Gender"))
        {
            TextView errorText = (TextView)gender.getSelectedView();
            errorText.setError("Please select", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            gender.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
            err = true;
        }
        if(referal.getSelectedItem().equals("Where You Heard About Us"))
        {
            TextView errorText = (TextView)referal.getSelectedView();
            errorText.setError("Please select", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            referal.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
            err = true;
        }

        if(err == true)
        {
            Toast.makeText(getContext(), "Fill the form correctly, all fields are required", Toast.LENGTH_LONG).show();
        }
        else
        {
            clearError();
            loading.show();
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            try {
                String url2go = URL+"?name="+ URLEncoder.encode(name.getText().toString().trim(), "UTF-8")+"&email="+URLEncoder.encode(email.getText().toString().trim(), "UTF-8")+"&phone="+URLEncoder.encode(phone.getText().toString().trim(), "UTF-8")+"&gender="+URLEncoder.encode(gender.getSelectedItem().toString(), "UTF-8")+"&age="+URLEncoder.encode(age.getSelectedItem().toString(), "UTF-8")+"&subject="+URLEncoder.encode(subject.getText().toString().trim(), "UTF-8")+"&message="+URLEncoder.encode(message.getText().toString().trim(), "UTF-8")+"&referal="+URLEncoder.encode(referal.getSelectedItem().toString().trim(), "UTF-8")+"&type=ask-a-coun";

                StringRequest stringRequest = new StringRequest(url2go, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        if(response.equals("ok"))
                        {
                            Toast.makeText(getContext(), "Sent Successfully", Toast.LENGTH_LONG).show();
                            cleanFields();
                        }
                        else if(response.equals("dup"))
                        {
                            Toast.makeText(getContext(), "Ignored, already sent before", Toast.LENGTH_LONG).show();
                            cleanFields();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();

                        }


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loading.dismiss();
                                Toast.makeText(getContext(), "Please check your network and retry", Toast.LENGTH_LONG).show();
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
        phone.setText("");
        age.setSelection(0);
        gender.setSelection(0);
        subject.setText("");
        message.setText("");
        gender.setSelection(0);
    }

    public void clearError()
    {
        name.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
        email.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
        phone.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
        age.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
        gender.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
        referal.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);
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
