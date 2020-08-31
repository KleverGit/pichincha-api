package com.ec.pichincha.filters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kleverhidalgo
 */
public class SimpleGrantedAuthorityMixin {
	
	@JsonCreator
	public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {
	}

}
