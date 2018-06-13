package org.twocoolguys.fitlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Back extends AppCompatActivity {

    ImageView barbellRow, latPulldown, dumbbellShrug;
    Animation lefttoright, righttoleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        barbellRow = (ImageView)findViewById(R.id.barbell_row);
        latPulldown = (ImageView)findViewById(R.id.lat_pulldown);
        dumbbellShrug = (ImageView)findViewById(R.id.dumbbell_shrug);

        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);

        barbellRow.setAnimation(lefttoright);
        latPulldown.setAnimation(righttoleft);
        dumbbellShrug.setAnimation(lefttoright);
    }
}
