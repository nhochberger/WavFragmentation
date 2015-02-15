package model;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;
import hochberger.utilities.eventbus.EventReceiver;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.events.MessageDisplayEvent;
import model.events.PlayWavEvent;

public class WavPlayer extends SessionBasedObject implements EventReceiver<PlayWavEvent> {

	private final WavBuffer wavBuffer;

	public WavPlayer(final BasicSession session, final WavBuffer wavBuffer) {
		super(session);
		this.wavBuffer = wavBuffer;
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
		File bufferedData = this.wavBuffer.getBufferedData();

		AudioInputStream stream;
		try {
			Clip clip = AudioSystem.getClip();
			stream = AudioSystem.getAudioInputStream(bufferedData);
			clip.open(stream);
			clip.start();
			session().getEventBus().publish(new MessageDisplayEvent("Replay finished."));
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			session().getEventBus().publish(new MessageDisplayEvent("Problem with data. See logs."));
			session().getLogger().error("Problem while playing av file.", e);
		}
	}
}
