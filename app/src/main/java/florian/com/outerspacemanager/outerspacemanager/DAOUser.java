package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fcavasin on 16/01/2018.
 */

public class DAOUser {
    // Database fields
    private SQLiteDatabase database;
    private OgameDB dbHelper;
    private String[] allColumns = {OgameDB.KEY_ID,OgameDB.KEY_USERNAME,OgameDB.KEY_PASSWORD,OgameDB.KEY_EMAIl};

    public DAOUser(Context context) {
        dbHelper = new OgameDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
