package com.finastra.soap2rest.bean;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParameterDetails implements Serializable{

    private String name = null;

    private String in = null;

    private String description = null;

    private boolean required = false;

    private String type = null;

    private String format = null;

    private SchemaObject schema = null;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public SchemaObject getSchema() {
        return schema;
    }

    public void setSchema(SchemaObject schema) {
        this.schema = schema;
    }


}
