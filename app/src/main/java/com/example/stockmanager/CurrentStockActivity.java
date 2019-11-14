package com.example.stockmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CurrentStockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stock);

        this.setTitle(getResources().getString(R.string.current__stock_activity_title));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#0E0D0D");
        window.setStatusBarColor(colorCodeDark);


        DatabaseHandler dataHelper=new DatabaseHandler(this);

        TableLayout tableLayout=(TableLayout)findViewById(R.id.tablelayout);
        // Add header row
        TableRow rowHeader = new TableRow(this);
        rowHeader.setBackgroundColor(Color.parseColor("#757575"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"Name","Quantity","Location"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(20);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }

        tableLayout.addView(rowHeader);

        SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        try
        {
            String selectQuery = "SELECT * FROM "+ dataHelper.TABLE_NAME1;
            Cursor cursor = db.rawQuery(selectQuery,null);
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {


                    String item_name = cursor.getString(cursor.getColumnIndex("itemName"));
                    int item_quantity = cursor.getInt(cursor.getColumnIndex("itemQuantity"));
                    String item_location = cursor.getString(cursor.getColumnIndex("itemLocation"));

                    // dara rows
                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {item_name, item_quantity + "", item_location};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(18);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setTextColor(Color.parseColor("#0E0D0D"));
                        tv.setText(text);
                        row.addView(tv);
                    }

                    tableLayout.addView(row);
                }

                }


                db.setTransactionSuccessful();

                }catch (SQLiteException e) {
                e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            //db.close();
            // Close database
        }













    }

}

