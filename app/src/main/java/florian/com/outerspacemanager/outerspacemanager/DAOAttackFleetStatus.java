package florian.com.outerspacemanager.outerspacemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DAOAttackFleetStatus {

    // Database fields
    private SQLiteDatabase database;
    private OgameDB dbHelper;
    private String[] allColumns = {OgameDB.KEY_ID,OgameDB.KEY_ATTACK_STATE_PLAYER_TO_ATTACK,OgameDB.KEY_ATTACK_STATE_STATE_DATE_RETURN,OgameDB.KEY_ATTACK_STATE_FLEET};


    public DAOAttackFleetStatus(Context context) {
        dbHelper = new OgameDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    
    public void close() {
        dbHelper.close();
    }

    public FleetAttackStatus createFleetAttackStatus(String playerToAttack,String dateAttackReturn, String fleet) {

        ContentValues values = new ContentValues();
        values.put(OgameDB.KEY_ATTACK_STATE_PLAYER_TO_ATTACK, playerToAttack);
        values.put(OgameDB.KEY_ATTACK_STATE_STATE_DATE_RETURN, dateAttackReturn);
        values.put(OgameDB.KEY_ATTACK_STATE_FLEET, fleet);
        UUID newID = UUID.randomUUID();
        values.put(OgameDB.KEY_ID, newID.toString());

        database.insert(OgameDB.ATTACK_STATE_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OgameDB.ATTACK_STATE_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" +newID.toString()+"\"",
                null,null, null, null);
        cursor.moveToFirst();
        FleetAttackStatus newFleetAttackStatus = cursorToFleetAttackStatus(cursor);
        cursor.close();
        return newFleetAttackStatus;
    }

    public Boolean deleteFleetAttackStatus(UUID attackStateUUID){
        return database.delete(OgameDB.ATTACK_STATE_TABLE_NAME,OgameDB.KEY_ID +'='+attackStateUUID,null) > 0;
    }

    private FleetAttackStatus cursorToFleetAttackStatus(Cursor cursor) {
        FleetAttackStatus comment = new FleetAttackStatus();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setPlayerToAttack(cursor.getString(1));
        comment.setDateAttackReturn(cursor.getString(2));
        comment.setFleet(cursor.getString(3));
        return comment;
    }

    public List<FleetAttackStatus> getAllFleetAttackStatus() {
        List<FleetAttackStatus> listFleetAttackStatus = new ArrayList<FleetAttackStatus>();
        Cursor cursor = database.query(OgameDB.ATTACK_STATE_TABLE_NAME, allColumns,null ,
                null,null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            FleetAttackStatus fleetAttackStatus = cursorToFleetAttackStatus(cursor);
            listFleetAttackStatus.add(fleetAttackStatus);
        }
        cursor.close();
        return listFleetAttackStatus;
    }

    public FleetAttackStatus getFleetAttackStatus(UUID uuid) {
        Cursor cursor = database.query(OgameDB.ATTACK_STATE_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" +uuid+"\"",
                null,null, null, null);
        cursor.moveToFirst();
        FleetAttackStatus fleetAttackStatus = cursorToFleetAttackStatus(cursor);
        cursor.close();
        return fleetAttackStatus;
    }


}
