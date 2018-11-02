package com.iag.Test;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

/**
 * Hello world! :sout=#rtp{sdp=rtsp://:8554/} :no-sout-all :sout-keep
 */
public class PlayerMain {

	static MyWindow frame;

	public static void main(String[] args) {
		NativeLibrary.addSearchPath(
				// 此处的路径是你安装vlc的路径加上sdk和lib下面会给出详细解释
				RuntimeUtil.getLibVlcLibraryName(), "E:\\wuyuinfo\\Test\\lib");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MyWindow();
					frame.setVisible(true);

					frame.getMediaPlayer().playMedia("http://127.0.0.1:5888");
					new SwingWorker<String, Integer>() {
						@Override
						protected String doInBackground() throws Exception {
							while (true) {
								long total = frame.getMediaPlayer().getLength();
								long curr = frame.getMediaPlayer().getTime();
								float percent = (float) curr / total;
								publish((int) (percent * 100));
								Thread.sleep(100);
							}
						}
						protected void process(java.util.List<Integer> chunks) {
							for (int v : chunks) {
								frame.getProgressBar().setValue(v);
							}
						};

					}.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 播放按钮
	public static void play() {
		frame.getMediaPlayer().play();
	}

	// 暂停按钮
	public static void pause() {
		frame.getMediaPlayer().pause();
	}

	// 停止按钮
	public static void stop() {
		frame.getMediaPlayer().stop();
	}

	// 跳转按钮
	public static void jumpTo(float to) {
		frame.getMediaPlayer().setTime((long) (to * frame.getMediaPlayer().getLength()));
	}

	// 音量按钮
	public static void setvol(int v) {
		frame.getMediaPlayer().setVolume(v);
	}

	// 打开文件
	public static void openVideo() {
		JFileChooser chooser = new JFileChooser();
		int v = chooser.showOpenDialog(null);
		if (v == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			frame.getMediaPlayer().playMedia(file.getAbsolutePath());
		}
	}

	// 退出
	public static void exit() {
		frame.getMediaPlayer().release();
		System.exit(0);
	}

}
