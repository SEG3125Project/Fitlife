package org.twocoolguys.fitlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogNutrition extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    EditText caloriesEditText;
    EditText proteinEditText;
    EditText fatsEditText;
    EditText carbsEditText;

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

        caloriesEditText = (EditText) findViewById(R.id.caloriesEditText);
        proteinEditText = (EditText) findViewById(R.id.proteinEditText);
        fatsEditText = (EditText) findViewById(R.id.fatsEditText);
        carbsEditText = (EditText) findViewById(R.id.carbsEditText);


        Button cancelButton = (Button) findViewById(R.id.cancelNutritionButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
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

        proteinEditText.setFilters(new InputFilter[] { filter });
        fatsEditText.setFilters(new InputFilter[] {filter});
        carbsEditText.setFilters(new InputFilter[] {filter});
    }

    InputFilter filter = new InputFilter() {
        final int maxDigitsBeforeDecimalPoint=3;
        final int maxDigitsAfterDecimalPoint=1;

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            StringBuilder builder = new StringBuilder(dest);
            builder.replace(dstart, dend, source
                    .subSequence(start, end).toString());
            if (!builder.toString().matches(
                    "(([1-9]{1})([0-9]{0,"+(maxDigitsBeforeDecimalPoint-1)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"

            )) {
                if(source.length()==0)
                    return dest.subSequence(dstart, dend);
                return "";
            }

            return null;

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submitOnClick(View view){
        boolean valid = true;

        String calories = caloriesEditText.getText().toString();
        String protein = proteinEditText.getText().toString();
        String fats = fatsEditText.getText().toString();
        String carbs = carbsEditText.getText().toString();

//        if((calories.equals("") && protein.equals("") && fats.equals("") && carbs.equals(""))){
//            valid = false;
//        }

        if(calories.equals("")){
            caloriesEditText.setError("Field Empty");
            valid = false;
        }
        if(protein.equals("")){
            proteinEditText.setError("Field Empty");
            valid = false;
        }
        if(fats.equals("")){
            fatsEditText.setError("Field Empty");
            valid = false;
        }
        if(carbs.equals("")){
            carbsEditText.setError("Field Empty");
            valid = false;
        }


        if(valid) {
            Nutrition nutrition = new Nutrition(calories, fats, protein, carbs, date, onlineUser.getName());
            nutritionDatabase.addNutrition(nutrition);

            Toast.makeText(getApplicationContext(), "Nutrition logged", Toast.LENGTH_LONG).show();

            Intent i = new Intent(LogNutrition.this, Calendar.class);
            startActivity(i);
        }
    }
}
