package com.whatcareer.app;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "whatcareerng";
    private static final String TABLE = "featured_posts";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_URL = "url";
    private static final String KEY_IMG = "image";
    private static final String KEY_DATE = "date";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_URL + " TEXT,"
                + KEY_IMG + " TEXT,"
                + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        // Create tables again
        onCreate(db);
    }


    public void truncate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        // Create tables again
        onCreate(db);
    }


    // code to add the new contact
    void addPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, post.getPostTitle());
        values.put(KEY_CONTENT, post.getPostContent());
        values.put(KEY_URL, post.getPostUrl());
        values.put(KEY_IMG, post.getPostImage());
        values.put(KEY_DATE, post.getPostDate());

        // Inserting Row
        db.insert(TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all contacts in a list view
    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<Post>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Post post = new Post(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );
                posts.add(post);
            } while (cursor.moveToNext());
        }

        // return contact list
        return posts;
    }
}
