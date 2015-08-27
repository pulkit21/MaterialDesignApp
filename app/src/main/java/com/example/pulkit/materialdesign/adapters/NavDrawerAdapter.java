package com.example.pulkit.materialdesign.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pulkit.materialdesign.model.DrawerInformation;
import com.example.pulkit.materialdesign.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by pulkit on 8/21/15.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<DrawerInformation> data = Collections.emptyList();
    private Context context;
    private ClickListener clickListener;


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public NavDrawerAdapter(Context context, List<DrawerInformation> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.navigation_list_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        DrawerInformation current = data.get(i);
        viewHolder.textView.setText(current.title);
        viewHolder.imageView.setImageResource(current.iconId);
//        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    Toast.makeText(v.getContext(),"Item clicked" + i, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.nav_list_item);
            imageView = (ImageView) itemView.findViewById(R.id.nav_list_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            context.startActivity(new Intent(context, TextActivity.class)); //This is not a good way of doing
                                                                              //stuff it should be in activity or fragment
//            delete(getAdapterPosition()); //To implement delete operation on list
            if (clickListener != null){
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
