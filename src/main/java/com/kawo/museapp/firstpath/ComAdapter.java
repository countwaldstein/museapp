package com.kawo.museapp.firstpath;

import android.content.Context;
import android.database.Cursor;
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

public class ComAdapter extends RecyclerView.Adapter<ComAdapter.ComViewHolder> {
    private ArrayList<ComRecyclerItem> mComRecyclerItemArrayList;
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;
    RecyclerView rv;

    public ComAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ComViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;



        public ComViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.comImageView);
            mTextView1 = itemView.findViewById(R.id.comTextView);
            mTextView2 = itemView.findViewById(R.id.comTextView2);

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

    public ComAdapter(ArrayList<ComRecyclerItem> comRecyclerItemArrayList){
        mComRecyclerItemArrayList= comRecyclerItemArrayList;
    }


    @Override
    public ComViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comrecycler_item,parent,false);
            ComViewHolder cvh = new ComViewHolder(v, mListener);
            return cvh;
    }
    @Override
    public void onBindViewHolder(@NonNull ComViewHolder holder, int position) {
        ComRecyclerItem currentItem = mComRecyclerItemArrayList.get(position);

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        String h = currentItem.getText1();
        char ch = h.charAt(0);
        if (Character.isDigit(ch))
            {
                String g=h.substring(1);holder.mTextView1.setText(g);}
        else
        {holder.mTextView1.setText(currentItem.getText1());}
        holder.mTextView2.setText(currentItem.getText2());
}



    @Override
    public int getItemCount() {
        return mComRecyclerItemArrayList.size();
    }


    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}

