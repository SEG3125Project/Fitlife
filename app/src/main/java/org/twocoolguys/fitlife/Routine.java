package org.twocoolguys.fitlife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Routine extends AppCompatActivity {

    ImageView chest, back, legs, arms, core;
    Animation lefttoright, righttoleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

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
}
