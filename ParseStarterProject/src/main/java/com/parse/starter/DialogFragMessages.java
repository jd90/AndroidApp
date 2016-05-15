package com.parse.starter;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DialogFragMessages extends DialogFragment implements View.OnClickListener {

    static TextView inputTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       ArrayList<String> comments =  getArguments().getStringArrayList("comments");
        View rootView = inflater.inflate(R.layout.messages_popup, container, false);

        Button send =(Button) rootView.findViewById(R.id.send);

        Button okB = (Button) rootView.findViewById(R.id.ok);
        Button cancelB = (Button) rootView.findViewById(R.id.cancel);

        ListView lv = (ListView) rootView.findViewById(R.id.listview);
        CommentsArrayAdapter ad = new CommentsArrayAdapter(getActivity(), comments);
        lv.setAdapter(ad);

        inputTitle = (TextView) rootView.findViewById(R.id.inputBox);

        okB.setOnClickListener(this);
        cancelB.setOnClickListener(this);
        okB.setTag("ok");
        cancelB.setTag("cancel");

        getDialog().setTitle("Comments");


        this.setCancelable(false);
        return rootView;
    }

    @Override
    public void onClick(View v) {

            if (v instanceof Button) {

                Button b = (Button) v;


                if (b.getTag().equals("ok")) {

                    inputTitle.getText().toString();






                } else {

                    dismiss();
                }
            }
        }


}