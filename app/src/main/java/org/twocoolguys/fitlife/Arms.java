package org.twocoolguys.fitlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Arms extends AppCompatActivity {

    ImageView bicepCurl, tricepsPushdown, shoulderPress;
    Animation righttoleft, lefttoright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arms);

        bicepCurl = (ImageView)findViewById(R.id.bicep_curl);
        tricepsPushdown = (ImageView)findViewById(R.id.triceps_pushdown);
        shoulderPress = (ImageView)findViewById(R.id.shoulder_press);

        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);

        bicepCurl.setAnimation(lefttoright);
        tricepsPushdown.setAnimation(righttoleft);
        shoulderPress.setAnimation(lefttoright);

    }
}
