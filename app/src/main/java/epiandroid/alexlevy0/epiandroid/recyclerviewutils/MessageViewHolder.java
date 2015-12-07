package epiandroid.alexlevy0.epiandroid.recyclerviewutils;

import android.content.Context;
import android.net.Uri;
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
import org.w3c.dom.Text;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private TextView messageTitle;
    private TextView messageNameProfil;
    private TextView messageTime;
    private TextView messageContent;
    private NetworkImageView pictureMes;
    private ImageLoader mImageLoader;
    private Context context;


    //itemView est la vue correspondante à 1 cellule
    public MessageViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView
        context = itemView.getContext();
        messageTitle = (TextView) itemView.findViewById(R.id.MessageTitle);
        messageNameProfil = (TextView) itemView.findViewById(R.id.MessageNameProfil);
        messageTime = (TextView) itemView.findViewById(R.id.MessageTime);
        messageContent = (TextView) itemView.findViewById(R.id.MessageContent);

        pictureMes = (NetworkImageView) itemView.findViewById(R.id.pictureMessage);

        // date + user.title (user name) + content
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
            messageTitle.setText(jsonObject.getString("title").replaceAll("\\<.*?>", ""));
            messageNameProfil.setText(jsonObject.getJSONObject("user").getString("title"));
            Log.d("set text  --->", "" + jsonObject.getString("date"));
            messageTime.setText(jsonObject.getString("date"));
            messageContent.setText(jsonObject.getString("content").replaceAll("\\<.*?>", ""));

            String urlpicture = jsonObject.getJSONObject("user").getString("picture");
            if (!urlpicture.equals("null")) {
                mImageLoader = NetworkSingleton.getInstance(context).getImageLoader();
                pictureMes.setImageUrl(urlpicture, mImageLoader);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
