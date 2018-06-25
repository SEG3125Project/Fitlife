package org.twocoolguys.fitlife;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static java.util.Calendar.getInstance;

public class Calendar extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private CalendarView calendarView;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.calendarDrawerLayout); //navigation bar
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);

        calendarView = (CalendarView) findViewById(R.id.calendarView);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                //this is where the pressing of the date stuff will happen


                date = year + "/" + (month + 1) + "/" + day;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Calendar.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_popup_calendar, null);

                TextView datePopup = (TextView) mView.findViewById(R.id.datePopup);
                Button logExerciseButton = (Button) mView.findViewById(R.id.logExercise);
                Button logNutritionButton = (Button) mView.findViewById(R.id.logNutrition);
                Button cancelButton = (Button) mView.findViewById(R.id.cancel);


                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show(); //create new dialog box with option to see current info for that day or to log exercises, nutrition


                datePopup.setText(date); //set selected date to the date of the popup


                logExerciseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Calendar.this, LogExercise.class);
                        i.putExtra("date", date);
                        startActivity(i); //go to exercise page

                    }

                });

                logNutritionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Calendar.this, LogNutrition.class);
                        i.putExtra("date", date);
                        startActivity(i); //go to nutrition page

                    }

                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Calendar.this, Calendar.class);
                        startActivity(i); //restart , go back to calendar
                    }

                });



              /*  Intent i = new Intent(Calendar.this, PopupCalendar.class);
                i.putExtra("date", date);
                startActivity(i);*/


                //System.out.println(date);
            }
        });

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
