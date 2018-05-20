package org.twocoolguys.fitlife;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kevin on 29/11/17.
 */

public class Session extends AppCompatActivity {
    private User user;

    public Session(String name){
        UserDatabase users = new UserDatabase(this);    //There's probably a better way to do this
        user = users.getUserByName(name);
    }

    public User getUser(){
        return this.user;
    }
}
