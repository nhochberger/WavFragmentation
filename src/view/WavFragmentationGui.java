package view;

import hochberger.utilities.application.ApplicationProperties;
import hochberger.utilities.gui.ApplicationGui;
import hochberger.utilities.gui.EDTSafeFrame;
import hochberger.utilities.text.Text;

public class WavFragmentationGui implements ApplicationGui {

	private final ApplicationProperties properties;
	EDTSafeFrame frame;

	public WavFragmentationGui(final ApplicationProperties properties) {
		super();
		this.properties = properties;
		this.frame = new WavFragmentationMainFrame(this.properties.title() + Text.space() + this.properties.version());
	}

	@Override
	public void activate() {
		this.frame.show();
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
	}
}
