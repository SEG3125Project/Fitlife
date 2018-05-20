package org.twocoolguys.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class SignUp extends AppCompatActivity {
    EditText username;
    EditText fName;
    EditText lName;
    EditText email;
    EditText password;

    TextView textError;
    UserDatabase dbHandler;

    ImageView iconImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = (EditText) findViewById(R.id.nameText);
        fName = (EditText) findViewById(R.id.fNameText);
        lName = (EditText) findViewById(R.id.lNameText);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);

        dbHandler = new UserDatabase(this);
    }

    public void newUser (View view) {
        if(dbHandler.checkUser(username.getText().toString())) {
            textError.setText("Username not available");
        } else if(!allFieldsFilled(view)) {
            textError.setText("Please be sure all entries valid");
        } else {
            User user = new User(username.getText().toString(), fName.getText().toString(), lName.getText().toString(), email.getText().toString(), password.getText().toString());
            dbHandler.addUser(user);
            Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_LONG).show();
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();

        }
    }

    private boolean allFieldsFilled(View view){
        return !(username.getText().toString().equals("") || fName.getText().toString().equals("") || lName.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals(""));
    }

    public void cancel(View view){
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }


}