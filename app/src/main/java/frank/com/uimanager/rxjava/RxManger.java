package frank.com.uimanager.rxjava;


import frank.com.uimanager.bean.MeetingTitleBean;
import frank.com.uimanager.bean.MobileAddress;
import frank.com.uimanager.utils.Constant;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxManger {

    private static final Long DURATION = 10000L;
    public static RxManger sRxManger = new RxManger();
    private  RxApi rxApi;

    public RxManger(){
        rxApi = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RxApi.class);
    }


    public static RxManger getInstance() {
        return sRxManger;
    }

    public Observable<MobileAddress> getTestNews() {
        //http://api.avatardata.cn/MobilePlace/
        rxApi = new Retrofit.Builder()
                .baseUrl("http://api.avatardata.cn/MobilePlace/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RxApi.class);
        //key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512
        return rxApi.getTestNews("ec47b85086be4dc8b5d941f5abd37a4e","13021671512");
    }

    public Observable<MeetingTitleBean> getNews(String urlName){
        String url = Constant.BASE_URL+urlName;//通用
        //如何使bean就调用getObservableBean(参数列表)
        Observable<MeetingTitleBean> observableBean = CacheManager.getInstance().getObservableBeanFromDB(url, MeetingTitleBean.class);
        if (observableBean != null) {
            return observableBean;
        } else {
            return CacheManager.getInstance().cacheBeanStringToDB(url,rxApi.getNews(urlName));
        }
    }


}
