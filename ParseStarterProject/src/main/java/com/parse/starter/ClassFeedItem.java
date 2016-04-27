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
    List itemSeen;
    String id;

    public ClassFeedItem(String username, String profileName, int percent, String date, List comments, List likes, List itemSeen, String id){

        this.username=username;
        this.profileName=profileName;
        this.percent=percent;
        this.date=date;
        this.comments=comments;
        this.likes=likes;
        this.itemSeen=itemSeen;
        this.id=id;
    }



}
