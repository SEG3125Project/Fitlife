package org.twocoolguys.fitlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void enterOnClick(View view){
        UserDatabase users = new UserDatabase(this);

        EditText textName = (EditText)findViewById(R.id.usernameLogin);
        EditText textPassword = (EditText)findViewById(R.id.passwordLogin);

        String name = textName.getText().toString();
        String password = textPassword.getText().toString();


        if(users.authUser(name, password)){

            SharedPreferences.Editor editor = getSharedPreferences("sessionDetails", MODE_PRIVATE).edit();
            editor.putString("sessionUsername", name);
            editor.apply();

            Intent intent = new Intent(getBaseContext(), Homepage.class);
            startActivity(intent);

        } else if(users.checkUser(name)){
            Context context = getApplicationContext();
            CharSequence text = "Incorrect Password";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Unrecognized User";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }





    }

}
