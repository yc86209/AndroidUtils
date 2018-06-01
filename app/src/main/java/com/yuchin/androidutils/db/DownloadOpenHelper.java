package com.yuchin.androidutils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.socks.library.KLog;
import com.yuchin.androidutils.entity.DaoMaster;
import com.yuchin.androidutils.entity.UserDao;
import org.greenrobot.greendao.database.Database;

/**
 * Created by Kenvin on 2017/11/2.
 */
public class DownloadOpenHelper extends DaoMaster.OpenHelper {

    public DownloadOpenHelper(Context context, String name) {
        this(context, name, null);
    }

    public DownloadOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 升级数据库
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        KLog.w("db version update from " + oldVersion + " to " + newVersion);
        for (int i = oldVersion; i < newVersion; i++) {
            KLog.w("db version update " + i);
            switch (i) {
                case 1:
                    MigrationHelper.getInstance().migrate(db, UserDao.class);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
        }
    }
}
