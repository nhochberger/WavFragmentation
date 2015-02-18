package model;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

public class WavBuffer extends SessionBasedObject {

	LinkedList<ByteArrayOutputStream> bufferedData;

	public WavBuffer(final BasicSession session) {
		super(session);
		this.bufferedData = new LinkedList<>();
	}

	public void bufferData(final ByteArrayOutputStream stream) {
		this.bufferedData.add(stream);
	}

	public LinkedList<ByteArrayOutputStream> getBufferedData() {
		return this.bufferedData;
	}

	public void clearBuffer() {
		session().getLogger().info("Buffer cleared. Removed " + this.bufferedData.size() + " elements.");
		this.bufferedData.clear();
	}
}
