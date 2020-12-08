package com.kawo.museapp.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kawo.museapp.R;

import java.util.ArrayList;

public class DashComposerAdapter extends RecyclerView.Adapter<DashComposerAdapter.AdapterViewHolder> {

    private OnItemClickListener mListener;
    ArrayList<DashBoardCardType> cardComposer;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(DashComposerAdapter.OnItemClickListener listener){
        mListener = listener;
    }



    public DashComposerAdapter(ArrayList<DashBoardCardType> dashBoardCardTypeArrayList) {
        cardComposer= dashBoardCardTypeArrayList;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dash_composers_card_design,parent,false);
        AdapterViewHolder adapterViewHolder = new DashComposerAdapter.AdapterViewHolder(view,mListener);

        return adapterViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        DashBoardCardType dashBoardCardType = cardComposer.get(position);
        holder.image.setImageResource(dashBoardCardType.getImage());

        String h = dashBoardCardType.getTitle();
        char ch = h.charAt(0);
        if (Character.isDigit(ch))
        {                Log.d("xDXDXD", h.substring(1));
            String g=h.substring(1);holder.title.setText(g);}
        else

        {holder.title.setText(dashBoardCardType.getTitle());}
        holder.description.setText(dashBoardCardType.getDescription());
    }

    @Override
    public int getItemCount() {
        return cardComposer.size();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, description;


        public AdapterViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);


            image = itemView.findViewById(R.id.db_card_image);
            title = itemView.findViewById(R.id.db_card_title);
            description = itemView.findViewById(R.id.db_card_description);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


}
