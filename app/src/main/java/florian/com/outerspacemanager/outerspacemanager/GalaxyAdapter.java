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

public class GalaxyAdapter extends ArrayAdapter<GalaxyUser> {

    private OnListViewUserChildrenClick OnListViewUserChildrenClick;
    private Handler handler = new Handler();
    private int delay = 100; //milliseconds

    public void setOnEventListener(OnListViewUserChildrenClick listener) {
        OnListViewUserChildrenClick = listener;
    }

    private User user;

    public GalaxyAdapter(@NonNull Context context , @NonNull List<GalaxyUser> galaxyUsers, @NonNull User user) {
        super(context, R.layout.row_galaxy_user_template, galaxyUsers);
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_galaxy_user_template,parent, false);
        }

        GalaxyUserViewHolder viewHolder = (GalaxyUserViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new GalaxyUserViewHolder();
            viewHolder.imageViewPlanetID = (ImageView) convertView.findViewById(R.id.imageViewPlanetID);
            viewHolder.textViewUsernameID = (TextView) convertView.findViewById(R.id.textViewUsernameID);
            viewHolder.textViewScoreID = (TextView) convertView.findViewById(R.id.textViewScoreID);
            viewHolder.RelativeLayoutAttackButtonID = (RelativeLayout) convertView.findViewById(R.id.RelativeLayoutAttackButtonID);

            convertView.setTag(viewHolder);
        }

        final GalaxyUser galaxyUser = getItem(position);

        String planetImageName = Constant.definePlanetByUserName(galaxyUser.getUsername());
        viewHolder.imageViewPlanetID.setImageResource(getContext().getResources().getIdentifier(planetImageName, "drawable", getContext().getPackageName()));
        viewHolder.textViewUsernameID.setText(galaxyUser.getUsername());
        viewHolder.textViewScoreID.setText(format("%,d", round(galaxyUser.getPoints())));

        viewHolder.RelativeLayoutAttackButtonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                OnListViewUserChildrenClick.OnClick(galaxyUser.getUsername(), v);
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

    class GalaxyUserViewHolder {
        public ImageView imageViewPlanetID;
        public TextView textViewUsernameID;
        public TextView textViewScoreID;
        public RelativeLayout RelativeLayoutAttackButtonID;
    }
}