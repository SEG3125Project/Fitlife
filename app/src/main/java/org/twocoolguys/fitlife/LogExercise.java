package org.twocoolguys.fitlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LogExercise extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    int count;
    int groupCount = 10;
    UserDatabase userDatabase;
    ExerciseDatabase exerciseDatabase;
    User onlineUser;
    String s;

    ArrayList<ArrayList<EditText>> listOLists = new ArrayList<ArrayList<EditText>>();
    ArrayList<EditText> singleList = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_exercise);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.logExerciseDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);


        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        Button button = (Button) findViewById(R.id.subtractButton);
        button.setVisibility(View.INVISIBLE);

        Button homeButton = (Button) findViewById(R.id.homeButtonLogExercise);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogExercise.this, Calendar.class));
            }
        });


        SharedPreferences sessionDetails = getSharedPreferences("sessionDetails", MODE_PRIVATE);
        s = sessionDetails.getString("sessionUsername", null);

        userDatabase = new UserDatabase(this);
        onlineUser = userDatabase.getUserByName(s);

        exerciseDatabase = new ExerciseDatabase(this);

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


    public void addNewExercise(View view) {
        singleList = new ArrayList<EditText>();
        Button but = (Button) findViewById(R.id.subtractButton);
        but.setVisibility(View.VISIBLE);

        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);

        LinearLayout horizontal1 = new LinearLayout(this);
        horizontal1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 100, 0, 0);
        horizontal1.setLayoutParams(params);
        LinearLayout horizontal2 = new LinearLayout(this);
        horizontal2.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout horizontal3 = new LinearLayout(this);
        horizontal3.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout horizontal4 = new LinearLayout(this);
        horizontal4.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView1 = new TextView(this);
        textView1.setText("Exercise");
        TextView textView2 = new TextView(this);
        textView2.setText("Weight");
        TextView textView3 = new TextView(this);
        textView3.setText("Sets");
        TextView textView4 = new TextView(this);
        textView4.setText("Reps");



        EditText exerciseEditText = new EditText(this);
        EditText weightEditText = new EditText(this);
        EditText setsEditText = new EditText(this);
        EditText repsEditText = new EditText(this);



        singleList.add(exerciseEditText);
        singleList.add(weightEditText);
        singleList.add(setsEditText);
        singleList.add(repsEditText);

        listOLists.add(singleList);

        count = 0;

        exerciseEditText.setText("Exercise");
        exerciseEditText.setId(count++ + groupCount);
        System.out.println(count);
        weightEditText.setText("Weight");
        weightEditText.setId(count++ + groupCount);
        System.out.println(count);
        setsEditText.setText("Sets");
        setsEditText.setId(count++ + groupCount);
        System.out.println(count);
        repsEditText.setText("Reps");
        repsEditText.setId(count++ + groupCount);
        System.out.println(count);
        System.out.println(groupCount);
        //        this adds the text when the button is pressed

        exerciseEditText.setLayoutParams(params);

        horizontal1.addView(textView1);
        horizontal1.addView(exerciseEditText);
        mLinearLayout.addView(horizontal1);
        horizontal2.addView(textView2);
        horizontal2.addView(weightEditText);
        mLinearLayout.addView(horizontal2);
        horizontal3.addView(textView3);
        horizontal3.addView(setsEditText);
        mLinearLayout.addView(horizontal3);
        horizontal4.addView(textView4);
        horizontal4.addView(repsEditText);
        mLinearLayout.addView(horizontal4);



        groupCount += 10;
    }

    public void printExercises(View view){

        Exercise exercise;

        String name, isCardio, time, weight, sets, reps, user, date;
        System.out.println(listOLists.toString());
        for(int i = 0; i < listOLists.size(); i++){
            name = listOLists.get(i).get(0).getText().toString();
            weight = listOLists.get(i).get(1).getText().toString();
            sets = listOLists.get(i).get(2).getText().toString();
            reps = listOLists.get(i).get(3).getText().toString();
            isCardio = "false";
            time = "N/A";
            user = s;
            date="s"; //was getting compilation error was date wasn't initailized so i intiailized it here sir.

            date = "NOT DONE YET";

            exercise = new Exercise(name, isCardio, time, weight, sets, reps, user, date);
            exerciseDatabase.addExercise(exercise);



        }

    }


    public void subtractNewExercise(View veiw) {

        if (groupCount <= 20) {
            Button button = (Button) findViewById(R.id.subtractButton);
            button.setVisibility(View.INVISIBLE);
        }
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);

//        EditText exerciseEditText = (EditText) findViewById(24);


//        mLinearLayout.removeView(exerciseEditText);
    }
//    public void subtractNewExercise(View veiw){
//
//        if(groupCount <= 20){
//            Button button = (Button) findViewById(R.id.subtractButton);
//            button.setVisibility(View.INVISIBLE);
//        }
//        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);
//
//        EditText exerciseEditText = (EditText) findViewById(groupCount + count - 4);
//        EditText weightEditText = (EditText) findViewById(groupCount + count - 3);
//        EditText setsEditText = (EditText) findViewById(groupCount + count - 2);
//        EditText repsEditText = (EditText) findViewById(groupCount + count - 1);
//
//        mLinearLayout.removeView(exerciseEditText);
//        mLinearLayout.removeView(weightEditText);
//        mLinearLayout.removeView(setsEditText);
//        mLinearLayout.removeView(repsEditText);
//        System.out.println("THE COUNT BEFORE IS. COUNT:" + count + "GROUPCOUNT:" + groupCount);
//        count -= 4;
//        groupCount -= 10;
//        System.out.println("THE COUNT AFTER IS. COUNT:" + count + "GROUPCOUNT:" + groupCount);
//    }
    public void getInformationWithIds(View view){
        int count2;
        for(int i = 0; i < count; i++){
            EditText editText = (EditText) findViewById(i);
            System.out.println("QWERTYUIOP" + editText.getText().toString());
        }
    }

}
