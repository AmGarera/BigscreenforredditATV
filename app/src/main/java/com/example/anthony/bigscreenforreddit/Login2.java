package com.example.anthony.bigscreenforreddit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Anthony on 6/21/2015.
 */
public class Login2 extends Activity {
        EditText username = (EditText)findViewById(R.id.editText2);
        EditText password = (EditText)findViewById(R.id.editText1);
        Button b1;
        EditText ed1,ed2;

        /**
         * Called when the activity is first created.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            b1 = (Button) findViewById(R.id.btn);
            ed1 = (EditText) findViewById(R.id.editText2);
            ed2 = (EditText) findViewById(R.id.editText1);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ed1.getText().toString().equals("admin") &&

                            ed2.getText().toString().equals("admin")) {
                        Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }
        public void login(View view){
            if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                //correct password
            }
            else{
                //wrong password
            }
        }

    }
