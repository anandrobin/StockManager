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

public class SoldStockActivity extends AppCompatActivity {

    String itmName;
    String itmQty;

    int latestQuantity;

    EditText itmNameInput;

    EditText itmQtyInput;

    Button removeButton;

    AddStockActivity addStockActivity;

    DatabaseHandler db;

    int latestSoldQuantity;

    Button buttonCheck;

    boolean checkCondition=true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_stock);

        addStockActivity = new AddStockActivity();

        db = new DatabaseHandler(this);


        itmNameInput = (EditText) findViewById(R.id.editTextItmNam);

        itmQtyInput = (EditText) findViewById(R.id.editTextItmQty);

        removeButton = (Button) findViewById(R.id.buttonRemove);

//        buttonCheck=(Button)findViewById(R.id.buttonCheck);

        this.setTitle(getResources().getString(R.string.remove__stock_activity_title));


        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeStock();
            }
        });



//        buttonCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                checkStock();
//            }
//        });

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#0E0D0D");
        window.setStatusBarColor(colorCodeDark);




    }




    public void removeStock(){


        itmName=itmNameInput.getText().toString();

        itmQty=itmQtyInput.getText().toString();

        if(itmName.equals("") || itmQty.equals("")){


            Toast toast=Toast.makeText(SoldStockActivity.this, "Please complete the information in the provided fields", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();
        }


        else if (addStockActivity.checkName(itmName)==true){

            Toast toast=Toast.makeText(SoldStockActivity.this, "Invalid Name!! Symbols, numbers and Capital letters are not allowed", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();

        }

        else if (addStockActivity.checkQty(itmQty)==true){

            Toast toast=Toast.makeText(SoldStockActivity.this, "Invalid Quantity!! Please Specify quantity in numbers between (1-100).", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();

        }


        else if(itemExists(itmName)==false){


            Toast toast=Toast.makeText(SoldStockActivity.this, "Invalid Item !! This item does not exist in the stock ", Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.BLACK);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();




        } else{

            db.getItemDetails(itmName);


            if(db.previousQuantity==0){



                StringBuffer buffer= new StringBuffer();

                buffer.append("The item : "+itmName +" is all sold out from stock!!!"+"\n\n");
                buffer.append("So it is not available for sale."+"\n\n");
                buffer.append("Please order item: "+itmName+", as soon as possible "+"\n\n");
                buffer.append("Thank You!!!"+"\n");


                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Item Sold Out!!!!");
                builder.setMessage(buffer.toString());

                builder.show();


            }else if(db.previousQuantity < Integer.parseInt(itmQty)){

                StringBuffer buffer1= new StringBuffer();

                buffer1.append("There are only : "+db.previousQuantity+" "+itmName+" left in stock." +""+"\n\n");
                buffer1.append("So you can only sell: "+db.previousQuantity+ " "+itmName+"."+"\n\n");
                buffer1.append("So please select a value less than or equal to "+db.previousQuantity+"\n\n");
                buffer1.append("Thank You!!!"+"\n");


                AlertDialog.Builder builder1=new AlertDialog.Builder(this);
                builder1.setCancelable(true);
                builder1.setTitle("Alert!!!!");
                builder1.setMessage(buffer1.toString());

                builder1.show();




            }

            else {

                latestQuantity=db.previousQuantity-(Integer.parseInt(itmQty));

                db.updateItem(itmName,latestQuantity);

                Toast toast=Toast.makeText(SoldStockActivity.this, "Record Updated for item: "+itmName, Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(Color.BLACK);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.WHITE);
                toast.show();

                db.checkSoldItem();



                latestSoldQuantity=db.getSoldItemQuantity+(Integer.parseInt(itmQty));


                for(int i=0; i<db.soldListLength;i++){


                    if(itmName.equals(db.soldItemList.get(i))){



                        db.updateSoldStock(itmName, latestSoldQuantity);
                        checkCondition=false;
                        System.out.println("This is check"+checkCondition);
                        return;

                    }


                }

                if(checkCondition=true){

                    System.out.println("NOW check is "+ checkCondition);

                    db.insertIntoSoldStock(itmName, Integer.parseInt(itmQty));

                    checkCondition=false;


                }



            }









        }












    }


    public void checkStock(){

        Cursor res= db.getSoldItemName();

        if(res.getCount()==0){
            showMessage("Error", "No data found");
            return;

        }

        StringBuffer buffer= new StringBuffer();
        while(res.moveToNext()){
            buffer.append("itemName :"+res.getString(1)+"\n");
            buffer.append("soldQty :"+res.getString(2)+"\n\n");

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


    public boolean itemExists(String putItemName){

        db.checkItem();


        boolean checkForItem=false;

        for (int i=0; i<db.itemListLength;i++) {

            if (putItemName.equals(db.itemNames.get(i))) {

                checkForItem = true;



            }



        }

        System.out.println(checkForItem);

        return checkForItem;


    }


}

