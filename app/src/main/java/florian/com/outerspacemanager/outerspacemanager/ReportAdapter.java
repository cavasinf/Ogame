package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.List;

import static java.lang.Math.round;

/**
 * Created by fcavasin on 27/02/2018.
 */

public class ReportAdapter extends ArrayAdapter<Ship> {

    private Handler handler = new Handler();
    private int delay = 100; //milliseconds
    private OnListViewChildrenClick mOnListViewChildrenClick;

    public void setOnEventListener(OnListViewChildrenClick listener) {
        mOnListViewChildrenClick = listener;
    }

    public ReportAdapter(@NonNull Context context, @NonNull List<Ship> fleet) {
        super(context, R.layout.row_report_ship_template, fleet);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_report_ship_template,parent, false);
        }

        ReportFleetViewHolder viewHolder = (ReportFleetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ReportFleetViewHolder();


            viewHolder.imageViewShipID = (ImageView) convertView.findViewById(R.id.imageViewShipID);
            viewHolder.textViewShipNameID = (TextView) convertView.findViewById(R.id.textViewShipNameID);
            viewHolder.textViewShipNumberID = (TextView) convertView.findViewById(R.id.textViewShipNumberID);

            convertView.setTag(viewHolder);
        }

        final Ship ship = getItem(position);

        String shipName = Normalizer.normalize(ship.getName().replace(" ", "_").replace("'", ""), Normalizer.Form.NFD);
        shipName = shipName.replaceAll("[^\\p{ASCII}]", "");
        viewHolder.imageViewShipID.setImageResource(getContext().getResources().getIdentifier(shipName+"_rounded_enabled", "drawable", getContext().getPackageName()));
        viewHolder.textViewShipNameID.setText(ship.getName());
        viewHolder.textViewShipNumberID.setText(ship.getAmount()+"");

        return convertView;
    }

    class ReportFleetViewHolder {

        public ImageView imageViewShipID;
        public TextView textViewShipNameID;
        public TextView textViewShipNumberID;
    }
}