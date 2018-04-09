package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.List;

import static java.lang.Math.round;
import static java.lang.String.format;

/**
 * Created by fcavasin on 27/02/2018.
 */

public class ShipAdapter extends ArrayAdapter<Ship> {

    private OnListViewShipChildrenClick mOnListViewShipChildrenClick;
    private Handler handler = new Handler();
    private int delay = 100; //milliseconds

    public void setOnEventListener(OnListViewShipChildrenClick listener) {
        mOnListViewShipChildrenClick = listener;
    }

    private User user;

    public ShipAdapter(@NonNull Context context, @NonNull List<Ship> ships, @NonNull User user) {
        super(context, R.layout.row_ship_template, ships);
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_ship_template, parent, false);
        }

        BuildingViewHolder viewHolder = (BuildingViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new BuildingViewHolder();
            viewHolder.imageViewConstructionID = (ImageView) convertView.findViewById(R.id.imageViewConstructionID);
            viewHolder.textView2ConstructionTitleID = (TextView) convertView.findViewById(R.id.textView2ConstructionTitleID);
            viewHolder.textViewProdTimeID = (TextView) convertView.findViewById(R.id.textViewProdTimeID);
            viewHolder.textViewRessource1ID = (TextView) convertView.findViewById(R.id.textViewRessource1ID);
            viewHolder.textViewRessource2ID = (TextView) convertView.findViewById(R.id.textViewRessource2ID);
            viewHolder.textViewConstructionLevelID = (TextView) convertView.findViewById(R.id.textViewConstructionLevelID);
            viewHolder.RelativeLayoutConstructButtonID = (RelativeLayout) convertView.findViewById(R.id.RelativeLayoutConstructButtonID);
            viewHolder.editTextProdCountID = (EditText) convertView.findViewById(R.id.editTextProdCountID);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        final Ship ship = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        String shipName = Normalizer.normalize(ship.getName().replace(" ", "_").replace("'", ""), Normalizer.Form.NFD);
        shipName = shipName.replaceAll("[^\\p{ASCII}]", "");
        viewHolder.imageViewConstructionID.setImageResource(getContext().getResources().getIdentifier(shipName, "drawable", getContext().getPackageName()));
        viewHolder.textView2ConstructionTitleID.setText(ship.getName());
        viewHolder.textViewProdTimeID.setText(round(ship.getTimeToBuild()) + "s");
        viewHolder.textViewRessource1ID.setText(format("%,d", ship.getMineralCost()));
        viewHolder.textViewRessource2ID.setText(format("%,d", ship.getGasCost()));
        viewHolder.editTextProdCountID.setText("");

        final BuildingViewHolder finalViewHolder = viewHolder;
        viewHolder.RelativeLayoutConstructButtonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOnListViewShipChildrenClick.OnClick(ship.getShipId(), finalViewHolder.editTextProdCountID.getText().toString(), v);
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

    class BuildingViewHolder {
        public ImageView imageViewConstructionID;
        public TextView textView2ConstructionTitleID;
        public TextView textViewProdTimeID;
        public TextView textViewRessource1ID;
        public TextView textViewRessource2ID;
        public TextView textViewConstructionLevelID;
        public RelativeLayout RelativeLayoutConstructButtonID;
        public EditText editTextProdCountID;
    }
}