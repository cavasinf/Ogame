package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
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

public class MessageAdapter extends ArrayAdapter<Report> {

    private Handler handler = new Handler();
    private int delay = 100; //milliseconds
    private OnListViewChildrenClick mOnListViewChildrenClick;

    public void setOnEventListener(OnListViewChildrenClick listener) {
        mOnListViewChildrenClick = listener;
    }

    private User user;
    private int fromMessageNumber;

    public MessageAdapter(@NonNull Context context, @NonNull List<Report> reports, @NonNull User user, int fromMessageNumber) {
        super(context, R.layout.row_report_template, reports);
        this.user = user;
        this.fromMessageNumber = fromMessageNumber;
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


            viewHolder.textViewReportHiddenID = (TextView) convertView.findViewById(R.id.textViewReportHiddenID);
            viewHolder.textViewAttackerLabelID = (TextView) convertView.findViewById(R.id.textViewAttackerLabelID);
            viewHolder.textViewAttackerNameID = (TextView) convertView.findViewById(R.id.textViewAttackerNameID);
            viewHolder.textViewRessourcesTotalID = (TextView) convertView.findViewById(R.id.textViewRessourcesTotalID);
            viewHolder.textViewTimeID = (TextView) convertView.findViewById(R.id.textViewTimeID);
            viewHolder.textViewDefenderLabelID = (TextView) convertView.findViewById(R.id.textViewDefenderLabelID);
            viewHolder.textViewDefenderNameID = (TextView) convertView.findViewById(R.id.textViewDefenderNameID);
            viewHolder.textViewShowReportID = (TextView) convertView.findViewById(R.id.textViewShowReportID);

            convertView.setTag(viewHolder);
        }

        final Report report = getItem(position);

        viewHolder.textViewAttackerNameID.setText(report.getFrom());
        if (user.getUsername().equals(report.getFrom()))
            viewHolder.textViewAttackerNameID.setTypeface(null, Typeface.BOLD);
        if (report.getAttackerFleetAfterBattle().getSurvivingShips() == 0) {
            viewHolder.textViewAttackerLabelID.setTextColor(Color.RED);
            viewHolder.textViewAttackerNameID.setTextColor(Color.RED);
        } else {
            viewHolder.textViewAttackerLabelID.setTextColor(Color.GREEN);
            viewHolder.textViewAttackerNameID.setTextColor(Color.GREEN);
        }
        viewHolder.textViewRessourcesTotalID.setText(round(report.getGasWon()+report.getMineralsWon())+"");
        viewHolder.textViewTimeID.setText(Constant.convertTimeInSecondsToReadableDate(report.getDate()));
        viewHolder.textViewDefenderNameID.setText(report.getTo());
        if (user.getUsername().equals(report.getTo()))
            viewHolder.textViewDefenderNameID.setTypeface(null, Typeface.BOLD);
        if (report.getDefenderFleetAfterBattle().getSurvivingShips() == 0) {
            viewHolder.textViewDefenderLabelID.setTextColor(Color.RED);
            viewHolder.textViewDefenderNameID.setTextColor(Color.RED);
        } else {
            viewHolder.textViewDefenderLabelID.setTextColor(Color.GREEN);
            viewHolder.textViewDefenderNameID.setTextColor(Color.GREEN);
        }

        int counterForHiddenID = position+fromMessageNumber;
        viewHolder.textViewReportHiddenID.setText(counterForHiddenID+"");

        final ReportViewHolder finalViewHolder = viewHolder;
        // CLICK => show more
        viewHolder.textViewShowReportID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                mOnListViewChildrenClick.OnClick(Integer.parseInt(finalViewHolder.textViewReportHiddenID.getText().toString()), v);
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

    class ReportViewHolder {

        public TextView textViewReportHiddenID;
        public TextView textViewAttackerLabelID;
        public TextView textViewAttackerNameID;
        public TextView textViewRessourcesTotalID;
        public TextView textViewTimeID;
        public TextView textViewDefenderNameID;
        public TextView textViewDefenderLabelID;
        public TextView textViewShowReportID;
    }
}