package org.twocoolguys.fitlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LogExercise extends AppCompatActivity {
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_exercise);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }


    public void dispatchsKeyEvent(View view) {

        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);

        EditText lEditText = new EditText(this);
        lEditText.setText("Text Here");
        lEditText.setId(count++);
        System.out.println("#####################################: " + lEditText.getId());
        System.out.println("#####################################: ");
        mLinearLayout.addView(lEditText);

    }

    public void getInformationWithIds(View view){
        int count2;
        for(int i = 0; i < count; i++){
            EditText editText = (EditText) findViewById(i);
            System.out.println("QWERTYUIOP" + editText.getText().toString());
        }
    }

}
