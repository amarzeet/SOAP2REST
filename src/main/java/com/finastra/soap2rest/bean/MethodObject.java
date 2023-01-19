package com.finastra.soap2rest.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MethodObject implements Serializable{

    private List<String> tags = null;

    private String summary = null;

    private String description = null;

    private String operationId = null;

    private List<ParameterDetails> parameters = null;

    private Map<Object,ResponseObject> responses = null;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<ParameterDetails> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDetails> parameters) {
        this.parameters = parameters;
    }

    public Map<Object, ResponseObject> getResponses() {
        return responses;
    }

    public void setResponses(Map<Object, ResponseObject> responses) {
        this.responses = responses;
    }
}
