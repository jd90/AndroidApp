package com.parse.starter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borris on 05/04/2016.
 */
public class ClassFeedItem {

    String username;
    String profileName;
    int percent;
    String date;
    List comments;
    List likes;
    String id;

    public ClassFeedItem(String username, String profileName, int percent, String date, List comments, List likes, String id){

        this.username=username;
        this.profileName=profileName;
        this.percent=percent;
        this.date=date;
        this.comments=comments;
        this.likes=likes;
        this.id=id;
    }



}
