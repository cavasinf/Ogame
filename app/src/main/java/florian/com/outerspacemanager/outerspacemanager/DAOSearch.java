package florian.com.outerspacemanager.outerspacemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DAOSearch {

    // Database fields
    private SQLiteDatabase database;
    private OgameDB dbHelper;
    private String[] allColumns = {OgameDB.KEY_ID,
            OgameDB.KEY_SEARCH_SEARCH_ID,
            OgameDB.KEY_SEARCH_LEVEL,
            OgameDB.KEY_SEARCH_amountOfEffectByLevel,
            OgameDB.KEY_SEARCH_amountOfEffectLevel0,
            OgameDB.KEY_SEARCH_BUILDING,
            OgameDB.KEY_SEARCH_effect,
            OgameDB.KEY_SEARCH_gasCostByLevel,
            OgameDB.KEY_SEARCH_gasCostLevel0,
            OgameDB.KEY_SEARCH_mineralCostByLevel,
            OgameDB.KEY_SEARCH_mineralCostLevel0,
            OgameDB.KEY_SEARCH_name,
            OgameDB.KEY_SEARCH_timeToBuildByLevel,
            OgameDB.KEY_SEARCH_timeToBuildLevel0};

    public DAOSearch(Context context) {
        dbHelper = new OgameDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Search createSearch(int buildingId, int level, int amountOfEffectByLevel, int amountOfEffectLevel0, boolean building, String effect, int gasCostByLevel, int gasCostLevel0, int mineralCostByLevel, int mineralCostLevel0, String name, int timeToBuildByLevel, int timeToBuildLevel0) {

        ContentValues values = new ContentValues();
        values.put(OgameDB.KEY_SEARCH_SEARCH_ID, buildingId);
        values.put(OgameDB.KEY_SEARCH_LEVEL, level);
        values.put(OgameDB.KEY_SEARCH_amountOfEffectByLevel, amountOfEffectByLevel);
        values.put(OgameDB.KEY_SEARCH_amountOfEffectLevel0, amountOfEffectLevel0);
        values.put(OgameDB.KEY_SEARCH_BUILDING, building);
        values.put(OgameDB.KEY_SEARCH_effect, effect);
        values.put(OgameDB.KEY_SEARCH_gasCostByLevel, gasCostByLevel);
        values.put(OgameDB.KEY_SEARCH_gasCostLevel0, gasCostLevel0);
        values.put(OgameDB.KEY_SEARCH_mineralCostByLevel, mineralCostByLevel);
        values.put(OgameDB.KEY_SEARCH_mineralCostLevel0, mineralCostLevel0);
        values.put(OgameDB.KEY_SEARCH_name, name);
        values.put(OgameDB.KEY_SEARCH_timeToBuildByLevel, timeToBuildByLevel);
        values.put(OgameDB.KEY_SEARCH_timeToBuildLevel0, timeToBuildLevel0);
        UUID newID = UUID.randomUUID();
        values.put(OgameDB.KEY_ID, newID.toString());

        database.insert(OgameDB.SEARCH_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OgameDB.SEARCH_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" + newID.toString() + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Search newSearch = cursorToSearch(cursor);
        cursor.close();
        return newSearch;
    }

    public Boolean deleteSearch(int searchId) {
        return database.delete(OgameDB.SEARCH_TABLE_NAME, OgameDB.KEY_SEARCH_SEARCH_ID + '=' + searchId, null) > 0;

    }

    public Boolean deleteAllSearches() {
        return database.delete(OgameDB.SEARCH_TABLE_NAME, OgameDB.KEY_SEARCH_SEARCH_ID + "> -1", null) > 0;

    }

    private Search cursorToSearch(Cursor cursor) {
        Search comment = new Search();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setSearchId(cursor.getInt(1));
        comment.setLevel(cursor.getInt(2));
        comment.setAmountOfEffectByLevel(cursor.getInt(3));
        comment.setAmountOfEffectLevel0(cursor.getInt(4));
        comment.setIsBuilding(cursor.getInt(5)==1);
        comment.setEffect(cursor.getString(6));
        comment.setGasCostByLevel(cursor.getInt(7));
        comment.setGasCostLevel0(cursor.getInt(8));
        comment.setMineralCostByLevel(cursor.getInt(9));
        comment.setMineralCostLevel0(cursor.getInt(10));
        comment.setName(cursor.getString(11));
        comment.setTimeToBuildByLevel(cursor.getInt(12));
        comment.setTimeToBuildLevel0(cursor.getInt(13));
        return comment;
    }

    public List<Search> getAllSearch() {
        List<Search> listSearch= new ArrayList<Search>();
        Cursor cursor = database.query(OgameDB.SEARCH_TABLE_NAME, allColumns, null,
                null, null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Search search = cursorToSearch(cursor);
            listSearch.add(search);
        }
        cursor.close();
        return listSearch;
    }

    public Search getSearch(String id) {
        Cursor cursor = database.query(OgameDB.SEARCH_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" + id + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Search search = cursorToSearch(cursor);
        cursor.close();
        return search;
    }

    public Search getSearchByName(String name) {
        Cursor cursor = database.query(OgameDB.SEARCH_TABLE_NAME, allColumns,
                OgameDB.KEY_SEARCH_name + " =\"" + name + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Search search = cursorToSearch(cursor);
        cursor.close();
        return search;
    }

    public Search getSearchByEffect(String effect) {
        Cursor cursor = database.query(OgameDB.SEARCH_TABLE_NAME, allColumns,
                OgameDB.KEY_SEARCH_effect + " =\"" + effect + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Search search = cursorToSearch(cursor);
        cursor.close();
        return search;
    }
}
