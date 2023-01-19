package com.finastra.soap2rest.bean;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfoDetail implements Serializable{

    private String description = null;

    private String version = null;

    private String title = null;

    public InfoDetail value(String value) {
        this.description = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InfoDetail type(String type) {
        this.version = type;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public InfoDetail primary(String primary) {
        this.title = primary;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InfoDetail infoDetail = (InfoDetail) o;
        return Objects.equals(this.description, infoDetail.description) &&
                Objects.equals(this.version, infoDetail.version) &&
                Objects.equals(this.title, infoDetail.title) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, version, title);
    }


    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
