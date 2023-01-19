package com.finastra.soap2rest.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class Swagger implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String swagger = null;

    private InfoDetail info = null;

    private String host=null;

    private String basePath = null;

    private List<tagDetail> tags = null;

    private List<String> schemes = null;

    private List<String> consumes = null;

    private List<String> produces = null;

    private Map<Object,Map<Object,MethodObject>> paths = null;

    private Map<Object,DefinitionDetail> definitions = null;

    private Map<Object,Object> parameters = null;

    private Object securityDefinitions=null;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Object getSecurityDefinitions() {
        return securityDefinitions;
    }

    public void setSecurityDefinitions(Object securityDefinitions) {
        this.securityDefinitions = securityDefinitions;
    }

    public Swagger schemas(InfoDetail schemas) {
        this.info = schemas;
        return this;
    }

    public InfoDetail getInfo() {
        return info;
    }

    public void setInfo(InfoDetail info) {
        this.info = info;
    }

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<tagDetail> getTags() {
        return tags;
    }

    public void setTags(List<tagDetail> tags) {
        this.tags = tags;
    }

    public List<String> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<String> schemes) {
        this.schemes = schemes;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    public Map<Object, Map<Object, MethodObject>> getPaths() {
        return paths;
    }

    public void setPaths(Map<Object, Map<Object, MethodObject>> paths) {
        this.paths = paths;
    }

    public Map<Object, DefinitionDetail> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<Object, DefinitionDetail> definitions) {
        this.definitions = definitions;
    }

    public Map<Object, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Object, Object> parameters) {
        this.parameters = parameters;
    }
}
