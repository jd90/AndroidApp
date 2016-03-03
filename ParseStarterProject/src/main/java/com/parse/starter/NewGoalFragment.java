package com.parse.starter;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NewGoalFragment extends DialogFragment implements View.OnClickListener, TextWatcher {

    EditText inputTitle;
    TextView warningMessage;
    Spinner freqSpin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.new_goal_popup, container, false);

        getDialog().setTitle("New Goal");
        inputTitle = (EditText) rootView.findViewById(R.id.titleInput);
        inputTitle.setHint("User Must Enter Title");
        warningMessage = (TextView) rootView.findViewById(R.id.warning);

        Button okB = (Button) rootView.findViewById(R.id.ok);
        Button cancelB = (Button) rootView.findViewById(R.id.cancel);

        okB.setOnClickListener(this);
        cancelB.setOnClickListener(this);
        okB.setTag("ok");
        cancelB.setTag("cancel");

        freqSpin = (Spinner) rootView.findViewById(R.id.freqSpin);

        this.setCancelable(false);
        return rootView;
    }

    @Override
    public void onClick(View v) {


        if (v instanceof Button) {

            Button b = (Button) v;


            if (b.getTag().equals("ok")) {

                if (inputTitle.getText().toString().equals("")) {

                    warningMessage.setVisibility(View.VISIBLE);
                    inputTitle.addTextChangedListener(this);

                } else {
                    //ProfileMainActivity.fgoalStore.add(new Goal(inputTitle.getText().toString(), 8));
                    //ProfileMainActivity.fgoalStore.saveToDatabase();

                    FutureGoals.fgoalStore.add(new Goal(inputTitle.getText().toString(), Integer.parseInt(freqSpin.getSelectedItem().toString())));

                    FutureGoals.fgoalStore.saveToDatabase();


                    dismiss();
                }
            } else {

                dismiss();
            }
        }
    }


    //implementing textWatcher because onKeyListener ... i think only listens for backspace and done from the keyboard?? this one works tho.

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (inputTitle.getText().toString().equals("")) {
            //undecided whether to make this revisible when deleting all text, or to just wait until user attempts to save it having ignored the advise and redeleted their text...
            warningMessage.setVisibility(View.VISIBLE);
        } else {
            warningMessage.setVisibility(View.GONE);

        }
    }
    @Override
    public void afterTextChanged(Editable s) {

    }
}