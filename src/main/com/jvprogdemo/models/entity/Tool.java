package com.jvprogdemo.models.entity;

import java.util.Objects;

public class Tool extends AbstractEntity {
    private final String code;
    private final String typeName;
    private final String brandName;

    public Tool(String code, String type, String brandName) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Tool may not have a NULL or blank Code.");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Tool may not have a NULL or blank Type Name.");
        }
        if (brandName == null || brandName.isBlank()) {
            throw new IllegalArgumentException("Tool may not have a NULL or blank Brand Name.");
        }
        this.code = code;
        this.typeName = type;
        this.brandName = brandName;
    }
    public String getCode() {
        return code;
    }
    public String getTypeName() {
        return typeName;
    }
    public String getBrandName() {
        return brandName;
    }

    @Override
    public String getKey() {
        return getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tool tool = (Tool) o;
        return Objects.equals(code, tool.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
