package org.twocoolguys.fitlife;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;
import java.util.LinkedList;

public class NutritionDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Nutrition.db";
    public static final String TABLE_NUTRITIONS = "Nutritions";
    public static final String COLUMN_FATS = "fats";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_PROTEIN = "protein";
    public static final String COLUMN_CARBS = "carbs";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_OWNER = "owner";



    public NutritionDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //Creates both tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NUTRITIONS_TABLE = "CREATE TABLE " + TABLE_NUTRITIONS + " (" + COLUMN_CARBS + " TEXT, " +
                COLUMN_FATS + " TEXT, " + COLUMN_CALORIES + " TEXT, " +
                COLUMN_PROTEIN + " TEXT, " + COLUMN_DATE + " TEXT, " +
                COLUMN_OWNER + " TEXT " + ")";
        db.execSQL(CREATE_NUTRITIONS_TABLE);
    }

    //Overridden onUpgrade(). Updates the database, again for both Nutritions.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRITIONS);
        onCreate(db);
    }
    public void addNutrition(Nutrition nutrition){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FATS, nutrition.getFats());
        values.put(COLUMN_CALORIES, nutrition.getCalories());
        values.put(COLUMN_PROTEIN, nutrition.getProtein());
        values.put(COLUMN_CARBS, nutrition.getCarbs());
        values.put(COLUMN_DATE, nutrition.getDate());
        values.put(COLUMN_OWNER, nutrition.getOwner());


        db.insert(TABLE_NUTRITIONS, null, values);
        db.close();
    }

    public Nutrition[] getAllNutritions(){
        //created by Chris 2017/11/27
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Nutrition> nutritionList = new ArrayList<Nutrition>();
        //cursor is used to parse through the rows of table used with .moveToNext
        Cursor cursorDB = db.rawQuery("SELECT * FROM Nutritions",null);

        if(cursorDB.moveToFirst()){
            String fats = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_FATS));
            String calories = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_CALORIES));
            String protein = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_PROTEIN));
            String carbs = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_CARBS));
            String date = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_DATE));
            String owner = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_OWNER));
            Nutrition newNutrition = new Nutrition(calories, fats, protein, carbs, date, owner);

            nutritionList.add(newNutrition);

            while(cursorDB.moveToNext()){
                fats = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_FATS));
                calories = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_CALORIES));
                protein = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_PROTEIN));
                carbs = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_CARBS));
                date = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_DATE));
                owner = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_OWNER));
                newNutrition = new Nutrition(fats, calories, protein, carbs, date, owner);
                nutritionList.add(newNutrition);
            }
        }

        Nutrition[] newnutritionList = nutritionList.toArray(new Nutrition[nutritionList.size()]);

        cursorDB.close();
        db.close();
        return newnutritionList;
    }
    public boolean deleteNutrition(String calories, String fats, String protein, String carbs, String date, String owner){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_NUTRITIONS + " WHERE " + COLUMN_CALORIES + " = \"" + calories + "\"";
        Cursor cursor = db.rawQuery(query, null);
        Log.d("TEST","test0");

        if (cursor.moveToFirst()){
            Log.d("TEST","test1");

            String[] name = {calories, fats, protein, carbs, date, owner};
            db.delete(TABLE_NUTRITIONS,
                    COLUMN_CALORIES + "=? AND " +
                    COLUMN_FATS + "=? AND " +
                    COLUMN_PROTEIN + "=? AND " +
                    COLUMN_CARBS + "=? AND " +
                    COLUMN_DATE + "=? AND " +
                    COLUMN_OWNER + "=?" ,
                    name);
            Log.d("TEST","test");
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
//    public void updateNutrition(Nutrition nutrition) {
//        //follows same pattern as updateUser in UserDatabase, see function there
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_FATS, nutrition.getFats());
//        cv.put(COLUMN_CALORIES, nutrition.getCalories());
//        cv.put(COLUMN_PROTEIN, nutrition.getProtein());
//        cv.put(COLUMN_CARBS, nutrition.getCarbs());
//        String query = "Select * FROM " + TABLE_NUTRITIONS + " WHERE " + COLUMN_CALORIES + " = \'" + nutrition.getCalories() + "\'";
//        String[] name = {nutrition.getCalories()};
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()){
//            db.update(TABLE_NUTRITIONS,cv, COLUMN_CALORIES + "=?", name);
//            cursor.close();
//        }
//        db.close();
//    }
//    public void updatenutrition(Nutrition nutrition, String oldName) {
//        //if the name of the nutrition is being changed, this method is called
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_NUTRITIONS, nutrition.getName());
//        cv.put(COLUMN_FATS, nutrition.isCardio());
//        cv.put(COLUMN_CALORIES, nutrition.getTime());
//        cv.put(COLUMN_PROTEIN, nutrition.getWeight());
//        cv.put(COLUMN_CARBS, nutrition.getSets());
//        cv.put(COLUMN_REPS, nutrition.getReps());
//        String query = "Select * FROM " + TABLE_NUTRITIONS + " WHERE " + COLUMN_NUTRITIONS + " = \'" + oldName + "\'";
//        String[] name = {oldName};
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()){
//            db.update(TABLE_NUTRITIONS,cv, COLUMN_NUTRITIONS + "=?", name);
//            cursor.close();
//        }
//        db.close();
//    }
}