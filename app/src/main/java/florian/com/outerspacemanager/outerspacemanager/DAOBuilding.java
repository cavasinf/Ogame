package florian.com.outerspacemanager.outerspacemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DAOBuilding {

    // Database fields
    private SQLiteDatabase database;
    private OgameDB dbHelper;
    private String[] allColumns = {OgameDB.KEY_ID,
            OgameDB.KEY_BUILDING_BUILDING_ID,
            OgameDB.KEY_BUILDING_LEVEL,
            OgameDB.KEY_BUILDING_amountOfEffectByLevel,
            OgameDB.KEY_BUILDING_amountOfEffectLevel0,
            OgameDB.KEY_BUILDING_BUILDING,
            OgameDB.KEY_BUILDING_effect,
            OgameDB.KEY_BUILDING_gasCostByLevel,
            OgameDB.KEY_BUILDING_gasCostLevel0,
            OgameDB.KEY_BUILDING_imageUrl,
            OgameDB.KEY_BUILDING_mineralCostByLevel,
            OgameDB.KEY_BUILDING_mineralCostLevel0,
            OgameDB.KEY_BUILDING_name,
            OgameDB.KEY_BUILDING_timeToBuildByLevel,
            OgameDB.KEY_BUILDING_timeToBuildLevel0};

    public DAOBuilding(Context context) {
        dbHelper = new OgameDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Building createBuilding(int buildingId, int level, int amountOfEffectByLevel, int amountOfEffectLevel0, boolean building, String effect, int gasCostByLevel, int gasCostLevel0, String imageUrl, int mineralCostByLevel, int mineralCostLevel0, String name, int timeToBuildByLevel, int timeToBuildLevel0) {

        ContentValues values = new ContentValues();
        values.put(OgameDB.KEY_BUILDING_BUILDING_ID, buildingId);
        values.put(OgameDB.KEY_BUILDING_LEVEL, level);
        values.put(OgameDB.KEY_BUILDING_amountOfEffectByLevel, amountOfEffectByLevel);
        values.put(OgameDB.KEY_BUILDING_amountOfEffectLevel0, amountOfEffectLevel0);
        values.put(OgameDB.KEY_BUILDING_BUILDING, building);
        values.put(OgameDB.KEY_BUILDING_effect, effect);
        values.put(OgameDB.KEY_BUILDING_gasCostByLevel, gasCostByLevel);
        values.put(OgameDB.KEY_BUILDING_gasCostLevel0, gasCostLevel0);
        values.put(OgameDB.KEY_BUILDING_imageUrl, imageUrl);
        values.put(OgameDB.KEY_BUILDING_mineralCostByLevel, mineralCostByLevel);
        values.put(OgameDB.KEY_BUILDING_mineralCostLevel0, mineralCostLevel0);
        values.put(OgameDB.KEY_BUILDING_name, name);
        values.put(OgameDB.KEY_BUILDING_timeToBuildByLevel, timeToBuildByLevel);
        values.put(OgameDB.KEY_BUILDING_timeToBuildLevel0, timeToBuildLevel0);
        UUID newID = UUID.randomUUID();
        values.put(OgameDB.KEY_ID, newID.toString());

        database.insert(OgameDB.BUILDING_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OgameDB.BUILDING_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" + newID.toString() + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Building newBuilding = cursorToBuilding(cursor);
        cursor.close();
        return newBuilding;
    }

    public Boolean deleteBuilding(int buildingId) {
        return database.delete(OgameDB.BUILDING_TABLE_NAME, OgameDB.KEY_BUILDING_BUILDING_ID + '=' + buildingId, null) > 0;

    }

    public Boolean deleteAllBuildings() {
        return database.delete(OgameDB.BUILDING_TABLE_NAME, OgameDB.KEY_BUILDING_BUILDING_ID + "> -1", null) > 0;

    }

    private Building cursorToBuilding(Cursor cursor) {
        Building comment = new Building();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setBuildingId(cursor.getInt(1));
        comment.setLevel(cursor.getInt(2));
        comment.setAmountOfEffectByLevel(cursor.getInt(3));
        comment.setAmountOfEffectLevel0(cursor.getInt(4));
        comment.setIsBuilding(cursor.getInt(5)==1);
        comment.setEffect(cursor.getString(6));
        comment.setGasCostByLevel(cursor.getInt(7));
        comment.setGasCostLevel0(cursor.getInt(8));
        comment.setImageUrl(cursor.getString(9));
        comment.setMineralCostByLevel(cursor.getInt(10));
        comment.setMineralCostLevel0(cursor.getInt(11));
        comment.setName(cursor.getString(12));
        comment.setTimeToBuildByLevel(cursor.getInt(13));
        comment.setTimeToBuildLevel0(cursor.getInt(14));
        return comment;
    }

    public List<Building> getAllBuilding() {
        List<Building> listBuilding= new ArrayList<Building>();
        Cursor cursor = database.query(OgameDB.BUILDING_TABLE_NAME, allColumns, null,
                null, null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Building building = cursorToBuilding(cursor);
            listBuilding.add(building);
        }
        cursor.close();
        return listBuilding;
    }

    public Building getBuilding(String id) {
        Cursor cursor = database.query(OgameDB.BUILDING_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" + id + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Building building = cursorToBuilding(cursor);
        cursor.close();
        return building;
    }

    public Building getBuildingByName(String name) {
        Cursor cursor = database.query(OgameDB.BUILDING_TABLE_NAME, allColumns,
                OgameDB.KEY_BUILDING_name + " =\"" + name + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Building building = cursorToBuilding(cursor);
        cursor.close();
        return building;
    }

    public Building getBuildingByEffect(String effect) {
        Cursor cursor = database.query(OgameDB.BUILDING_TABLE_NAME, allColumns,
                OgameDB.KEY_BUILDING_effect + " =\"" + effect + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Building building = cursorToBuilding(cursor);
        cursor.close();
        return building;
    }
}
