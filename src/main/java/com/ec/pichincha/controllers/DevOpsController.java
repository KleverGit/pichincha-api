package com.ec.pichincha.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ec.pichincha.vos.MessageVo;

@RestController
public class DevOpsController {

	/**
	 * POST Method send New Message
	 * @param message
	 * @return
	 */
	@PostMapping(value = "/DevOps",consumes = "application/json", produces = "application/json")
	public ResponseEntity<MessageVo> sendMessage(@RequestBody MessageVo message) {
		MessageVo messageResponse = null;
		try {
			messageResponse = new MessageVo();
			messageResponse.setMessage("Hello " + message.getFrom() + " your message will be send !!");
		} catch (Exception e) {
			return new ResponseEntity<MessageVo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageVo>(messageResponse, HttpStatus.OK);
	}

}
