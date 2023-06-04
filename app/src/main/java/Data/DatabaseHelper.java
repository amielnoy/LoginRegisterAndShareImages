package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import Model.User;
import Utill.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context ctx;
    String CREATE_REGISTER_TABLE;
    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }


    //ID is the primary key table
    //EMAIL must be unique
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.CREATE_REGISTER_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + " ("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_USERNAME + " TEXT,"
                + Constants.KEY_PASSWORD + " TEXT,"
                + Constants.KEY_EMAIL + " TEXT UNIQUE,"
                + Constants.KEY_ROLE + " TEXT )";
        Log.d("TableCreated", "done");
        db.execSQL(CREATE_REGISTER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }

    /*
     * CRUD OPERATIONS : CREATE -->  add user  , DELETE , UPDATE  METHODS .
     *
     */

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_USERNAME, user.getUserName());
        values.put(Constants.KEY_PASSWORD, user.getPassword());
        values.put(Constants.KEY_EMAIL, user.getEmail());
        values.put(Constants.KEY_ROLE, user.getRole());
        // Error
        long res = db.insert(Constants.TABLE_NAME, null, values);
        Log.d("Saved!", "saved to DB");
        db.close();
        return res;

    }






    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_USERNAME, user.getUserName());
        values.put(Constants.KEY_PASSWORD, user.getPassword());
        values.put(Constants.KEY_EMAIL, user.getEmail());
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?",
                new String[]{String.valueOf(user.getId())});
    }

    public Cursor getUserDetails()
    {
        String allFieldsQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allFieldsQuery, null);
        return cursor;
    }
    public ArrayList<User> getAllUsers()
    {
        ArrayList<User>arrayOfUser=new ArrayList<>();
        String st="select * from "+Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(st,null);
        while (cursor.moveToNext())
        {
            User user=new User();
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id"))));
            user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("userName")));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("Password")));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow("role")));
            arrayOfUser.add(user);
        }
        return arrayOfUser;



    }
    public int getUsersCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


    public Boolean checkUser(String email, String password) {
        String[] columns = {Constants.KEY_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = Constants.KEY_EMAIL + "=?" + " and " + Constants.KEY_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }

    public String getUserRole(String email, String password) {
        int count=-1;
        String userRole;
        String[] columns = {Constants.KEY_ID,Constants.KEY_ROLE,};
        SQLiteDatabase db=null;
        Cursor cursor=null;

        try {
            db = getReadableDatabase();
            String selection = Constants.KEY_EMAIL + "=?" + " and " + Constants.KEY_PASSWORD + "=?";
            String[] selectionArgs = {email, password};
            String allFieldsQuery = "SELECT * FROM " + Constants.TABLE_NAME;
            cursor = db.rawQuery(allFieldsQuery, null);

            //cursor = db.query(Constants.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            count = cursor.getCount();

            if (count > 0)
                userRole = cursor.getString(1);
            else
                userRole="error in role!";
            return userRole;
        }catch (Exception e){
            e.printStackTrace();
            userRole="error in role!";
            return userRole;
        }finally {
            cursor.close();
            db.close();
        }
    }


    // not clean code
    public String selectOneUserSendUserName(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.KEY_EMAIL + " = '"+email.trim()+"'" +" and "+ Constants.KEY_PASSWORD + " = '"+password.trim()+"'" , null);
        c.moveToFirst();

        int x = 0 ;
        String y = "";
        while (c != null) {
            int userIdColumnIndex=c.getColumnIndex(Constants.KEY_ID);
            if(userIdColumnIndex>=0)
              x = Integer.parseInt(c.getString(userIdColumnIndex));
            int userNameColumnIndex=c.getColumnIndex(Constants.KEY_USERNAME);
            if(userIdColumnIndex>=0)
              y = c.getString(userNameColumnIndex);
            Log.d("tagOneUser", Integer.toString(x) );
            Log.d("tagOneUser", y );
            break;
        }
        c.moveToNext();
        return y;
    }
    public int selectOneUserSendId(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.KEY_EMAIL + " = '"+email.trim()+"'" +" and "+ Constants.KEY_PASSWORD + " = '"+password.trim()+"'" , null);
        c.moveToFirst();

        int x = 0 ;
        String y = "";
        while (c != null) {
            int userIdColumnIndex=c.getColumnIndex(Constants.KEY_ID);
            if(userIdColumnIndex>=0)
              x = Integer.parseInt(c.getString(userIdColumnIndex));
            int userNameColumnIndex=c.getColumnIndex(Constants.KEY_ID);
            if(userNameColumnIndex>=0)
              y = c.getString(userNameColumnIndex);
            Log.d("tagOneUser", Integer.toString(x) );
            Log.d("tagOneUser", y );
            break;
        }
        c.moveToNext();
        return x;
    }


}


