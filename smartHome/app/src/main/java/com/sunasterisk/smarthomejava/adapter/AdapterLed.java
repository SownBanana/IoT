package com.sunasterisk.smarthomejava.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sunasterisk.smarthomejava.R;
import com.sunasterisk.smarthomejava.model.Led;

import java.util.List;

public class AdapterLed extends RecyclerView.Adapter<AdapterLed.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private List<Led> leds;
    private int value;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    public AdapterLed(List leds, Context mContext) {
        this.mContext = mContext;
        this.leds = leds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View studentView =
                inflater.inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(studentView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Led led = leds.get(position);
        boolean switchCheck = led.value == 0 ? false : true;
        if(led.value == 0) {
            holder.imageLight.setImageResource(R.drawable.ic_light_off);
            holder.switchLight.setChecked(false);

        }
        else  {
            holder.imageLight.setImageResource(R.drawable.ic_light_on);
            holder.switchLight.setChecked(true);
        }
        holder.switchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    led.value  = 1;
                    holder.imageLight.setImageResource(R.drawable.ic_light_on);
                } else {
                    led.value  = 0;
                    holder.imageLight.setImageResource(R.drawable.ic_light_off);
                }
                leds.forEach((e) -> {
                    Log.d("tag", e.toString());
                });
//                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return leds.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //        private View itemview;
        private Switch switchLight;
        private ImageView imageLight;

        public ViewHolder(View itemView) {
            super(itemView);
            imageLight = itemView.findViewById(R.id.imageLight);
            switchLight = itemView.findViewById(R.id.switchLight);
//            switchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        // Your code
//                    } else {
//                        // Your code
//                    }
//                }
//            });
        }
    }
}
