package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.widget.Toast;

import static java.lang.String.format;

/**
 * Created by fcavasin on 23/01/2018.
 */

public class Constant {

    public static final String PREFS_USER = "UserPrefs";
    public static final String EXTRA_USER = "UserExtra";

    public static void ToastErrorConnection(Context ApplicationContext) {
        Toast toast = Toast.makeText(ApplicationContext, "Erreur lors de la connexion ! \n Verifier votre connexion internet", Toast.LENGTH_LONG);
        toast.show();
    }

    public static String FormatToNumber(String NumberToFormat) {
        return format("%,d", NumberToFormat);
    }
}
