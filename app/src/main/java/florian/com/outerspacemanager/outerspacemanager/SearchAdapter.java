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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.List;

import static java.lang.Math.round;
import static java.lang.String.format;

/**
 * Created by fcavasin on 27/02/2018.
 */

public class SearchAdapter extends ArrayAdapter<Search> {

    private AdapterView.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private User user;

    public SearchAdapter(@NonNull Context context , @NonNull List<Search> searches, @NonNull User user) {
        super(context, R.layout.row_search_template, searches);
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_search_template,parent, false);
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

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Search search = getItem(position);

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



        // TODO CLICK button

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
    }
}