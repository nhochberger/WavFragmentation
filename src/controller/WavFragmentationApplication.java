package controller;

import hochberger.utilities.application.ApplicationProperties;
import hochberger.utilities.application.BasicLoggedApplication;
import hochberger.utilities.application.session.BasicSession;
import hochberger.utilities.eventbus.EventBus;
import hochberger.utilities.eventbus.SimpleEventBus;
import model.WavBuffer;
import model.WavFragmenter;
import model.WavPlayer;
import model.events.BeginFragmentationEvent;
import model.events.PlayWavEvent;
import view.WavFragmentationGui;

public class WavFragmentationApplication extends BasicLoggedApplication {

	private final WavFragmentationGui gui;
	private final EventBus eventBus;
	private final BasicSession session;
	private final WavFragmenter wavFragmenter;
	private final WavPlayer wavPlayer;
	private final WavBuffer wavBuffer;

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
		this.wavBuffer = new WavBuffer(this.session);
		this.wavFragmenter = new WavFragmenter(this.session, this.wavBuffer);
		this.wavPlayer = new WavPlayer(this.session, this.wavBuffer);

		this.eventBus.register(this.wavFragmenter, BeginFragmentationEvent.class);
		this.eventBus.register(this.wavPlayer, PlayWavEvent.class);

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
