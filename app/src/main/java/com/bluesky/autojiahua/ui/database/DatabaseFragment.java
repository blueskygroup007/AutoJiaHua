package com.bluesky.autojiahua.ui.database;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.bean.InterLock;
import com.bluesky.autojiahua.database.DeviceDao;
import com.bluesky.autojiahua.database.DeviceRepository;
import com.bluesky.autojiahua.database.InterLockDao;
import com.bluesky.autojiahua.databinding.FragmentDatabaseBinding;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class DatabaseFragment extends Fragment {

    private DatabaseViewModel mViewModel;
    private ListeningExecutorService mPool;
    private FragmentDatabaseBinding mBinding;

    public static DatabaseFragment newInstance() {
        return new DatabaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_database, container, false);
        mBinding = FragmentDatabaseBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trans();
            }
        });
        trans();
    }

    private void trans() {
        DeviceRepository repository = DeviceRepository.getInstance();
        mPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
        ListenableFuture<List<InterLock>> future = mPool.submit(new Callable<List<InterLock>>() {
            @Override
            public List<InterLock> call() throws Exception {
                return repository.getInterLockDao().getAllInterLock();
            }
        });

        Futures.addCallback(future, new FutureCallback<List<InterLock>>() {
            @Override
            public void onSuccess(List<InterLock> result) {
                DeviceDao deviceDao = repository.getDeviceDao();
                for (InterLock interlock :
                        result) {
                    //根据interlock的tag,查询device表内所有行
                    //如果有结果,将该行的data改为有连锁.
                    ListenableFuture<List<Device>> future1 = mPool.submit(new Callable<List<Device>>() {
                        @Override
                        public List<Device> call() throws Exception {
                            return deviceDao.getDevicesByTag(interlock.getTag());
                        }
                    });
                    Futures.addCallback(future1, new FutureCallback<List<Device>>() {
                        @Override
                        public void onSuccess(List<Device> result) {
                            //对应Tag的devices,理论上只有一个,全修改即可.
                            Device[] devices = result.toArray(new Device[result.size()]);
                            deviceDao.insertAll(devices);

                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    }, mPool);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, mPool);
    }
}