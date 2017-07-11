package org.yoqu.webservice.test.client;

import org.yoqu.webservice.test.service.DemoService;
import org.yoqu.webservice.test.service.DemoServiceImplService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author yoqu
 * @date 2017年07月03日
 * @time 下午9:12
 * @email wcjiang2@iflytek.com
 */
public class Client {

    public static void main(String[] args) throws MalformedURLException {
//        URL url = new URL("http://127.0.0.1:8989/ds?wsdl");
//        QName q = new QName("http://service.test.webservice.yoqu.org/","DemoServiceImplService");
//        Service service = Service.create(url,q);
//        DemoService demoService = service.getPort(DemoService.class);
//        System.out.println(demoService.add(1,2));
        DemoServiceImplService demoServiceImplService = new DemoServiceImplService();
        DemoService demoService = demoServiceImplService.getDemoServiceImplPort();
        demoService.add(1,2);

    }
}
