package epiandroid.alexlevy0.epiandroid.recyclerviewutils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;

/**
 * Created by axel on 06/12/15.
 */

public class PlanningAdapter extends RecyclerView.Adapter<PlanningViewHolder> {
    private JSONObject jsonObject;

    public PlanningAdapter(JSONObject jsonObject) { this.jsonObject = jsonObject; }

    @Override
    public PlanningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_planning,parent,false);
        return new PlanningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanningViewHolder holder, int position) {
        try {
            final JSONArray messages = jsonObject.getJSONArray("modules");
            holder.bind(messages.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            final JSONArray messages = jsonObject.getJSONArray("modules");
            return messages.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

