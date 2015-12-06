package epiandroid.alexlevy0.epiandroid.recyclerviewutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class MessageViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;
    private NetworkImageView pictureMes;
    private ImageLoader mImageLoader;
    private Context context;


    //itemView est la vue correspondante à 1 cellule
    public MessageViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView
        context = itemView.getContext();
        textViewView = (TextView) itemView.findViewById(R.id.textMessage);
        pictureMes = (NetworkImageView) itemView.findViewById(R.id.pictureMessage);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    /*                  OLD ************************************************************
    public void bind(MyObject myObject){

        textViewView.setText(myObject.getText());
        mImageLoader = NetworkSingleton.getInstance(context).getImageLoader();
        pictureMes.setImageUrl(myObject.getImageUrl(),mImageLoader);
    }
    */

    public void bind(JSONObject jsonObject) {
        try {
            textViewView.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
