package com.tobilukan.whatcareerng;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AskAProfessionalTab extends Fragment {

    Toast toast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ask_a_professional_frag, container, false);

    }

    public void sendAskCounselor()
    {
        toast = Toast.makeText(getContext(), "Hello", Toast.LENGTH_LONG);
        toast.show();
    }
}
