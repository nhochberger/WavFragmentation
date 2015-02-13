package controller;

import hochberger.utilities.application.ApplicationProperties;
import hochberger.utilities.application.BasicLoggedApplication;
import view.WavFragmentationGui;

public class WavFragmentationApplication extends BasicLoggedApplication {

	private final ApplicationProperties properties;

	public static void main(final String[] args) {
		setUpLoggingServices(WavFragmentationApplication.class);
		try {
			getLogger().info("Preparing application start");
			final ApplicationProperties properties = new ApplicationProperties();
			final WavFragmentationApplication application = new WavFragmentationApplication(properties);
			application.start();
		} catch (final Exception e) {
			getLogger().fatal(e.getCause(), e);
		}
	}

	public WavFragmentationApplication(final ApplicationProperties properties) {
		super();
		this.properties = properties;
	}

	@Override
	public void start() {
		super.start();
		WavFragmentationGui gui = new WavFragmentationGui(this.properties);
		gui.activate();
	}

	@Override
	public void stop() {
		super.stop();
	}

}
