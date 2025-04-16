package org.example.springlab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String object;
    private String field;
    private Object value;

    public ResourceNotFoundException(String object, String field, Object value) {
        super(String.format("%s with %s '%s' not found", object, field, value));
        this.object = object;
        this.field = field;
        this.value = value;
    }

    public String getObject() { return object; }

    public String getField() { return field; }

    public Object getValue() { return value; }
}
