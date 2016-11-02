package com.scottlindley.farmgroceryapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scottlindley.farmgroceryapp.Farm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Lindley on 11/1/2016.
 */

public class MySQLiteHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "FarmApp";
    public static final int VERSION_NUMBER = 1;

    public static final String USER_TABLE_NAME = "Users";
    public static final String LIKES_TABLE_NAME = "Likes";
    public static final String FOODS_TABLE_NAME = "Foods";
    public static final String FARMS_TABLE_NAME = "Farms";

    public static final String COL_ID = "ID";
    public static final String COL_NAME = "Name";
    public static final String COL_STATE = "State";
    public static final String COL_STORY = "Story";
    public static final String COL_SPECIALTY = "Specialty";
    public static final String COL_PRICE = "Price";
    public static final String COL_FARM_ID = "FarmID";
    public static final String COL_USER_ID = "UserID";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE "+USER_TABLE_NAME+" ("+
                    COL_ID+" INTEGER PRIMARY KEY, "+
                    COL_NAME+" TEXT, "+
                    COL_STATE+" TEXT)";
    public static final String CREATE_LIKE_TABLE =
            "CREATE TABLE "+LIKES_TABLE_NAME+" ("+
                    COL_ID+" INTEGER PRIMARY KEY, "+
                    COL_USER_ID+" INT, "+
                    COL_FARM_ID+" INT)";
    public static final String CREATE_FARMS_TABLE =
            "CREATE TABLE "+FARMS_TABLE_NAME+" ("+
                    COL_FARM_ID+" INTEGER PRIMARY KEY, "+
                    COL_NAME+" TEXT, "+
                    COL_STORY+" TEXT, "+
                    COL_SPECIALTY+" TEXT, "+
                    COL_STATE+" TEXT)";
    public static final String CREATE_FOODS_TABLE =
            "CREATE TABLE "+FOODS_TABLE_NAME+" ("+
                    COL_NAME+" TEXT,"+
                    COL_FARM_ID+" INTEGER,"+
                    COL_PRICE+" REAL, "+
                    "PRIMARY KEY("+COL_NAME+", "+COL_FARM_ID+"))";

    private static MySQLiteHelper sInstance;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    public static MySQLiteHelper getInstance(Context context){
        if(sInstance==null){
            sInstance = new MySQLiteHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_FARMS_TABLE);
        sqLiteDatabase.execSQL(CREATE_FOODS_TABLE);
        sqLiteDatabase.execSQL(CREATE_LIKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public List<Farm> getAllFarms(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                FARMS_TABLE_NAME,
                null,null,null,null,null,null);

        List<Farm> allFarms = new ArrayList<>();

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                allFarms.add(new Farm(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_SPECIALTY)),
                        cursor.getString(cursor.getColumnIndex(COL_STATE))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return allFarms;
    }

    public List<Farm> searchFarms(String query){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                FARMS_TABLE_NAME, null,
                COL_NAME+" LIKE ? OR "+COL_STATE+" LIKE ?",
                new String[]{"%"+query+"%","%"+query+"%"}
                ,null,null,null);

        List<Farm> allFarms = new ArrayList<>();

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                allFarms.add(new Farm(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_SPECIALTY)),
                        cursor.getString(cursor.getColumnIndex(COL_STATE))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return allFarms;
    }

}
