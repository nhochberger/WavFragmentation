package view;

import hochberger.utilities.eventbus.Event;
import hochberger.utilities.eventbus.EventBus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class PublishOnEventBusActionListener<TYPE extends Event> implements ActionListener {

	private final EventBus eventBus;

	public PublishOnEventBusActionListener(final EventBus eventBus) {
		super();
		this.eventBus = eventBus;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		this.eventBus.publishFromEDT(event());
	}

	protected abstract TYPE event();

}
