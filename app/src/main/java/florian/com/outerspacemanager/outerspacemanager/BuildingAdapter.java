package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.os.Build;
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
import java.util.List;

import static java.lang.Math.round;
import static java.lang.String.format;

/**
 * Created by fcavasin on 27/02/2018.
 */

public class BuildingAdapter extends ArrayAdapter<Building> {

    private OnListViewChildrenClick mOnListViewChildrenClick;

    public void setOnEventListener(OnListViewChildrenClick listener) {
        mOnListViewChildrenClick = listener;
    }

    private User user;

    public BuildingAdapter(@NonNull Context context , @NonNull List<Building> buildings,@NonNull User user) {
        super(context, R.layout.row_construction_template, buildings);
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_construction_template,parent, false);
        }

        BuildingViewHolder viewHolder = (BuildingViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new BuildingViewHolder();
            viewHolder.imageViewConstructionID = (ImageView) convertView.findViewById(R.id.imageViewConstructionID);
            viewHolder.textView2ConstructionTitleID = (TextView) convertView.findViewById(R.id.textView2ConstructionTitleID);
            viewHolder.textViewProdTimeID = (TextView) convertView.findViewById(R.id.textViewProdTimeID);
            viewHolder.textViewProdNextLevelID = (TextView) convertView.findViewById(R.id.textViewProdNextLevelID);
            viewHolder.textViewRessource1ID = (TextView) convertView.findViewById(R.id.textViewRessource1ID);
            viewHolder.textViewRessource2ID = (TextView) convertView.findViewById(R.id.textViewRessource2ID);
            viewHolder.textViewConstructionLevelID = (TextView) convertView.findViewById(R.id.textViewConstructionLevelID);
            viewHolder.RelativeLayoutConstructButtonID = (RelativeLayout) convertView.findViewById(R.id.RelativeLayoutConstructButtonID);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        final Building building = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        String buildingName = Normalizer.normalize(building.getName().replace(" ","_"), Normalizer.Form.NFD);
        buildingName = buildingName.replaceAll("[^\\p{ASCII}]", "");
        viewHolder.imageViewConstructionID.setImageResource(getContext().getResources().getIdentifier(buildingName,"drawable",getContext().getPackageName()));
        viewHolder.textView2ConstructionTitleID.setText(building.getName());
        viewHolder.textViewProdTimeID.setText(round(building.getTimeToBuildLevel0() + building.getLevel() *  building.getTimeToBuildByLevel())+"s");
        viewHolder.textViewProdNextLevelID.setText((building.getLevel()+1)+" :");
        viewHolder.textViewRessource1ID.setText(format("%,d",Constant.costMineralBuilding(building)));
        viewHolder.textViewRessource2ID.setText(format("%,d",Constant.costGasBuilding(building)));
        viewHolder.textViewConstructionLevelID.setText(building.getLevel()+"");
        // TODO CLICK button
        viewHolder.RelativeLayoutConstructButtonID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnListViewChildrenClick.OnClick(building.getBuildingId());
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
    }
}