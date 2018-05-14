package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

    public static String userToken = "";

    public static User User;

    public static final String CST_PLANETS_TYPE[] = {"dry","normal","water"};

    public static SimpleDateFormat formater = new SimpleDateFormat("HH'h 'mm'm 'ss's'", Locale.ENGLISH);

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

    public static String definePlanetByUserName(String userName) {

        String planetImageName;
        int intPlanetType;
        int intPlanetNumber;
        int sizeName = userName.length();

        String repl = SHA256(userName);
        repl = repl.replaceAll("\\D", "");
        intPlanetType = Character.getNumericValue(repl.charAt(0));
        if (intPlanetType < 4)
            intPlanetType = 0;
        else {
            while (intPlanetType > 2) {
                intPlanetType = intPlanetType / 3;
            }
        }
        planetImageName = "planet_"+Constant.CST_PLANETS_TYPE[intPlanetType]+"_";
//        intPlanetNumber = sizeName;
//        while (intPlanetNumber > 10) {
//            intPlanetNumber = intPlanetNumber / 10;
//        }
        intPlanetNumber = Character.getNumericValue(repl.charAt(0)) + 1;
        planetImageName += intPlanetNumber+"_3";

        return planetImageName;
    }

    public static String SHA256(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getHumanDateFromTimeSecond(int timeInSecond){
        formater.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formater.format(new Date((timeInSecond)*1000L));
    }

}
