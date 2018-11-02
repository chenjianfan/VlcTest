package com.iag.Test;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class HttpPushScreenTest {

	static MediaPlayerFactory mediaPlayerFactory;

	public static void main(String[] args) throws Exception {

		NativeLibrary.addSearchPath(
				// 此处的路径是你安装vlc的路径加上sdk和lib下面会给出详细解释
				RuntimeUtil.getLibVlcLibraryName(), "E:\\wuyuinfo\\Test\\lib");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		mediaPlayerFactory = new MediaPlayerFactory(args);
		HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
		mediaPlayer.playMedia("screen:// ", getMediaOptions("127.0.0.1", 5888));
		Thread.currentThread().join();
	}

	private static String formatHttpStream(String serverAddress, int serverPort) {
		StringBuilder sb = new StringBuilder(100);

		sb.append(":sout=#transcode{vcodec=h264,vb072}:duplicate{dst=std{access=http,mux=ts,");
		sb.append("dst=");
		sb.append(serverAddress);
		sb.append(':');
		sb.append(serverPort);
		sb.append("}}");
		return sb.toString();
	}

	private static String[] getMediaOptions(String serverAddress, int serverPort) {
		// return new String[] { formatHttpStream(serverAddress, serverPort),
		// ":screen-fps=30", ":screen-caching=100" };

		return new String[] { formatHttpStream(serverAddress, serverPort), ":screen-fps=30" };

	}

}
