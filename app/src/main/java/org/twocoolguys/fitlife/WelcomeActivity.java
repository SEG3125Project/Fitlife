package org.twocoolguys.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
        public void userOnClick(View view){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivityForResult(intent, 0);
        }

        public void newUserOnClick(View view){
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
        }
//        Button login = (Button)findViewById(R.id.loginButton);
//        Button signup = (Button)findViewById(R.id.signupButton);
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(WelcomeActivity.this, Login.class));
//            }
//        });
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(WelcomeActivity.this, SignUp.class));
//            }
//        });

}
