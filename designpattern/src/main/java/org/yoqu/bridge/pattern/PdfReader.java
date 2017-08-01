package org.yoqu.bridge.pattern;

/**
 * @author yoqu
 * @date 2017年07月26日
 * @time 下午5:38
 * @email wcjiang2@iflytek.com
 */
public class PdfReader extends Reader {

    public PdfReader(Database database) {
        super(database);
    }

    public void convertStr(String str) {
        System.out.println("pdf reader:");
        System.out.println(database.read(str));
    }
}
