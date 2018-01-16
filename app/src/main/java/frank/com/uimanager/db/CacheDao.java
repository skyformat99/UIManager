package frank.com.uimanager.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import frank.com.uimanager.app.MyApplication;


/**
 * @author frank
 * @Description: 对网络缓存内容处理
 */
public class CacheDao {

    private CacheOpenHelper cacheOpenHelper;

    public CacheDao(Context context) {
        cacheOpenHelper = new CacheOpenHelper(context);
        cacheOpenHelper.getWritableDatabase();
    }


    //插入操作
    public void insert(String url, String content, String stmp) {
        SQLiteDatabase database = cacheOpenHelper.getWritableDatabase();
        String sql = "insert into " + CacheOpenHelper.TABLE_NAME + "(" + CacheOpenHelper.COLOMN_CACHE_URL + "," + CacheOpenHelper.COLOMN_CACHE_CONTENT + "," + CacheOpenHelper.COLOMN_CACHE_STMP + ")" + " values(?,?,?)";
        database.execSQL(sql, new String[]{url, content, stmp});
        database.close();
    }

    //删除操作
    public void delete(String url) {
        SQLiteDatabase database = cacheOpenHelper.getWritableDatabase();
        String sql = "delete from " + CacheOpenHelper.TABLE_NAME + " where url = ?";
        database.execSQL(sql, new String[]{url});
        database.close();
    }

    //修改操作
    public void update(String url, String content, String stmp) {
        SQLiteDatabase database = cacheOpenHelper.getWritableDatabase();
        String sql = "update " + CacheOpenHelper.TABLE_NAME + " set " + CacheOpenHelper.COLOMN_CACHE_CONTENT + " = ?," + CacheOpenHelper.COLOMN_CACHE_STMP + " = ? where " + CacheOpenHelper.COLOMN_CACHE_URL + " = ?";
        database.execSQL(sql, new String[]{content, stmp, url});
        database.close();
    }

    //查询操作
    public String[] query(String url) {
        SQLiteDatabase database = cacheOpenHelper.getWritableDatabase();
        String sql = "select " + CacheOpenHelper.COLOMN_CACHE_CONTENT + "," + CacheOpenHelper.COLOMN_CACHE_STMP + " from " + CacheOpenHelper.TABLE_NAME + " where " + CacheOpenHelper.COLOMN_CACHE_URL + " = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{url});
        String[] result = new String[2];
        int count = 0;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                count++;
                String content = cursor.getString(cursor.getColumnIndex(CacheOpenHelper.COLOMN_CACHE_CONTENT));
                String stmp = cursor.getString(cursor.getColumnIndex(CacheOpenHelper.COLOMN_CACHE_STMP));
                result[0] = content;
                result[1] = stmp;
            }
        }
        cursor.close();
        database.close();
        if (count > 1) {
            Toast.makeText(MyApplication.mContext, "有多个url的存储内容", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

}
