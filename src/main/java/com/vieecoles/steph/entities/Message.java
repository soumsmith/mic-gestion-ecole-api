package com.vieecoles.steph.entities;

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
	private String id;
	private String type;
	private String title;
	private String detail;
	private String styleClass;
}
