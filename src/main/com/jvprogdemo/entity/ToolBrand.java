package com.jvprogdemo.entity;

import java.util.Objects;

public class ToolBrand extends AbstractEntity {
	private final String brandName;
	// I'm sure there'd be more information than just the company name in a real-world scenario

	public ToolBrand(String brandName) {
		if (brandName == null || brandName.isBlank()) {
			throw new IllegalArgumentException("Brand Name may not be NULL or blank.");
		}
		this.brandName = brandName;
	}

	public String getBrandName() {
		return brandName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ToolBrand toolBrand = (ToolBrand) o;
		return Objects.equals(brandName, toolBrand.brandName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(brandName);
	}
}
