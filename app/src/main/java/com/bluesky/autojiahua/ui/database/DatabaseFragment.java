package com.bluesky.autojiahua.ui.database;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.bean.InterLock;
import com.bluesky.autojiahua.database.DeviceDao;
import com.bluesky.autojiahua.database.DeviceRepository;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnTrans.setOnClickListener(v -> trans());
        trans();
    }

    /**
     * 转换符合联锁中tag的devices的data字段,以显示不同的lock图标
     */
    private void trans() {
        DeviceRepository repository = DeviceRepository.getInstance();
        mPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
        ListenableFuture<List<InterLock>> future = mPool.submit(() -> repository.getInterLockDao().LoadAllInterLock());

        Futures.addCallback(future, new FutureCallback<List<InterLock>>() {
            @Override
            public void onSuccess(List<InterLock> result) {
                DeviceDao deviceDao = repository.getDeviceDao();
                for (InterLock interlock :
                        result) {
                    //根据interlock的tag,查询device表内所有行
                    //如果有结果,将该行的data改为有连锁.
                    ListenableFuture<List<Device>> future1 = mPool.submit(() -> deviceDao.getDevicesByTag(interlock.getTag()));
                    Futures.addCallback(future1, new FutureCallback<List<Device>>() {
                        @Override
                        public void onSuccess(List<Device> result) {
                            //对应Tag的devices,理论上只有一个,全修改即可.
                            if (result.size() > 0) {
                                result.get(0).setDate("alarm");
                                Device[] devices = result.toArray(new Device[0]);
                                deviceDao.insertAll(devices);
                            }
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