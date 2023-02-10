package nndb.com.parkinglotbctt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHoder> {
    private List<SliderData> mlistSlider;

    public SliderAdapter(List<SliderData> mlistSlider) {
        this.mlistSlider = mlistSlider;
    }

    @NonNull
    @Override
    public SliderViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, parent, false);
        return new SliderViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHoder holder, int position) {
        SliderData sliderData = mlistSlider.get(position);
        if (sliderData==null)
        {
            return;
        }
        holder.imageView.setImageResource(sliderData.getImgUrl());
    }

    @Override
    public int getItemCount() {
        if(mlistSlider != null)
            return  mlistSlider.size();
        return 0;
    }

    public class SliderViewHoder extends RecyclerView.ViewHolder {
        private  ImageView imageView;
        public SliderViewHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.myimage);
        }
    }
}
