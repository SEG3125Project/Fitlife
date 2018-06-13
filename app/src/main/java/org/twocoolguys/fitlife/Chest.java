package org.twocoolguys.fitlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Chest extends AppCompatActivity {

    ImageView flatBench, inclineBench, dumbbellFly, chestDip;
    Animation lefttoright, righttoleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest);

        flatBench = (ImageView)findViewById(R.id.flat_bench);
        inclineBench = (ImageView)findViewById(R.id.dumbbell_inlcine);
        dumbbellFly = (ImageView)findViewById(R.id.dumbbell_fly);
        chestDip = (ImageView)findViewById(R.id.chest_dip);

        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);


        flatBench.setAnimation(lefttoright);
        inclineBench.setAnimation(righttoleft);
        dumbbellFly.setAnimation(lefttoright);
        chestDip.setAnimation(righttoleft);
    }


}
