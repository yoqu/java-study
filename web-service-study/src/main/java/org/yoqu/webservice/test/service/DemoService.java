
package org.yoqu.webservice.test.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "DemoService", targetNamespace = "http://service.test.webservice.yoqu.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DemoService {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://service.test.webservice.yoqu.org/", className = "org.yoqu.webservice.test.service.Add")
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://service.test.webservice.yoqu.org/", className = "org.yoqu.webservice.test.service.AddResponse")
    @Action(input = "http://service.test.webservice.yoqu.org/DemoService/addRequest", output = "http://service.test.webservice.yoqu.org/DemoService/addResponse")
    public int add(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

}
