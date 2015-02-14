package model;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;
import hochberger.utilities.eventbus.EventReceiver;
import model.events.MessageDisplayEvent;
import model.events.PlayWavEvent;

public class WavPlayer extends SessionBasedObject implements EventReceiver<PlayWavEvent> {

	public WavPlayer(final BasicSession session) {
		super(session);
	}

	@Override
	public void receive(final PlayWavEvent event) {
		playFromBuffer();
	}

	private void playFromBuffer() {
		session().getEventBus().publish(new MessageDisplayEvent("Replaying wav from buffer"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session().getEventBus().publish(new MessageDisplayEvent("Replay finished."));
	}
}
