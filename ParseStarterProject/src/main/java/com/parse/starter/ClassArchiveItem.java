package com.parse.starter;

/**
 * Created by Borris on 23/04/2016.
 */
public class ClassArchiveItem {

    int percent;
    String date;
    String profileName;

    public ClassArchiveItem(int percent, String date){

        this.percent=percent;
        this.date=date;

    }

    public ClassArchiveItem(String profileName, int percent, String date){

        this.percent=percent;
        this.date=date;
        this.profileName=profileName;
    }

    public String getPercentage(){

        if(percent>100){
            percent=100;
        }
        return ""+percent+"%";
    }




}
