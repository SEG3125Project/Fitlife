package org.twocoolguys.fitlife;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.util.Calendar.getInstance;

public class Calendar extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private CalendarView calendarView;

    String date;
    UserDatabase userDatabase = new UserDatabase(this);
    ExerciseDatabase exerciseDatabase = new ExerciseDatabase(this);
    NutritionDatabase nutritionDatabase = new NutritionDatabase(this);
    User onlineUser;

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

        //get online user
        SharedPreferences sessionDetails = getSharedPreferences("sessionDetails", MODE_PRIVATE);
        String s = sessionDetails.getString("sessionUsername", null);
        onlineUser = userDatabase.getUserByName(s);
        Log.d("USER", onlineUser.getName());

        Log.e("TEST", onlineUser.getName());


        navigationView.getMenu().findItem(R.id.nav_account).setTitle(onlineUser.getFirstName() + " " + onlineUser.getLastName()); //set the username on the navigation menu

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                //this is where the pressing of the date stuff will happen


                java.util.Calendar selected = java.util.Calendar.getInstance(); //we have same name as java's calendar class
                selected.set(year, month, day);
                SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM d, yyyy"); //set format format for date
                String dayTitlePopup = formatter.format(selected.getTime());


                date = year + "/" + (month + 1) + "/" + day;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Calendar.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_popup_calendar, null);

                TextView datePopup = (TextView) mView.findViewById(R.id.datePopup);
                Button logExerciseButton = (Button) mView.findViewById(R.id.logExercise);
                Button logNutritionButton = (Button) mView.findViewById(R.id.logNutrition);
                Button cancelButton = (Button) mView.findViewById(R.id.cancel);


                addExercises(mView, date);
                addCardio(mView, date);
                addNutritions(mView, date);

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show(); //create new dialog box with option to see current info for that day or to log exercises, nutrition


                datePopup.setText(dayTitlePopup); //set selected date to the date of the popup


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


            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.nav_calendar):
                        Intent i = new Intent(getApplicationContext(), Calendar.class);
                        startActivity(i);
                        break;
                    case (R.id.nav_exercises):
                        i = new Intent(getApplicationContext(), Routine.class);
                        startActivity(i);
                        break;
                    case (R.id.nav_logout):
                        i = new Intent(getApplicationContext(), WelcomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        break;
                }

                return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addExercises(View view, String date) {

        boolean showNone = true;

        Exercise[] exercises = exerciseDatabase.getAllExercises();

        LinearLayout mLinearLayout = (LinearLayout) view.findViewById(R.id.exerciseLayout);


        for (final Exercise e : exercises) {
            if ((e.getOwner() != null) && ((e.getOwner().equals(onlineUser.getName()) && e.getDate().equals(date) && !e.getName().equals("") && !e.getWeight().equals("")))) {

                LinearLayout titleHorizontal = new LinearLayout(this);
                titleHorizontal.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                titleHorizontal.setLayoutParams(titleParams);

                LinearLayout underline = new LinearLayout(this); //underline below exercise title
                underline.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                underlineParams.setMargins(40,0,40,0);
                underline.setLayoutParams(underlineParams);


                LinearLayout horizontal = new LinearLayout(this);
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                horizontal.setGravity(Gravity.CENTER_HORIZONTAL);
                horizontal.setLayoutParams(params);
                //horizontalRight
                LinearLayout horizontalRight = new LinearLayout(this);
                horizontalRight.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                paramsRight.weight = 1.0f;
                horizontalRight.setLayoutParams(paramsRight);
                //horizontalLeft
                LinearLayout horizontalLeft = new LinearLayout(this);
                horizontalLeft.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );


                paramsLeft.weight = 6.0f;
                horizontalLeft.setLayoutParams(paramsLeft);


                String exerciseString = "         Weight: " + e.getWeight() + "lbs\n         Sets: " + e.getSets() + "\n         Reps: " + e.getReps() + "\n";
                Button delete = new Button(this);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!e.getIsCardio().equals("")){
                            Exercise newExercise = new Exercise("", e.getIsCardio(), e.getTime(), "", "", "", e.getOwner(), e.getDate());
                            exerciseDatabase.addExercise(newExercise);
                        }

                        boolean deleted = exerciseDatabase.deleteExercise(e.getName(), e.getIsCardio(), e.getTime(), e.getWeight(), e.getSets(), e.getReps(), e.getOwner(), e.getDate());
                        if(deleted) {
                            Toast.makeText(getApplicationContext(), "Exercise Deleted.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Calendar.this, Calendar.class);
                            startActivity(i);
                        }
                    }
                });

                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(80, 80);
                paramsButton.gravity = Gravity.END | Gravity.CENTER_VERTICAL;


                paramsButton.setMargins(0,20,0,20); //set margins to button

                delete.setLayoutParams(paramsButton);
                delete.setBackgroundResource(R.drawable.trash);


                TextView titleTextView = new TextView(this);
                TextView textView = new TextView(this);
                View underlineView = new View(this);


                titleTextView.setText("     " + e.getName());
                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);

                underlineView.setBackgroundResource(R.drawable.horizontal_line);

                textView.setText(exerciseString);

                underline.addView(underlineView);
                horizontalLeft.addView(textView);
                horizontalRight.addView(delete);
                horizontal.addView(horizontalLeft);
                horizontal.addView(horizontalRight);
                titleHorizontal.addView(titleTextView);

                mLinearLayout.addView(titleHorizontal);
                mLinearLayout.addView(underline);
                mLinearLayout.addView(horizontal);
                showNone = false;
            }
        }

        if(showNone){
            TextView textView = new TextView(this);
            textView.setText("You have not logged any exercises for this date. Click 'Log Exercise' below to enter this dates information.");
            mLinearLayout.addView(textView);
        }
    }
    public void addCardio(View view, String date) {

        boolean showNone = true;

        Exercise[] exercises = exerciseDatabase.getAllExercises();

        LinearLayout mLinearLayout = (LinearLayout) view.findViewById(R.id.cardioLayout);


        for (final Exercise e : exercises) {
            if ((e.getOwner() != null) && (e.getOwner().equals(onlineUser.getName()) && e.getDate().equals(date) && !e.getIsCardio().equals(""))) {

                LinearLayout titleHorizontal = new LinearLayout(this);
                titleHorizontal.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
//                titleParams.gravity = Gravity.CENTER_HORIZONTAL;
                titleHorizontal.setLayoutParams(titleParams);

                LinearLayout horizontal = new LinearLayout(this);
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                horizontal.setLayoutParams(params);


                LinearLayout underline = new LinearLayout(this); //underline below exercise title
                underline.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                underlineParams.setMargins(40,0,40,0);
                underline.setLayoutParams(underlineParams);

                LinearLayout horizontalRight = new LinearLayout(this);
                horizontalRight.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                paramsRight.weight = 1.0f;
                horizontalRight.setLayoutParams(paramsRight);
                //horizontalLeft
                LinearLayout horizontalLeft = new LinearLayout(this);
                horizontalLeft.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );


                paramsLeft.weight = 6.0f;
                horizontalLeft.setLayoutParams(paramsLeft);


                String exerciseString = "         Time: " + e.getTime() + "mins\n";
                Button delete = new Button(this);
//                delete.setBackground();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Exercise newExercise = new Exercise(e.getName(), "", "", e.getWeight(), e.getSets(), e.getReps(), e.getOwner(), e.getDate());
                        exerciseDatabase.addExercise(newExercise);
                        boolean deleted = exerciseDatabase.deleteExercise(e.getName(), e.getIsCardio(), e.getTime(), e.getWeight(), e.getSets(), e.getReps(), e.getOwner(), e.getDate());
                        if(deleted) {
                            Toast.makeText(getApplicationContext(), "Cardio Deleted.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Calendar.this, Calendar.class);
                            startActivity(i);
                        }
                    }
                });

                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(80, 80);
