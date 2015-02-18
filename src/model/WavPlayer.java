package model;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;
import hochberger.utilities.eventbus.EventReceiver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
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
		session().getEventBus().publish(new MessageDisplayEvent("Replaying " + this.wavBuffer.getBufferedData().size() + " chunks from buffer"));
		AudioInputStream stream;
		try {
			Clip clip = AudioSystem.getClip();
			clip.addLineListener(new LineListener() {
				@Override
				public void update(final LineEvent e) {
					if (LineEvent.Type.START == e.getType()) {
						session().getEventBus().publish(new MessageDisplayEvent("Starting replay."));
					}
					if (LineEvent.Type.STOP == e.getType()) {
						session().getEventBus().publish(new MessageDisplayEvent("Replay finished."));
					}
				}
			});
			for (ByteArrayOutputStream bufferedStream : this.wavBuffer.getBufferedData()) {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(bufferedStream.toByteArray());
				stream = AudioSystem.getAudioInputStream(inputStream);
				clip.open(stream);
				clip.start();
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			session().getEventBus().publish(new MessageDisplayEvent("Problem with data. See logs."));
			session().getLogger().error("Problem while playing av file.", e);
		}
	}
}
