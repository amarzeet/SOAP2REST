
package com.finastra.soap2rest.wsdl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "OrdersService", targetNamespace = "http://example/ns/OrdersService", wsdlLocation = "file:/C:/Users/amakumar/Downloads/SOAP2Rest/resources/MPProvisioningService_1.wsdl")
public class OrdersService
    extends Service
{

    private final static URL ORDERSSERVICE_WSDL_LOCATION;
    private final static WebServiceException ORDERSSERVICE_EXCEPTION;
    private final static QName ORDERSSERVICE_QNAME = new QName("http://example/ns/OrdersService", "OrdersService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Users/amakumar/Downloads/SOAP2Rest/resources/MPProvisioningService_1.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ORDERSSERVICE_WSDL_LOCATION = url;
        ORDERSSERVICE_EXCEPTION = e;
    }

    public OrdersService() {
        super(__getWsdlLocation(), ORDERSSERVICE_QNAME);
    }

    public OrdersService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ORDERSSERVICE_QNAME, features);
    }

    public OrdersService(URL wsdlLocation) {
        super(wsdlLocation, ORDERSSERVICE_QNAME);
    }

    public OrdersService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ORDERSSERVICE_QNAME, features);
    }

    public OrdersService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OrdersService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns GetOrdersPortType
     */
    @WebEndpoint(name = "GetOrdersPort")
    public GetOrdersPortType getGetOrdersPort() {
        return super.getPort(new QName("http://example/ns/OrdersService", "GetOrdersPort"), GetOrdersPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetOrdersPortType
     */
    @WebEndpoint(name = "GetOrdersPort")
    public GetOrdersPortType getGetOrdersPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://example/ns/OrdersService", "GetOrdersPort"), GetOrdersPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ORDERSSERVICE_EXCEPTION!= null) {
            throw ORDERSSERVICE_EXCEPTION;
        }
        return ORDERSSERVICE_WSDL_LOCATION;
    }

}
