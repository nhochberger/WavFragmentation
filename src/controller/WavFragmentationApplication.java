package controller;

import hochberger.utilities.application.ApplicationProperties;
import hochberger.utilities.application.BasicLoggedApplication;
import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.eventbus.EventBus;
import hochberger.utilities.eventbus.SimpleEventBus;
import view.WavFragmentationGui;

public class WavFragmentationApplication extends BasicLoggedApplication {

	private final WavFragmentationGui gui;
	private final EventBus eventBus;
	private final BasicSession session;

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
		this.eventBus = new SimpleEventBus();
		this.session = new BasicSession(properties, this.eventBus, logger());
		this.gui = new WavFragmentationGui(this.session);
		Runtime.getRuntime().addShutdownHook(new Thread("Shutdown Hook") {
			@Override
			public void run() {
				super.run();
				WavFragmentationApplication.this.stop();
			}
		});
	}

	@Override
	public void start() {
		super.start();
		this.gui.activate();
	}

	@Override
	public void stop() {
		super.stop();
	}
}
