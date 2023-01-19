package com.finastra.soap2rest.parser;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.finastra.soap2rest.bean.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.wsdl.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

import org.apache.camel.util.CastUtils;
import com.ibm.wsdl.extensions.schema.SchemaImpl;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAll;
import org.apache.ws.commons.schema.XmlSchemaChoice;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaEnumerationFacet;
import org.apache.ws.commons.schema.XmlSchemaFacet;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaSequence;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeRestriction;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;


public class WSDLParser {
    private static final Logger LOG = LoggerFactory.getLogger(WSDLParser.class);
    public String wsdlUrl, serviceUrl, serviceName, wsNamespace, soapPortName, dirPath;
    private Port soapPort;
    private XmlSchema schema;
    private Swagger swagger;
    private Map<Object,DefinitionDetail> definitionDetailMap=new HashMap<>();



    public void readWSDL() {
        try {
            // Read the WSDL URL and docBase from config file
            //InitialContext ctx = new InitialContext();
            //wsdlUrl = (String) ctx.lookup("java:comp/env/wsdlUrl");
            wsdlUrl = "https://www.w3schools.com/xml/tempconvert.asmx?WSDL";
            LOG.info("WSDL URL: " + wsdlUrl);
            //dirPath = (String) ctx.lookup("java:comp/env/dirPath");
            dirPath = "C:\\soap_to_rest\\output\\";
            //dirPath = "C:\\Users\\New\\Desktop\\output\\";
            // ctx.close();

            // Set up the reader
            WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();
            reader.setFeature("javax.wsdl.verbose", false);
            reader.setFeature("javax.wsdl.importDocuments", true);

            File file = new File("resources"+ File.separator+"MPProvisioningService_1.wsdl");
            FileWriter file1 = null;
            // Get WSDL as a document
            Document wsdlDoc = getWSDLAsDocument();
//            Document wsdlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
//                    .parse(new FileInputStream(file));
            // Convert the WSDL document to a WSDL definition
            Definition def = reader.readWSDL(null, wsdlDoc);

            initializeVariables(def);
            // Get the XML Schema
            List<?> extensions = def.getTypes().getExtensibilityElements();
            for (Object extension : extensions) {
                if (extension instanceof SchemaImpl) {
                    Element schElement = ((SchemaImpl) extension).getElement();
                    schema = new XmlSchemaCollection().read(schElement);
                }
            }

            getOperations();
            
            ObjectMapper mapper = new ObjectMapper();
            String json="";
            org.json.simple.JSONObject jo = null;
            try {
                // convert user object to json string and return it 
                json= mapper.writeValueAsString(swagger);
               
               jo =  mapper.convertValue(swagger, org.json.simple.JSONObject.class);
                LOG.info(".#######################################\n"+json+"\n###################################################");
                
            }
            catch (JsonMappingException  e) {
                // catch various errors
                e.printStackTrace();
            }
               file1 = new FileWriter("resources"+ File.separator+"temp.txt");
                try {
                	file1.write(jo.toJSONString());
                }catch (Exception e) {
					// TODO: handle exception
				} finally {
					file1.flush();
					file1.close();
				}
                
                try (PrintWriter out = new PrintWriter(new FileWriter("resources"+ File.separator+"temp.json"))) {
                   
					out.write(jo.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


        } catch (WSDLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private Document getWSDLAsDocument() {
        LOG.info("Retrieving document from the WSDL URL...");
        Document doc = null;
        try {
        	
        	 File file = new File("resources"+ File.separator+"MPProvisioningService_1.wsdl");
            // Get the content from URL
            InputStream inputStream = new FileInputStream(file);
            InputSource inputSource = new InputSource(inputStream);
            inputSource.setEncoding("UTF-8");
            System.out.println(inputSource.getEncoding());
            inputSource.setSystemId(wsdlUrl);
            if (inputStream == null) {
                throw new IllegalArgumentException("No content at URL.");
            }

            // Set up the factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);

            // Read the content into a document
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(inputSource);
            inputStream.close();

        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private void initializeVariables(Definition def) {
        swagger = new Swagger();
        // Read the WSDL namespace
        wsNamespace = def.getTargetNamespace();
        LOG.info("Service namespace: " + wsNamespace);
        swagger.setSwagger("2.0");
        //swagger.setHost(wsNamespace);
        InfoDetail infoDetail=new InfoDetail();
        infoDetail.setVersion("1.0");
        // Set the constants for processing of SOAP requests
        //MappingRoute.WS_NAMESPACE = wsNamespace;

        // Get the details to specify the service endpoint
        Collection<Service> services = CastUtils.cast(def.getAllServices().values());
        List<tagDetail> tagDetailList=new ArrayList<>();
        for (Service service : services) {
            Collection<Port> ports = CastUtils.cast(service.getPorts().values());
            for (Port port : ports) {
                List<?> extensions = port.getExtensibilityElements();
                for (Object extension : extensions) {
                    if (extension instanceof SOAPAddress) {
                        tagDetail tagDetailObj=new tagDetail();
                        serviceUrl = ((SOAPAddress) extension).getLocationURI();
                        serviceName = service.getQName().getLocalPart();
                        soapPortName = port.getName();
                        soapPort = port;
                        LOG.info("Service address: " + serviceUrl);
                        LOG.info("Service name: " + serviceName);
                        LOG.info("Port name: " + soapPortName);
                        tagDetailObj.setName(serviceName);
                        tagDetailObj.setDescription(serviceName);
                        tagDetailList.add(tagDetailObj);
                    }
                }
            }
        }
        swagger.setTags(tagDetailList);
        infoDetail.setTitle(serviceName);
        infoDetail.setDescription(serviceName);
        swagger.setInfo(infoDetail);
        List<String> produceConsme=new ArrayList<>();
        produceConsme.add("application/json");
        swagger.setProduces(produceConsme);
        swagger.setConsumes(produceConsme);
        swagger.setSchemes(Arrays.asList("https"));
    }

    private void getOperations() {
        Map<Object,Map<Object,MethodObject>> pathObjectMap=new HashMap<>();
        
        Map<Object,ResponseObject> responsesMap=new HashMap<>();

        //code = new CodeWriter(wsdlUrl);
        LOG.info("**** Listing methods ****");

        // Iterate over all operations defined for a given soap port
        for (Object op : soapPort.getBinding().getPortType().getOperations()) {
        	Map<Object,MethodObject> methodObjectMap=new HashMap<>();
            Operation operation = (Operation) op;
            String opName = operation.getName();
            //System.out.println(" - " + opName);
            MethodObject methodObject=new MethodObject();
            methodObject.setTags(Arrays.asList(serviceName));
            methodObject.setSummary(opName);
            methodObject.setOperationId(opName);
            methodObject.setDescription(opName);
            // Get parts from WSDL that describe the operation's parameters
            Collection<Part> inParts = CastUtils.cast(operation.getInput().getMessage().getParts().values());

            // Get parts from WSDL that describe the operation's return value
            Collection<Part> outParts = CastUtils.cast(operation.getOutput().getMessage().getParts().values());
            //System.out.println(" - " + opName);
            Collection<Fault> faults = CastUtils.cast(operation.getFaults().values());
            List<ParameterDetails> parameterDetailsList=new ArrayList<>();
            for (Part inPart : inParts) {
                ParameterDetails parameterDetails=new ParameterDetails();
                parameterDetails.setIn("body");
                parameterDetails = getParameterDetails(inPart.getName(), inPart.getElementName(), inPart.getTypeName(),parameterDetails);
                parameterDetailsList.add(parameterDetails);
            }
            methodObject.setParameters(parameterDetailsList);
            for (Part outPart : outParts) {
                ResponseObject responseObject=new ResponseObject();
                responseObject = getResponseObjectPart(outPart.getName(), outPart.getElementName(), outPart.getTypeName(), responseObject);
                responsesMap.put("200",responseObject);
            }
            for (Fault fault : faults) {
                String faultName=fault.getName();
                ResponseObject responseObject=new ResponseObject();
                responseObject = getResponseObjectPart(faultName, fault.getMessage().getQName(), null, responseObject);
                //System.out.println("-faults..."+fault.getName());
                if("technicalFault".equalsIgnoreCase(faultName)){
                    responsesMap.put("404",responseObject);
                }else if("businessFault".equalsIgnoreCase(faultName)){
                    responsesMap.put("400",responseObject);
                }else if("authenticationFault".equalsIgnoreCase(faultName)){
                    responsesMap.put("401",responseObject);
                }else{
                    responsesMap.put("500",responseObject);
                }
            }
            methodObject.setResponses(responsesMap);
            methodObjectMap.put("post",methodObject);
            pathObjectMap.put("/"+opName,methodObjectMap);
        }
        swagger.setPaths(pathObjectMap);
        swagger.setDefinitions(definitionDetailMap);
    }

    private ResponseObject getResponseObjectPart(String name, QName elementName, QName typeName,ResponseObject responseObject) {

        XmlSchemaType param = (elementName != null)
                ? schema.getElementByName(elementName).getSchemaType()
                : schema.getTypeByName(typeName);
        responseObject.setDescription((elementName != null)?elementName.getLocalPart():null);
        if (param instanceof XmlSchemaComplexType) {
            SchemaObject schemaObject=new SchemaObject();
            Map<Object,Object> jsonMap=new HashMap<>();
            jsonMap.put("$ref","#/definitions/"+responseObject.getDescription());
            schemaObject.setItems(jsonMap);
            responseObject.setSchema(schemaObject);
            definitionDetailMap.put(responseObject.getDescription(),processComplexType((XmlSchemaComplexType) param, name));
        }
        return responseObject;

    }

    private ResponseObject getResponseObjectFault(String name, QName elementName, QName typeName,ResponseObject responseObject) {

        XmlSchemaType param = (elementName != null)
                ? schema.getElementByName(elementName).getSchemaType()
                : schema.getTypeByName(typeName);
        responseObject.setDescription(name);
        if (param instanceof XmlSchemaComplexType) {
            SchemaObject schemaObject=new SchemaObject();
            Map<Object,Object> jsonMap=new HashMap<>();
            jsonMap.put("$ref","#/definitions/"+name);
            schemaObject.setItems(jsonMap);
            responseObject.setSchema(schemaObject);
            definitionDetailMap.put(responseObject.getDescription(),processComplexType((XmlSchemaComplexType) param, name));
        }
        return responseObject;

    }




    private ParameterDetails getParameterDetails(String name, QName elementName, QName typeName,ParameterDetails parameterDetails) {

        XmlSchemaType param = (elementName != null)
                ? schema.getElementByName(elementName).getSchemaType()
                : schema.getTypeByName(typeName);
        parameterDetails.setName((elementName != null)?elementName.getLocalPart():null);
        parameterDetails.setRequired(isOptional(schema.getElementByName(elementName)));
        if (param instanceof XmlSchemaComplexType) {
            SchemaObject schemaObject=new SchemaObject();
            Map<Object,Object> jsonMap=new HashMap<>();
            jsonMap.put("$ref","#/definitions/"+parameterDetails.getName());
            schemaObject.setItems(jsonMap);
            parameterDetails.setSchema(schemaObject);
            definitionDetailMap.put(elementName.getLocalPart(),processComplexType((XmlSchemaComplexType) param, name));
        } else if (param instanceof XmlSchemaSimpleType) {
            parameterDetails.setType(processSimpleType((XmlSchemaSimpleType) param, name, isOptional(schema.getElementByName(elementName))));
        }
        return parameterDetails;

    }


    private DefinitionDetail processComplexType(XmlSchemaComplexType elemType, String name){
        DefinitionDetail definitionDetail=new DefinitionDetail();
        XmlSchemaParticle particle = elemType.getParticle();
        String ret = "";

        if (particle == null) { // WSDL4J fails parsing some advanced definitions
            if (elemType.getUnhandledAttributes() != null) {
                return  definitionDetail;
            } else { // aka. no such element in the WSDL definition
                return definitionDetail;
            }
        }

        List<?> elementList = null;
        if (particle instanceof XmlSchemaSequence) {
            elementList = ((XmlSchemaSequence) particle).getItems();
        } else if (particle instanceof XmlSchemaAll) {
            elementList = ((XmlSchemaAll) particle).getItems();
        } else if (particle instanceof XmlSchemaChoice) {
            elementList = ((XmlSchemaChoice) particle).getItems();
        }
        definitionDetail.setType("object");
        Map<Object,PropertiesObject> definitionsProperties=new HashMap<>();
        for (Object member : elementList) {
            if (member instanceof XmlSchemaElement) {
                XmlSchemaElement element = ((XmlSchemaElement) member);
                XmlSchemaType elementType = element.getSchemaType();
                PropertiesObject propertiesObject=new PropertiesObject();
                propertiesObject.setDescription(element.getName());
                //LOG.info(element.getName());
                if (elementType instanceof XmlSchemaSimpleType) {
                    propertiesObject.setType(processSimpleType((XmlSchemaSimpleType) elementType, element.getName(), isOptional(element)));
                } else if (elementType instanceof XmlSchemaComplexType) {
                    propertiesObject.setType("object");
                    Map<Object,Object> jsonMap=new HashMap<>();
                    jsonMap.put("$ref","#/definitions/"+element.getName());
                    propertiesObject.setItems(jsonMap);
                    definitionDetailMap.put(element.getName(),processComplexType((XmlSchemaComplexType) elementType, element.getName()));
                }
                definitionsProperties.put(element.getName(),propertiesObject);
            }
        }
        definitionDetail.setProperties(definitionsProperties);
        return definitionDetail;
    }

    /**
     * Determines the type name of an XML Schema Simple Type entity
     *
     * @param type       The entity to check
     * @param name       The name of that entity
     * @param isOptional Whether this is defined as optional in the WSDL or not
     * @return The type of the parameter type as a string
     */
    private String processSimpleType(XmlSchemaSimpleType type, String name, boolean isOptional) {
        String tmp = "";
        if (isEnumeration(type)) {
            tmp += (enumeratorValues(type));
        } else {
            tmp = (type.getName());
        }
        //LOG.info("Simple Element...."+tmp);
        return tmp;
    }

    public static boolean isEnumeration(XmlSchemaSimpleType type) {
        try {
            XmlSchemaSimpleTypeRestriction restriction = (XmlSchemaSimpleTypeRestriction) type.getContent();
            List<XmlSchemaFacet> facets = restriction.getFacets();
            for (XmlSchemaFacet facet : facets) {
                if (facet instanceof XmlSchemaEnumerationFacet) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Retrieve the string values for an enumeration.
     */
    private static List<String> enumeratorValues(XmlSchemaSimpleType type) {
        XmlSchemaSimpleTypeRestriction restriction = (XmlSchemaSimpleTypeRestriction) type.getContent();
        List<XmlSchemaFacet> facets = restriction.getFacets();
        List<String> values = new ArrayList<String>();
        for (XmlSchemaFacet facet : facets) {
            XmlSchemaEnumerationFacet enumFacet = (XmlSchemaEnumerationFacet) facet;
            values.add(enumFacet.getValue().toString());
        }
        return values;
    }

    /**
     * Determines if an element is optional
     *
     * @param element The element to check
     * @return True if it is optional, false if not
     */
    private static boolean isOptional(XmlSchemaElement element) {
        return element.getMinOccurs() == 0;
    }

    /**
     * Getter for SOAP port name
     *
     * @return The port name
     */
    public String getPortName() {
        return soapPortName;
    }


}
