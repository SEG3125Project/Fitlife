package org.twocoolguys.fitlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogExercise extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    AutoCompleteTextView exerciseAutoEditTextOrig;
    EditText weightEditTextOrig;
    EditText setsEditTextOrig;
    EditText repsEditTextOrig;
    AutoCompleteTextView cardioAutoEditTextOrig;
    EditText timeEditTextOrig;

    private ArrayAdapter<String> adapterExercises;
    private ArrayAdapter<String> adapterCardio;

    int count;
    int groupCount = 10;
    UserDatabase userDatabase;
    ExerciseDatabase exerciseDatabase;
    User onlineUser;
    String s;
    String date;


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


        exerciseAutoEditTextOrig = (AutoCompleteTextView) findViewById(R.id.exerciseAutoEditText);
        weightEditTextOrig = (EditText) findViewById(R.id.weightEditText);
        setsEditTextOrig = (EditText) findViewById(R.id.setsEditText);
        repsEditTextOrig = (EditText) findViewById(R.id.repsEditText);
        cardioAutoEditTextOrig = (AutoCompleteTextView) findViewById(R.id.cardioAutoEditText);
        timeEditTextOrig = (EditText) findViewById(R.id.timeEditText);

        String[] exerciseList = getResources().getStringArray(R.array.exercises_array); //initialize array of autocomplete exercise suggestions
        adapterExercises = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exerciseList);
        exerciseAutoEditTextOrig.setAdapter(adapterExercises);

        String[] cardioList = getResources().getStringArray(R.array.cardio_array); //initialize array of autocomplete cardio suggestions
        adapterCardio = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardioList);
        cardioAutoEditTextOrig.setAdapter(adapterCardio);

        Button cancelButton = (Button) findViewById(R.id.cancelExerciseButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogExercise.this, Calendar.class));
            }
        });


        singleList = new ArrayList<EditText>();
        singleList.add(exerciseAutoEditTextOrig);
        singleList.add(weightEditTextOrig);
        singleList.add(setsEditTextOrig);
        singleList.add(repsEditTextOrig);
        singleList.add(cardioAutoEditTextOrig);
        singleList.add(timeEditTextOrig);

        listOLists.add(singleList);

        Intent i = getIntent();
        date = i.getStringExtra("date");

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

        exerciseAutoEditTextOrig.setInputType(InputType.TYPE_CLASS_TEXT);
        weightEditTextOrig.setInputType(InputType.TYPE_CLASS_NUMBER);
        setsEditTextOrig.setInputType(InputType.TYPE_CLASS_NUMBER);
        repsEditTextOrig.setInputType(InputType.TYPE_CLASS_NUMBER);
        cardioAutoEditTextOrig.setInputType(InputType.TYPE_CLASS_TEXT);
        timeEditTextOrig.setInputType(InputType.TYPE_CLASS_NUMBER);

        exerciseAutoEditTextOrig.setFilters(new InputFilter[]{new InputFilter.LengthFilter(24), justTextFilter()});
        weightEditTextOrig.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        setsEditTextOrig.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        repsEditTextOrig.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        cardioAutoEditTextOrig.setFilters(new InputFilter[]{new InputFilter.LengthFilter(24), justTextFilter()});
        timeEditTextOrig.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

    }

    public static InputFilter justTextFilter() {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) //  condition
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
                Matcher ms = ps.matcher(String.valueOf(c));
                return ms.matches();
            }
        };
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
//        Button but = (Button) findViewById(R.id.subtractButton);
//        but.setVisibility(View.VISIBLE);

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



        AutoCompleteTextView exerciseAutoEditText = new AutoCompleteTextView(this);
        EditText weightEditText = new EditText(this);
        EditText setsEditText = new EditText(this);
        EditText repsEditText = new EditText(this);



        singleList.add(exerciseAutoEditText);
        singleList.add(weightEditText);
        singleList.add(setsEditText);
        singleList.add(repsEditText);
        singleList.add((AutoCompleteTextView) findViewById(R.id.cardioAutoEditText));
        singleList.add((EditText) findViewById(R.id.timeEditText));

        listOLists.add(singleList);

        count = 0;


        exerciseAutoEditText.setText("Exercise");
        exerciseAutoEditText.setId(count++ + groupCount);
        exerciseAutoEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        exerciseAutoEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(24), justTextFilter()});
        exerciseAutoEditText.setAdapter(adapterExercises);

        exerciseAutoEditText.setDropDownWidth(exerciseAutoEditTextOrig.getDropDownWidth());
        exerciseAutoEditText.setDropDownHorizontalOffset(exerciseAutoEditTextOrig.getDropDownHorizontalOffset());


