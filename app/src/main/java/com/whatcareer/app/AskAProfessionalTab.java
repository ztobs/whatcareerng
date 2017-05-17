package com.whatcareer.app;


import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AskAProfessionalTab extends Fragment  {

    View view;
    EditText name, email, phone, subject, message;
    Spinner gender, age, referal, level;
    private static final String URL = "http://whatcareer.ng/android/askQuestion.php";
    ProgressDialog loading;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ask_a_professional_frag, container, false);

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button submitBtn = (Button) getActivity().findViewById(R.id.submit_btn_p);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitResponse();
            }
        });
    }




    public void submitResponse()
    {

        name = (EditText) getActivity().findViewById(R.id.name_p);
        email = (EditText) getActivity().findViewById(R.id.email_p);
        phone = (EditText) getActivity().findViewById(R.id.phone_p);
        subject = (EditText) getActivity().findViewById(R.id.subject_p);
        message = (EditText) getActivity().findViewById(R.id.message_p);
        gender = (Spinner) getActivity().findViewById(R.id.gender_p);
        age = (Spinner) getActivity().findViewById(R.id.age_p);
        referal = (Spinner) getActivity().findViewById(R.id.referal_p);

        level = (Spinner) getActivity().findViewById(R.id.level_p);

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
        if(level.getSelectedItem().equals("Choose Current Education Level"))
        {
            TextView errorText = (TextView)level.getSelectedView();
            errorText.setError("Please select", getResources().getDrawable(R.drawable.ic_error_black_24dp));
            level.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background_error), PorterDuff.Mode.SRC_ATOP);
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

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

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
                        params.put("subject",subject.getText().toString());
                        params.put("message",message.getText().toString().trim());
                        params.put("referal",referal.getSelectedItem().toString().trim());
                        params.put("level",level.getSelectedItem().toString().trim());
                        params.put("type","ask-a-pro");

                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            } catch (Exception e) {
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
        referal.setSelection(0);
        subject.setText("");
        message.setText("");
        level.setSelection(0);
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
        level.getBackground().setColorFilter(getResources().getColor(R.color.edittext_background), PorterDuff.Mode.SRC_ATOP);

    }



    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}