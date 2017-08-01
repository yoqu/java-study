package org.yoqu.bridge.pattern;

/**
 * @author yoqu
 * @date 2017年07月25日
 * @time 上午9:10
 * @email wcjiang2@iflytek.com
 */
public abstract class Reader {

    public Reader(Database database){
        this.database=database;
    }
    protected Database database;

    public abstract void convertStr(String str);
}
