package com.parse.starter;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Borris on 13/03/2016.
 */
public class ProfileDatastore {

    ArrayList<ClassProfile> profiles;
    int count;

    public ProfileDatastore() {

        profiles= new ArrayList<>();
        count=0;
    }


    public void addProfile(ClassProfile profile){
        profiles.add(profile);
    }

    public boolean nameTaken(String name){

        for(int i=0; i< profiles.size(); i++){
            if(name.equals(profiles.get(i).name)){
                return true;
            }
        }
        return false;
    }

    public ClassProfile getProfile(int i){
        return profiles.get(i);
    };

    public ClassProfile getProfile(String name){

        for(int i=0; i<profiles.size();i++){
            if(profiles.get(i).name.equals(name)){
                return profiles.get(i);
            }
        }
        return null;
    }

    public int getSize(){
        return profiles.size();
    }

    public void removeProfile(ClassProfile profileToRemove){

        try {
            ActGoals.deleteDatabase(profileToRemove.databaseNum);
            Log.i("67056705", "Successfully deleted " + profileToRemove.databaseNum);
        }catch(Exception e){Log.i("67056705", "error deleting databse... not created yet?");
            Log.i("67056705", "error deleting databse... "+e.toString());}
                profiles.remove(profileToRemove);
                //count--; //probably eventually put this in after testing - removes count so after deletion, so that count (database names) dont become infinite

            }





}

