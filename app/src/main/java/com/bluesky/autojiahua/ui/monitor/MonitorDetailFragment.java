package com.bluesky.autojiahua.ui.monitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.Device;
import com.github.chrisbanes.photoview.PhotoView;


public class MonitorDetailFragment extends Fragment {

    BeanMonitor mMonitor;
    PhotoView mPhotoView;
    private MonitorViewModel mMonitorViewModel;


    public MonitorDetailFragment() {
        // Required empty public constructor
    }


    public static MonitorDetailFragment newInstance() {
        MonitorDetailFragment fragment = new MonitorDetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMonitor = (BeanMonitor) getArguments().getSerializable("BeanMonitor");
        }
        mMonitorViewModel = new ViewModelProvider(this).get(MonitorViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monitor_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoView = view.findViewById(R.id.photo_view);
        mPhotoView.setImageResource(mMonitor.getThumb());
    }
}