package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


public class OgameDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "OgameDB.db";

    // ============== USER ===================

    public static final String USER_TABLE_NAME = "User";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIl = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME + " (" + KEY_ID + " TEXT, " +
            KEY_EMAIl + " TEXT, " + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT);";

    // ============== BUILDING STATE ===================

    public static final String BUILDING_STATE_TABLE_NAME = "BuildingState";
    public static final String KEY_BUILDING = "building";
    public static final String KEY_DATE_CONSTRUCTION = "dateConstruction";

    private static final String BUILDING_STATE_TABLE_CREATE = "CREATE TABLE " + BUILDING_STATE_TABLE_NAME + " (" + KEY_BUILDING + " TEXT, " +
            KEY_DATE_CONSTRUCTION + " TEXT);";


    public OgameDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(BUILDING_STATE_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion < newVersion)
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BUILDING_STATE_TABLE_NAME);
        onCreate(db);
    }
}
