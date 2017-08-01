package org.yoqu.bridge.pattern;

/**
 * @author yoqu
 * @date 2017年07月25日
 * @time 上午9:12
 * @email wcjiang2@iflytek.com
 */
public class OracleDatabase implements Database{
    public String read(String txt) {
        return "oracle :"+txt;
    }
}
