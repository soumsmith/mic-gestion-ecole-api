package com.vieecoles.ressource.steph.entities;

import lombok.Data;

@Data
public class Message {

	public Message() {
		super();
	}
	public Message(String type, String title, String detail) {
		super();
		this.type = type;
		this.title = title;
		this.detail = detail;
	}
	private String type;
	private String title;
	private String detail;
}
