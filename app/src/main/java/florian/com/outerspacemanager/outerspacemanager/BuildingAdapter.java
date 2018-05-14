package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import static java.lang.Math.round;
import static java.lang.String.format;

/**
 * Created by fcavasin on 27/02/2018.
 */

public class BuildingAdapter extends ArrayAdapter<Building> {

    private OnListViewChildrenClick mOnListViewChildrenClick;

    private Handler handler = new Handler();
    private int delay = 100; //milliseconds
    private List<BuildingStatus> listBuildingStatus = new ArrayList<BuildingStatus>();
    private ArrayMap<Integer, Boolean> isTimerLaunched = new ArrayMap<>();


    public void setOnEventListener(OnListViewChildrenClick listener) {
        mOnListViewChildrenClick = listener;
    }

    private User user;
    private Date currentDate;
    private Building buildingUsineDeNanite;
    private Search searchRobotique;

    public BuildingAdapter(@NonNull Context context, @NonNull List<Building> buildings, @NonNull User user, Date currentDate, List<BuildingStatus> listBuildingStatus) {
        super(context, R.layout.row_construction_template, buildings);
        this.user = user;
        this.currentDate = currentDate;
        this.listBuildingStatus = listBuildingStatus;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_construction_template, parent, false);
        }

        DAOBuilding daoBuilding = new DAOBuilding(getContext());
        Environment.getExternalStorageDirectory();
        daoBuilding.open();
        buildingUsineDeNanite = daoBuilding.getBuildingByEffect("speed_building");

        DAOSearch daoSearch = new DAOSearch(getContext());
        Environment.getExternalStorageDirectory();
        daoSearch.open();
        searchRobotique = daoSearch.getSearchByEffect("speed_building");

        BuildingViewHolder viewHolder = (BuildingViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new BuildingViewHolder();
            viewHolder.imageViewConstructionID = (ImageView) convertView.findViewById(R.id.imageViewConstructionID);
            viewHolder.textView2ConstructionTitleID = (TextView) convertView.findViewById(R.id.textView2ConstructionTitleID);
            viewHolder.textViewProdTimeID = (TextView) convertView.findViewById(R.id.textViewProdTimeID);
            viewHolder.textViewProdNextLevelID = (TextView) convertView.findViewById(R.id.textViewProdNextLevelID);
            viewHolder.textViewRessource1ID = (TextView) convertView.findViewById(R.id.textViewRessource1ID);
            viewHolder.textViewRessource2ID = (TextView) convertView.findViewById(R.id.textViewRessource2ID);
            viewHolder.textViewConstructionLevelID = (TextView) convertView.findViewById(R.id.textViewConstructionLevelID);
            viewHolder.RelativeLayoutConstructButtonID = (RelativeLayout) convertView.findViewById(R.id.RelativeLayoutConstructButtonID);
            viewHolder.imageViewConstructButtonBackgroundID = (ImageView) convertView.findViewById(R.id.imageViewConstructButtonBackgroundID);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        final Building building = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        String buildingName = Normalizer.normalize(building.getName().replace(" ", "_"), Normalizer.Form.NFD);
        buildingName = buildingName.replaceAll("[^\\p{ASCII}]", "");
        viewHolder.imageViewConstructionID.setImageResource(getContext().getResources().getIdentifier(buildingName, "drawable", getContext().getPackageName()));
        viewHolder.textView2ConstructionTitleID.setText(building.getName());

        viewHolder.textViewProdNextLevelID.setText((building.getLevel() + 1) + " :");
        viewHolder.textViewRessource1ID.setText(format("%,d", building.getCostMineral(building)));
        viewHolder.textViewRessource2ID.setText(format("%,d", building.getCostGas(building)));
        viewHolder.textViewConstructionLevelID.setText(building.getLevel() + "");
        viewHolder.RelativeLayoutConstructButtonID.setEnabled(!building.isBuilding());
        viewHolder.imageViewConstructButtonBackgroundID.setEnabled(!building.isBuilding());
        final BuildingViewHolder finalViewHolder = viewHolder;


        if (finalViewHolder.timer != null) {
            finalViewHolder.timer.cancel();
        }
        if (building.isBuilding()) {
            for (final BuildingStatus buildingStatus : listBuildingStatus) {
                if (Objects.equals(buildingStatus.getBuildingId(), building.getBuildingId().toString())) {
                    if (isTimerLaunched != null) {
                        if (!isTimerLaunched.containsKey(building.getBuildingId())) {

                            startTimer(building, buildingStatus, finalViewHolder);
                        }
                    } else {
                        startTimer(building, buildingStatus, finalViewHolder);
                    }
                    break;
                }
            }

        } else {
//            viewHolder.textViewProdTimeID.setText(round(building.getTimeToBuild(false) - amountSecondSpeedEffect()) + "s ");
            viewHolder.textViewProdTimeID.setText(Constant.getHumanDateFromTimeSecond(building.getTimeToBuild(false) - amountSecondSpeedEffect()));
        }

        viewHolder.RelativeLayoutConstructButtonID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                mOnListViewChildrenClick.OnClick(building.getBuildingId(), v);
                v.setSelected(true);

                handler.postDelayed(new Runnable() {
                    public void run() {
                        v.setSelected(false);
                    }
                }, delay);
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        Building building = getItem(position);
        assert building != null;
        return building.getBuildingId();
    }

    class BuildingViewHolder {
        public ImageView imageViewConstructionID;
        public TextView textView2ConstructionTitleID;
        public TextView textViewProdTimeID;
        public TextView textViewProdNextLevelID;
        public TextView textViewRessource1ID;
        public TextView textViewRessource2ID;
        public TextView textViewConstructionLevelID;
        public RelativeLayout RelativeLayoutConstructButtonID;
        public ImageView imageViewConstructButtonBackgroundID;
        public CountDownTimer timer;
    }

    private void startTimer(final Building building, final BuildingStatus buildingStatus, final BuildingViewHolder viewHolderToChange) {
        final BuildingViewHolder storedViewHolderToChange = viewHolderToChange;
        // Define time for counter : (building time without effect - (building effect + search effect) * 1000)
        CountDownTimer counter = new CountDownTimer((building.getBuildingTimeLeftWithoutEffects(buildingStatus.getDateConstruction()) - amountSecondSpeedEffect()) * 1000, 1000) {
            public void onTick(long millisUntilDone) {
                isTimerLaunched.put(building.getBuildingId(), true);
//                storedViewHolderToChange.textViewProdTimeID.setText(building.getBuildingTimeLeftWithoutEffects(buildingStatus.getDateConstruction()) - amountSecondSpeedEffect() + "s");
                storedViewHolderToChange.textViewProdTimeID.setText(Constant.getHumanDateFromTimeSecond(building.getBuildingTimeLeftWithoutEffects(buildingStatus.getDateConstruction()) - amountSecondSpeedEffect()));
            }

            public void onFinish() {
                storedViewHolderToChange.textViewProdTimeID.setText("DONE");
                isTimerLaunched.put(building.getBuildingId(), false);
            }
        }.start();
        storedViewHolderToChange.timer = counter;
    }

    private int amountSecondSpeedEffect() {
        return buildingUsineDeNanite.getLevel() * buildingUsineDeNanite.getAmountOfEffectByLevel() + searchRobotique.getLevel() * searchRobotique.getAmountOfEffectByLevel();
    }
}