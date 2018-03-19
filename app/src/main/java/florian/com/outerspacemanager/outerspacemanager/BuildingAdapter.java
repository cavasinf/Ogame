package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.round;
import static java.lang.String.format;

/**
 * Created by fcavasin on 27/02/2018.
 */

public class BuildingAdapter extends ArrayAdapter<Building> {

    private OnListViewChildrenClick mOnListViewChildrenClick;

    private BuildingViewHolder viewHolder;

    private Handler handler = new Handler();
    private int delay = 100; //milliseconds
    private List<BuildingStatus> listBuildingStatus = new ArrayList<BuildingStatus>();
    private List<Boolean> isTimerLaunched = new ArrayList<Boolean>();

    public void setOnEventListener(OnListViewChildrenClick listener) {
        mOnListViewChildrenClick = listener;
    }

    private User user;
    private Date currentDate;

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

        viewHolder = (BuildingViewHolder) convertView.getTag();
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
        viewHolder.textViewRessource1ID.setText(format("%,d", Constant.costMineralBuilding(building)));
        viewHolder.textViewRessource2ID.setText(format("%,d", Constant.costGasBuilding(building)));
        viewHolder.textViewConstructionLevelID.setText(building.getLevel() + "");
        viewHolder.RelativeLayoutConstructButtonID.setEnabled(!building.isBuilding());
        viewHolder.imageViewConstructButtonBackgroundID.setEnabled(!building.isBuilding());


        if (building.isBuilding()) {
            for (BuildingStatus buildingStatus : listBuildingStatus) {
                if (Objects.equals(buildingStatus.getBuildingId(), building.getBuildingId().toString())) {
                    if (isTimerLaunched.size() > 0){
                        if (isTimerLaunched.size() > building.getBuildingId()) {
                            if (!isTimerLaunched.get(building.getBuildingId())) {
//                                startTimer(building.getBuildingTimeLeft(buildingStatus.getDateConstruction()), building, buildingStatus, viewHolder);
//                    viewHolder.textViewProdTimeID.setText(building.getBuildingTimeLeft(buildingStatus.getDateConstruction())+"s");
                            }
                        }
                    } else {
//                        startTimer(building.getBuildingTimeLeft(buildingStatus.getDateConstruction()), building, buildingStatus, viewHolder);
                    }
                    break;
                } else {
                    viewHolder.textViewProdTimeID.setText(round(building.getTimeToBuildLevel0() + (building.getLevel()) * building.getTimeToBuildByLevel()) + "s");
                }
            }

        } else {
            viewHolder.textViewProdTimeID.setText(round(building.getTimeToBuildLevel0() + (building.getLevel()) * building.getTimeToBuildByLevel()) + "s");
        }

        //TODO : Convert string to date + minus + display on label

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
    }

    private void startTimer(long time, final Building building, final BuildingStatus buildingStatus, final BuildingViewHolder viewHolderToChange) {
        isTimerLaunched.set(building.getBuildingId(), true);
        CountDownTimer counter = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilDone) {
                isTimerLaunched.set(building.getBuildingId(), true);
                viewHolderToChange.textViewProdTimeID.setText(building.getBuildingTimeLeft(buildingStatus.getDateConstruction()) + "s");
            }

            public void onFinish() {
                viewHolderToChange.textViewProdTimeID.setText("DONE!");
                isTimerLaunched.set(building.getBuildingId(), false);
            }
        }.start();
    }
}