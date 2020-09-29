package cssl.dialstar.representative_util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create grievance table
        db.execSQL(Note.CREATE_TABLE);
       // Log.d("Notification Table created ",0+"");

        // create event table
      db.execSQL(Note.CREATE_TABLE2);

        // create DISCUSSION table
        db.execSQL(Note.CREATE_TABLE3);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME3);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String note,int grievanceid,String usertype,String username,int read) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.COLUMN_GRIEVANCE_ID,grievanceid);
        values.put(Note.COLUMN_NOTE, note);

        String date = new Date(System.currentTimeMillis()).toLocaleString();

        //date.toString("yyyy-MM-dd HH:mm:ss");

        values.put(Note.COLUMN_TIMESTAMP, date);
        values.put(Note.COLUMN_USERTYPE, usertype);
        values.put(Note.COLUMN_USERNAME, username);
        values.put(Note.COLUMN_READ, read);

        //dvalues.put(Note.COLUMN_TIMESTAMP,"2018 April 28 12:45:45");

        // insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();
        Log.d("Data insert ",0+"");

        // return newly inserted row id
        return id;
    }


    public long insertEvent(String event,int eventid,String usertype,String username,int read) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.COLUMN_EVENT_ID,eventid);
        values.put(Note.COLUMN_EVENT, event);

        String date = new Date(System.currentTimeMillis()).toLocaleString();

        //date.toString("yyyy-MM-dd HH:mm:ss");

        values.put(Note.COLUMN_TIMESTAMP, date);
        values.put(Note.COLUMN_USERTYPE, usertype);
        values.put(Note.COLUMN_USERNAME, username);
        values.put(Note.COLUMN_READ, read);

        //dvalues.put(Note.COLUMN_TIMESTAMP,"2018 April 28 12:45:45");

        // insert row
        long id = db.insert(Note.TABLE_NAME2, null, values);

        // close db connection
        db.close();
        Log.d("Data insert ",0+"");

        // return newly inserted row id
        return id;
    }

    public long insertDiscussion(String discussion,int discussionid,String usertype,String username,int read) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.COLUMN_DISCUSSION_ID,discussionid);
        values.put(Note.COLUMN_DISCUSSION, discussion);

        String date = new Date(System.currentTimeMillis()).toLocaleString();

        //date.toString("yyyy-MM-dd HH:mm:ss");

        values.put(Note.COLUMN_TIMESTAMP, date);
        values.put(Note.COLUMN_USERTYPE, usertype);
        values.put(Note.COLUMN_USERNAME, username);
        values.put(Note.COLUMN_READ, read);

        //dvalues.put(Note.COLUMN_TIMESTAMP,"2018 April 28 12:45:45");

        // insert row
        long id = db.insert(Note.TABLE_NAME3, null, values);

        // close db connection
        db.close();
        Log.d("Data insert ",0+"");

        // return newly inserted row id
        return id;
    }

    public long insertData(int datasize,int memorysize) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.COLUMN_DATA,datasize);
        values.put(Note.COLUMN_MEMORYSIZE,memorysize);
        String date = new Date(System.currentTimeMillis()).toLocaleString();

        //date.toString("yyyy-MM-dd HH:mm:ss");

        values.put(Note.COLUMN_TIMESTAMP, date);

        //dvalues.put(Note.COLUMN_TIMESTAMP,"2018 April 28 12:45:45");

        // insert row
        long id = db.insert(Note.TABLE_NAME1, null, values);

        // close db connection
        db.close();
        Log.d("Data insert ",0+"");

        // return newly inserted row id
        return id;
    }



    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setGrievanceid(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_GRIEVANCE_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                note.setUsertype(cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERTYPE)));
                note.setUsername(cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERNAME)));
                note.setRead(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_READ)));

                notes.add(note);
                //if(notes.size()<10)


            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }
    public List<Note> getAllEvents() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME2 + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setEventid(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_EVENT_ID)));
                note.setEvent(cursor.getString(cursor.getColumnIndex(Note.COLUMN_EVENT)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                note.setUsertype(cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERTYPE)));
                note.setUsername(cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERNAME)));
                note.setRead(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_READ)));
                notes.add(note);
               // if(notes.size()<10)


            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }
    public List<Note> getAllDiscussion() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME3 + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setDiscussionid(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_DISCUSSION_ID)));
                note.setDiscussion(cursor.getString(cursor.getColumnIndex(Note.COLUMN_DISCUSSION)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                note.setUsertype(cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERTYPE)));
                note.setUsername(cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERNAME)));
                note.setRead(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_READ)));
                notes.add(note);
                //if(notes.size()<10)


            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }
    public List<Note> getAlldata() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME1 + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setData(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_DATA)));
                note.setMemorysize(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MEMORYSIZE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                notes.add(note);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }


    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID,Note.COLUMN_GRIEVANCE_ID,
                        Note.COLUMN_NOTE,Note.COLUMN_TIMESTAMP,
                        Note.COLUMN_USERTYPE,Note.COLUMN_USERNAME},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_GRIEVANCE_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERTYPE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERNAME)),
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_READ)));


        // close the db connection
        cursor.close();

        return note;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void updateNote(ArrayList<Note> notes) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(int i=0;i<notes.size();i++){
            ContentValues values = new ContentValues();
            values.put(Note.COLUMN_READ, 1);

            // updating row
            db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(notes.get(i).getId())});

            db.update(Note.TABLE_NAME2, values, Note.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(notes.get(i).getId())});

            db.update(Note.TABLE_NAME3, values, Note.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(notes.get(i).getId())});

        }


    }
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public int updateData(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_DATA, note.getData());
        values.put(Note.COLUMN_MEMORYSIZE, note.getMemorysize());

        // updating row
        return db.update(Note.TABLE_NAME1, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}
