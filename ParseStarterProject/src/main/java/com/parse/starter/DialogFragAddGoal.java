package com.parse.starter;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class DialogFragAddGoal extends DialogFragment implements View.OnClickListener, TextWatcher {

    EditText inputTitle;
    TextView warningMessage;
    Spinner freqSpin;
    NumberPicker throughPick;
    LinearLayout timesAWeek;
    LinearLayout throughTheWeek;
    boolean saveClickedBool = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.new_goal_popup, container, false);

        timesAWeek =(LinearLayout) rootView.findViewById(R.id.timesAWeek);
        throughTheWeek=(LinearLayout) rootView.findViewById(R.id.throughTheWeek);

        getDialog().setTitle("New Goal");
        inputTitle = (EditText) rootView.findViewById(R.id.titleInput);
        inputTitle.setHint("User Must Enter Title");
        inputTitle.addTextChangedListener(this);
        warningMessage = (TextView) rootView.findViewById(R.id.warning);
        warningMessage.setText("Warning! Title must be:\n" +
                "Provided\nLess than 20 characters");

        Button okB = (Button) rootView.findViewById(R.id.ok);
        Button cancelB = (Button) rootView.findViewById(R.id.cancel);

        okB.setOnClickListener(this);
        cancelB.setOnClickListener(this);
        okB.setTag("ok");
        cancelB.setTag("cancel");


        timesAWeek.setVisibility(View.GONE);
        throughTheWeek.setVisibility(View.GONE);
        freqSpin = (Spinner) rootView.findViewById(R.id.freqSpin);
        throughPick=(NumberPicker) rootView.findViewById(R.id.numPick);
        throughPick.setValue(0);
        throughPick.setMaxValue(999);

        RadioGroup rg= (RadioGroup) rootView.findViewById(R.id.radiogroup);

        RadioButton rb1 = (RadioButton) rootView.findViewById(R.id.radioButton1);
        RadioButton rb2 = (RadioButton) rootView.findViewById(R.id.radioButton2);
        rb1.setTag("times");
        rb2.setTag("through");
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);

        rb1.setChecked(true);
        timesAWeek.setVisibility(View.VISIBLE);
        throughTheWeek.setVisibility(View.GONE);

        this.setCancelable(false);
        return rootView;
    }

    @Override
    public void onClick(View v) {

        if(v instanceof RadioButton){
            if(v.getTag().equals("times")){
                timesAWeek.setVisibility(View.VISIBLE);
                throughTheWeek.setVisibility(View.GONE);
            }else{
                timesAWeek.setVisibility(View.GONE);
                throughTheWeek.setVisibility(View.VISIBLE);
            }
        }else {


            if (v instanceof Button) {

                Button b = (Button) v;


                if (b.getTag().equals("ok")) {

                    if (inputTitle.getText().toString().equals("")||inputTitle.getText().toString().length() >20) {

                        warningMessage.setVisibility(View.VISIBLE);
                        saveClickedBool = true;

                    } else {
                        //ActGoals.fgoalStore.add(new ClassGoal(inputTitle.getText().toString(), 8));
                        //ActGoals.fgoalStore.saveToDatabase();

                        if(timesAWeek.getVisibility()==View.VISIBLE) {

                            ActFutureGoals.fgoalStore.add(new ClassGoal(inputTitle.getText().toString(), Integer.parseInt(freqSpin.getSelectedItem().toString()), false));

                        }else{

                            ActFutureGoals.fgoalStore.add(new ClassGoal(inputTitle.getText().toString(), throughPick.getValue(), true));

                        }
                        ActFutureGoals.fgoalStore.saveToDatabase();
                        dismiss();
                    }
                } else {

                    dismiss();
                }
            }
        }
    }


    //implementing textWatcher because onKeyListener ... i think only listens for backspace and done from the keyboard?? this one works tho.

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        Log.i("contains", inputTitle.getText().toString());

        if(inputTitle.getText().toString().contains("'")||inputTitle.getText().toString().contains("\"")||inputTitle.getText().toString().contains("\\")){
            inputTitle.setText(inputTitle.getText().toString().substring(0, inputTitle.length()-1));
            inputTitle.setSelection(inputTitle.getText().toString().length());//changes cursor to still be at the end
        }
        if(inputTitle.getText().toString().length() > 20){
            warningMessage.setVisibility(View.VISIBLE);
        } else {
            warningMessage.setVisibility(View.GONE);
        }


       if(saveClickedBool) {
           if (inputTitle.getText().toString().equals(""))  {
               //undecided whether to make this revisible when deleting all text, or to just wait until user attempts to save it having ignored the advise and redeleted their text...
               warningMessage.setVisibility(View.VISIBLE);
           } else {
               warningMessage.setVisibility(View.GONE);

           }
       }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
}