package florian.com.outerspacemanager.outerspacemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DAOSearchStatus {

    // Database fields
    private SQLiteDatabase database;
    private OgameDB dbHelper;
    private String[] allColumns = {OgameDB.KEY_ID,OgameDB.KEY_SEARCH_ID,OgameDB.KEY_SEARCHING,OgameDB.KEY_DATE_SEARCHING};

    public DAOSearchStatus(Context context) {
        dbHelper = new OgameDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    
    public void close() {
        dbHelper.close();
    }

    public SearchStatus createSearchStatus(int searchId,String searchState, String dateConstruction) {

        ContentValues values = new ContentValues();
        String sSearchId = String.valueOf(searchId);
        values.put(OgameDB.KEY_SEARCH_ID, sSearchId);
        values.put(OgameDB.KEY_SEARCHING, searchState);
        values.put(OgameDB.KEY_DATE_SEARCHING, dateConstruction);
        UUID newID = UUID.randomUUID();
        values.put(OgameDB.KEY_ID, newID.toString());

        database.insert(OgameDB.SEARCH_STATE_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OgameDB.SEARCH_STATE_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" +newID.toString()+"\"",
                null,null, null, null);
        cursor.moveToFirst();
        SearchStatus newSearchStatus = cursorToSearchStatus(cursor);
        cursor.close();
        return newSearchStatus;
    }

    public Boolean deleteSearchState(int searchId){
        return database.delete(OgameDB.SEARCH_STATE_TABLE_NAME,OgameDB.KEY_SEARCH_ID +'='+searchId,null) > 0;

    }

    private SearchStatus cursorToSearchStatus(Cursor cursor) {
        SearchStatus comment = new SearchStatus();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setSearchId(cursor.getString(1));
        comment.setSearching(cursor.getString(2));
        comment.setDateSearching(cursor.getString(3));
        return comment;
    }

    public List<SearchStatus> getAllSearchStatus() {
        List<SearchStatus> listSearchStatus = new ArrayList<SearchStatus>();
        Cursor cursor = database.query(OgameDB.SEARCH_STATE_TABLE_NAME, allColumns,null ,
                null,null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            SearchStatus searchStatus = cursorToSearchStatus(cursor);
            listSearchStatus.add(searchStatus);
        }
        cursor.close();
        return listSearchStatus;
    }

    public SearchStatus getSearchStatus(String id) {
        Cursor cursor = database.query(OgameDB.SEARCH_STATE_TABLE_NAME, allColumns,
                OgameDB.KEY_ID + " =\"" +id+"\"",
                null,null, null, null);
        cursor.moveToFirst();
        SearchStatus searchStatus = cursorToSearchStatus(cursor);
        cursor.close();
        return searchStatus;
    }


}
