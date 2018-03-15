package florian.com.outerspacemanager.outerspacemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.ViewDebug;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DAOBuildingStatus {

    // Database fields
    private SQLiteDatabase database;
    private OgameDB dbHelper;
    private String[] allColumns = {OgameDB.KEY_ID,OgameDB.KEY_BUILDING_ID,OgameDB.KEY_BUILDING,OgameDB.KEY_DATE_CONSTRUCTION};

    public DAOBuildingStatus(Context context) {
        dbHelper = new OgameDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    
    public void close() {
        dbHelper.close();
    }

    public BuildingStatus createBuildingStatus(int buildingId,String buildingState, String dateConstruction) {

        ContentValues values = new ContentValues();
        String sBuildingId = String.valueOf(buildingId);
        values.put(OgameDB.KEY_BUILDING_ID, sBuildingId);
        values.put(OgameDB.KEY_BUILDING, buildingState);
        values.put(OgameDB.KEY_DATE_CONSTRUCTION, dateConstruction);
        UUID newID = UUID.randomUUID();
        values.put(OgameDB.KEY_ID, newID.toString());

        database.insert(OgameDB.BUILDING_STATE_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OgameDB.BUILDING_STATE_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" +newID.toString()+"\"",
                null,null, null, null);
        cursor.moveToFirst();
        BuildingStatus newBuildingStatus = cursorToBuildingStatus(cursor);
        cursor.close();
        return newBuildingStatus;
    }

    public Boolean deleteBuildingState(int buildingId){
        return database.delete(OgameDB.BUILDING_STATE_TABLE_NAME,OgameDB.KEY_BUILDING_ID +'='+buildingId,null) > 0;

    }

    private BuildingStatus cursorToBuildingStatus(Cursor cursor) {
        BuildingStatus comment = new BuildingStatus();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setBuildingId(cursor.getString(1));
        comment.setBuilding(cursor.getString(2));
        comment.setDateConstruction(cursor.getString(3));
        return comment;
    }

    public List<BuildingStatus> getAllBuildingStatus() {
        List<BuildingStatus> listBuildingStatus = new ArrayList<BuildingStatus>();
        Cursor cursor = database.query(OgameDB.BUILDING_STATE_TABLE_NAME, allColumns,null ,
                null,null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            BuildingStatus buildingStatus = cursorToBuildingStatus(cursor);
            listBuildingStatus.add(buildingStatus);
        }
        cursor.close();
        return listBuildingStatus;
    }

    public BuildingStatus getBuildingStatus(String id) {
        Cursor cursor = database.query(OgameDB.BUILDING_STATE_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" +id+"\"",
                null,null, null, null);
        cursor.moveToFirst();
        BuildingStatus buildingStatus = cursorToBuildingStatus(cursor);
        cursor.close();
        return buildingStatus;
    }


}
