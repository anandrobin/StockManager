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

public class SearchStockActivity extends AppCompatActivity {


    String itm_Name;

    EditText input_itemName;

    Button searchButton;

    AddStockActivity stock;

    DatabaseHandler dbHandler;

    int itemQuantity;

    String getItemName;

    String getItemLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stock);

        input_itemName=(EditText)findViewById(R.id.editTextSearchName);

        searchButton=(Button)findViewById(R.id.buttonSearch);

        this.setTitle(getResources().getString(R.string.search__stock_activity_title));


        dbHandler= new DatabaseHandler(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayStock();
            }
        });

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#0E0D0D");
        window.setStatusBarColor(colorCodeDark);

    }


    public void displayStock(){

        itm_Name=input_itemName.getText().toString();

        if(itm_Name.equals("")){

            Toast toast=Toast.makeText(SearchStockActivity.this, "Please complete the information in the provided fields", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();


        }

        else if(stock.checkName(itm_Name)==true){

            Toast toast=Toast.makeText(SearchStockActivity.this, "Invalid Name!! Symbols, numbers and Capital letters are not allowed", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();


        }

        else if(itemExists(itm_Name)==false){


            Toast toast=Toast.makeText(SearchStockActivity.this, "Invalid Item !! This item does not exist in the stock ", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();

        }

        else{



            viewStock(itm_Name);


        }

    }


    public boolean itemExists(String putItemName){

        dbHandler.checkItem();


        boolean validate=false;

        for (int i=0; i<dbHandler.itemListLength;i++) {

            if (putItemName.equals(dbHandler.itemNames.get(i))) {

                validate = true;



            }



        }

        System.out.println(validate);

        return validate;


    }

    public void viewStock(String item_Name){

        Cursor res= dbHandler.getPreviousLocation(item_Name);

        if(res.getCount()==0){

            showMessage("Error", "No data found");
            return;

        }





        while(res.moveToNext()){

            getItemName=res.getString(1);


            itemQuantity=res.getInt(2);

            getItemLocation=res.getString(3);




        }



        if(itemQuantity==0){


            StringBuffer buffer1= new StringBuffer();


            buffer1.append("The item : "+item_Name +" is all sold out from stock!!!"+"\n\n");
            buffer1.append("Please order item: "+item_Name+", as soon as possible "+"\n\n");
            buffer1.append("Thank You!!!"+"\n");


            AlertDialog.Builder builder1=new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setTitle("Item Sold Out!!!!");
            builder1.setMessage(buffer1.toString());

            builder1.show();


        }

        else{

            StringBuffer buffer= new StringBuffer();

            buffer.append("item name : "+getItemName+"\n\n");
            buffer.append("item quantity  : "+itemQuantity+"\n\n");
            buffer.append("item location  : "+getItemLocation+"\n\n");
            showMessage("Search Result",buffer.toString());

        }



    }

    public void showMessage(String title, String Message){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);

        builder.show();


    }





}

