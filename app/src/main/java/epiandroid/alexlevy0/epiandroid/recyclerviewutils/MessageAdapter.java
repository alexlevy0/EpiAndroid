package epiandroid.alexlevy0.epiandroid.recyclerviewutils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    /*                  OLD ************************************************************
    List<MyObject> list;
    */

    private JSONObject jsonObject;

    //ajouter un constructeur prenant en entrée une liste
    /*                  OLD ************************************************************
    public MessageAdapter(List<MyObject> list) {
        this.list = list;
    }
    */
    public MessageAdapter(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_message,viewGroup,false);
        return new MessageViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(MessageViewHolder myViewHolder, int position) {
         /*                  OLD ************************************************************
        MyObject myObject = list.get(position);
        myViewHolder.bind(myObject);
        */

        /*
        try {
            final JSONArray messages = jsonObject.getJSONArray("modules");
            final int n = messages.length();
            for (int i = 0; i < n; ++i) {
                final JSONObject message = messages.getJSONObject(i);
                textViewView.setText(message.getString("title"));
                Log.d("MESSAGE VIEW TEXT SET ---------------------------->", message.getString("title"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */

        try {
            final JSONArray messages = jsonObject.getJSONArray("modules");
            myViewHolder.bind(messages.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        // return list.size(); OLD
        try {
            final JSONArray messages = jsonObject.getJSONArray("modules");
            return messages.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
