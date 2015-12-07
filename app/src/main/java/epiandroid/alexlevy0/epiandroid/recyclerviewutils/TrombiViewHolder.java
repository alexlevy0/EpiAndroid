package epiandroid.alexlevy0.epiandroid.recyclerviewutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class TrombiViewHolder extends RecyclerView.ViewHolder {

    private TextView            name;
    private NetworkImageView    picture;
    private ImageLoader         tImageLoader;
    private Context             context;

    public TrombiViewHolder(View itemView) {
        super(itemView);

        final Context context = itemView.getContext();
        name = (TextView) itemView.findViewById(R.id.nameProfilTrombi);
        picture = (NetworkImageView) itemView.findViewById(R.id.pictureTrombi);
        this.context = itemView.getContext();
    }

    public void bind(JSONObject jsonObject) {
        try {
            Log.d("bind ---->", "wwwwwWWWWTTTTTFFFFFF");
                name.setText(jsonObject.getString("title"));
            final String urlpicture = jsonObject.getString("picture");
            tImageLoader = NetworkSingleton.getInstance(context).getImageLoader();
            picture.setImageUrl(urlpicture, tImageLoader);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
