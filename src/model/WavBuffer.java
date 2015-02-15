package model;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class WavBuffer extends SessionBasedObject {

	private File bufferedData;
	private ByteArrayOutputStream streamBuffer;

	public WavBuffer(final BasicSession session) {
		super(session);
	}

	public void bufferData(final File file) {
		this.bufferedData = file;
	}

	public File getBufferedData() {
		return this.bufferedData;
	}

	public void bufferData(final ByteArrayOutputStream stream) {
		this.streamBuffer = stream;
	}

	public ByteArrayOutputStream getBufferedStreamData() {
		return this.streamBuffer;
	}
}
