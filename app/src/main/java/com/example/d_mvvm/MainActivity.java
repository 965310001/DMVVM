package com.example.d_mvvm;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.activity.HorizontalTabActivity;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;

import java.util.ArrayList;

import java.util.List;

public class MainActivity extends HorizontalTabActivity {

    @Override
    protected String[] getTabTitles() {
        return new String[]{"展示","王五"};
    }

    @Override
    protected List<BaseFragment> getTabFragments() {
        List<BaseFragment> list = new ArrayList<>();
        list.add(new BlankFragment());
        list.add(new BlankFragment());
        return list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        ARouter.getInstance().inject(this);

    }

}
