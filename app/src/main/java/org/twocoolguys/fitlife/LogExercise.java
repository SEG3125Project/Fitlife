package org.twocoolguys.fitlife;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LogExercise extends AppCompatActivity {
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
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        Button button = (Button) findViewById(R.id.subtractButton);
        button.setVisibility(View.INVISIBLE);

        SharedPreferences sessionDetails = getSharedPreferences("sessionDetails", MODE_PRIVATE);
        s = sessionDetails.getString("sessionUsername", null);

        userDatabase = new UserDatabase(this);
        onlineUser = userDatabase.getUserByName(s);

        exerciseDatabase = new ExerciseDatabase(this);
    }


    public void addNewExercise(View view) {
        singleList = new ArrayList<EditText>();
        Button but = (Button) findViewById(R.id.subtractButton);
        but.setVisibility(View.VISIBLE);

        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);

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
        mLinearLayout.addView(exerciseEditText);
        mLinearLayout.addView(weightEditText);
        mLinearLayout.addView(setsEditText);
        mLinearLayout.addView(repsEditText);



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
