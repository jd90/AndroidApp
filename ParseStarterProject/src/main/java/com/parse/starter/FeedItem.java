package com.parse.starter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borris on 05/04/2016.
 */
public class FeedItem {

    String username;
    String profileName;
    int percent;
    String date;
    List comments;

    public FeedItem(String username, String profileName, int percent, String date, List comments){

        this.username=username;
        this.profileName=profileName;
        this.percent=percent;
        this.date=date;
        this.comments=comments;
    }



}
