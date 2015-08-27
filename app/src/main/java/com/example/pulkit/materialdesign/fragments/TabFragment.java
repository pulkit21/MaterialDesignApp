package com.example.pulkit.materialdesign.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pulkit.materialdesign.R;
import com.example.pulkit.materialdesign.network.VolleySingleton;

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
            if (bundle.getInt("position") == 0) {
                textView.setText("calls record");
            }
            if (bundle.getInt("position") == 1) {
                textView.setText("chats");
            }
            if (bundle.getInt("position") == 2) {
                textView.setText("contacts");
            }
        }

        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, "http://blog.teamtreehouse.com/api/get_recent_summary/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

        return view;
    }
}