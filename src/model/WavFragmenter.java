package model;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;
import hochberger.utilities.eventbus.EventReceiver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

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

		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);

			// AudioFormat format = stream.getFormat();
			//
			// float sampleRate = format.getSampleRate();
			// int bitsPerSample = format.getSampleSizeInBits();
			// int channels = format.getChannels();
			// int bitsToStore = (int) (bitsPerSample * channels * sampleRate) /
			// fps;
			// System.err.println("bits per 1/" + fps + " second: " +
			// bitsToStore);
			//
			// // System.err.println("frame length:" + stream.getFrameLength());
			// // System.err.println("frame size: " + format.getFrameSize());
			// // System.err.println("bla bla: " + stream.getFrameLength() *
			// // format.getFrameSize() / 1024);
			//
			// byte[] buffer = new byte[(int) (stream.getFrameLength() *
			// format.getFrameSize())];
			// stream.read(buffer);
			ByteArrayOutputStream foo = new ByteArrayOutputStream();
			AudioSystem.write(stream, Type.WAVE, foo);
			this.wavBuffer.bufferData(foo);
			// for (byte b : buffer) {
			// System.err.println(b);
			// }

			// stream.read(b)

		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		session().getEventBus().publish(new MessageDisplayEvent("Fragmentation finished"));
	}
}
