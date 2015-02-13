package view;

import hochberger.utilities.gui.EDTSafeFrame;
import hochberger.utilities.gui.StretchingBackgroundedPanel;
import hochberger.utilities.gui.input.SelfHighlightningValidatingTextField;
import hochberger.utilities.gui.input.validator.IntegerStringInputValidator;
import hochberger.utilities.gui.lookandfeel.SetLookAndFeelTo;
import hochberger.utilities.images.loader.ImageLoader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class WavFragmentationMainFrame extends EDTSafeFrame {

	public WavFragmentationMainFrame(final String title) {
		super(title);
	}

	@Override
	protected void buildUI() {
		exitOnClose();
		center();
		SetLookAndFeelTo.nimbusLookAndFeel();
		setSize(300, 200);
		notResizable();
		setContentPane(new StretchingBackgroundedPanel(ImageLoader.loadImage("graphics/background_white.png")));
		useLayoutManager(new MigLayout("nogrid, debug", "[center]"));
		SelfHighlightningValidatingTextField fpsInput = new SelfHighlightningValidatingTextField();
		fpsInput.setText("20");
		fpsInput.setHorizontalAlignment(JTextField.RIGHT);
		fpsInput.addValidator(new IntegerStringInputValidator());
		add(fpsInput, "wmin 45");
		add(new JLabel("files per second"), "wrap");

		JTextField folderLabel = new JTextField("Choose folder");
		folderLabel.setEditable(false);
		add(folderLabel, "wmin 200");
		JButton folderChooserbutton = new JButton("Choose");
		add(folderChooserbutton, "wrap");

		JButton beginFragmentationButton = new JButton("frag");
		add(beginFragmentationButton);
		JButton playButton = new JButton("play");
		add(playButton, "wrap push");

		JLabel messageLabel = new JLabel("Messages...");
		add(messageLabel, "left, wmin 280, wmax 280");
	}
}
