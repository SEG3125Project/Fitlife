package org.twocoolguys.fitlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogNutrition extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    String date;
    UserDatabase userDatabase;
    NutritionDatabase nutritionDatabase;
    String session;
    User onlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_nutrition);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.logNutritionDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);


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
