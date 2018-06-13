package org.twocoolguys.fitlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Core extends AppCompatActivity {

    ImageView plank, legRaises, crunches;
    Animation lefttoright, righttoleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        plank = (ImageView)findViewById(R.id.plank);
        legRaises = (ImageView)findViewById(R.id.leg_raises);
        crunches = (ImageView)findViewById(R.id.crunches);

        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);

        plank.setAnimation(lefttoright);
        crunches.setAnimation(righttoleft);
        legRaises.setAnimation(lefttoright);

    }
}
