package Data;

import static Utill.Constants.USERS_KEY_EMAIL;
import static Utill.Constants.IMAGES_TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import Model.User;
import Utill.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context ctx;
    String CREATE_REGISTER_TABLE;
    String CREATE_USER_IMAGES_TABLE;
    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }


    //ID is the primary key table
    //EMAIL must be unique
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.CREATE_REGISTER_TABLE = "CREATE TABLE " + IMAGES_TABLE_NAME + " ("
                + Constants.IMAGES_KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.IMAGES_KEY_USERNAME + " TEXT,"
                + Constants.IMAGES_USER_ID_KEY + " TEXT,"
                + USERS_KEY_EMAIL + " TEXT UNIQUE,"
                + Constants.USERS_KEY_ROLE + " TEXT )";
        Log.d("TableCreated", "done");
        db.execSQL(CREATE_REGISTER_TABLE);
        //Image table creation
        this.CREATE_USER_IMAGES_TABLE = "CREATE TABLE " + IMAGES_TABLE_NAME + " ("
                + Constants.IMAGES_KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.IMAGES_KEY_USERNAME + " TEXT,"
                + Constants.IMAGES_USER_ID_KEY + " TEXT)";
        Log.d("TableCreated "+IMAGES_TABLE_NAME, "done");
        db.execSQL(CREATE_REGISTER_TABLE);
    }`


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IMAGES_TABLE_NAME);

        onCreate(db);
    }

    /*
     * CRUD OPERATIONS : CREATE -->  add user  , DELETE , UPDATE  METHODS .
     *
     */

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.IMAGES_KEY_USERNAME, user.getUserName());
        values.put(Constants.IMAGES_USER_ID_KEY, user.getPassword());
        values.put(USERS_KEY_EMAIL, user.getEmail());
        values.put(Constants.USERS_KEY_ROLE, user.getRole());
        // Error
        long res = db.insert(IMAGES_TABLE_NAME, null, values);
        Log.d("Saved!", "saved to DB");
        db.close();
        return res;

    }






    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.IMAGES_KEY_USERNAME, user.getUserName());
        values.put(Constants.IMAGES_USER_ID_KEY, user.getPassword());
        values.put(USERS_KEY_EMAIL, user.getEmail());
        return db.update(IMAGES_TABLE_NAME, values, Constants.IMAGES_KEY_ID + "=?",
                new String[]{String.valueOf(user.getId())});
    }
    public int getEmailCountByQuery(String email)
    {
        String emailFieldsQuery = "SELECT "+"count("
                +Constants.USERS_KEY_EMAIL +") FROM  "
                + IMAGES_TABLE_NAME
               + " where "+ USERS_KEY_EMAIL +"= '"+email+ "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(emailFieldsQuery, null);
       int emailCount=cursor.getColumnIndex(USERS_KEY_EMAIL);

        return emailCount;
    }
    public Cursor getUserDetails()
    {
        String allFieldsQuery = "SELECT * FROM " + IMAGES_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allFieldsQuery, null);
        return cursor;
    }
    public ArrayList<User> getAllUsers()
    {
        ArrayList<User>arrayOfUser=new ArrayList<>();
        String st="select * from "+ IMAGES_TABLE_NAME;
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
        String countQuery = "SELECT * FROM " + IMAGES_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


    public Boolean checkUser(String email, String password) {
        String[] columns = {Constants.IMAGES_KEY_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USERS_KEY_EMAIL + "=?" + " and " + Constants.IMAGES_USER_ID_KEY + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(IMAGES_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
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
        String[] columns = {Constants.IMAGES_KEY_ID,Constants.USERS_KEY_ROLE,};
        SQLiteDatabase db=null;
        Cursor cursor=null;

        try {
            db = getReadableDatabase();
            String selection = USERS_KEY_EMAIL + "=?" + " and " + Constants.IMAGES_USER_ID_KEY + "=?";
            String[] selectionArgs = {email, password};
            String allFieldsQuery = "SELECT * FROM " + IMAGES_TABLE_NAME;
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

    public String getUserName(String email) {
        int count=-1;
        String userName;

        SQLiteDatabase db=null;
        Cursor cursor=null;

        try {
            db = getReadableDatabase();
            String selection = USERS_KEY_EMAIL + "=?";
            String[] selectionArgs = {email};
            String allFieldsQuery = "SELECT * FROM " + IMAGES_TABLE_NAME;
            cursor = db.rawQuery(allFieldsQuery, null);

            //cursor = db.query(Constants.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            count = cursor.getCount();

            if (count > 0)
                userName = cursor.getString(1);
            else
                userName="error in role!";
            return userName;
        }catch (Exception e){
            e.printStackTrace();
            userName="error in role!";
            return userName;
        }finally {
            cursor.close();
            db.close();
        }
    }


    // not clean code
    public String selectOneUserSendUserName(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + IMAGES_TABLE_NAME + " WHERE " + USERS_KEY_EMAIL + " = '"+email.trim()+"'" +" and "+ Constants.IMAGES_USER_ID_KEY + " = '"+password.trim()+"'" , null);
        c.moveToFirst();

        int x = 0 ;
        String y = "";
        while (c != null) {
            int userIdColumnIndex=c.getColumnIndex(Constants.IMAGES_KEY_ID);
            if(userIdColumnIndex>=0)
              x = Integer.parseInt(c.getString(userIdColumnIndex));
            int userNameColumnIndex=c.getColumnIndex(Constants.IMAGES_KEY_USERNAME);
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

        Cursor c = db.rawQuery("SELECT * FROM " + IMAGES_TABLE_NAME + " WHERE " + USERS_KEY_EMAIL + " = '"+email.trim()+"'" +" and "+ Constants.IMAGES_USER_ID_KEY + " = '"+password.trim()+"'" , null);
        c.moveToFirst();

        int x = 0 ;
        String y = "";
        while (c != null) {
            int userIdColumnIndex=c.getColumnIndex(Constants.IMAGES_KEY_ID);
            if(userIdColumnIndex>=0)
              x = Integer.parseInt(c.getString(userIdColumnIndex));
            int userNameColumnIndex=c.getColumnIndex(Constants.IMAGES_KEY_ID);
            if(userNameColumnIndex>=0)
              y = c.getString(userNameColumnIndex);
            Log.d("tagOneUser", Integer.toString(x) );
            Log.d("tagOneUser", y );
            break;
        }
        c.moveToNext();
        return x;
    }

    public void add_user_details_with_img(String userName, byte [] imageArray){
        SQLiteDatabase db=this.getReadableDatabase();
        String sql_Insert="INSERT INTO contacts(Name,Img) values(?,?)";
        SQLiteStatement statement=db.compileStatement(sql_Insert);
        statement.bindString(0,Name);
        statement.bindBlob(1,Img);
        statement.execute();
    }


}


