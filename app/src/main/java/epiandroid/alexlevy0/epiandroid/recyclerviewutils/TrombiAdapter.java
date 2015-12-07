package epiandroid.alexlevy0.epiandroid.recyclerviewutils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;

public class TrombiAdapter extends RecyclerView.Adapter<TrombiViewHolder> {

    private JSONArray jsonArray;

    public TrombiAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @Override
    public TrombiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_trombi, parent,false);
        return new TrombiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrombiViewHolder holder, int position) {
        try {
            Log.d("CALLA BIND --->", "" + jsonArray.getJSONObject(position));
            holder.bind(jsonArray.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}
