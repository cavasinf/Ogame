package florian.com.outerspacemanager.outerspacemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DAOShip {

    // Database fields
    private SQLiteDatabase database;
    private OgameDB dbHelper;
    private String[] allColumns = {OgameDB.KEY_ID,
            OgameDB.KEY_SHIP_SHIP_ID,
            OgameDB.KEY_SHIP_gasCost,
            OgameDB.KEY_SHIP_life,
            OgameDB.KEY_SHIP_maxAttack,
            OgameDB.KEY_SHIP_minAttack,
            OgameDB.KEY_SHIP_mineralCost,
            OgameDB.KEY_SHIP_name,
            OgameDB.KEY_SHIP_shield,
            OgameDB.KEY_SHIP_spatioportLevelNeeded,
            OgameDB.KEY_SHIP_speed,
            OgameDB.KEY_SHIP_timeToBuild};

    public DAOShip(Context context) {
        dbHelper = new OgameDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Ship createShip(Integer capacity, Integer gasCost, Integer life, Integer maxAttack, Integer minAttack, Integer mineralCost, String name, Integer shield, Integer shipId, Integer spatioportLevelNeeded, Integer speed, Integer timeToBuild) {

        ContentValues values = new ContentValues();
        values.put(OgameDB.KEY_SHIP_SHIP_ID, shipId);
        values.put(OgameDB.KEY_SHIP_gasCost, gasCost);
        values.put(OgameDB.KEY_SHIP_life, life);
        values.put(OgameDB.KEY_SHIP_maxAttack, maxAttack);
        values.put(OgameDB.KEY_SHIP_minAttack, minAttack);
        values.put(OgameDB.KEY_SHIP_mineralCost, mineralCost);
        values.put(OgameDB.KEY_SHIP_name, name);
        values.put(OgameDB.KEY_SHIP_shield, shield);
        values.put(OgameDB.KEY_SHIP_spatioportLevelNeeded, spatioportLevelNeeded);
        values.put(OgameDB.KEY_SHIP_speed, speed);
        values.put(OgameDB.KEY_SHIP_timeToBuild, timeToBuild);

        UUID newID = UUID.randomUUID();
        values.put(OgameDB.KEY_ID, newID.toString());

        database.insert(OgameDB.SHIP_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OgameDB.SHIP_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" + newID.toString() + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Ship newShip = cursorToShip(cursor);
        cursor.close();
        return newShip;
    }

    public Boolean deleteShip(int shipId) {
        return database.delete(OgameDB.SHIP_TABLE_NAME, OgameDB.KEY_SHIP_SHIP_ID + '=' + shipId, null) > 0;

    }

    public Boolean deleteAllShips() {
        return database.delete(OgameDB.SHIP_TABLE_NAME, OgameDB.KEY_SHIP_SHIP_ID + "> -1", null) > 0;

    }

    private Ship cursorToShip(Cursor cursor) {
        Ship comment = new Ship();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setShipId(cursor.getInt(1));
        comment.setGasCost(cursor.getInt(2));
        comment.setLife(cursor.getInt(3));
        comment.setMaxAttack(cursor.getInt(4));
        comment.setMinAttack(cursor.getInt(5));
        comment.setMineralCost(cursor.getInt(6));
        comment.setName(cursor.getString(7));
        comment.setShield(cursor.getInt(8));
        comment.setSpatioportLevelNeeded(cursor.getInt(9));
        comment.setSpeed(cursor.getInt(10));
        comment.setTimeToBuild(cursor.getInt(11));

        return comment;
    }

    public int getNumberOfRows() {
        int count = 0;
        List<Ship> listShip = getAllShip();
        for (Ship ship : listShip
                ) {
            count++;
        }
        return count;
    }

    public List<Ship> getAllShip() {
        List<Ship> listShip= new ArrayList<Ship>();
        Cursor cursor = database.query(OgameDB.SHIP_TABLE_NAME, allColumns, null,
                null, null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Ship building = cursorToShip(cursor);
            listShip.add(building);
        }
        cursor.close();
        return listShip;
    }

    public Ship getShip(String id) {
        Cursor cursor = database.query(OgameDB.SHIP_TABLE_NAME, allColumns,
                OgameDB.KEY_SHIP_SHIP_ID + " =\"" + id + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Ship building = cursorToShip(cursor);
        cursor.close();
        return building;
    }

    public Ship getShipByName(String name) {
        Cursor cursor = database.query(OgameDB.SHIP_TABLE_NAME, allColumns,
                OgameDB.KEY_SHIP_name + " =\"" + name + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Ship building = cursorToShip(cursor);
        cursor.close();
        return building;
    }

}