//        System.out.println(count);
        weightEditText.setText("Weight (lbs)");
        weightEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        weightEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        weightEditText.setId(count++ + groupCount);
//        System.out.println(count);
        setsEditText.setText("Sets");
        setsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        setsEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        setsEditText.setId(count++ + groupCount);
//        System.out.println(count);
        repsEditText.setText("Reps");
        repsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        repsEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        repsEditText.setId(count++ + groupCount);
//        System.out.println(count);
//        System.out.println(groupCount);
        //        this adds the text when the button is pressed

        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(50,30);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        int px100 = (int) Math.ceil(100 * logicalDensity);
        int px150 = (int) Math.ceil(150 * logicalDensity);

        textView1.setWidth(px100);
        textView2.setWidth(px100);
        textView3.setWidth(px100);
        textView4.setWidth(px100);
        exerciseAutoEditText.setWidth(px150);
        exerciseAutoEditText.setHint("Bench Press");
        exerciseAutoEditText.setText("");
        weightEditText.setWidth(px150);
        weightEditText.setHint("315");
        weightEditText.setText("");
        setsEditText.setWidth(px150);
        setsEditText.setHint("5");
        setsEditText.setText("");
        repsEditText.setWidth(px150);
        repsEditText.setHint("15");
        repsEditText.setText("");


        horizontal1.addView(textView1);
        horizontal1.addView(exerciseAutoEditText);
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

    public void addExerciseToDB(View view){
        boolean addToDB = true;

        Exercise exercise;

        String name, isCardio, time, weight, sets, reps, user;

        isCardio = listOLists.get(0).get(4).getText().toString();
        time = listOLists.get(0).get(5).getText().toString();

        for(int i = 0; i < listOLists.size(); i++){
            name = listOLists.get(i).get(0).getText().toString();
            weight = listOLists.get(i).get(1).getText().toString();
            sets = listOLists.get(i).get(2).getText().toString();
            reps = listOLists.get(i).get(3).getText().toString();
            user = s;

            if(i != 0){
                isCardio = "";
                time = "";
            }

            if(name.equals("")){
                listOLists.get(i).get(0).setError("Field Empty");
                addToDB = false;
            }
            if(weight.equals("")){
                listOLists.get(i).get(1).setError("Field Empty");
                addToDB = false;
            }
            if(sets.equals("")){
                listOLists.get(i).get(2).setError("Field Empty");
                addToDB = false;
            }
            if(reps.equals("")){
                listOLists.get(i).get(3).setError("Field Empty");
                addToDB = false;
            }

            if(name.equals("") && name.equals("") && name.equals("") && name.equals("")){
                if(isCardio.equals("") && time.equals("")){
                    addToDB = false;
                } else {
                    addToDB = true;
                }
            }

            if(addToDB){
                exercise = new Exercise(name, isCardio, time, weight, sets, reps, user, date);
                exerciseDatabase.addExercise(exercise);

                Toast.makeText(getApplicationContext(), "Exercise logged", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LogExercise.this, Calendar.class);
                startActivity(intent);
            }

        }

    }


    public void subtractNewExercise(View veiw) {

        if (groupCount <= 20) {
//            Button button = (Button) findViewById(R.id.subtractButton);
//            button.setVisibility(View.INVISIBLE);
        }
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);

//        EditText exerciseAutoEditText = (EditText) findViewById(24);


//        mLinearLayout.removeView(exerciseAutoEditText);
    }
//    public void subtractNewExercise(View veiw){
//
//        if(groupCount <= 20){
//            Button button = (Button) findViewById(R.id.subtractButton);
//            button.setVisibility(View.INVISIBLE);
//        }
//        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.verticalLayout1);
//
//        EditText exerciseAutoEditText = (EditText) findViewById(groupCount + count - 4);
//        EditText weightEditText = (EditText) findViewById(groupCount + count - 3);
//        EditText setsEditText = (EditText) findViewById(groupCount + count - 2);
//        EditText repsEditText = (EditText) findViewById(groupCount + count - 1);
//
//        mLinearLayout.removeView(exerciseAutoEditText);
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
