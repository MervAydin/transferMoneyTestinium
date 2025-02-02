package com.testinium.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents information about a web element, including its key, value, type, and index.
 */
public class ElementInfo {

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("index")
    @Expose
    private int index;

    /**
     * Default constructor.
     */
    public ElementInfo(String id, String exampleId) {
    }

    /**
     * Constructor to initialize all fields.
     */
    public ElementInfo() {
        this.key = key;
        this.value = value;
        this.type = type;
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementInfo that = (ElementInfo) o;

        if (index != that.index) return false;
        if (!key.equals(that.key)) return false;
        if (!value.equals(that.value)) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + index;
        return result;
    }

    @Override
    public String toString() {
        return "ElementInfo{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", index=" + index +
                '}';
    }
}