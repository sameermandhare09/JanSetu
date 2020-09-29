package cssl.dialstar.representative_util;

/**
 * Created by ravi on 20/02/18.
 */

public class Note {
    public static final String TABLE_NAME = "grievance_data";
    public static final String TABLE_NAME1 = "memory_data";
    public static final String TABLE_NAME2 = "event_data";
    public static final String TABLE_NAME3 = "discussion_data";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_GRIEVANCE_ID = "grievance_id";
    public static final String COLUMN_EVENT = "event";
    public static final String COLUMN_EVENT_ID = "event_id";
    public static final String COLUMN_DISCUSSION = "discussion";
    public static final String COLUMN_DISCUSSION_ID = "discussion_id";


    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_USERTYPE = "usertype";
    public static final String COLUMN_USERNAME = "username";

    public static final String COLUMN_READ = "read";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_MEMORYSIZE = "memory_size";



    private int id;
    private int grievanceid;
    private int eventid;
    private int discussionid;
    private int data;
    private int memorysize;
    private int read;





    private String note;
    private String event;
    private String discussion;
    private String timestamp;
    private String usertype;
    private String username;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_GRIEVANCE_ID + " INTEGER,"
                    + COLUMN_TIMESTAMP + " DATE,"
                    + COLUMN_USERTYPE + " TEXT,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_READ + " INTEGER "
                    + ")";



    public static final String CREATE_TABLE1 =
            "CREATE TABLE " + TABLE_NAME1 + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DATA + " INTEGER,"
                    +COLUMN_MEMORYSIZE+" INTEGER,"
                    + COLUMN_TIMESTAMP + " DATE "
                    + ")";



    public static final String CREATE_TABLE2 =
            "CREATE TABLE " + TABLE_NAME2 + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EVENT + " TEXT,"
                    + COLUMN_EVENT_ID + " INTEGER,"
                    + COLUMN_TIMESTAMP + " DATE,"
                    + COLUMN_USERTYPE + " TEXT,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_READ + " INTEGER "
                    + ")";


    public static final String CREATE_TABLE3 =
            "CREATE TABLE " + TABLE_NAME3 + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DISCUSSION + " TEXT,"
                    + COLUMN_DISCUSSION_ID + " INTEGER,"
                    + COLUMN_TIMESTAMP + " DATE,"
                    + COLUMN_USERTYPE + " TEXT,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_READ + " INTEGER "
                    + ")";


//DATETIME DEFAULT CURRENT_TIMESTAMP
    public Note() {
    }

    public Note(int id,int grievanceid, String note, String timestamp,String usertype,String username,int read) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
        this.grievanceid=grievanceid;
        this.usertype=usertype;
        this.username=username;
        this.read = read;

    }



    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getGrievanceid() {return grievanceid;}

    public void setGrievanceid(int grievanceid) {this.grievanceid = grievanceid;}

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getMemorysize() {
        return memorysize;
    }

    public void setMemorysize(int memorysize) {
        this.memorysize = memorysize;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getDiscussionid() {
        return discussionid;
    }

    public void setDiscussionid(int discussionid) {
        this.discussionid = discussionid;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }
}
