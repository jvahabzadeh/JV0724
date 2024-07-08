package com.jvprogdemo.entity;

import java.util.Objects;

public class Tool extends AbstractEntity {
    private final String code;
    private final String type;
    private final String brand;

    public Tool(String code, String type, String brand) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Tool may not have a NULL or blank Code.");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Tool may not have a NULL or blank Type.");
        }
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Tool may not have a NULL or blank Brand.");
        }
        this.code = code;
        this.type = type;
        this.brand = brand;
    }
    public String getCode() {
        return code;
    }
    public String getType() {
        return type;
    }
    public String getBrand() {
        return brand;
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
