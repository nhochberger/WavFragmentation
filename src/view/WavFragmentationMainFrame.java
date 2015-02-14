package view;

import hochberger.utilities.eventbus.EventBus;
import hochberger.utilities.eventbus.EventReceiver;
import hochberger.utilities.gui.EDTSafeFrame;
import hochberger.utilities.gui.StretchingBackgroundedPanel;
import hochberger.utilities.gui.input.SelfHighlightningValidatingTextField;
import hochberger.utilities.gui.input.validator.IntegerStringInputValidator;
import hochberger.utilities.gui.lookandfeel.SetLookAndFeelTo;
import hochberger.utilities.images.loader.ImageLoader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.events.BeginFragmentationEvent;
import model.events.MessageDisplayEvent;
import model.events.PlayWavEvent;
import net.miginfocom.swing.MigLayout;

public class WavFragmentationMainFrame extends EDTSafeFrame implements EventReceiver<MessageDisplayEvent> {

	private JLabel messageLabel;
	private final EventBus eventBus;

	public WavFragmentationMainFrame(final String title, final EventBus eventBus) {
		super(title);
		this.eventBus = eventBus;
	}

	@Override
	protected void buildUI() {
		exitOnClose();
		center();
		SetLookAndFeelTo.nimbusLookAndFeel();
		setSize(300, 200);
		notResizable();
		setContentPane(new StretchingBackgroundedPanel(ImageLoader.loadImage("graphics/background_white.png")));
		useLayoutManager(new MigLayout("nogrid", "[center]"));

		final SelfHighlightningValidatingTextField fpsInput = new SelfHighlightningValidatingTextField();
		fpsInput.setText("20");
		fpsInput.setHorizontalAlignment(JTextField.RIGHT);
		fpsInput.addValidator(new IntegerStringInputValidator());
		add(fpsInput, "wmin 45");
		add(new JLabel("files per second"), "wrap");

		final JTextField sourceFileLabel = new JTextField("Choose folder");
		sourceFileLabel.setEditable(false);
		add(sourceFileLabel, "wmin 200");
		JButton sourceFileChooserButton = new JButton("Choose");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(".wav-files", "wav"));
		sourceFileChooserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				fileChooser.showOpenDialog(WavFragmentationMainFrame.this.frame());
				File selectedFile = fileChooser.getSelectedFile();
				if (null != selectedFile) {
					sourceFileLabel.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		add(sourceFileChooserButton, "wrap");

		JButton beginFragmentationButton = new JButton("frag");
		beginFragmentationButton.addActionListener(new PublishOnEventBusActionListener<BeginFragmentationEvent>(this.eventBus) {
			@Override
			protected BeginFragmentationEvent event() {
				return new BeginFragmentationEvent(sourceFileLabel.getText(), fpsInput.getText());
			};
		});
		add(beginFragmentationButton);

		JButton playButton = new JButton("play");
		playButton.addActionListener(new PublishOnEventBusActionListener<PlayWavEvent>(this.eventBus) {
			@Override
			protected PlayWavEvent event() {
				return new PlayWavEvent();
			};
		});
		add(playButton, "wrap push");

		this.messageLabel = new JLabel("Choose a wav-file.");
		add(this.messageLabel, "left, wmin 280, wmax 280");
	}

	@Override
	public void receive(final MessageDisplayEvent event) {
		this.messageLabel.setText(event.getMessageText());
	}
}
