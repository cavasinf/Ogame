package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private String playerToAttack;

    public void setOnEventListener(OnGridViewSpaceShipChildrenMaxClick listener) {
        mOnGridViewSpaceShipChildrenMaxClick = listener;
    }

    private User user;

    public SpaceShipAdapter(@NonNull Context context, @NonNull List<Ship> ships, @NonNull User user,String playerToAttack) {
        super(context, R.layout.grid_fleet_template, ships);
        this.user = user;
        this.playerToAttack = playerToAttack;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_fleet_template, parent, false);
        }

        ShipViewHolder viewHolder = (ShipViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ShipViewHolder();
            viewHolder.imageViewFleetID = (ImageView) convertView.findViewById(R.id.imageViewFleetID);
            viewHolder.imageViewOverviewID = (ImageView) convertView.findViewById(R.id.imageViewOverviewID);
            viewHolder.textViewNumberFleetID = (TextView) convertView.findViewById(R.id.textViewNumberFleetID);
            viewHolder.editTextCountFleetID = (EditText) convertView.findViewById(R.id.editTextCountFleetID);
            viewHolder.imageViewSetMaxID = (ImageView) convertView.findViewById(R.id.imageViewSetMaxID);
            viewHolder.imageViewSetZeroID = (ImageView) convertView.findViewById(R.id.imageViewSetZeroID);
            viewHolder.LinearLayoutSetNumberID = (LinearLayout) convertView.findViewById(R.id.LinearLayoutSetNumberID);

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
        if (ship.getAmount() == 0 )
        {
            viewHolder.imageViewOverviewID.setImageResource(getContext().getResources().getIdentifier("ship_overlay_info_blocked", "drawable", getContext().getPackageName()));
            viewHolder.LinearLayoutSetNumberID.setVisibility(View.GONE);
        }

        if (playerToAttack == "")
            viewHolder.LinearLayoutSetNumberID.setVisibility(View.GONE);

        final ShipViewHolder finalViewHolder = viewHolder;
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

        final ShipViewHolder finalViewHolder1 = viewHolder;
        viewHolder.editTextCountFleetID.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (finalViewHolder1.editTextCountFleetID.getText().toString().equals(""))
                    finalViewHolder1.editTextCountFleetID.setText("0");
                Integer numberSet = Integer.parseInt(finalViewHolder1.editTextCountFleetID.getText().toString());
                SpaceShipActivity.numberOfShipForAttack.put(ship.getShipId(),numberSet);
                if (numberSet > 0)
                    finalViewHolder1.imageViewOverviewID.setImageResource(getContext().getResources().getIdentifier("ship_overlay_info_selected", "drawable", getContext().getPackageName()));
                else
                    finalViewHolder1.imageViewOverviewID.setImageResource(getContext().getResources().getIdentifier("ship_overlay_info", "drawable", getContext().getPackageName()));
            }
        });

        return convertView;
    }

    class ShipViewHolder {
        public ImageView imageViewFleetID;
        public ImageView imageViewOverviewID;
        public TextView textViewNumberFleetID;
        public EditText editTextCountFleetID;
        public ImageView imageViewSetMaxID;
        public ImageView imageViewSetZeroID;
        public LinearLayout LinearLayoutSetNumberID;
    }
}