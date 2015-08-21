package com.example.pulkit.materialdesign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by pulkit on 8/21/15.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<DrawerInformation> data = Collections.emptyList();

    public NavDrawerAdapter(Context context, List<DrawerInformation> data){
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

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.nav_list_item);
            imageView = (ImageView) itemView.findViewById(R.id.nav_list_image);
        }
    }
}
