package com.finastra.soap2rest.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefinitionDetail implements Serializable{

    private String type = null;

    private List<String> required = null;

    private String description = null;

    private String title = null;

    private Map<Object,PropertiesObject> properties = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<Object, PropertiesObject> getProperties() {
        return properties;
    }

    public void setProperties(Map<Object, PropertiesObject> properties) {
        this.properties = properties;
    }
}
