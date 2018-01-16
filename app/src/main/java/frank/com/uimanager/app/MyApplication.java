package frank.com.uimanager.app;

import android.app.Application;

/**
 * Created by frank on 2018/1/11.
 */

public class MyApplication extends Application {
    public static MyApplication mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
