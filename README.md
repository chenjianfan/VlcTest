![image](https://github.com/chenjianfan/VlcTest/blob/master/images/2.png)


HttpPushScreenTest类中直接运行mian命令

    	NativeLibrary.addSearchPath(
				// 此处的路径是你安装vlc的路径加上sdk和lib下面会给出详细解释
				RuntimeUtil.getLibVlcLibraryName(), "E:\\wuyuinfo\\Test\\lib");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		mediaPlayerFactory = new MediaPlayerFactory(args);
		HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
		mediaPlayer.playMedia("screen:// ", getMediaOptions("127.0.0.1", 5888));
		Thread.currentThread().join();



PlayerMain类中，直接运行，其中的127.0.0.1。就是录屏端的ip.

    EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MyWindow();
					frame.setVisible(true);
                    //是
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


