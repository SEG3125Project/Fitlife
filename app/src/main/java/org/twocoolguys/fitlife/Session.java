package org.twocoolguys.fitlife;

import android.support.v7.app.AppCompatActivity;


public class Session extends AppCompatActivity {
    private User user;

    public Session(String name){
        UserDatabase users = new UserDatabase(this);
        user = users.getUserByName(name);
    }

    public User getUser(){
        return this.user;
    }
}
