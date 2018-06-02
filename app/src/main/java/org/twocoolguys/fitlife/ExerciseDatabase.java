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

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Exercise.db";
    public static final String TABLE_EXERCISES = "Exercises";
    public static final String COLUMN_EXERCISES = "exerciseName";
    public static final String COLUMN_ISCARDIO = "isCardio";
    //GOT TO HERE
    public static final String COLUMN_RESOURCES = "resources";
    public static final String COLUMN_GROUPNAME = "groupname";
    public static final String COLUMN_REWARD = "reward";
    public static final String COLUMN_DUEDATE = "duedate";
    public static final String COLUMN_ASSIGNED = "assigned";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //Creates both tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + " ("+COLUMN_EXERCISES+" TEXT PRIMARY KEY, " +
                COLUMN_ISCARDIO + " TEXT, " + COLUMN_RESOURCES + " TEXT, " + COLUMN_GROUPNAME
                + " TEXT, " + COLUMN_REWARD + " INTEGER, " + COLUMN_DUEDATE + " INTEGER, " + COLUMN_ASSIGNED
                + " TEXT " + ")";
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
        values.put(COLUMN_ISCARDIO, exercise.isCardio());
        values.put(COLUMN_RESOURCES, exercise.getResources());
        values.put(COLUMN_GROUPNAME, exercise.getGroup());
        values.put(COLUMN_REWARD, exercise.getReward());
        values.put(COLUMN_DUEDATE, exercise.getDueDate());
        values.put(COLUMN_ASSIGNED, exercise.getAssigned());
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
            String desc = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_ISCARDIO));
            String res = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_RESOURCES));
            String grp = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_GROUPNAME));
            int reward = cursorDB.getInt(cursorDB.getColumnIndex(this.COLUMN_REWARD));
            int date = cursorDB.getInt(cursorDB.getColumnIndex(this.COLUMN_DUEDATE));
            String ass = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_ASSIGNED));
            Exercise newExercise = new Exercise(name,desc,res,grp,reward,date,ass);

            exerciseList.add(newExercise);

            while(cursorDB.moveToNext()){
                name = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_EXERCISES));
                desc = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_ISCARDIO));
                res = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_RESOURCES));
                grp = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_GROUPNAME));
                reward = cursorDB.getInt(cursorDB.getColumnIndex(this.COLUMN_REWARD));
                date = cursorDB.getInt(cursorDB.getColumnIndex(this.COLUMN_DUEDATE));
                ass = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_ASSIGNED));
                newExercise = new Exercise(name,desc,res,grp,reward,date,ass);
                exerciseList.add(newExercise);
            }
        }

        exercise[] newexerciseList = exerciseList.toArray(new exercise[exerciseList.size()]);

        cursorDB.close();
        db.close();
        return newexerciseList;
    }
    public boolean deleteexercise(String exerciseName){
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
    public void updateexercise(exercise exercise) {
        //follows same pattern as updateUser in UserDatabase, see function there
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EXERCISES, exercise.getName());
        cv.put(COLUMN_ISCARDIO, exercise.isCardio());
        cv.put(COLUMN_RESOURCES, exercise.getResources());
        cv.put(COLUMN_GROUPNAME, exercise.getGroup());
        cv.put(COLUMN_REWARD, exercise.getReward());
        cv.put(COLUMN_DUEDATE, exercise.getDueDate());
        cv.put(COLUMN_ASSIGNED, exercise.getAssigned());
        String query = "Select * FROM " + TABLE_EXERCISES + " WHERE " + COLUMN_EXERCISES + " = \'" + exercise.getName() + "\'";
        String[] name = {exercise.getName()};
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            db.update(TABLE_EXERCISES,cv, COLUMN_EXERCISES + "=?", name);
            cursor.close();
        }
        db.close();
    }
    public void updateexercise(exercise exercise, String oldName) {
        //if the name of the exercise is being changed, this method is called
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EXERCISES, exercise.getName());
        cv.put(COLUMN_ISCARDIO, exercise.isCardio());
        cv.put(COLUMN_RESOURCES, exercise.getResources());
        cv.put(COLUMN_GROUPNAME, exercise.getGroup());
        cv.put(COLUMN_REWARD, exercise.getReward());
        cv.put(COLUMN_DUEDATE, exercise.getDueDate());
        cv.put(COLUMN_ASSIGNED, exercise.getAssigned());
        String query = "Select * FROM " + TABLE_EXERCISES + " WHERE " + COLUMN_EXERCISES + " = \'" + oldName + "\'";
        String[] name = {oldName};
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            db.update(TABLE_EXERCISES,cv, COLUMN_EXERCISES + "=?", name);
            cursor.close();
        }
        db.close();
    }
}