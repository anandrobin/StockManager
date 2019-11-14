package com.example.stockmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AddStockActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String itemName;
    String itemQuantity;
    String location;
    String price;

    int latestQuantity=0;


    EditText itemNameInput;

    EditText quantity;

    EditText priceInput;


    private Button addButton;

    private Button showButton;

    DatabaseHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        dataHandler= new DatabaseHandler(this);

//        Spinner spinner =findViewById(R.id.spinner);
//
//        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.Quantity,android.R.layout.simple_spinner_item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        //spinner.setPrompt("Quantity");
//
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(this);

        itemNameInput= (EditText) findViewById(R.id.editTextItemName);

        quantity= (EditText) findViewById(R.id.editTextQuantity);

        //priceInput= (EditText) findViewById(R.id.editTextPrice);

        addButton=(Button) findViewById(R.id.addButton);

        this.setTitle(getResources().getString(R.string.add__stock_activity_title));




        Spinner spinner1 =findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this, R.array.Location,android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        //adapter1.setDropDownViewResource(android.R.layout.simple_list_item_activated_1)
        //spinner1.setPrompt("Location");

        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(this);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addStock();
            }
        });

//        showButton=(Button)findViewById(R.id.buttonShow);
//        showButton.setOnClickListener(new View.OnClickListener() {
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

    @Override

    //Setting adapter for the dropdown spinner
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        location=adapterView.getItemAtPosition(i).toString();






    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {




    }


    //Validating item name
    public static boolean checkName(String input) {

        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        char currentCharacter;
        boolean numberPresent = false;
        boolean upperCasePresent = false;
        //boolean lowerCasePresent = true;
        boolean specialCharacterPresent = false;

        for (int i = 0; i < input.length(); i++) {
            currentCharacter = input.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                numberPresent = true;
            } else if (Character.isUpperCase(currentCharacter)) {
                upperCasePresent = true;

            } else if (specialChars.contains(String.valueOf(currentCharacter))) {
                specialCharacterPresent = true;
            }
        }

        return
                numberPresent || upperCasePresent || specialCharacterPresent;
    }




    //Checking if item previously exist in the stock
    public boolean itemAlreadyExist(String newItemName){

        dataHandler.checkItem();

        boolean check=false;

        for (int i=0; i<dataHandler.itemListLength;i++) {

            if (newItemName.equals(dataHandler.itemNames.get(i))) {

                check = true;



            }



        }
        System.out.println(check);

        return check;

    }

    public void viewAll(){



        Cursor res= dataHandler.getAllStock();

        if(res.getCount()==0){
            showMessage("Error", "No data found");
            return;

        }

        StringBuffer buffer= new StringBuffer();
        while(res.moveToNext()){
            buffer.append("id :"+res.getString(0)+"\n");
            buffer.append("name :"+res.getString(1)+"\n");
            buffer.append("qty :"+res.getString(2)+"\n");
            buffer.append("loc :"+res.getString(3)+"\n\n");

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


    public static boolean checkQty(String input) {

        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        char currentCharacter;
        boolean letterPresent = false;
        boolean upperCasePresent = false;
        //boolean lowerCasePresent = true;
        boolean specialCharacterPresent = false;

        for (int i = 0; i < input.length(); i++) {
            currentCharacter = input.charAt(i);
            if (Character.isLetter(currentCharacter)) {
                letterPresent = true;
            } else if (Character.isUpperCase(currentCharacter)) {
                upperCasePresent = true;

            } else if (specialChars.contains(String.valueOf(currentCharacter))) {
                specialCharacterPresent = true;
            }
        }

        return
                letterPresent || upperCasePresent || specialCharacterPresent;
    }












    public void addStock(){

        itemName=itemNameInput.getText().toString();

        itemQuantity=quantity.getText().toString();

        //price=priceInput.getText().toString();


        if (itemName.equals("")|| itemQuantity.equals("") ) {

            Toast toast=Toast.makeText(AddStockActivity.this, "Please complete the information in the provided fields", Toast.LENGTH_LONG);

            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();


        }

        else if (checkName(itemName)==true){

            Toast toast=Toast.makeText(AddStockActivity.this, "Invalid Name!! Symbols, numbers and Capital letters are not allowed", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();




        }

        else if (checkQty(itemQuantity)==true){

            Toast toast=Toast.makeText(AddStockActivity.this, "Invalid Quantity!! Please Specify quantity in numbers between (1-100).", Toast.LENGTH_LONG);

            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();

        }


        else if (itemAlreadyExist(itemName)==true){

            dataHandler.getItemDetails(itemName);

            if(location.equals(dataHandler.getLocation)){

                latestQuantity=dataHandler.previousQuantity+Integer.parseInt(itemQuantity);

                dataHandler.updateItem(itemName,latestQuantity);

                Toast toast=Toast.makeText(AddStockActivity.this, "Stock Updated", Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(Color.BLACK);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.WHITE);
                toast.show();

            } else{

                StringBuffer buffer= new StringBuffer();

                buffer.append("This item is already assigned to the location: "+dataHandler.getLocation +"\n\n");
                buffer.append("So it cannot be assigned to a new location!!!"+"\n\n");
                buffer.append("To update the quantity of this item, please assign it to the location "+dataHandler.getLocation +"\n\n");
                buffer.append("Thank You!!!"+"\n");

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("ERROR!!!!");
                builder.setMessage(buffer.toString());

                builder.show();
            }


        }else{

            boolean stockAdded = dataHandler.addStock(itemName,itemQuantity, location);

                if (stockAdded = true) {

                    Toast toast=Toast.makeText(AddStockActivity.this, "Stock Added", Toast.LENGTH_LONG);
                    View view =toast.getView();
                    view.setBackgroundColor(Color.BLACK);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.WHITE);
                    toast.show();
                } else {
                   Toast toast= Toast.makeText(AddStockActivity.this, "Try Again", Toast.LENGTH_LONG);
                    View view =toast.getView();
                    view.setBackgroundColor(Color.BLACK);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.WHITE);
                    toast.show();

                }





        }




    }
}

