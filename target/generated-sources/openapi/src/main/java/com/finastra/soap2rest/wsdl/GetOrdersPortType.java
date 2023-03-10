
package com.finastra.soap2rest.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "GetOrdersPortType", targetNamespace = "http://example/ns/OrdersService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface GetOrdersPortType {


    /**
     * 
     * @param parameters
     * @return
     *     returns com.finastra.soap2rest.wsdl.GetOrdersResponse
     */
    @WebMethod(operationName = "GetOrders")
    @WebResult(name = "GetOrdersResponse", targetNamespace = "http://example/schema/OrdersService", partName = "parameters")
    public GetOrdersResponse getOrders(
        @WebParam(name = "GetOrders", targetNamespace = "http://example/schema/OrdersService", partName = "parameters")
        GetOrders parameters);

}
