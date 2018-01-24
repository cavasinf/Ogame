package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;

/**
 * Created by fcavasin on 23/01/2018.
 */

public class Constant {

    public static final String PREFS_USER = "UserPrefs";
    public static final String EXTRA_USER = "UserExtra";
    public static final String BASE_URL = "https://outer-space-manager.herokuapp.com/api/v1/";

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static void ToastErrorConnection(Context ApplicationContext) {
        Toast toast = Toast.makeText(ApplicationContext, "Erreur lors de la connexion ! \n Verifier votre connexion internet", Toast.LENGTH_LONG);
        toast.show();
    }

    public static String FormatToNumber(String NumberToFormat) {
        return format("%,d", NumberToFormat);
    }


}
