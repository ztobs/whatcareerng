package com.tobilukan.whatcareerng;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hp on 12/29/2016.
 */
public class AskACounselorTab extends Fragment implements View.OnClickListener{

    private Toast toast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.ask_a_counselor_frag, container, false);
    }

    public void sendAskProfessional()
    {
        toast = Toast.makeText(getContext(), "Hello", Toast.LENGTH_LONG);
        toast.show();
    }


    @Override
    public void onClick(View v) {

    }
}
