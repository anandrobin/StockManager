package com.example.stockmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {



    String userName;

    String getUserName;
    String getPassword;

    String password;
    private Button registerButton;
    private Button buttonView;

    DatabaseHandler dbHandler;


    EditText nameInput;
    EditText passwordInput;


    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHandler=new DatabaseHandler(this);

        nameInput=(EditText)findViewById(R.id.editTextUserName);
        passwordInput=(EditText)findViewById(R.id.editTextCredentials);
        registerButton=(Button)findViewById(R.id.registerButton);

        this.setTitle(getResources().getString(R.string.sign_up_activity_title));


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUserAccount();
            }
        });

//        buttonView=(Button)findViewById(R.id.buttonView);
//        buttonView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                viewAll();
//            }
//        });


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#0E0D0D");
        window.setStatusBarColor(colorCodeDark);

    }

    public void createUserAccount() {

        boolean start = true;

        userName = nameInput.getText().toString();
        password = passwordInput.getText().toString();

        dbHandler = new DatabaseHandler(this);

        dbHandler.validateUserName();

        if (userName.equals("") || password.equals("")) {

            Toast toast=Toast.makeText(SignUpActivity.this, "Please complete the information in the provided fields", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();

        }else if (!(userName.equals("") && password.equals(""))){



            if(isValidUserName(userName)){

                for (int i = 0; i < dbHandler.listLength; i++) {

                    System.out.println(dbHandler.list.get(i));

                    if (userName.equals(dbHandler.list.get(i))) {
                        Toast toast=Toast.makeText(SignUpActivity.this, "Username Already taken, Please try another username", Toast.LENGTH_LONG);
                        View view =toast.getView();
                        view.setBackgroundColor(Color.BLACK);
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.WHITE);
                        toast.show();
                        start = false;
                        return;
                    }
                }


                while (start == true) {

                    boolean isInserted = dbHandler.insertData(userName, password);

                    if (isInserted = true) {

                        Toast toast = Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.BLACK);
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.WHITE);
                        toast.show();
                    }

                    else {
                        Toast toast=Toast.makeText(SignUpActivity.this, "Try Again", Toast.LENGTH_LONG);
                        View view =toast.getView();
                        view.setBackgroundColor(Color.BLACK);
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.WHITE);
                        toast.show();
                    }

                    start = false;

                }


            }else if(!(isValidUserName(userName))){

                Toast toast=Toast.makeText(SignUpActivity.this, "Please Enter a Valid user-name", Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(Color.BLACK);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.WHITE);
                toast.show();



            }








        }





    }

    public void viewAll(){

        Cursor res= dbHandler.getAllData();

        if(res.getCount()==0){
            showMessage("Error", "No data found");
            return;

        }

        StringBuffer buffer= new StringBuffer();
        while(res.moveToNext()){
            buffer.append("user_name :"+res.getString(0)+"\n");
            buffer.append("password :"+res.getString(1)+"\n\n");

        }

        showMessage("Data",buffer.toString());

    }

    public void showMessage(String title, String Message){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);

        builder.show();


    }

    public boolean isValidUserName(final String uName){



        String regex = "[A-Za-z0-9_]+";
        return userName.matches(regex);

//        pattern = Pattern.compile(USERNAME_PATTERN);
//
//
//        matcher = pattern.matcher(uName);
//
//        return matcher.matches();


    }




//    public void validateUserName(){
//
//        Cursor res=dbHandler.getAllData();
//
//        if(res.moveToNext()){
//
//            getUserName=res.getString(0);
//            getPassword=res.getString(1);
//
//
//
//        }
//
//
//
//
//    }






}
