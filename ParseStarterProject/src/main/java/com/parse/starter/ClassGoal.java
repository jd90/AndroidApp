package com.parse.starter;

import android.util.Log;

/**
 * Created by Borris on 21/02/2016.
 */

public class ClassGoal {

    public String name = "input title here";
    public int done = 0;
    public int total = 7;
    public boolean[] buttons = new boolean[]{true, true, true, true, true, true, true};
    public double percentNum=0;
    public double percent=done*((double) 100/total);
    public String profileName="";

    public int[] buttonsThrough = new int[]{0,0,0,0,0,0,0};

    boolean type = false;

    //have a type variable - enum?
    //have an array for numberInputforDay - aligns with buttons array
    //this adds to the done
    //when variable type is selected the onclick will open an Alert Dialog with a numberinput spinner thing
    //when reopening this pop up it will take its value from the numberinputforday array and you can add to or subtract from it
    //figure out what is needed to save and load this from the cloud
    //save draft and try implementing and testing it

    public ClassGoal(String n, int t){// take this out once converted to online loading properly
        this.name = n;
        this.done = 0;
        this.total=t;
        percentNum = (double) 100/total;
        percent=0;


    }
    public ClassGoal(String n, int t, boolean type, String profileName){
        this.name = n;
        this.done = 0;
        this.total=t;
        percentNum = (double) 100/total;
        percent=0;
        this.type=type;
        this.profileName = profileName;
    }

    //could/should encapsulate variables and get Get and Set methods.
    //also make abstract and change to different types of goals/input styles

    public String getPercentage(){
        double per = done*((double) 100/total);
        double returnPercent = per;
        if(returnPercent > 100){returnPercent =100;}

        return (int) returnPercent + "%";
    }

    public double getPercentage2(){
        double per = done*((double) 100/total);
        double returnPercent = per;
        if(returnPercent > 100){returnPercent =100;}
        return returnPercent;
    }

    public String getTargets(){
        return "" + done + " out of " + total;
    }

    public void changePercent(Boolean b){
        if(b){percent += percentNum;}
        else{percent -= percentNum;}

    }
    public void changePercent(Boolean b, int amount){
        if(b){percent += amount*percentNum;}
        else{percent -= amount* percentNum;}

    }




    public String toString(){
        return name+" "+done+" "+profileName;
    }

    public void buttonClick(int s){
        //int a = Integer.parseInt(s);
        if(buttons[s]) {
            buttons[s] = false;
            changePercent(true);
            done++;}
        else{buttons[s]=true;
            changePercent(false);
            done--;}

    }

    public void buttonClick(int s, int amount, boolean updown){
        Log.i("3333", " buttonsthrough is"+buttonsThrough[s]);
        Log.i("3333", " amount is "+amount);
        Log.i("3333", "1"+ updown);
        if(!updown){


                Log.i("3333", "2");
                changePercent(true, amount);

                Log.i("3333", "3");
                done+= amount;

                Log.i("3333", "4");
                buttonsThrough[s]+=amount;
        }else{
                changePercent(false, amount);
                done-= amount;
                buttonsThrough[s]-=amount;
        }

        Log.i("3333", " buttonsthrough is"+buttonsThrough[s]);
        //this doesnae work. it has to change the amount and the done and the percent etc to what done is - and if numberpicker =0 it shouldnt subtract 0, but set it to 0
        if(buttonsThrough[s] == 0){    //dae this with the setValue of the numberpicker - compare it to the selected amount and work from there, etc.
            Log.i("3333", " setting to not clicked");
            buttons[s]=true;}else{buttons[s]=false;}

    }

    public int getButton(int i){
        if(buttons[i]){return 1;}
        else{return 0;}
    }
    public void setButton(int i, int state){
        if(state==1) {buttons[i]=true;}
        else{buttons[i]=false;}
    }



}