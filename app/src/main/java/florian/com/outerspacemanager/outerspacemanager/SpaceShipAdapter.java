package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SpaceShipAdapter extends ArrayAdapter<Ship> {

    private OnGridViewSpaceShipChildrenMaxClick mOnGridViewSpaceShipChildrenMaxClick;
    private Handler handler = new Handler();
    private int delay = 100; //milliseconds

    public void setOnEventListener(OnGridViewSpaceShipChildrenMaxClick listener) {
        mOnGridViewSpaceShipChildrenMaxClick = listener;
    }

    private User user;

    public SpaceShipAdapter(@NonNull Context context, @NonNull List<Ship> ships, @NonNull User user) {
        super(context, R.layout.grid_fleet_template, ships);
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_fleet_template, parent, false);
        }

        BuildingViewHolder viewHolder = (BuildingViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new BuildingViewHolder();
            viewHolder.imageViewFleetID = (ImageView) convertView.findViewById(R.id.imageViewFleetID);
            viewHolder.imageViewOverviewID = (ImageView) convertView.findViewById(R.id.imageViewOverviewID);
            viewHolder.textViewNumberFleetID = (TextView) convertView.findViewById(R.id.textViewNumberFleetID);
            viewHolder.editTextCountFleetID = (EditText) convertView.findViewById(R.id.editTextCountFleetID);
            viewHolder.imageViewSetMaxID = (ImageView) convertView.findViewById(R.id.imageViewSetMaxID);
            viewHolder.imageViewSetZeroID = (ImageView) convertView.findViewById(R.id.imageViewSetZeroID);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        final Ship ship = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        String shipName = Normalizer.normalize(ship.getName().replace(" ", "_").replace("'", ""), Normalizer.Form.NFD);
        shipName = shipName.replaceAll("[^\\p{ASCII}]", "")+ "_rounded";
        shipName += "_enabled";
        viewHolder.imageViewFleetID.setImageResource(getContext().getResources().getIdentifier(shipName, "drawable", getContext().getPackageName()));
        viewHolder.textViewNumberFleetID.setText(Math.round(ship.getAmount())+"");

        final BuildingViewHolder finalViewHolder = viewHolder;
        viewHolder.imageViewSetMaxID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOnGridViewSpaceShipChildrenMaxClick.OnClick(ship.getAmount(), v);
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
        public ImageView imageViewFleetID;
        public ImageView imageViewOverviewID;
        public TextView textViewNumberFleetID;
        public EditText editTextCountFleetID;
        public ImageView imageViewSetMaxID;
        public ImageView imageViewSetZeroID;
    }
}