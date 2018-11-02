package com.iag.Test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class MyWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	EmbeddedMediaPlayerComponent playerComponent;
	private JPanel panel;
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnStop;
	private JPanel controlpanel;
	private JProgressBar progress;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem openmenu;
	private JMenuItem closemenu;
	private JSlider slider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public MyWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menu = new JMenu("\u6587\u4EF6");
		menuBar.add(menu);

		openmenu = new JMenuItem("\u6253\u5F00");
		menu.add(openmenu);

		closemenu = new JMenuItem("\u9000\u51FA");
		menu.add(closemenu);

		openmenu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PlayerMain.openVideo();
			}

//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				PlayerMain.openVideo();
//			}
		});

		closemenu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PlayerMain.exit();
			}
			

//			public void actionPerformed(ActionEvent e) {
//
//				PlayerMain.exit();
//			}
		});

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel Videopanel = new JPanel();
		contentPane.add(Videopanel, BorderLayout.CENTER);
		Videopanel.setLayout(new BorderLayout(0, 0));

		playerComponent = new EmbeddedMediaPlayerComponent();
		Videopanel.add(playerComponent);

		panel = new JPanel();
		Videopanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		controlpanel = new JPanel();
		panel.add(controlpanel);

		btnStop = new JButton("stop");
		controlpanel.add(btnStop);

		btnPlay = new JButton("play");
		controlpanel.add(btnPlay);

		btnPause = new JButton("pause");
		controlpanel.add(btnPause);

		slider = new JSlider();
		slider.setValue(100);
		slider.setMaximum(120);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				PlayerMain.setvol(slider.getValue());
			}
		});
		controlpanel.add(slider);

		progress = new JProgressBar();
		progress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				PlayerMain.jumpTo(((float) x / progress.getWidth()));

			}
		});
		progress.setStringPainted(true);
		panel.add(progress, BorderLayout.NORTH);
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayerMain.pause();
			}
		});
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayerMain.play();
			}
		});
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				PlayerMain.stop();
			}
		});
	}

	public EmbeddedMediaPlayer getMediaPlayer() {

		return playerComponent.getMediaPlayer();
	}

	public JProgressBar getProgressBar() {

		return progress;
	}

}
