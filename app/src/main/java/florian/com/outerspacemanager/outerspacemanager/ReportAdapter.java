package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static java.lang.Math.round;
import static java.lang.String.format;

/**
 * Created by fcavasin on 27/02/2018.
 */

public class ReportAdapter extends ArrayAdapter<Report> {

    private AdapterView.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private User user;

    public ReportAdapter(@NonNull Context context , @NonNull List<Report> reports, @NonNull User user) {
        super(context, R.layout.row_report_template, reports);
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_report_template,parent, false);
        }

        ReportViewHolder viewHolder = (ReportViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ReportViewHolder();


            viewHolder.textViewAttackerNameID = (TextView) convertView.findViewById(R.id.textViewAttackerNameID);
            viewHolder.textViewRessourcesTotalID = (TextView) convertView.findViewById(R.id.textViewRessourcesTotalID);
            viewHolder.textViewTimeID = (TextView) convertView.findViewById(R.id.textViewTimeID);
            viewHolder.textViewDefenderNameID = (TextView) convertView.findViewById(R.id.textViewDefenderNameID);

            convertView.setTag(viewHolder);
        }

        Report report = getItem(position);

        viewHolder.textViewAttackerNameID.setText(report.getFrom());
        if (report.getFleetAfterBattle().getSurvivingShips() == 0)
            viewHolder.textViewAttackerNameID.setTextColor(Color.RED);
        else
            viewHolder.textViewAttackerNameID.setTextColor(Color.GREEN);
        viewHolder.textViewRessourcesTotalID.setText(round(report.getGasWon()+report.getMineralsWon())+"");
        viewHolder.textViewTimeID.setText(Constant.convertTimeInSecondsToReadableDate(report.getDate()));
        viewHolder.textViewDefenderNameID.setText(report.getTo());
        if (report.getDefenderFleetAfterBattle().getSurvivingShips() == 0)
            viewHolder.textViewAttackerNameID.setTextColor(Color.RED);
        else
            viewHolder.textViewAttackerNameID.setTextColor(Color.GREEN);

        return convertView;
    }

    class ReportViewHolder {

        public TextView textViewAttackerNameID;
        public TextView textViewRessourcesTotalID;
        public TextView textViewTimeID;
        public TextView textViewDefenderNameID;
    }
}