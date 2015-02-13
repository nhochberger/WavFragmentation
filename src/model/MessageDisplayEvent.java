package model;

import hochberger.utilities.eventbus.Event;

public class MessageDisplayEvent implements Event {

	String text;

	public MessageDisplayEvent(final String text) {
		super();
		this.text = text;
	}

	@Override
	public void performEvent() {
		// TODO Auto-generated method stub
	}

	public String getMessageText() {
		return this.text;
	}
}
