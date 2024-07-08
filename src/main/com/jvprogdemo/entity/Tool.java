package com.jvprogdemo.entity;

import java.util.Objects;

public class Tool extends AbstractEntity {
    private final String toolCode;
    private final ToolType toolType;
    private final ToolBrand toolBrand;

    public Tool(String toolCode, ToolType toolType, ToolBrand toolBrand) {
        if (toolCode == null || toolCode.isBlank()) {
            throw new IllegalArgumentException("Tool may not have a NULL or blank tool code.");
        }
        if (toolType == null) {
            throw new IllegalArgumentException("Tool Type may not be NULL.");
        }
        if (toolBrand == null) {
            throw new IllegalArgumentException("Tool Brand may not be NULL.");
        }
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
    }
    public String getToolCode() {
        return toolCode;
    }
    public ToolType getToolType() {
        return toolType;
    }
    public ToolBrand getToolBrand() {
        return toolBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tool tool = (Tool) o;
        return Objects.equals(toolCode, tool.toolCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(toolCode);
    }
}
