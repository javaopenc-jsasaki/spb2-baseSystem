package com.example.demo.form;

import jakarta.validation.constraints.Size;

public class Form {
	@Size(min=1, max=10, message="1～10文字以内にしてください。")
	private String name1;
	
	private String searchString;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}
	
}
