package florian.com.outerspacemanager.outerspacemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DAOShipStatus {

    // Database fields
    private SQLiteDatabase database;
    private OgameDB dbHelper;
    private String[] allColumns = {OgameDB.KEY_ID,OgameDB.KEY_SHIP_STATE_SHIP_ID,OgameDB.KEY_SHIP_STATE_NUMBER,OgameDB.KEY_SHIP_STATE_DATE_CONSTRUCTION_LAUNCH,OgameDB.KEY_SHIP_STATE_DATE_CONSTRUCTION_END};

    public DAOShipStatus(Context context) {
        dbHelper = new OgameDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    
    public void close() {
        dbHelper.close();
    }

    public ShipStatus createShipStatus(int shipId,String number, String dateConstructionLaunch, String dateConstructionEnd) {

        ContentValues values = new ContentValues();
        String sShipId = String.valueOf(shipId);
        values.put(OgameDB.KEY_SHIP_STATE_NUMBER, number);
        values.put(OgameDB.KEY_SHIP_STATE_DATE_CONSTRUCTION_LAUNCH, dateConstructionLaunch);
        values.put(OgameDB.KEY_SHIP_STATE_DATE_CONSTRUCTION_END, dateConstructionEnd);
        UUID newID = UUID.randomUUID();
        values.put(OgameDB.KEY_ID, newID.toString());

        database.insert(OgameDB.SHIP_STATE_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OgameDB.SHIP_STATE_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" +newID.toString()+"\"",
                null,null, null, null);
        cursor.moveToFirst();
        ShipStatus newShipStatus = cursorToShipStatus(cursor);
        cursor.close();
        return newShipStatus;
    }

    public Boolean deleteBuildingState(int shipId){
        return database.delete(OgameDB.SHIP_STATE_TABLE_NAME,OgameDB.KEY_SHIP_STATE_SHIP_ID +'='+shipId,null) > 0;

    }

    private ShipStatus cursorToShipStatus(Cursor cursor) {
        ShipStatus comment = new ShipStatus();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setShipId(cursor.getString(1));
        comment.setDateConstructionLaunch(cursor.getString(2));
        comment.setDateConstructionEnd(cursor.getString(3));
        return comment;
    }

    public List<ShipStatus> getAllShipStatus() {
        List<ShipStatus> listShipStatus = new ArrayList<ShipStatus>();
        Cursor cursor = database.query(OgameDB.SHIP_STATE_TABLE_NAME, allColumns,null ,
                null,null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            ShipStatus ShipStatus = cursorToShipStatus(cursor);
            listShipStatus.add(ShipStatus);
        }
        cursor.close();
        return listShipStatus;
    }

    public ShipStatus getShipStatus(String id) {
        Cursor cursor = database.query(OgameDB.SHIP_STATE_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" +id+"\"",
                null,null, null, null);
        cursor.moveToFirst();
        ShipStatus ShipStatus = cursorToShipStatus(cursor);
        cursor.close();
        return ShipStatus;
    }


}
