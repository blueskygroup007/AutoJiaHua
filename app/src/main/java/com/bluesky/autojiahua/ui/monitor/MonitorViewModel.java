package com.bluesky.autojiahua.ui.monitor;

import androidx.lifecycle.ViewModel;

import com.bluesky.autojiahua.R;

import java.util.ArrayList;
import java.util.List;

public class MonitorViewModel extends ViewModel {

    private List<BeanMonitor> mListPicHuaChanMain = new ArrayList<>();
    private List<BeanMonitor> mListPicHuaChanZhiSuan = new ArrayList<>();
    private List<BeanMonitor> mListPicGanXiJiao = new ArrayList<>();
    private List<BeanMonitor> mListPicJiaoLu = new ArrayList<>();



    {
        mListPicHuaChanMain.add(new BeanMonitor("chuleng", "初冷", R.drawable.chuleng));
        mListPicHuaChanMain.add(new BeanMonitor("dianbu", "电捕", R.drawable.dianbu));
        mListPicHuaChanMain.add(new BeanMonitor("gufengji", "鼓风机", R.drawable.gufengji));
        mListPicHuaChanMain.add(new BeanMonitor("jiaoyouanshui1", "焦油氨水1", R.drawable.jiaoyouanshui1));
        mListPicHuaChanMain.add(new BeanMonitor("jiaoyouanshui2", "焦油氨水2", R.drawable.jiaoyouanshui2));
        mListPicHuaChanMain.add(new BeanMonitor("liuan1", "硫铵1", R.drawable.liuan1));
        mListPicHuaChanMain.add(new BeanMonitor("liuan2", "硫铵2", R.drawable.liuan2));
        mListPicHuaChanMain.add(new BeanMonitor("tuoliu1", "脱硫1", R.drawable.tuoliu1));
        mListPicHuaChanMain.add(new BeanMonitor("tuoliu2", "脱硫2", R.drawable.tuoliu2));
        mListPicHuaChanMain.add(new BeanMonitor("xiaofangshui", "消防水", R.drawable.xiaofangshui));
        mListPicHuaChanMain.add(new BeanMonitor("xidita", "洗涤塔", R.drawable.xidita));
        mListPicHuaChanMain.add(new BeanMonitor("youku", "油库", R.drawable.youku));
        mListPicHuaChanMain.add(new BeanMonitor("zhengan", "蒸氨", R.drawable.zhengan));
        mListPicHuaChanMain.add(new BeanMonitor("zhonglengxiben", "终冷洗苯", R.drawable.zhonglengxiben));
    }

    {
        mListPicHuaChanZhiSuan.add(new BeanMonitor("yuchuli", "预处理", R.drawable.yuchuli));
        mListPicHuaChanZhiSuan.add(new BeanMonitor("fenshao1", "焚烧一", R.drawable.fenshao1));
        mListPicHuaChanZhiSuan.add(new BeanMonitor("fenshao2", "焚烧二", R.drawable.fenshao2));
        mListPicHuaChanZhiSuan.add(new BeanMonitor("jinghua", "净化", R.drawable.jinghua));
        mListPicHuaChanZhiSuan.add(new BeanMonitor("zhuanhua", "转化", R.drawable.zhuanhua));
        mListPicHuaChanZhiSuan.add(new BeanMonitor("ganxi1", "干吸一", R.drawable.ganxi1));
        mListPicHuaChanZhiSuan.add(new BeanMonitor("ganxi2", "干吸二", R.drawable.ganxi2));
    }

    public MonitorViewModel() {
    }

    public List<BeanMonitor> getListPicHuaChanMain() {
        return mListPicHuaChanMain;
    }

    public List<BeanMonitor> getListPicHuaChanZhiSuan() {
        return mListPicHuaChanZhiSuan;
    }

    public List<BeanMonitor> getListPicGanXiJiao() {
        return mListPicGanXiJiao;
    }

    public List<BeanMonitor> getListPicJiaoLu() {
        return mListPicJiaoLu;
    }
}