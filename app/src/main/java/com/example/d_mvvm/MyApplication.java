package com.example.d_mvvm;

import com.goldze.common.dmvvm.base.BaseApplication;
import com.xuexiang.xui.XUI;

public class MyApplication extends BaseApplication {
    @Override
    public void run() {
        XUI.init(this);
    }
}
