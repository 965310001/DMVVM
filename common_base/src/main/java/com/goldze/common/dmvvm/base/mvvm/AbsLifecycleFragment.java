package com.goldze.common.dmvvm.base.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.base.mvvm.stateview.ErrorState;
import com.goldze.common.dmvvm.base.mvvm.stateview.StateConstants;
import com.goldze.common.dmvvm.utils.TUtil;
import com.tqzhang.stateview.stateview.BaseStateControl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GuoFeng
 * @date :2019/1/17 16:48
 * @description:
 */
public abstract class AbsLifecycleFragment<VD extends ViewDataBinding, T extends AbsViewModel> extends BaseFragment<VD> {

    protected T mViewModel;

    protected Object mStateEventKey;

    protected String mStateEventTag;

    private List<Object> eventKeys = new ArrayList<>();

    @Override
    public void initView(Bundle state) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));

        if (null != mViewModel) {
            dataObserver();
            mStateEventKey = getStateEventKey();
            mStateEventTag = getStateEventTag();
            eventKeys.add(new StringBuilder((String) mStateEventKey).append(mStateEventTag).toString());
            LiveBus.getDefault().subscribe(mStateEventKey, mStateEventTag).observe(this, observer);
        }
    }

    /**
     * ViewPager +fragment tag
     *
     * @return
     */
    protected String getStateEventTag() {
        return "";
    }

    /**
     * get state page event key
     *
     * @return
     */
    protected abstract Object getStateEventKey();

    /**
     * create ViewModelProviders
     *
     * @return ViewModel
     */
    protected <T extends ViewModel> T VMProviders(BaseFragment
                                                          fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, Class<T> tClass) {

        return registerObserver(eventKey, null, tClass);
    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, String tag, Class<T> tClass) {
        String event;
        if (TextUtils.isEmpty(tag)) {
            event = (String) eventKey;
        } else {
            event = eventKey + tag;
        }
        eventKeys.add(event);
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
    }

    @Override
    protected void onStateRefresh() {
        showLoading();
    }


    /**
     * 获取网络数据
     */
    protected void getRemoteData() {

    }

    protected void showError(Class<? extends BaseStateControl> stateView, Object tag) {
        showStateView(stateView, tag);
    }

    protected void showError(Class<? extends BaseStateControl> stateView) {
        showError(stateView, null);
    }

//    protected void showSuccess() {
//        showSuccess();
//    }

    protected void showLoading() {
        showLoadingState();
    }


    protected Observer observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            if (!TextUtils.isEmpty(state)) {
                if (StateConstants.ERROR_STATE.equals(state)) {
                    showError(ErrorState.class, "2");
                } else if (StateConstants.NET_WORK_STATE.equals(state)) {
                    showError(ErrorState.class, "1");
                } else if (StateConstants.LOADING_STATE.equals(state)) {
                    showLoading();
                } else if (StateConstants.SUCCESS_STATE.equals(state)) {
                    showSuccess();
                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (eventKeys != null && eventKeys.size() > 0) {
            for (int i = 0; i < eventKeys.size(); i++) {
                LiveBus.getDefault().clear(eventKeys.get(i));
            }
        }
    }
}
