package epiandroid.alexlevy0.epiandroid.recyclerviewutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;

/**
 * Created by axel on 06/12/15.
 */
public class PlanningViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewView;
    private Context context;

    public PlanningViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        textViewView = (TextView) itemView.findViewById(R.id.PlanningName);
    }

    public void bind(JSONObject jsonObject) {
        try {
            textViewView.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
