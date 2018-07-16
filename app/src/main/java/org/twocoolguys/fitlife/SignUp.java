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
    EditText password2;

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
        password2 = (EditText) findViewById(R.id.password2Text);

        dbHandler = new UserDatabase(this);

        textError = (TextView) findViewById(R.id.ErrorText);
    }

    //compare passwords
    public boolean checkPass (EditText fpassword, EditText spassword2){
        return fpassword.getText().toString().equals(spassword2.getText().toString());
    }

    public void newUser (View view) {
        boolean bothUandP = false;

        if (dbHandler.checkUser(username.getText().toString())) {
            textError.setText("Username not available");
            bothUandP = true;
        }

        if (!allFieldsFilled(view)) {
            if(username.getText().toString().equals("")) {
                username.setError("Field Empty");
            }
            if(fName.getText().toString().equals("")) {
                fName.setError("Field Empty");
            }
            if(lName.getText().toString().equals("")) {
                lName.setError("Field Empty");
            }
            if(email.getText().toString().equals("")) {
                email.setError("Field Empty");
            }
            if(password.getText().toString().equals("")) {
                password.setError("Field Empty");
            }
            if(password2.getText().toString().equals("")) {
                password2.setError("Field Empty");
            }
            textError.setText("");

        } else if (!checkPass(password, password2)){
            if(bothUandP){
                textError.setText("Username not available.\nPasswords do not match.");
            } else {
                textError.setText("Passwords do not match");
            }
        }else {
            if(!bothUandP) {
                User user = new User(username.getText().toString(), fName.getText().toString(), lName.getText().toString(), email.getText().toString(), password.getText().toString());
                dbHandler.addUser(user);
                Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
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