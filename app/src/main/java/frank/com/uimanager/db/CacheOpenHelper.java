package frank.com.uimanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME              = "cache.db";
	public static final int    VERSION              = 1;
	public static final String TABLE_NAME           = "cache";// 表名
	public static final String COLOMN_CACHE_URL     = "url";// 数据库字段-网络路径
	public static final String COLOMN_CACHE_CONTENT = "content";// 数据库字段-缓存的内容
	public static final String COLOMN_CACHE_STMP    = "stmp";// 数据库字段-时间
	public static final String DB_SQL               = "create table " + TABLE_NAME
			+ " (_id integer primary key autoincrement," + COLOMN_CACHE_URL
			+ " varchar (20)," + COLOMN_CACHE_CONTENT
			+ " varchar (20)," + COLOMN_CACHE_STMP +" varchar (40) )";


	public CacheOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