//                paramsButton.weight = 1.0f;
                paramsButton.gravity = Gravity.END | Gravity.CENTER_VERTICAL;

                paramsButton.setMargins(0,20,0,20); //set margins to button

                delete.setLayoutParams(paramsButton);
                delete.setBackgroundResource(R.drawable.trash);

                TextView textView = new TextView(this);
                TextView titleTextView = new TextView(this);
                View underlineView = new View(this);


                titleTextView.setText("     " + e.getIsCardio());
                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);

                underlineView.setBackgroundResource(R.drawable.horizontal_line);

                underline.addView(underlineView);
                textView.setText(exerciseString);
                horizontalLeft.addView(textView);
                horizontalRight.addView(delete);
                horizontal.addView(horizontalLeft);
                horizontal.addView(horizontalRight);
                titleHorizontal.addView(titleTextView);

                mLinearLayout.addView(titleHorizontal);
                mLinearLayout.addView(underline);
                mLinearLayout.addView(horizontal);
                showNone = false;
            }
        }

        if(showNone){
            TextView textView = new TextView(this);
            textView.setText("You have not logged any exercises for this date. Click 'Log Exercise' below to enter this dates information.");
            mLinearLayout.addView(textView);
        }
    }

    public void addNutritions(View view, String date) {

        boolean showNone = true;

        Nutrition[] nutritions = nutritionDatabase.getAllNutritions();

        LinearLayout mLinearLayout = (LinearLayout) view.findViewById(R.id.nutritionLayout);

        for (final Nutrition n : nutritions) {
            if (n.getOwner().equals(onlineUser.getName()) && n.getDate().equals(date)) {

                LinearLayout horizontal = new LinearLayout(this);
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                horizontal.setLayoutParams(params);

                //horizontalRight
                LinearLayout horizontalRight = new LinearLayout(this);
                horizontalRight.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                paramsRight.weight = 1.0f;
                horizontalRight.setLayoutParams(paramsRight);
                //horizontalLeft
                LinearLayout horizontalLeft = new LinearLayout(this);
                horizontalLeft.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                paramsLeft.weight = 6.0f;
                horizontalLeft.setLayoutParams(paramsLeft);

                String nutritionString = "         Calories: " + n.getCalories() + "\n         Fats: " + n.getFats() + "g\n         Protein: " + n.getProtein() + "g\n         Carbohydrates: " + n.getCarbs() + "g\n";

                Button delete = new Button(this);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Nutrition", "\n Calories: " + n.getCalories() + "\n Fats: " + n.getFats() + "g\n Protein: " + n.getProtein() + "g\n Carbohydrates: " + n.getCarbs() + "g\n Date: " + n.getDate() + "g\n Owner: " + n.getOwner());
                        boolean deleted = nutritionDatabase.deleteNutrition(n.getCalories(), n.getFats(), n.getProtein(), n.getCarbs(), n.getDate(), n.getOwner());
                        if(deleted) {
                            Toast.makeText(getApplicationContext(), "Nutrition Deleted.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Calendar.this, Calendar.class);
                            startActivity(i);
                        }
                    }
                });

                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(80, 80);
                paramsButton.gravity = Gravity.END | Gravity.CENTER_VERTICAL;

                paramsButton.setMargins(0,20,0,20); //set margins to button

                delete.setLayoutParams(paramsButton);
                delete.setBackgroundResource(R.drawable.trash);




                TextView textView = new TextView(this);
                textView.setText(nutritionString);
                horizontalLeft.addView(textView);
                horizontalRight.addView(delete);
                horizontal.addView(horizontalLeft);
                horizontal.addView(horizontalRight);
                mLinearLayout.addView(horizontal);
                showNone = false;
            }
        }

        if(showNone){
            TextView textView = new TextView(this);
            textView.setText("You have not logged any nutrition for this date. Click 'Log Nutrition' below to enter this dates information.");
            mLinearLayout.addView(textView);
        }
    }


}
