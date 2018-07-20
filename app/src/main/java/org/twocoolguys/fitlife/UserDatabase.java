package org.twocoolguys.fitlife;

import android.app.LauncherActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Chris
 *
 * This is a modified version of a Database handler created in a previous class SEG 2105
 *
 * Format:
 * UserName|FirstName|LastName|Email|Password
 */





public class UserDatabase extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserInfo.db";

    //Database attributes responsible for user information
    public static final String TABLE_USERS = "UserInformation";
    public static final String COLUMN_NAME = "Users";
    public static final String COLUMN_FIRSTNAME = "FName";
    public static final String COLUMN_LASTNAME = "LName";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORDS = "Passwords";

    public UserDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + COLUMN_NAME + " TEXT PRIMARY KEY, " + COLUMN_FIRSTNAME + " TEXT, " + COLUMN_LASTNAME + " TEXT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PASSWORDS + " TEXT)";

        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_FIRSTNAME, user.getFirstName());
        values.put(COLUMN_LASTNAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORDS, user.getPassword());

        db.insert(TABLE_USERS, null, values);

        db.close(); //Save our changes to the database
    }



    //Will check to see if a user with "name" as their name exists
    public boolean checkUser(String name){
        User[] userList = getAllUsers();
        try{
            for(User u : userList) {
                String tName = u.getName();
                if (tName.equals(name)) {
                    return true;
                }
            }
        } catch (Exception E){
            return false;
        }

        return false;
    }

    //Authenticates a user given the login and password information
    public Boolean authUser(String name, String pass){
        User[] userList = getAllUsers();
        try{
            for(User u : userList) {
                String tName = u.getName();
                String tPass = u.getPassword();
                if (tName.equals(name) && tPass.equals(pass)) {
                    return true;
                }
            }
        } catch (Exception E){
            return false;
        }

        return false;
    }

    //Returns a USER type object
    public User getUserByName(String name){
        User[] userList = getAllUsers();

        try{
            for(User u : userList) {
                String tName = u.getName();
                if (tName.equals(name)) {
                    return u;
                }
            }
        } catch (Exception E){
            return null;
        }

        return null;
    }



    public User[] getAllUsers(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> userList = new ArrayList<User>();

        //cursor is used to parse through the rows of table used with .moveToNext
        String[] columns = {this.COLUMN_NAME, this.COLUMN_FIRSTNAME, this.COLUMN_LASTNAME, this.COLUMN_LASTNAME, this.COLUMN_PASSWORDS};
        Cursor cursorDB = db.rawQuery("SELECT * FROM UserInformation",null);

        if(cursorDB.moveToFirst()){
            String name = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_NAME));
            String fName = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_FIRSTNAME));
            String lName = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_LASTNAME));
            String email = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_EMAIL));
            String pass = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_PASSWORDS));


            User newUser = new User(name, fName, lName, email, pass);

            userList.add(newUser);

            while(cursorDB.moveToNext()){
                name = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_NAME));
                fName = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_FIRSTNAME));
                lName = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_LASTNAME));
                email = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_EMAIL));
                pass = cursorDB.getString(cursorDB.getColumnIndex(this.COLUMN_PASSWORDS));


                newUser = new User(name, fName, lName, email, pass);
                userList.add(newUser);
            }
        }
        User[] newUserList = userList.toArray(new User[userList.size()]);
        cursorDB.close();
        db.close();
        return newUserList;
    }


//    //only used in testing to delete a previously created person
//    public void deleteUser(String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "Select * FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + " = \"" + name + "\"";
//        Cursor cursor = db.rawQuery(query, null);
//        if(cursor.moveToFirst()){
//            String idStr = cursor.getString(0);
//            db.delete(TABLE_USERS, COLUMN_NAME + " = " + idStr, null);
//            cursor.close();
//        }
//        db.close();
//    }
//    public void updateUser(User updatedUser) {
//        //getting database and building a new content values variable from the passed user
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_NAME, updatedUser.getName());
//        cv.put(COLUMN_FIRSTNAME, updatedUser.getFirstName());
//        cv.put(COLUMN_LASTNAME, updatedUser.getLastName());
//        cv.put(COLUMN_EMAIL, updatedUser.getEmail());
//        cv.put(COLUMN_PASSWORDS, updatedUser.getPassword());
//
//        //building the search query and finding the proper value
//        String query = "Select * FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + " = \'" + updatedUser.getName() + "\'";
//        String[] name = {updatedUser.getName()};
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()){
//
//            db.update(TABLE_USERS,cv, COLUMN_NAME + "=?", name);
//            cursor.close();
//        }
//        db.close();
//    }
}
