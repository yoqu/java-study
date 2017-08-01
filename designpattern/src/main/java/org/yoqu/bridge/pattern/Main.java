package org.yoqu.bridge.pattern;

import javafx.scene.chart.PieChart;

/**
 * @author yoqu
 * @date 2017年07月25日
 * @time 上午9:10
 * @email wcjiang2@iflytek.com
 */
public class Main {
    public static void main(String[] args) {
        Database database = new OracleDatabase();
        Reader reader =  new PdfReader(database);
        reader.convertStr("oracle database");

    }
}
