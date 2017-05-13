package models.request.Check;

import play.data.validation.Constraints.*;

public class ResultPostRequest {

	@Required
	@Pattern("[\\w]+")
	@MaxLength(15)
	private String name;

	// getter and setter
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
