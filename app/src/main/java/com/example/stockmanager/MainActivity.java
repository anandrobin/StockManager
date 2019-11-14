package com.example.stockmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Button buttonLogin;
    private Button buttonSignUp;

    ArrayList <String> uNames=new ArrayList<String>();

    ArrayList <String> uPass=new ArrayList<String>();

    String name;
    String password;



    int getLength;

    EditText nameInput;
    EditText passwordInput;

    DatabaseHandler dH;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        dH= new DatabaseHandler(this);

        nameInput= (EditText) findViewById(R.id.editTextName);

        passwordInput= (EditText) findViewById(R.id.editTextPassword);



        buttonLogin =(Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivity2();
            }
        });


        buttonSignUp=(Button) findViewById(R.id.createAccButton);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSignUpActivity();
            }
        });


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#0E0D0D");
        window.setStatusBarColor(colorCodeDark);


    }

    //Opening MainMenuActivity
    public void openActivity2(){
        Intent intent = new Intent(this, HomeActivity.class);


        boolean check=true;

        name=nameInput.getText().toString();
        password=passwordInput.getText().toString();

        getValues();

        if (name.equals("") || password.equals("")) {

            Toast toast=Toast.makeText(MainActivity.this, "Please complete the information in the provided fields", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();

        }
        else if(!(name.equals("") || password.equals(""))) {

            for (int i = 0; i < getLength; i++) {

                if ((name.equals(uNames.get(i))) && (password.equals(uPass.get(i)))) {


                    check = false;

                    //return;
                    startActivity(intent);


                    return;


                }

            }

            while (check==true){

                Toast toast=Toast.makeText(MainActivity.this, "Invalid user-name or password", Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(Color.BLACK);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.WHITE);
                toast.show();
                check=false;
            }


        }


    }



    //Opening SignUpActivity
    public void openSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);


    }


    public void getValues(){
        Cursor res= dH.getAllData();

        if(res.getCount()==0){

            return;

        }


        while(res.moveToNext()){

            uNames.add(res.getString(0));
            uPass.add(res.getString(1));




        }

        getLength=uNames.size();

    }






}

