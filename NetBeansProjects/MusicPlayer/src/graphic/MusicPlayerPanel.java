package graphic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.AbstractListModel;

import sound.Sound;
import utils.Constants.LoopMode;
import utils.IO;
import utils.IO.MessageType;

public class MusicPlayerPanel extends MainPanel implements sound.LineListener {

	private static final long serialVersionUID = -6094447515371261862L;

	private MusicPlayerControlPanel controls;
	private javax.swing.JList<String> fileList;
	private javax.swing.JList<String> playlistList;
	private utils.Dictionary<File, String> library;
	private java.util.LinkedList<File> playlist;
	private boolean dragEn, random;
	private int playlistIndex;
	private LoopMode loopmode;
	private java.util.LinkedList<Integer> playedStack;

	public MusicPlayerPanel() {
		init();
	}

	@SuppressWarnings("serial")
	private void init() {
		playedStack = new java.util.LinkedList<Integer>();
		dragEn = false;
		random = false;
		loopmode = utils.Constants.LoopMode.LOOP_NONE;
		library = new utils.Dictionary<>();
		playlist = new java.util.LinkedList<>();
		playlistIndex = -1;
		controls = new MusicPlayerControlPanel(this);
		fileList = new javax.swing.JList<>();
		playlistList = new javax.swing.JList<>();
		fileList.setModel(new AbstractListModel<String>() {

			@Override
			public String getElementAt(int i) {
				return library.get(i);
			}

			@Override
			public int getSize() {
				return library.size();
			}
		});
		fileList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					addSelectedFiles();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				dragEn = true;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				dragEn = false;
			}
		});
		playlistList.setModel(new AbstractListModel<String>() {

			@Override
			public String getElementAt(int arg0) {
				return playlist.get(arg0).getName();
			}

			@Override
			public int getSize() {
				return playlist.size();
			}
		});
		playlistList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					playlistIndex = playlistList.getSelectedIndex();
					playSelected();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (dragEn) {
					dragEn = false;
					addSelectedFiles();
				}
			}
		});
		File[] soundfiles = Sound.listSoundFilesInDirectory(Sound.SOUNDFILE);
		for (File file : soundfiles) {
			addToLibrary(file, false);
		}
		this.setLayout(new BorderLayout());
		javax.swing.JPanel mainPanel = new javax.swing.JPanel(new GridLayout(1, 2));
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(fileList);
		mainPanel.add(scrollPane);
		scrollPane = new javax.swing.JScrollPane(playlistList);
		mainPanel.add(scrollPane);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(controls, BorderLayout.SOUTH);
		this.addKeyListener(this);
		for (java.awt.Component c : this.getComponents())
			c.addKeyListener(this);
		this.playlistList.addKeyListener(this);
		this.fileList.addKeyListener(this);
		Sound.addLineListener(this);
	}

	@SuppressWarnings("serial")
	public boolean addToPlaylist(File... files) {
		File[] addedfiles = Sound.initClips(files);
		if (addedfiles != null) {
			for (File f : addedfiles)
				playlist.add(f);
			playlistList.setModel(new AbstractListModel<String>() {

				@Override
				public String getElementAt(int arg0) {
					return playlist.get(arg0).getName();
				}

				@Override
				public int getSize() {
					return playlist.size();
				}
			});
			return true;
		} else {
			return false;
		}
	}

	private void addSelectedFiles() {
		if (!fileList.isSelectionEmpty()) {
			boolean needInit = false;
			int[] indices = fileList.getSelectedIndices();
			final File[] files = new File[indices.length];
			for (int i = 0; i < indices.length; i++) {
				files[i] = library.getKey(indices[i]);
				if (!Sound.containsSoundfile(files[i]))
					needInit = true;
			}
			if (needInit)
				new Thread(new Runnable() {
					@Override
					public void run() {
						ProgressPanel.showProgressDialog(MainFrame.FRAME);
					}
				}).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					addToPlaylist(files);
					ProgressPanel.closeDialog();
				}
			}).start();
		}
	}

	public void playSelected() {
		if (playlistIndex >= 0 && playlistIndex < playlist.size()) {
			Sound.playSoundClip(playlist.get(playlistIndex), 1);
			if (playlistIndex != playlistList.getSelectedIndex())
				playlistList.setSelectedIndex(playlistIndex);
		}
	}

	public void playPrevious() {
		if (playedStack.size() > 0) {
			playlistIndex = playedStack.pop();
		} else {
			this.playlistIndex--;
			if (this.playlistIndex < 0)
				this.playlistIndex = playlist.size() - 1;
		}
		playSelected();
	}

	public void playNext() {
		playedStack.push(playlistIndex);
		if (random && playlist.size() > 1) {
			int index;
			do {
				index = (int) (Math.random() * playlist.size());
			} while (index == playlistIndex);
			playlistIndex = index;
		} else {
			this.playlistIndex++;
			if (this.playlistIndex >= playlist.size())
				this.playlistIndex = 0;
		}
		playSelected();
	}

	@SuppressWarnings("serial")
	public boolean addToLibrary(File file, boolean recursive) {
		file = file.getAbsoluteFile();
		boolean success = false;
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = Sound.listSoundFilesInDirectory(file);
				for (File soundfile : files) {
					if (library.add(soundfile, soundfile.getName())) {
						IO.println("added " + soundfile + " to library", IO.MessageType.DEBUG);
						success = true;
					} else
						IO.println("library already contains key: " + soundfile,
						IO.MessageType.DEBUG);
				}
			} else {
				success = library.add(file, file.getName());
				if (!success)
					IO.println("library already contains key: " + file, IO.MessageType.DEBUG);
			}
			fileList.setModel(new AbstractListModel<String>() {

				@Override
				public String getElementAt(int i) {
					return library.get(i);
				}

				@Override
				public int getSize() {
					return library.size();
				}
			});
			if (recursive) {
				for (File subdir : file.listFiles(new FileFilter() {

					@Override
					public boolean accept(File arg0) {
						return arg0.isDirectory();
					}
				})) {
					addToLibrary(subdir, true);
				}
			}
		} else
			IO.println(file + " doesnt exist", MessageType.ERROR);
		return success;
	}

	@Override
	public void onSelect() {
		requestFocus();
	}

	@Override
	public void onDisselect() {
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
		if (key != null) {
			switch (key) {
				case KEY_LEFT:
					controls.seekbar.setValue(controls.seekbar.getValue() - 1000);
					break;
				case KEY_RIGHT:
					controls.seekbar.setValue(controls.seekbar.getValue() + 1000);
					break;
				case KEY_ENTER:
					addSelectedFiles();
				case KEY_PLAY:
					if (Sound.isPlaying())
						Sound.pause();
					else
						Sound.play();
				default:
					printNext();
					break;
			}
		} else {
			IO.println("unsupported keypress", MessageType.DEBUG);
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
	}

	@Override
	public void update(LineEvent evt) {
		if (evt == sound.LineListener.LineEvent.START) {
			controls.seekbar.setRefreshTimerPaused(false);
			controls.seekbar.setMaximum((int) (Sound.getMicrosecondLength() / 1000));
		} else if (evt == sound.LineListener.LineEvent.STOP) {
			// controls.seekbar.setRefreshTimerPaused(true);
			if (Sound.getMicrosecondLength() / 1000 == Sound.getMillisecondPosition()) {
				if (loopmode == LoopMode.LOOP_ALL) {
					playNext();
				} else if (loopmode == LoopMode.LOOP_THIS) {
					playSelected();
				} else {
					Sound.stop();
				}
			}
		}
	}

	class MusicPlayerControlPanel extends javax.swing.JPanel implements sound.LineListener,
	ActionListener {
		private static final long serialVersionUID = -7785387609715436241L;
		private final MusicPlayerPanel parent;
		private SeekBar seekbar;
		private javax.swing.JLabel clipLabel;
		private javax.swing.JButton prevBtn, nextBtn, playpauseBtn, stopBtn, loopModeBtn;
		private javax.swing.JCheckBox randCBox;

		MusicPlayerControlPanel(MusicPlayerPanel parent) {
			this.parent = parent;
			init();
		}

		private void init() {
			seekbar = new SeekBar();
			clipLabel = new javax.swing.JLabel("no clip selected");
			prevBtn = new javax.swing.JButton("<<<");
			prevBtn.addActionListener(this);
			prevBtn.setFocusable(false);
			playpauseBtn = new javax.swing.JButton("Play");
			playpauseBtn.addActionListener(this);
			playpauseBtn.setFocusable(false);
			nextBtn = new javax.swing.JButton(">>>");
			nextBtn.addActionListener(this);
			nextBtn.setFocusable(false);
			stopBtn = new javax.swing.JButton("Stop");
			stopBtn.addActionListener(this);
			stopBtn.setFocusable(false);
			randCBox = new javax.swing.JCheckBox("Random");
			randCBox.addActionListener(this);
			randCBox.setFocusable(false);
			loopModeBtn = new javax.swing.JButton(parent.loopmode.toString());
			loopModeBtn.addActionListener(this);
			loopModeBtn.setFocusable(false);
			javax.swing.JPanel btnPanel = new javax.swing.JPanel();
			btnPanel.setLayout(new FlowLayout());
			btnPanel.add(prevBtn);
			btnPanel.add(playpauseBtn);
			btnPanel.add(nextBtn);
			btnPanel.add(stopBtn);
			btnPanel.add(loopModeBtn);
			btnPanel.add(randCBox);
			btnPanel.add(clipLabel);
			this.setLayout(new BorderLayout());
			this.add(btnPanel, BorderLayout.CENTER);
			this.add(seekbar, BorderLayout.NORTH);
			Sound.addLineListener(this);
		}

		@Override
		public void update(sound.LineListener.LineEvent evt) {
			if (evt == sound.LineListener.LineEvent.START) {
				playpauseBtn.setText("Pause");
				clipLabel.setText(parent.playlist.get(parent.playlistIndex).getName());
			} else if (evt == sound.LineListener.LineEvent.STOP) {
				playpauseBtn.setText("Play");
			}
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource().equals(prevBtn)) {
				parent.playPrevious();
			} else if (evt.getSource().equals(nextBtn)) {
				parent.playNext();
			} else if (evt.getSource().equals(playpauseBtn)) {
				if (Sound.isPlaying())
					Sound.pause();
				else {
					if (parent.playlistIndex < 0 && parent.playlist.size() > 0) {
						parent.playlistIndex = 0;
						parent.playSelected();
					} else
						Sound.play();
				}
			} else if (evt.getSource().equals(stopBtn)) {
				Sound.pause();
				Sound.setMicrosecondPosition(0);
			} else if (evt.getSource().equals(randCBox)) {
				parent.random = randCBox.isSelected();
			} else if (evt.getSource().equals(loopModeBtn)) {
				parent.loopmode = parent.loopmode.getNext();
				loopModeBtn.setText(parent.loopmode.toString());
			}
		}
	}
}
