package frank.com.uimanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * 自定义自动切换的View
 */

public abstract class CommonPager extends FrameLayout implements View.OnClickListener {

    private View mLoadView;

    private View mErrorView;

    private View mSuccessView;

    public CommonPager(Context context) {
        this(context, null);
    }

    public CommonPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        if (mLoadView == null) {
            mLoadView = View.inflate(getContext(), R.layout.page_loading, null);
        }
        if (mErrorView == null) {
            mErrorView = View.inflate(getContext(), R.layout.page_error, null);
            Button button = (Button) mErrorView.findViewById(R.id.btn_reload);
            button.setOnClickListener(this);
        }
        if (mSuccessView == null) {
            mSuccessView = getSuccessView();
        }
        addView(mLoadView);
        addView(mErrorView);
        addView(mSuccessView);
        showPager();
        //changView();
        setLoadData();
    }

    public enum LoadState {
        loading, success, error
    }

    public LoadState mCurrentState = LoadState.loading;

    public void showPager() {
        mLoadView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mSuccessView.setVisibility(View.GONE);
        switch (mCurrentState) {
            case loading:
                mLoadView.setVisibility(View.VISIBLE);
                break;
            case success:
                mSuccessView.setVisibility(View.VISIBLE);
                break;
            case error:
                mErrorView.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }

    //根据数据自动切换页面
    public void updateView(LoadState loadState) {
        mCurrentState = loadState;
        showPager();
    }

    //内部重新加载
    @Override
    public void onClick(View view) {
        mCurrentState = LoadState.loading;
        showPager();

        setLoadData();
    }

    public abstract View getSuccessView();

    protected abstract void setLoadData();

}
