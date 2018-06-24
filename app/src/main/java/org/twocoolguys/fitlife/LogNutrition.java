package org.twocoolguys.fitlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogNutrition extends AppCompatActivity {

    String date;
    UserDatabase userDatabase;
    NutritionDatabase nutritionDatabase;
    String session;
    User onlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_nutrition);

        Button homeButton = (Button) findViewById(R.id.homeButtonLogNutrition);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogNutrition.this, Calendar.class));
            }
        });

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        SharedPreferences sessionDetails = getSharedPreferences("sessionDetails", MODE_PRIVATE);
        session = sessionDetails.getString("sessionUsername", null);

        userDatabase = new UserDatabase(this);
        onlineUser = userDatabase.getUserByName(session);

        nutritionDatabase = new NutritionDatabase(this);
    }

    public void submitOnClick(View view){

        EditText caloriesEditText = (EditText) findViewById(R.id.caloriesEditText);
        EditText proteinEditText = (EditText) findViewById(R.id.proteinEditText);
        EditText fatsEditText = (EditText) findViewById(R.id.fatsEditText);
        EditText carbsEditText = (EditText) findViewById(R.id.carbsEditText);

        String calories = caloriesEditText.getText().toString();
        String protein = proteinEditText.getText().toString();
        String fats = fatsEditText.getText().toString();
        String carbs = carbsEditText.getText().toString();

        Nutrition nutrition = new Nutrition(calories, fats, protein, carbs, date, onlineUser.getName());

        nutritionDatabase.addNutrition(nutrition);
    }
}
