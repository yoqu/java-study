package org.yoqu.bridge.pattern;

/**
 * @author yoqu
 * @date 2017年07月25日
 * @time 上午9:23
 * @email wcjiang2@iflytek.com
 */
public class TxtReader extends Reader {

    public TxtReader(Database database) {
        super(database);
    }

    public void convertStr(String str) {
        System.out.println("txt reader");
        System.out.println(database.read(str));

    }
}
