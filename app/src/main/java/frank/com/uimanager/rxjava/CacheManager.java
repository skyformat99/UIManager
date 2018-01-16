package frank.com.uimanager.rxjava;


import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.List;

import frank.com.uimanager.app.MyApplication;
import frank.com.uimanager.db.CacheDao;
import frank.com.uimanager.utils.GsonUtil;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;



/*
 *缓存管理
 */

public class CacheManager {
    private static final Long DURATION = 10000L;

    private CacheManager() {

    }

    private static CacheManager sCacheManager = new CacheManager();

    public static CacheManager getInstance() {
        return sCacheManager;
    }

    public <T> Observable<T> getObservableBeanFromDB(String url, Class<T> clss) {
        CacheDao cacheDao = new CacheDao(MyApplication.mContext);
        String[] query = cacheDao.query(url);
        if (TextUtils.isEmpty(query[1])) {
            return null;
        } else {
            long l = System.currentTimeMillis() - Long.parseLong(query[1]);
            System.out.println("判断时间差BeanString:" + l);
            if (Math.abs(l) < DURATION) {
                return Observable.just(GsonUtil.parseJsonToBean(query[0], clss));
            } else {
                return null;
            }
        }
    }

    public <T> Observable<List<T>> getObservableListFromDB(String url, Class<T> clss) {
        CacheDao cacheDao = new CacheDao(MyApplication.mContext);
        String[] query = cacheDao.query(url);
        if (TextUtils.isEmpty(query[1])) {
            return null;
        } else {
            long l = System.currentTimeMillis() - Long.parseLong(query[1]);
            System.out.println("判断时间差ListString:" + l);
            if (Math.abs(l) < DURATION) {
                return Observable.just(GsonUtil.fromJsonArray(query[0], clss));
            } else {
                return null;
            }
        }
    }


    public <T> Observable<T> cacheBeanStringToDB(final String url, Observable<T> observable) {
        final CacheDao cacheDao = new CacheDao(MyApplication.mContext);
        String[] query = cacheDao.query(url);
        if (TextUtils.isEmpty(query[1])) {
            observable.map(new Function<T, String>() {
                @Override
                public String apply(T t) throws Exception {
                    return null;
                }
            });
            observable.map(new Function<T, String>() {
                @Override
                public String apply(@NonNull T t) throws Exception {
                    return new Gson().toJson(t);
                }
            }).subscribeOn(Schedulers.io()).subscribe(new Consumer<String>() {
                @Override
                public void accept(String content) throws Exception {
                    cacheDao.insert(url, content, System.currentTimeMillis() + "");
                    System.out.println("插入:" + content);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    System.out.println("插入出错,网络或者数据有问题");
                    String message = throwable.getMessage();
                    //System.out.println(message);

                }
            });

        } else {
            observable.map(new Function<T, String>() {
                @Override
                public String apply(@NonNull T t) throws Exception {
                    return new Gson().toJson(t);
                }
            }).subscribeOn(Schedulers.io()).subscribe(new Consumer<String>() {
                @Override
                public void accept(String content) throws Exception {
                    cacheDao.update(url, content, System.currentTimeMillis() + "");
                    System.out.println("更新:" + content);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    System.out.println("更新出错,网络或者数据有问题");
                }
            });
        }
        return observable;
    }

    public <T> Observable<List<T>> cacheListBeanStringToDB(final String url, Observable<List<T>> observable) {
        final CacheDao cacheDao = new CacheDao(MyApplication.mContext);
        String[] query = cacheDao.query(url);
        if (TextUtils.isEmpty(query[1])) {
            observable.map(new Function<List<T>, String>() {
                @Override
                public String apply(@NonNull List<T> ts) throws Exception {
                    return new Gson().toJson(ts);
                }
            }).subscribeOn(Schedulers.io()).subscribe(new Consumer<String>() {
                @Override
                public void accept(String content) throws Exception {
                    cacheDao.insert(url,content,System.currentTimeMillis() + "");
                    System.out.println("插入:" + content);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    System.out.println("插入出错,网络或者数据有问题");
                }
            });
        } else {
            observable.map(new Function<List<T>, String>() {
                @Override
                public String apply(@NonNull List<T> ts) throws Exception {
                    return new Gson().toJson(ts);
                }
            }).subscribeOn(Schedulers.io()).subscribe(new Consumer<String>() {
                @Override
                public void accept(String content) throws Exception {
                    cacheDao.update(url, content, System.currentTimeMillis() + "");
                    System.out.println("更新:" + content);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    System.out.println("跟新出错,网络或者数据有问题");
                }
            });
        }
        return observable;
    }


}
