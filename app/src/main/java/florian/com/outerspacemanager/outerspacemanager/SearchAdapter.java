package florian.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class SearchAdapter extends ArrayAdapter<Search> {

    private Handler handler = new Handler();
    private int delay = 100; //milliseconds
    private OnListViewChildrenClick mOnListViewChildrenClick;
    private List<SearchStatus> listSearchStatus = new ArrayList<SearchStatus>();
    private ArrayMap<Integer, Boolean> isTimerLaunched = new ArrayMap<>();

    public void setOnEventListener(OnListViewChildrenClick listener) {
        mOnListViewChildrenClick = listener;
    }

    private User user;
    private Date currentDate;

    public SearchAdapter(@NonNull Context context , @NonNull List<Search> searches, @NonNull User user, Date currentDate, List<SearchStatus> listSearchStatus) {
        super(context, R.layout.row_construction_template, searches);
        this.user = user;
        this.currentDate = currentDate;
        this.listSearchStatus = listSearchStatus;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_construction_template,parent, false);
        }

        SearchViewHolder viewHolder = (SearchViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new SearchViewHolder();
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
        final Search search = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        String buildingName = Normalizer.normalize(search.getName().replace(" ","_"), Normalizer.Form.NFD);
        buildingName = buildingName.replaceAll("[^\\p{ASCII}]", "");
        viewHolder.imageViewConstructionID.setImageResource(getContext().getResources().getIdentifier(buildingName,"drawable",getContext().getPackageName()));
        viewHolder.textView2ConstructionTitleID.setText(search.getName());
        viewHolder.textViewProdTimeID.setText(round(search.getTimeToBuildLevel0() + search.getLevel() *  search.getTimeToBuildByLevel())+"s");
        viewHolder.textViewProdNextLevelID.setText((search.getLevel()+1)+" :");
        viewHolder.textViewRessource1ID.setText(format("%,d",Constant.costMineralSearch(search)));
        viewHolder.textViewRessource2ID.setText(format("%,d",Constant.costGasSearch(search)));
        viewHolder.textViewConstructionLevelID.setText(search.getLevel()+"");
        viewHolder.RelativeLayoutConstructButtonID.setEnabled(!search.isBuilding());
        viewHolder.imageViewConstructButtonBackgroundID.setEnabled(!search.isBuilding());

        final SearchViewHolder finalViewHolder = viewHolder;



        if (finalViewHolder.timer != null) {
            finalViewHolder.timer.cancel();
        }
        if (search.isBuilding()) {
            for (final SearchStatus searchingStatus : listSearchStatus) {
                if (Objects.equals(searchingStatus.getSearchId(), search.getSearchId().toString())) {
                    if (isTimerLaunched != null) {
                        if (!isTimerLaunched.containsKey(search.getSearchId())) {

                            startTimer(search.getSearchTimeLeft(searchingStatus.getDateSearching()), search, searchingStatus, finalViewHolder);
                        }
                    } else {
                        startTimer(search.getSearchTimeLeft(searchingStatus.getDateSearching()), search, searchingStatus, finalViewHolder);
                    }
                    break;
                }
            }

        } else {
//            viewHolder.textViewProdTimeID.setText(round(search.getTimeToBuild(false)) + "s");
            viewHolder.textViewProdTimeID.setText(Constant.getHumanDateFromTimeSecond(search.getTimeToBuild(false)));
        }

        viewHolder.RelativeLayoutConstructButtonID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                mOnListViewChildrenClick.OnClick(search.getSearchId(), v);
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

    class SearchViewHolder {
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

    private void startTimer(long time, final Search search, final SearchStatus searchStatus, final SearchViewHolder viewHolderToChange) {
        final SearchViewHolder storedViewHolderToChange = viewHolderToChange;
        CountDownTimer counter = new CountDownTimer(search.getSearchTimeLeft(searchStatus.getDateSearching()) * 1000, 1000) {
            public void onTick(long millisUntilDone) {
                isTimerLaunched.put(search.getSearchId(), true);
//                storedViewHolderToChange.textViewProdTimeID.setText(search.getSearchTimeLeft(searchStatus.getDateSearching()) + "s");
                storedViewHolderToChange.textViewProdTimeID.setText(Constant.getHumanDateFromTimeSecond(Integer.parseInt(searchStatus.getDateSearching())));
            }

            public void onFinish() {
                storedViewHolderToChange.textViewProdTimeID.setText("DONE");
                isTimerLaunched.put(search.getSearchId(), false);
            }
        }.start();
        storedViewHolderToChange.timer = counter;
    }
}