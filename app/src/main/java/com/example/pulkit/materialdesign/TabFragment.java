package com.example.pulkit.materialdesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pulkit on 8/25/15.
 */
//public class TabFragment {
//}
public class TabFragment extends Fragment {

    private TextView textView;

    public static TabFragment getInstance(int position) {
        TabFragment tabFragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        tabFragment.setArguments(args);
        return tabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, container, false);
        textView = (TextView) view.findViewById(R.id.tabFragment);
        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText("page selected " + bundle.getInt("position"));
        }
        return view;
    }
}