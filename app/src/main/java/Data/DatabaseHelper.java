package Data;

import static Utill.Constants.IMAGES_KEY_ID;
import static Utill.Constants.IMAGES_USER_ID_KEY;
import static Utill.Constants.USERS_KEY_EMAIL;
import static Utill.Constants.IMAGES_TABLE_NAME;
import static Utill.Constants.USERS_KEY_ID;
import static Utill.Constants.USERS_KEY_PASSWORD;
import static Utill.Constants.USERS_KEY_ROLE;
import static Utill.Constants.USERS_KEY_USERNAME;
import static Utill.Constants.USERS_TABLE_NAME;
import static Utill.Constants.USER_IMAGE_KEY;

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
import Model.UserImage;
import Utill.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context ctx;
    String CREATE_USERS_TABLE;
    String CREATE_USER_IMAGES_TABLE;
    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        this.CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME + " ("
                + USERS_KEY_ID + " INTEGER PRIMARY KEY,"
                + USERS_KEY_USERNAME + " TEXT UNIQUE,"
                + USERS_KEY_PASSWORD + " TEXT,"
                + USERS_KEY_EMAIL + " TEXT UNIQUE,"
                + USERS_KEY_ROLE + " TEXT )";
        Log.d("TableCreated", "done");
        db.execSQL(CREATE_USERS_TABLE);
        //Image table creation
        this.CREATE_USER_IMAGES_TABLE = "CREATE TABLE IF NOT EXISTS " + IMAGES_TABLE_NAME + " ("
                + IMAGES_KEY_ID + " INTEGER PRIMARY KEY,"
                + USER_IMAGE_KEY + " TEXT,"
                + IMAGES_USER_ID_KEY + " TEXT)";
        Log.d("TableCreated "+IMAGES_TABLE_NAME, "done");
        db.execSQL(CREATE_USER_IMAGES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IMAGES_TABLE_NAME);

        onCreate(db);
    }



    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_KEY_USERNAME, user.getUserName());
        values.put(USERS_KEY_PASSWORD, user.getPassword());
        values.put(USERS_KEY_EMAIL, user.getEmail());
        values.put(Constants.USERS_KEY_ROLE, user.getRole());
        // Error
        long res = db.insert(USERS_TABLE_NAME, null, values);
        Log.d("Saved!", "saved to DB");
        db.close();
        return res;

    }






    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_IMAGE_KEY, user.getUserName());
        values.put(IMAGES_USER_ID_KEY, user.getPassword());
        values.put(USERS_KEY_EMAIL, user.getEmail());
        return db.update(IMAGES_TABLE_NAME, values, IMAGES_KEY_ID + "=?",
                new String[]{String.valueOf(user.getId())});
    }
    public int getEmailCountByQuery(String email)
    {
        String emailFieldsQuery = "SELECT "+"count("
                +Constants.USERS_KEY_EMAIL +") FROM  "
                + USERS_TABLE_NAME
               + " where "+ USERS_KEY_EMAIL +"= '"+email+ "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(emailFieldsQuery, null);
       int emailCount=cursor.getColumnIndex(USERS_KEY_EMAIL);

        return emailCount;
    }


    public ArrayList<UserImage> getAllUserImages()
    {
        UserImage userImage;
        ArrayList<UserImage>arrayOfUserImages=new ArrayList<>();
        String query_statement="select * from "+ IMAGES_TABLE_NAME;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query_statement,null);
        while (cursor.moveToNext())
        {
            userImage=new UserImage(0,null,"");
            userImage.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("ImageId"))));
            userImage.setUserImage(cursor.getBlob(cursor.getColumnIndexOrThrow("UserImage")));
            userImage.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("UserName")));

            arrayOfUserImages.add(userImage);
        }
        return arrayOfUserImages;

    }

    public ArrayList<UserImage> getAllSpecificUserImages(String userName)
    {
        UserImage userImage;
        ArrayList<UserImage>arrayOfUserImages=new ArrayList<>();
        String query_statement="select * from "
                + IMAGES_TABLE_NAME
                + " "+"where "
                + IMAGES_USER_ID_KEY+" ='"+userName+"'";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query_statement,null);
        while (cursor.moveToNext())
        {
            userImage=new UserImage(0,null,"");
            userImage.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("ImageId"))));
            userImage.setUserImage(cursor.getBlob(cursor.getColumnIndexOrThrow("UserImage")));
            userImage.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("UserName")));

            arrayOfUserImages.add(userImage);
        }
        return arrayOfUserImages;

    }
    public ArrayList<User> getAllUsers()
    {
        User user;
        ArrayList<User>arrayOfUser=new ArrayList<>();
        String query_statement="select * from "+ USERS_TABLE_NAME;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query_statement,null);
        while (cursor.moveToNext())
        {
            user=new User(0,"","","","");

            int indexOfUserId=cursor.getColumnIndexOrThrow("id");
            String userId=cursor.getString(indexOfUserId);
            user.setId(Integer.parseInt(userId));

            String userName=cursor.getString(cursor.getColumnIndexOrThrow("userName"));
            user.setUserName(userName);
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
        String[] columns = {USERS_KEY_EMAIL};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USERS_KEY_EMAIL + "=?" + " and " + USERS_KEY_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count == 1)
            return true;
        else
            return false;
    }

    public Boolean checkIsUserExistsByUserName(String userName) {
        String[] columns = {USERS_KEY_USERNAME};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USERS_KEY_USERNAME + "=?" ;
        String[] selectionArgs = {userName};
        Cursor cursor = db.query(USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count == 1)
            return true;
        else
            return false;
    }

    public String getUserRole(String email, String password) {
        int count=-1;
        String userRole;
        String[] columns = {USERS_KEY_EMAIL,USERS_KEY_PASSWORD,};
        SQLiteDatabase db=null;
        Cursor cursor=null;

        try {
            db = getReadableDatabase();
            String selection = USERS_KEY_EMAIL + "=?" + " and " + USERS_KEY_PASSWORD + "=?";
            String[] selectionArgs = {email, password};
            String allFieldsQuery = "SELECT * FROM " + USERS_TABLE_NAME;
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
    public String getUserRole(String email) {
        int count=-1;
        String userRole;

        SQLiteDatabase db=null;
        Cursor cursor=null;

        try {
            db = getReadableDatabase();
            String selection = USERS_KEY_EMAIL + "=?";
            String[] selectionArgs = {email};
            String allFieldsQuery = "SELECT * FROM " + USERS_TABLE_NAME;
            cursor = db.rawQuery(allFieldsQuery, null);

            //cursor = db.query(Constants.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            count = cursor.getCount();

            if (count > 0)
                userRole = cursor.getString(4);
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
            String allFieldsQuery = "SELECT * FROM " + USERS_TABLE_NAME;
            cursor = db.rawQuery(allFieldsQuery, null);

            //cursor = db.query(Constants.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            count = cursor.getCount();

            if (count > 0)
                userName = cursor.getString(1);
            else
                userName="no such username!";
            return userName;
        }catch (Exception e){
            e.printStackTrace();
            userName="no such username!!";
            return userName;
        }finally {
            cursor.close();
            db.close();
        }
    }



    public String selectOneUserSendUserName(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_KEY_EMAIL + " = '"+email.trim()+"'" +" and "+ USERS_KEY_PASSWORD + " = '"+password.trim()+"'" , null);
        c.moveToFirst();

        int x = 0 ;
        String y = "";
        while (c != null) {
            int userIdColumnIndex=c.getColumnIndex(USERS_KEY_ID);
            if(userIdColumnIndex>=0)
              x = Integer.parseInt(c.getString(userIdColumnIndex));
            int userNameColumnIndex=c.getColumnIndex(USERS_KEY_USERNAME);
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

        Cursor c = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_KEY_EMAIL + " = '"+email.trim()+"'" +" and "+ USERS_KEY_PASSWORD + " = '"+password.trim()+"'" , null);
        c.moveToFirst();

        int x = 0 ;
        String y = "";
        while (c != null) {
            int userIdColumnIndex=c.getColumnIndex(USERS_KEY_ID);
            if(userIdColumnIndex>=0)
              x = Integer.parseInt(c.getString(userIdColumnIndex));
            int userNameColumnIndex=c.getColumnIndex(USERS_KEY_USERNAME);
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
        String sql_Insert="INSERT INTO "+ IMAGES_TABLE_NAME+"(UserImage,UserName) values(?,?)";
        SQLiteStatement statement=db.compileStatement(sql_Insert);
        statement.bindBlob(1,imageArray);
        statement.bindString(2,userName);

        statement.execute();
    }


}


