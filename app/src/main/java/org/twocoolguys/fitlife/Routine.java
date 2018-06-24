package org.twocoolguys.fitlife;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Routine extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    //NavigationView navigationView;

    ImageView chest, back, legs, arms, core;
    Animation lefttoright, righttoleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.routineDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*navigationView = (NavigationView) findViewById(R.id.nav_view); //need to figure out how to bring back the colour of the icons in nav menu
        navigationView.setItemIconTintList(null);*/

        chest = (ImageView) findViewById(R.id.chestImage);
        back = (ImageView) findViewById(R.id.backImage);
        legs = (ImageView) findViewById(R.id.legsImage);
        arms = (ImageView) findViewById(R.id.armsImage);
        core = (ImageView) findViewById(R.id.coreImage);

        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);

        chest.setAnimation(righttoleft);
        back.setAnimation(lefttoright);
        legs.setAnimation(righttoleft);
        arms.setAnimation(lefttoright);
        core.setAnimation(righttoleft);

        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Routine.this, Chest.class);
                startActivity(i);

            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Routine.this, Back.class);
                startActivity(i);

            }

        });

        legs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Routine.this, Legs.class);
                startActivity(i);

            }

        });

        arms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Routine.this, Arms.class);
                startActivity(i);

            }

        });

        core.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Routine.this, Core.class);
                startActivity(i);

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

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.nav_account:
                Intent acc = new Intent(Routine.this, Back.class); //Don't know where to link this to rn.
                startActivity(acc);
                break;
            case R.id.nav_calendar:
                Intent cal = new Intent(Routine.this, Homepage.class); //Don't know where to link this to either.
                startActivity(cal);
                break;
            case R.id.nav_exercises:
                Intent exer = new Intent(Routine.this, Routine.class); //links to exercises
                startActivity(exer);
                break;

            case R.id.nav_logout:
                Intent lo = new Intent(Routine.this, Routine.class); //gotta log out, don't know what to do with this rn.
                startActivity(lo);
                break;
        }

    }*/
}
