package com.example.stockmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Stock.db";

    public static final String TABLE_NAME="user_accounts";

    public static final String TABLE_NAME1="current_stock";


    public static final String TABLE_NAME2="sold_stock";


    public static final String COLUMN_1="user_name";

    public static final String COLUMN_2="password";

    public static final String COL_1="itemID";

    public static final String COL_2="itemName";

    //public static final String COL_3="itemPrice";

    public static final String COL_3="itemQuantity";

    public static final String COL_4="itemLocation";


    public static final String ITEM_CODE="itemCode";

    public static final String ITEM_NAME="itemName";

    public static final String SOLD_QTY="soldQty";


    String getLocation;

    String getSoldItemName;

    int getSoldItemQuantity;

    int soldListLength;




    public static ArrayList <String> list=new ArrayList<String>();

    public static ArrayList <String> list1=new ArrayList<String>();


    public static ArrayList <String> nameList=new ArrayList<String>();

    public static ArrayList <String> passwordList=new ArrayList<>();


    public static ArrayList <String> soldItemList=new ArrayList<>();

    String getUserName;

    String getPassword;

    String getItemName;

    int itemListLength;

    public static int previousQuantity;

    public static ArrayList <String> itemNames=new ArrayList<>();

    int listLength;

    int nameListLength;


    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " +TABLE_NAME+"(USER_NAME PRIMARY KEY, PASSWORD TEXT )");


        sqLiteDatabase.execSQL("create table " +TABLE_NAME1+"(itemID INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT, itemQuantity INTEGER, itemLocation TEXT )");


        sqLiteDatabase.execSQL("create table " +TABLE_NAME2+"(itemCode INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT, soldQty INTEGER )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);


    }

    public boolean insertData(String userName, String password){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_1,userName);

        contentValues.put(COLUMN_2,password);

        long result= sqLiteDatabase.insert(TABLE_NAME,null, contentValues);

        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        Cursor res= sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME,null);

        return res;

    }

    public void validateUserName() {

        Cursor res = getAllData();

        while (res.moveToNext()) {

            getUserName = res.getString(0);
            getPassword=res.getString(1);

            list.add(getUserName);
            list1.add(getPassword);

            nameList.add(getUserName);
            passwordList.add(getPassword);







        }

        listLength=list.size();
        nameListLength=nameList.size();


        System.out.println(listLength);

        System.out.println(list.toString());

    }


    public boolean addStock(String itmName, String itmQuantity, String itmLocation){

        int newQty=Integer.parseInt(itmQuantity);

        //double newPrice=Double.parseDouble(itmPrice);


        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,itmName);

        contentValues.put(COL_3,newQty);

        //contentValues.put(COL_4,newPrice);

        contentValues.put(COL_4,itmLocation);


        long resultExec= sqLiteDatabase.insert(TABLE_NAME1,null, contentValues);

        if(resultExec == -1)
            return false;
        else
            return true;



    }

    public Cursor getItemNames(){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        Cursor res1= sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME1,null);

        return res1;


    }



    public void checkItem(){


        Cursor res1 = getItemNames();


        while (res1.moveToNext()) {

            getItemName = res1.getString(1);

            itemNames.add(getItemName);



        }

        itemListLength=itemNames.size();




    }


    public Cursor getPreviousLocation(String itemName){

        System.out.println("Item name"+ itemName);

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

//        String []projections={COL_3};
//        String [] selection_args={itemName};
//        String Selection=COL_2+" LIKE ?";

        Cursor resultLocation= sqLiteDatabase.rawQuery("Select * From current_stock Where itemName='"+ itemName +"'", null );

        System.out.println(resultLocation);

        return resultLocation;



    }


    public void getItemDetails(String nameItem){

        Cursor res1 = getPreviousLocation(nameItem);


        while (res1.moveToNext()) {

            previousQuantity=res1.getInt(2);
            getLocation=res1.getString(3);



        }

        System.out.println("item Quantity"+ previousQuantity);

        System.out.println("item Location"+ getLocation);



    }





    public boolean updateItem(String itmNam, int itmQuan){

        //double newPrice=Double.parseDouble(itmPri);
        //int newQuantity;

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        contentValues.put(COL_3,itmQuan);

        sqLiteDatabase.update(TABLE_NAME1,contentValues,"itemName = ?",new String[] {itmNam});

        return true;



    }




    public Cursor getAllStock(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        Cursor res= sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME1,null);

        return res;

    }


    public void insertIntoSoldStock(String soldItmName, int soldItemQty){


        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ITEM_NAME,soldItmName);

        contentValues.put(SOLD_QTY,soldItemQty);

        long result= sqLiteDatabase.insert(TABLE_NAME2,null, contentValues);




    }

    public boolean updateSoldStock(String solditemName, int soldItmQty){


        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        contentValues.put(SOLD_QTY,soldItmQty);

        sqLiteDatabase.update(TABLE_NAME2,contentValues,"itemName = ?",new String[] {solditemName});

        return true;


    }

    public Cursor getSoldItemName(){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        Cursor res1= sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME2,null);

        return res1;


    }

    public void checkSoldItem(){


        Cursor res1 = getSoldItemName();


        while (res1.moveToNext()) {

            getSoldItemName = res1.getString(1);
            getSoldItemQuantity=res1.getInt(2);

            soldItemList.add(getSoldItemName);



        }

        soldListLength=soldItemList.size();




    }
























}
