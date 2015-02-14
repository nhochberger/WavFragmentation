package view;

import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.application.session.SessionBasedObject;
import hochberger.utilities.gui.ApplicationGui;
import hochberger.utilities.text.Text;
import model.events.MessageDisplayEvent;

public class WavFragmentationGui extends SessionBasedObject implements ApplicationGui {

	WavFragmentationMainFrame mainFrame;

	public WavFragmentationGui(final BasicSession session) {
		super(session);
		this.mainFrame = new WavFragmentationMainFrame(session.getProperties().title() + Text.space() + session.getProperties().version(), session.getEventBus());
		session.getEventBus().register(this.mainFrame, MessageDisplayEvent.class);
	}

	@Override
	public void activate() {
		logger().info("GUI activated");
		this.mainFrame.show();
	}

	@Override
	public void deactivate() {
		logger().info("GUI deactivated");
	}

}
