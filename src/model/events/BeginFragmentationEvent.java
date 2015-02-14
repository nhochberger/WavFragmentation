package model.events;

import hochberger.utilities.eventbus.Event;

public class BeginFragmentationEvent implements Event {

	private final String sourceFilePath;
	private final String fps;

	public BeginFragmentationEvent(final String sourceFilePath, final String fps) {
		super();
		this.sourceFilePath = sourceFilePath;
		this.fps = fps;
	}

	@Override
	public void performEvent() {
		// TODO Auto-generated method stub
	}

	public String getSourceFilePath() {
		return this.sourceFilePath;
	}

	public String getFps() {
		return this.fps;
	}
}
