package org.twocoolguys.fitlife;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Legs extends AppCompatActivity {

ImageView barbellSquat, dumbbellLunge, romanianDeadlift;
Animation uptodown, downtoup, righttoleft, lefttoright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legs);

        barbellSquat = (ImageView)findViewById(R.id.barbell_squat);
        dumbbellLunge = (ImageView)findViewById(R.id.dumbbell_Lunge);
        romanianDeadlift = (ImageView)findViewById(R.id.romanian_deadlift);

        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);


        barbellSquat.setAnimation(lefttoright);
        dumbbellLunge.setAnimation(righttoleft);
        romanianDeadlift.setAnimation(lefttoright);

    }
}
