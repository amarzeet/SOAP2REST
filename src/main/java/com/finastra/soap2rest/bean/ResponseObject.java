package com.finastra.soap2rest.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject implements Serializable{

    private String description = null;

    private SchemaObject schema = null;

    private Object headers = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SchemaObject getSchema() {
        return schema;
    }

    public void setSchema(SchemaObject schema) {
        this.schema = schema;
    }

    public Object getHeaders() {
        return headers;
    }

    public void setHeaders(Object headers) {
        this.headers = headers;
    }
}
