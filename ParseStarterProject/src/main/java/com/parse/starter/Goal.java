package com.parse.starter;

/**
 * Created by Borris on 21/02/2016.
 */

public class Goal {

    public String name = "input title here";
    public int done = 0;
    public int total = 7;
    public boolean[] buttons = new boolean[]{true, true, true, true, true, true, true};
    public double percentNum;
    public double percent;

    //have a type variable - enum?
    //have an array for numberInputforDay - aligns with buttons array
    //this adds to the done
    //when variable type is selected the onclick will open an Alert Dialog with a numberinput spinner thing
    //when reopening this pop up it will take its value from the numberinputforday array and you can add to or subtract from it
    //figure out what is needed to save and load this from the cloud
    //save draft and try implementing and testing it


    public Goal(String n, int t){
        this.name = n;
        this.done = 0;
        this.total=t;
        percentNum = (double) 100/total;
        percent=0;
    }

    //could/should encapsulate variables and get Get and Set methods.
    //also make abstract and change to different types of goals/input styles

    public String getPercentage(){
        return "" + (int) percent + "%";
    }

    public String getTargets(){
        return "" + done + " out of " + total;
    }

    public void changePercent(Boolean b){
        if(b){percent += percentNum;}
        else{percent -= percentNum;}

        if(percent > 100){percent =100;}
    }

    public String toString(){
        return name;
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

    public int getButton(int i){
        if(buttons[i]){return 1;}
        else{return 0;}
    }
    public void setButton(int i, int state){
        if(state==1) {buttons[i]=true;}
        else{buttons[i]=false;}
    }



}