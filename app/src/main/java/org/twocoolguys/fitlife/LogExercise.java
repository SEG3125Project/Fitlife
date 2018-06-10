package org.twocoolguys.fitlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LogExercise extends AppCompatActivity {
    int count;
    int groupCount = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_exercise);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        Button button = (Button) findViewById(R.id.subtractButton);
        button.setVisibility(View.INVISIBLE);
    }


    public void addNewExercise(View view) {

        Button but = (Button) findViewById(R.id.subtractButton);
        but.setVisibility(View.VISIBLE);

        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);

        EditText exerciseEditText = new EditText(this);
        EditText weightEditText = new EditText(this);
        EditText setsEditText = new EditText(this);
        EditText repsEditText = new EditText(this);

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

    public void subtractNewExercise(View veiw) {

        if (groupCount <= 20) {
            Button button = (Button) findViewById(R.id.subtractButton);
            button.setVisibility(View.INVISIBLE);
        }
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);

        EditText exerciseEditText = (EditText) findViewById(24);


        mLinearLayout.removeView(exerciseEditText);
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
