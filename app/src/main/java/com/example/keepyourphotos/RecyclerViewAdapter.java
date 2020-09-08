package com.example.keepyourphotos;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<Bitmap> imageList;
    private ArrayList<String> explainList;

    public RecyclerViewAdapter(ArrayList<Bitmap> imageList, ArrayList<String> explainList) {
        this.imageList = imageList;
        this.explainList = explainList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);

        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RowHolder holder, int position) {

        holder.imageView2.setImageBitmap(imageList.get(position));
        holder.textView.setText(explainList.get(position));

    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        ImageView imageView2;
        TextView textView;

        public RowHolder(@NonNull View itemView) {
            super(itemView);

            imageView2 = itemView.findViewById(R.id.imageView2);
            textView = itemView.findViewById(R.id.textView6);

        }
    }


}
