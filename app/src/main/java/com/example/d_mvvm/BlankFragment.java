package com.example.d_mvvm;

import android.os.Bundle;

import com.example.d_mvvm.databinding.FragmentBlankBinding;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;

public class BlankFragment extends BaseFragment<FragmentBlankBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_blank;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {

        binding.setUser(new UserBean("张三", 10));
    }
}
