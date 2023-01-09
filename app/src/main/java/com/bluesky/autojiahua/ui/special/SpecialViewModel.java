package com.bluesky.autojiahua.ui.special;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bluesky.autojiahua.R;

import java.util.ArrayList;
import java.util.List;

public class SpecialViewModel extends ViewModel {

    private MutableLiveData<List<BeanSpecial>> mHuachan;
    private MutableLiveData<List<BeanSpecial>> mGanxijiao;

    private List<BeanSpecial> mListHuachan = new ArrayList<>();
    private List<BeanSpecial> mListGanxijiao = new ArrayList<>();

    {
        mListHuachan.add(new BeanSpecial("yanghuagao", "氧化锆分析仪", R.drawable.ic_oxt3000, "OXT3000"));
        mListHuachan.add(new BeanSpecial("dianbu", "电捕分析仪", R.drawable.ic_oxymat_61, "OXYMAT 61"));
        mListHuachan.add(new BeanSpecial("SO2&O2", "二氧化硫分析仪", R.drawable.ic_so2, "SO2分析仪:SERVOTOUGH  SpectraExact  2500 \n 分析仪:SERVOTOUGH OXY 1900"));
        mListHuachan.add(new BeanSpecial("PH", "PH值分析仪", R.drawable.ic_cm442, "变送器:E+H CM442 \n 探头:CPS11D"));
        mListHuachan.add(new BeanSpecial("H2SO4", "硫酸浓度计", R.drawable.ic_usc_3, "USC-III"));
    }

    public MutableLiveData<List<BeanSpecial>> getHuachan() {
        if (mHuachan == null) {
            mHuachan = new MutableLiveData<>();
            mHuachan.setValue(mListHuachan);
        }
        return mHuachan;
    }


    public MutableLiveData<List<BeanSpecial>> getGanxijiao() {
        if (mGanxijiao == null) {
            mGanxijiao = new MutableLiveData<>();
            mGanxijiao.setValue(mListGanxijiao);
        }
        return mGanxijiao;
    }


}