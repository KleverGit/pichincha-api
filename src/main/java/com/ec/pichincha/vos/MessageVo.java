package com.ec.pichincha.vos;

import lombok.Getter;
import lombok.Setter;

/**
 * Vo that represents plainData for rest API
 * @author HIDALGOPC
 *
 */
@Getter
@Setter
public class MessageVo {
	
	private String message;
	private String to;
	private String from; 
	private Integer timeToLifeSec ; 
}
