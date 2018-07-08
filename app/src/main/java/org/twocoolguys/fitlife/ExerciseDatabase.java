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

public class ExerciseDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Exercise.db";
    public static final String TABLE_EXERCISES = "Exercises";
    public static final String COLUMN_EXERCISES = "exerciseName";
    public static final String COLUMN_ISCARDIO = "isCardio";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_DATE = "date";


    public ExerciseDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //Creates both tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + " ("+COLUMN_EXERCISES+" TEXT, " +
                COLUMN_ISCARDIO + " TEXT, " + COLUMN_TIME + " TEXT, " +
                COLUMN_WEIGHT + " TEXT, " + COLUMN_SETS + " TEXT, " + COLUMN_REPS + " TEXT, " +
                COLUMN_USER + " TEXT, " + COLUMN_DATE + " TEXT " + ")";
        db.execSQL(CREATE_EXERCISES_TABLE);
    }

    //Overridden onUpgrade(). Updates the database, again for both Exercises.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }
    public void addExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISES, exercise.getName());
        values.put(COLUMN_ISCARDIO, exercise.getIsCardio());
        values.put(COLUMN_TIME, exercise.getTime());
        values.put(COLUMN_WEIGHT, exercise.getWeight());
        values.put(COLUMN_SETS, exercise.getSets());
        values.put(COLUMN_REPS, exercise.getReps());
        values.put(COLUMN_USER, exercise.getOwner());
        values.put(COLUMN_DATE, exercise.getDate());
        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }

    public Exercise[] getAllExercises(){
        //created by Chris 2017/11/27
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        //cursor is used to parse through the rows of table used with .moveToNext
        Cursor cursorDB = db.rawQuery("SELECT * FROM Exercises",null);

        if(cursorDB.moveToFirst()){
            String name = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_EXERCISES));
            String isCardio = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_ISCARDIO));
            String time = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_TIME));
            String weight = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_WEIGHT));
            String sets = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_SETS));
            String reps = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_REPS));
            String user = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_USER));
            String date = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_DATE));
            Exercise newExercise = new Exercise(name, isCardio, time, weight, sets, reps, user, date);

            exerciseList.add(newExercise);

            while(cursorDB.moveToNext()){
                name = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_EXERCISES));
                isCardio = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_ISCARDIO));
                time = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_TIME));
                weight = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_WEIGHT));
                sets = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_SETS));
                reps = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_REPS));
                user = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_USER));
                date = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_DATE));
                newExercise = new Exercise(name, isCardio, time, weight, sets, reps, user, date);
                exerciseList.add(newExercise);
            }
        }

        Exercise[] newexerciseList = exerciseList.toArray(new Exercise[exerciseList.size()]);

        cursorDB.close();
        db.close();
        return newexerciseList;
    }
    public boolean deleteExercise(String exerciseName){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_EXERCISES + " WHERE " + COLUMN_EXERCISES + " = \"" + exerciseName + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            String[] name = {exerciseName};
            db.delete(TABLE_EXERCISES, COLUMN_EXERCISES + "=?", name);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public void updateExercise(Exercise exercise) {
        //follows same pattern as updateUser in UserDatabase, see function there
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EXERCISES, exercise.getName());
        cv.put(COLUMN_ISCARDIO, exercise.getIsCardio());
        cv.put(COLUMN_TIME, exercise.getTime());
        cv.put(COLUMN_WEIGHT, exercise.getWeight());
        cv.put(COLUMN_SETS, exercise.getSets());
        cv.put(COLUMN_REPS, exercise.getReps());
        cv.put(COLUMN_USER, exercise.getOwner());
        cv.put(COLUMN_DATE, exercise.getDate());
        String query = "Select * FROM " + TABLE_EXERCISES + " WHERE " + COLUMN_EXERCISES + " = \'" + exercise.getName() + "\'";
        String[] name = {exercise.getName()};
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            db.update(TABLE_EXERCISES,cv, COLUMN_EXERCISES + "=?", name);
            cursor.close();
        }
        db.close();
    }

    public Boolean authDate(String date){
        Exercise[] exerciseList = getAllExercises();
        try{
            for(Exercise e : exerciseList){
                String edate = e.getDate();
                if(edate.equals(date)){
                    return true;
                }
            }
        } catch (Exception E){
            return false;
        }

        return false;
    }
//    public void updateexercise(Exercise exercise, String oldName) {
//        //if the name of the exercise is being changed, this method is called
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_EXERCISES, exercise.getName());
//        cv.put(COLUMN_ISCARDIO, exercise.isCardio());
//        cv.put(COLUMN_TIME, exercise.getTime());
//        cv.put(COLUMN_WEIGHT, exercise.getWeight());
//        cv.put(COLUMN_SETS, exercise.getSets());
//        cv.put(COLUMN_REPS, exercise.getReps());
//        String query = "Select * FROM " + TABLE_EXERCISES + " WHERE " + COLUMN_EXERCISES + " = \'" + oldName + "\'";
//        String[] name = {oldName};
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()){
//            db.update(TABLE_EXERCISES,cv, COLUMN_EXERCISES + "=?", name);
//            cursor.close();
//        }
//        db.close();
//    }
}