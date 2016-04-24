package com.parse.starter;

/**
 * Created by Borris on 23/04/2016.
 */
public class ClassArchiveItem {

    int percent;
    String date;

    public ClassArchiveItem(int percent, String date){

        this.percent=percent;
        this.date=date;

    }

    public String getPercentage(){

        if(percent>100){
            percent=100;
        }
        return ""+percent+"%";
    }




}
