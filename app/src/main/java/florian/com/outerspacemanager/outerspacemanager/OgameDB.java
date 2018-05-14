package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


public class OgameDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 11;

    private static final String DATABASE_NAME = Constant.User.getUsername()+"OgameDB.db";

    // ============== USER ===================

    public static final String USER_TABLE_NAME = "User";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIl = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME + " (" + KEY_ID + " TEXT, " +
            KEY_EMAIl + " TEXT, " + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT);";

    // ============== BUILDING ===================

    public static final String BUILDING_TABLE_NAME = "Building";
    public static final String KEY_BUILDING_BUILDING_ID = "building_id";
    public static final String KEY_BUILDING_LEVEL = "level"; //int
    public static final String KEY_BUILDING_amountOfEffectByLevel = "amountOfEffectByLevel"; //int
    public static final String KEY_BUILDING_amountOfEffectLevel0 = "amountOfEffectLevelZero"; //int
    public static final String KEY_BUILDING_BUILDING = "building"; //boolean
    public static final String KEY_BUILDING_effect = "effect"; //string
    public static final String KEY_BUILDING_gasCostByLevel = "gasCostByLevel"; //int
    public static final String KEY_BUILDING_gasCostLevel0 = "gasCostLevelZero"; //int
    public static final String KEY_BUILDING_imageUrl = "imageUrl"; //string
    public static final String KEY_BUILDING_mineralCostByLevel = "mineralCostByLevel"; //int
    public static final String KEY_BUILDING_mineralCostLevel0 = "mineralCostLevelZero"; //int
    public static final String KEY_BUILDING_name = "name"; //string
    public static final String KEY_BUILDING_timeToBuildByLevel = "timeToBuildByLevel"; //int
    public static final String KEY_BUILDING_timeToBuildLevel0 = "timeToBuildLevelZero"; //int


    private static final String BUILDING_TABLE_CREATE = "CREATE TABLE " + BUILDING_TABLE_NAME + " (" +
            KEY_ID + " TEXT, " +
            KEY_BUILDING_BUILDING_ID + " INTEGER, " +
            KEY_BUILDING_LEVEL + " INTEGER, " +
            KEY_BUILDING_amountOfEffectByLevel + " INTEGER, " +
            KEY_BUILDING_amountOfEffectLevel0 + " INTEGER, " +
            KEY_BUILDING_BUILDING + " INTEGER, " +
            KEY_BUILDING_effect + " TEXT, " +
            KEY_BUILDING_gasCostByLevel + " INTEGER, " +
            KEY_BUILDING_gasCostLevel0 + " INTEGER, " +
            KEY_BUILDING_imageUrl + " TEXT, " +
            KEY_BUILDING_mineralCostByLevel + " INTEGER, " +
            KEY_BUILDING_mineralCostLevel0 + " INTEGER, " +
            KEY_BUILDING_name + " TEXT, " +
            KEY_BUILDING_timeToBuildByLevel + " INTEGER, " +
            KEY_BUILDING_timeToBuildLevel0 + " INTEGER)";

    // ============== BUILDING STATE ===================

    public static final String BUILDING_STATE_TABLE_NAME = "BuildingState";
    public static final String KEY_BUILDING_STATE_BUILDING_ID = "building_id";
    public static final String KEY_BUILDING_STATE_BUILDING = "building";
    public static final String KEY_BUILDING_STATE_DATE_CONSTRUCTION = "dateConstruction";

    private static final String BUILDING_STATE_TABLE_CREATE = "CREATE TABLE " + BUILDING_STATE_TABLE_NAME + " (" + KEY_ID + " TEXT, " + KEY_BUILDING_STATE_BUILDING_ID + " TEXT, " +KEY_BUILDING_STATE_BUILDING + " TEXT, " +
            KEY_BUILDING_STATE_DATE_CONSTRUCTION + " TEXT);";


    // ============== SEARCH ===================

    public static final String SEARCH_TABLE_NAME = "Search";
    public static final String KEY_SEARCH_SEARCH_ID = "search_id";
    public static final String KEY_SEARCH_LEVEL = "level"; //int
    public static final String KEY_SEARCH_BUILDING = "building"; //boolean
    public static final String KEY_SEARCH_amountOfEffectByLevel = "amountOfEffectByLevel"; //int
    public static final String KEY_SEARCH_amountOfEffectLevel0 = "amountOfEffectLevelZero"; //int
    public static final String KEY_SEARCH_effect = "effect"; //string
    public static final String KEY_SEARCH_gasCostByLevel = "gasCostByLevel"; //int
    public static final String KEY_SEARCH_gasCostLevel0 = "gasCostLevelZero"; //int
    public static final String KEY_SEARCH_mineralCostByLevel = "mineralCostByLevel"; //int
    public static final String KEY_SEARCH_mineralCostLevel0 = "mineralCostLevelZero"; //int
    public static final String KEY_SEARCH_name = "name"; //string
    public static final String KEY_SEARCH_timeToBuildByLevel = "timeToBuildByLevel"; //int
    public static final String KEY_SEARCH_timeToBuildLevel0 = "timeToBuildLevelZero"; //int

    private static final String SEARCH_TABLE_CREATE = "CREATE TABLE " + SEARCH_TABLE_NAME + " (" +
            KEY_ID + " TEXT, " +
            KEY_SEARCH_SEARCH_ID + " INTEGER, " +
            KEY_SEARCH_LEVEL + " INTEGER, " +
            KEY_SEARCH_amountOfEffectByLevel + " INTEGER, " +
            KEY_SEARCH_amountOfEffectLevel0 + " INTEGER, " +
            KEY_SEARCH_BUILDING + " INTEGER, " +
            KEY_SEARCH_effect + " TEXT, " +
            KEY_SEARCH_gasCostByLevel + " INTEGER, " +
            KEY_SEARCH_gasCostLevel0 + " INTEGER, " +
            KEY_SEARCH_mineralCostByLevel + " INTEGER, " +
            KEY_SEARCH_mineralCostLevel0 + " INTEGER, " +
            KEY_SEARCH_name + " TEXT, " +
            KEY_SEARCH_timeToBuildByLevel + " INTEGER, " +
            KEY_SEARCH_timeToBuildLevel0 + " INTEGER)";

    // ============== SEARCH STATE ===================

    public static final String SEARCH_STATE_TABLE_NAME = "SearchState";
    public static final String KEY_SEARCH_ID = "search_id";
    public static final String KEY_SEARCHING = "searching";
    public static final String KEY_DATE_SEARCHING = "dateSearching";

    private static final String SEARCH_STATE_TABLE_CREATE = "CREATE TABLE " + SEARCH_STATE_TABLE_NAME + " (" + KEY_ID + " TEXT, " + KEY_SEARCH_ID + " TEXT, " +KEY_SEARCHING + " TEXT, " +
            KEY_DATE_SEARCHING + " TEXT);";

    // ============== SHIP ===================

    public static final String SHIP_TABLE_NAME = "Ship";
    public static final String KEY_SHIP_SHIP_ID = "ship_id";
    public static final String KEY_SHIP_gasCost = "gasCost"; //int
    public static final String KEY_SHIP_life = "life"; //int
    public static final String KEY_SHIP_maxAttack = "maxAttack"; //int
    public static final String KEY_SHIP_minAttack = "minAttack"; //int
    public static final String KEY_SHIP_mineralCost = "mineralCost"; //int
    public static final String KEY_SHIP_name = "name"; //string
    public static final String KEY_SHIP_shield = "shield"; //int
    public static final String KEY_SHIP_spatioportLevelNeeded = "spatioportLevelNeeded"; //int
    public static final String KEY_SHIP_speed = "speed"; //int
    public static final String KEY_SHIP_timeToBuild = "timeToBuild"; //int


    private static final String SHIP_TABLE_CREATE = "CREATE TABLE " + SHIP_TABLE_NAME + " (" +
            KEY_ID + " TEXT, " +
            KEY_SHIP_SHIP_ID + " INTEGER, " +
            KEY_SHIP_gasCost + " INTEGER, " +
            KEY_SHIP_life + " INTEGER, " +
            KEY_SHIP_maxAttack + " INTEGER, " +
            KEY_SHIP_minAttack + " INTEGER, " +
            KEY_SHIP_mineralCost + " INTEGER, " +
            KEY_SHIP_name + " TEXT, " +
            KEY_SHIP_shield + " INTEGER, " +
            KEY_SHIP_spatioportLevelNeeded + " INTEGER, " +
            KEY_SHIP_speed + " INTEGER, " +
            KEY_SHIP_timeToBuild + " INTEGER)";

    // ============== SHIP STATE ===================

    public static final String SHIP_STATE_TABLE_NAME = "ShipState";
    public static final String KEY_SHIP_STATE_SHIP_ID = "ship_id";
    public static final String KEY_SHIP_STATE_NUMBER = "number";
    public static final String KEY_SHIP_STATE_DATE_CONSTRUCTION_LAUNCH = "dateConstructionLaunch";
    public static final String KEY_SHIP_STATE_DATE_CONSTRUCTION_END = "dateConstructionEnd";

    private static final String SHIP_STATE_TABLE_CREATE = "CREATE TABLE " + SHIP_STATE_TABLE_NAME + " (" + KEY_ID + " TEXT, " + KEY_SHIP_STATE_SHIP_ID + " TEXT, " +KEY_SHIP_STATE_NUMBER + " TEXT, " + " TEXT, " +KEY_SHIP_STATE_DATE_CONSTRUCTION_LAUNCH + " TEXT, " +
            KEY_SHIP_STATE_DATE_CONSTRUCTION_END + " TEXT);";




    public OgameDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(BUILDING_STATE_TABLE_CREATE);
        db.execSQL(SEARCH_STATE_TABLE_CREATE);
        db.execSQL(BUILDING_TABLE_CREATE);
        db.execSQL(SEARCH_TABLE_CREATE);
        db.execSQL(SHIP_TABLE_CREATE);
        db.execSQL(SHIP_STATE_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion < newVersion)
        //TODO : Create back of data before erase List<ofTables>
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BUILDING_STATE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SEARCH_STATE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BUILDING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SEARCH_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SHIP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SHIP_STATE_TABLE_NAME);
        onCreate(db);
    }
}
