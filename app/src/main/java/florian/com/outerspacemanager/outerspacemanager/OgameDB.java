package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by fcavasin on 16/01/2018.
 */

public class OgameDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OgameDB.db";
    public static final String USER_TABLE_NAME = "User";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIl = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME + " (" + KEY_ID + " TEXT, " +
            KEY_EMAIl + " TEXT, " + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT);";
    public OgameDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
    if (oldVersion < newVersion)
        onCreate(db);
    //.....
    }
}
