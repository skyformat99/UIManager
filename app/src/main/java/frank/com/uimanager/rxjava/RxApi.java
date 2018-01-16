package frank.com.uimanager.rxjava;


import frank.com.uimanager.bean.MeetingTitleBean;
import frank.com.uimanager.bean.MobileAddress;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

;

public interface RxApi {

    @GET("LookUp")//http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512
    Observable<MobileAddress> getTestNews(@Query("key") String keyName, @Query("mobileNumber") String mobileNumber);

    @GET("{path}")//会议标题
    Observable<MeetingTitleBean> getNews(@Path("path") String arg);

}
