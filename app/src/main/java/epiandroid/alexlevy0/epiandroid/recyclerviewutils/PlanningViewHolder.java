package epiandroid.alexlevy0.epiandroid.recyclerviewutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;

/**
 * Created by axel on 06/12/15.
 */
public class PlanningViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView date_begin;
    private TextView date_end;
    private TextView module;
    private TextView typeTitle;
    private Context context;

    public PlanningViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        title = (TextView) itemView.findViewById(R.id.PlanningTitle);
        date_begin = (TextView) itemView.findViewById(R.id.PlanningDateBegin);
        date_end = (TextView) itemView.findViewById(R.id.PlanningDateEnd);
        module = (TextView) itemView.findViewById(R.id.PlanningModule);
        typeTitle = (TextView) itemView.findViewById(R.id.PlanningTypeTitle);
    }

    public void bind(JSONObject jsonObject) {
        try {
            title.setText(jsonObject.getString("acti_title"));
            date_begin.setText("Heure début : " + jsonObject.getString("allowed_planning_start"));
            date_end.setText("Heure fin : " + jsonObject.getString("end"));
            module.setText(jsonObject.getString("titlemodule"));
            typeTitle.setText(jsonObject.getString("type_title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
