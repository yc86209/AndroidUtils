package com.yuchin.androidutils.db;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yuchin.androidutils.BuildConfig;
import com.yuchin.androidutils.entity.User;
import com.yuchin.androidutils.gen.DaoMaster;
import com.yuchin.androidutils.gen.DaoSession;
import com.yuchin.androidutils.gen.UserDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import static com.yuchin.androidutils.UtilsApp.DEBUG;


/**
 * Created by Kenvin on 2017/11/2.
 */

public class DBManager {
    //这里我们定义一个标志，从而去切换数据库的标准模式和加密模式
    public static final boolean ENCRYPTED = false;
    private final static String DB_NAME = BuildConfig.APPLICATION_ID;

    private volatile static DBManager mInstance;
    private DaoSession daoSession;

    private DBManager(Context context) {
        QueryBuilder.LOG_SQL = DEBUG;
        QueryBuilder.LOG_VALUES = DEBUG;
        DownloadOpenHelper helper = new DownloadOpenHelper(context, ENCRYPTED ? DB_NAME + "-encrypted" : DB_NAME);
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    private DBManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    public static DBManager getInstance() {
        if (mInstance != null) return mInstance;
        throw new NullPointerException("u should init first");
    }

    public static void init(@NonNull final Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 插入一条记录
     *
     * @param user
     */
    public void insertUser(User user) {
        UserDao userDao = daoSession.getUserDao();
        userDao.insert(user);
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertUserList(@NotNull List<User> users) {

        UserDao userDao = daoSession.getUserDao();
        userDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteUser(User user) {
        UserDao userDao = daoSession.getUserDao();
        userDao.delete(user);
    }


    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateUser(User user) {
        UserDao userDao = daoSession.getUserDao();
        userDao.update(user);
    }


    /**
     * 查询用户列表
     */
    public List<User> queryUserList() {
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        List<User> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<User> queryUserList(int age) {
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Age.gt(age)).orderAsc(UserDao.Properties.Age);
        List<User> list = qb.list();
        return list;
    }
}
