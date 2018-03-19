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
//    public static final String BASE_URL = "https://outer-space-manager.herokuapp.com/api/v1/";
    public static final String BASE_URL = "https://outer-space-manager-staging.herokuapp.com/api/v1/";

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

    public static int dspToPixel(Context applicationContext ,int dspToFormat) {
        final float scale = applicationContext.getResources().getDisplayMetrics().density;
        int pixels = (int) (dspToFormat * scale + 0.5f);
        return pixels;
    }



    public static int costMineralSearch (Search search){
        double cost;
        if (search.getLevel() == 0)
            cost = search.getMineralCostLevel0();
        else {
            cost = search.getMineralCostLevel0() + search.getMineralCostByLevel()*search.getLevel();
        }
        return (int) Math.round(cost);
    }

    public static int costGasSearch (Search search){
        double cost;
        if (search.getLevel() == 0)
            cost = search.getGasCostLevel0();
        else {
            cost = search.getGasCostLevel0() + search.getGasCostByLevel()*search.getLevel();
        }
        return (int) Math.round(cost);
    }

    public static void displayToast(Context ApplicationContext,String messageToDisplay){
        Toast toast = Toast.makeText(ApplicationContext, messageToDisplay, Toast.LENGTH_LONG);
        toast.show();
    }

}
