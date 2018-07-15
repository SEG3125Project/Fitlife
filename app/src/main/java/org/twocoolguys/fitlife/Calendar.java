package org.twocoolguys.fitlife;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
                titleParams.gravity = Gravity.CENTER_HORIZONTAL;
                titleHorizontal.setLayoutParams(titleParams);

                LinearLayout horizontal = new LinearLayout(this);
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                horizontal.setLayoutParams(params);

                String exerciseString = "    Weight: " + e.getWeight() + "\n    Sets: " + e.getSets() + "\n    Reps: " + e.getReps() + "\n";
//                String cardioString = "\n Cardio: " + e.getIsCardio() + "\n Time: " + e.getTime();
                Button delete = new Button(this);
//                delete.setBackground();
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
//                paramsButton.weight = 1.0f;
                paramsButton.gravity = Gravity.CENTER_VERTICAL;
//                paramsButton.gravity = Gravity.END;

                delete.setLayoutParams(paramsButton);
                delete.setBackgroundResource(R.drawable.trash);

                //layout_alignParentRight = true

                TextView titleTextView = new TextView(this);
                TextView textView = new TextView(this);

                titleTextView.setText(e.getName());
                textView.setText(exerciseString);

                horizontal.addView(textView);
                horizontal.addView(delete);
                titleHorizontal.addView(titleTextView);

                mLinearLayout.addView(titleHorizontal);
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

                LinearLayout horizontal = new LinearLayout(this);
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                horizontal.setLayoutParams(params);
                String exerciseString = "    Name: " + e.getIsCardio() + "\n    Time: " + e.getTime();
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
                paramsButton.gravity = Gravity.CENTER_VERTICAL;
                delete.setLayoutParams(paramsButton);
                delete.setBackgroundResource(R.drawable.trash);

                TextView textView = new TextView(this);
                textView.setText(exerciseString);
                horizontal.addView(textView);
                horizontal.addView(delete);
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
//                horizontal.setBackgroundColor(0xFF00FF00);

                String nutritionString = "    Calories: " + n.getCalories() + "\n    Fats: " + n.getFats() + "\n    Protein: " + n.getProtein() + "\n    Carbohydrates: " + n.getCarbs();

                Button delete = new Button(this);
//                delete.setBackground();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Log.d("NUTRITION", n.getCalories());
                        Log.d("Nutrition", "\n Calories: " + n.getCalories() + "\n Fats: " + n.getFats() + "\n Protein: " + n.getProtein() + "\n Carbohydrates: " + n.getCarbs() + "\n Date: " + n.getDate() + " \n Owner: " + n.getOwner());
                        boolean deleted = nutritionDatabase.deleteNutrition(n.getCalories(), n.getFats(), n.getProtein(), n.getCarbs(), n.getDate(), n.getOwner());
                        if(deleted) {
                            Toast.makeText(getApplicationContext(), "Nutrition Deleted.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Calendar.this, Calendar.class);
                            startActivity(i);
                        }
                    }
                });

                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(80, 80);
//                paramsButton.weight = 1.0f;
                paramsButton.gravity = Gravity.CENTER_VERTICAL;
                delete.setLayoutParams(paramsButton);
                delete.setBackgroundResource(R.drawable.trash);




                TextView textView = new TextView(this);
                textView.setText(nutritionString);
                horizontal.addView(textView);
                horizontal.addView(delete);
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
