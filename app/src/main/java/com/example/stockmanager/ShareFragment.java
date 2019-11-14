package com.example.stockmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ShareFragment extends Fragment implements View.OnClickListener{

    Button buttonShare;
    DatabaseHandler dbHand;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_share, container, false);
        dbHand=new DatabaseHandler(getContext());
        buttonShare=(Button)view.findViewById(R.id.buttonStockShare);
        buttonShare.setOnClickListener((View.OnClickListener) this);


        return view;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonStockShare:

              shareStock();

            break;



        }







    }

    public void shareStock() {

        SQLiteDatabase db = dbHand.getReadableDatabase();
        StringBuilder data = new StringBuilder();
        String selectQuery = "SELECT * FROM " + dbHand.TABLE_NAME1;
        Cursor cur = db.rawQuery(selectQuery, null);
        data.append("Item Name,Quantity,Location");

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {


                String item_name = cur.getString(cur.getColumnIndex("itemName"));
                int item_quantity = cur.getInt(cur.getColumnIndex("itemQuantity"));
                String item_location = cur.getString(cur.getColumnIndex("itemLocation"));
                data.append("\n" + item_name + "," + String.valueOf(item_quantity) + "," + item_location);
            }

            try {

                System.out.println("working share button");

                FileOutputStream out = getActivity().openFileOutput("stock-data.csv",Context.MODE_PRIVATE);
                out.write((data.toString()).getBytes());
                out.close();

                Context context =getActivity().getApplicationContext();
                File filelocation = new File(getActivity().getFilesDir(),"stock-data.csv");
                Uri path = FileProvider.getUriForFile(context, "com.example.stockmanager.fileprovider", filelocation);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "stock data");
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent, "send mail"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else if (cur.getCount() <= 0) {

            StringBuffer buf = new StringBuffer();

            buf.append("No items are added yet!!!!" + "\n\n");
            buf.append("Please add some items first" + "\n\n");


            showMessage("No Data To Share!!!!!!", buf.toString());
            return;
        }
    }

        public void showMessage(String title, String Message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);

        builder.show();


    }








}
