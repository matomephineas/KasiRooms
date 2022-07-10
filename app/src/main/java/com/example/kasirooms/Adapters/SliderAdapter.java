package com.example.kasirooms.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirooms.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderHolder> {
  int[] images;

    public SliderAdapter(int[] images) {
        this.images = images;
    }

    @Override
    public SliderHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new SliderHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderHolder viewHolder, int position) {
        viewHolder.slider_view.setImageResource(images[position]);
    }

    @Override
    public int getCount() {
        return images.length;
    }


    public class SliderHolder extends SliderViewAdapter.ViewHolder
    {
        private ImageView slider_view;

        public SliderHolder(@NonNull View itemView) {
            super(itemView);
            slider_view=itemView.findViewById(R.id.slider_view);
        }
    }
}


