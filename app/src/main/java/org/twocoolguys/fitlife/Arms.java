package org.twocoolguys.fitlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Arms extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    ImageView bicepCurl, tricepsPushdown, shoulderPress;
    Animation righttoleft, lefttoright;

    UserDatabase userDatabase = new UserDatabase(this);
    User onlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arms);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.armsDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);

        SharedPreferences sessionDetails = getSharedPreferences("sessionDetails", MODE_PRIVATE); //get online user
        String s = sessionDetails.getString("sessionUsername", null);
        onlineUser = userDatabase.getUserByName(s);

        navigationView.getMenu().findItem(R.id.nav_account).setTitle("@" + onlineUser.getName()); //set the username on the navigation menu


        bicepCurl = (ImageView)findViewById(R.id.bicep_curl);
        tricepsPushdown = (ImageView)findViewById(R.id.triceps_pushdown);
        shoulderPress = (ImageView)findViewById(R.id.shoulder_press);

        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);

        bicepCurl.setAnimation(lefttoright);
        tricepsPushdown.setAnimation(righttoleft);
        shoulderPress.setAnimation(lefttoright);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.nav_calendar):
                        Intent i = new Intent(getApplicationContext(), Calendar.class);
                        startActivity(i);
                        break;
                    case(R.id.nav_exercises):
                        i = new Intent(getApplicationContext(), Routine.class);
                        startActivity(i);
                        break;
                    case(R.id.nav_logout):
                        i = new Intent(getApplicationContext(), WelcomeActivity.class);
                        startActivity(i);
                        break;
                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
