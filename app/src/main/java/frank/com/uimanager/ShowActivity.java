package frank.com.uimanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.google.gson.Gson;

import frank.com.uimanager.bean.MeetingTitleBean;
import frank.com.uimanager.rxjava.RxManger;
import frank.com.uimanager.utils.Constant;
import frank.com.uimanager.utils.ToastUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShowActivity extends AppCompatActivity {

    private TextView mTvDisplay;
    private static final String TAG = "ShowActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CommonPager commonPager = new CommonPager(this) {
            @Override
            public View getSuccessView() {
                View view = View.inflate(getContext(), R.layout.activity_show, null);
                mTvDisplay = (TextView) view.findViewById(R.id.tv_display);
                return view;
            }

            @Override
            protected void setLoadData() {
                RxManger.getInstance().getNews(Constant.GET_MEETING_DATA).map(new Function<MeetingTitleBean, String>() {
                    @Override
                    public String apply(@NonNull MeetingTitleBean meetingTitleBean) throws Exception {
                        MeetingTitleBean bean = meetingTitleBean;
                        System.out.println(bean.toString());
                       // SystemClock.sleep(1000);
                        return new Gson().toJson(meetingTitleBean);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                updateView(LoadState.success);
                                mTvDisplay.setText(s);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                updateView(LoadState.error);
                                ExceptionManager.ResponeThrowable e = ExceptionManager.getInstance().Throwable(throwable);
                                ToastUtil.showToast(ShowActivity.this,e.message+"..."+e.code);
                            }
                        });

            }

        };
        setContentView(commonPager);

    }
}
