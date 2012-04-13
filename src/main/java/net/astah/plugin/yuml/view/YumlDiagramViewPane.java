package net.astah.plugin.yuml.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import net.astah.plugin.yuml.builder.ActivityDiagramBuilder;
import net.astah.plugin.yuml.builder.ClassDiagramBuilder;
import net.astah.plugin.yuml.builder.UseCaseDiagramBuilder;

import org.apache.commons.lang3.StringUtils;

import com.change_vision.jude.api.inf.model.IActivityDiagram;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IUseCaseDiagram;

@SuppressWarnings("serial")
public class YumlDiagramViewPane extends JPanel {
	private static final int DEFAULT_URL_FIELD_ROWS = 3;
	private static final int DEFAULT_URL_FIELD_COLUMNS = 120;
	private JTextArea urlField;
	private JLabel yumlImageLabel;
	private JLabel astahImageLabel;
	
	public YumlDiagramViewPane() {
		setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		yumlImageLabel = new JLabel();
		yumlImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yumlImageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		JScrollPane yumlPane = new JScrollPane(yumlImageLabel);
		tabbedPane.addTab("yUML", yumlPane);
		
		astahImageLabel = new JLabel();
		astahImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		astahImageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		JScrollPane astahPane = new JScrollPane(astahImageLabel);
		tabbedPane.addTab("Astah", astahPane);
		add(tabbedPane, BorderLayout.CENTER);
		
		urlField = new JTextArea(DEFAULT_URL_FIELD_ROWS, DEFAULT_URL_FIELD_COLUMNS);
		urlField.setLineWrap(true);
		JScrollPane urlScrollPane = new JScrollPane(urlField);
		JButton copyButton = new JButton("Copy");
		copyButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		copyButton.addMouseListener(new CopyButtonMouseListener());
		JButton repaintButton = new JButton("Repaint");
		repaintButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		repaintButton.addMouseListener(new RepaintButtonMouseListener());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(copyButton);
		buttonPanel.add(repaintButton);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));
		controlPanel.add(urlScrollPane);
		controlPanel.add(buttonPanel);
		add(controlPanel, BorderLayout.NORTH);
	}
	
	class CopyButtonMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection selection = new StringSelection(urlField.getText());
			clipboard.setContents(selection, selection);
		}
	}
	
	class RepaintButtonMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			updateYumlContents(urlField.getText());
		}
	}
	
	public void updateContents(IDiagram diagram) {
		String yumlUrl = "";
		if (diagram instanceof IClassDiagram) {
			yumlUrl = new ClassDiagramBuilder((IClassDiagram) diagram).toYuml();
		} else if (diagram instanceof IActivityDiagram) {
			yumlUrl = new ActivityDiagramBuilder((IActivityDiagram) diagram).toYuml();
		} else if (diagram instanceof IUseCaseDiagram) {
			yumlUrl = new UseCaseDiagramBuilder((IUseCaseDiagram) diagram).toYuml();
		}
		
		updateYumlContents(yumlUrl);
		updateAstahContents(diagram);
	}
	
	private void updateAstahContents(final IDiagram diagram) {
		new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				astahImageLabel.setText("Now drawing...");
				astahImageLabel.setIcon(null);
				String imagePath = diagram.exportImage(System.getProperty("java.io.tmp"), "png", 72);
				return imagePath;
			}
			
			@Override
			public void done() {
				try {
					String imageRelativePath = get();
					String imageAbsolutePath = System.getProperty("java.io.tmp") + File.separator + imageRelativePath;
					astahImageLabel.setText("");
					astahImageLabel.setIcon(new ImageIcon(imageAbsolutePath));
				} catch (Exception e) {
					astahImageLabel.setText("Diagram can't be loaded.");
					astahImageLabel.setIcon(null);
				}
			}
		}.execute();
	}
	
	private void updateYumlContents(final String yumlUrl) {
		urlField.setText(yumlUrl);
		
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				yumlImageLabel.setIcon(null);
				yumlImageLabel.setText("Now drawing...");
				updateYumlImage(yumlUrl);
				return null;
			}
		}.execute();
	}

	private void updateYumlImage(String yumlUrl) {
		Object labelContents = createYumlLabelContents(yumlUrl);
		if (labelContents instanceof Icon) {
			yumlImageLabel.setIcon((Icon) labelContents);
			yumlImageLabel.setText("");
		} else {
			yumlImageLabel.setIcon(null);
			yumlImageLabel.setText((String) labelContents);
		}
	}

	private Object createYumlLabelContents(String yumlUrl) {
		if (StringUtils.isBlank(yumlUrl)) {
			return "";
		}
		
		try {
			ImageIcon imageIcon = new ImageIcon(new URL(yumlUrl));
			int status = imageIcon.getImageLoadStatus();
			if (status == MediaTracker.ABORTED || status == MediaTracker.ERRORED) {
				return "yUML diagram can't be loaded.";
			} else {
				return imageIcon;
			}
		} catch (MalformedURLException e) {
			return e.getLocalizedMessage();
		}
	}
}
