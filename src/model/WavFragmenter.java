package model;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;
import hochberger.utilities.eventbus.EventReceiver;

import java.io.File;

import model.events.BeginFragmentationEvent;
import model.events.MessageDisplayEvent;

public class WavFragmenter extends SessionBasedObject implements EventReceiver<BeginFragmentationEvent> {

	private final WavBuffer wavBuffer;

	public WavFragmenter(final BasicSession session, final WavBuffer wavBuffer) {
		super(session);
		this.wavBuffer = wavBuffer;
	}

	@Override
	public void receive(final BeginFragmentationEvent event) {
		int fps = 0;
		try {
			fps = Integer.parseInt(event.getFps());
		} catch (NumberFormatException e) {
			session().getEventBus().publish(new MessageDisplayEvent("Invalid fps"));
			return;
		}
		if (0 > fps) {
			session().getEventBus().publish(new MessageDisplayEvent("Invalid fps"));
			return;
		}
		File sourceFile = new File(event.getSourceFilePath());
		if (!sourceFile.exists()) {
			session().getEventBus().publish(new MessageDisplayEvent("Invalid source file"));
			return;
		}
		session().getEventBus().publish(new MessageDisplayEvent(event.getSourceFilePath() + " @ " + event.getFps() + " fps."));
		beginFragmentation(sourceFile, fps);
	}

	private void beginFragmentation(final File file, final int fps) {
		this.wavBuffer.bufferData(file);
		session().getEventBus().publish(new MessageDisplayEvent("Fragmentation finished"));
	}
}
