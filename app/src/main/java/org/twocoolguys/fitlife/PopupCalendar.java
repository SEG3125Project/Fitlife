package org.twocoolguys.fitlife;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

public class PopupCalendar extends AppCompatActivity { //ended up not using this class at all... originally was going to make this the popup as an activity that has a smaller width and height, but in the end decided to use AlertDialog. I'll keep this here in case we run into problems with that and have to use this.

    String date;
    TextView datePopup;
    Button logExerciseButton;
    Button logNutritionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_calendar);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; //getting width and height of the screen
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*.9)); //making screen smaller so its a popup

        date = getIntent().getStringExtra("date"); //get date from the last activity

        datePopup = (TextView)findViewById(R.id.datePopup);
        logExerciseButton = (Button)findViewById(R.id.logExercise);
        logNutritionButton = (Button)findViewById(R.id.logNutrition);

        datePopup.setText(date);



    }
}
