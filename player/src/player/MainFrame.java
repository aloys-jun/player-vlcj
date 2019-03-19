package player;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Canvas;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

		private JPanel contentPane;
		private JPanel panel;
		private JPanel controlPanel;
		private JProgressBar progressBar;
		private JSlider volumControlerSlider;
		private JMenuBar menuBar;
		private JMenu mnFile;
		private JMenuItem mntmOpenVideo;
		private JMenuItem mntmOpenSubtitle;
		private JButton stopButton;
		private JButton playButton;
		private JButton forwardButton;
		private JButton backwordButton;
		private JButton FullScreenButton;
		private JLabel volumLabel;
		private JPanel progressTimePanel;
		private JLabel currentLabel;
		private JLabel totalLabel;
		private EmbeddedMediaPlayerComponent playerComponent;
		private int flag = 0;

		/**
		 * Create the frame.
		 */
		public MainFrame() {
			setTitle("徐小俊专属播放器");
			//设置图标
			setIconImage(new ImageIcon("picture/resizeApi.png").getImage());

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 800, 600);
			
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			mnFile = new JMenu("文件");
			menuBar.add(mnFile);

			mntmOpenVideo = new JMenuItem("视频");
			mntmOpenVideo.setSelected(true);
			mnFile.add(mntmOpenVideo);

			mntmOpenSubtitle = new JMenuItem("字幕");
			mnFile.add(mntmOpenSubtitle);

			mntmOpenVideo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//打开视频操作
					Player.openVedio();
				}
			});

			mntmOpenSubtitle.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//打开字幕操作
					Player.openSubtitle();
				}
			});

			contentPane = new JPanel();
			contentPane.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentMoved(ComponentEvent e) {
					System.out.println("----move----");
				}
			});
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);

			JPanel videoPanel = new JPanel();
			contentPane.add(videoPanel, BorderLayout.CENTER);
			videoPanel.setLayout(new BorderLayout(0, 0));

			playerComponent = new EmbeddedMediaPlayerComponent();
			final Canvas videoSurface = playerComponent.getVideoSurface();
			
			videoSurface.addMouseListener(new MouseAdapter() {
					String btnText = ">";
					Timer mouseTime;
					@Override
					public void mouseClicked(MouseEvent e) {
					int i = e.getButton();
					if (i == MouseEvent.BUTTON1) {
						if (e.getClickCount() == 1) {
							mouseTime = new Timer(350, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if (playButton.getText() == ">") {
										//播放
										Player.play();
										btnText = "||";
										playButton.setText(btnText);
									} else {
										//暂停
										Player.pause();
										btnText = ">";
										playButton.setText(btnText);
									}
									mouseTime.stop();
								}
							});
							mouseTime.restart();
						} else if (e.getClickCount() == 2 && mouseTime.isRunning()) {
							mouseTime.stop();
							if (flag == 0) {
								//全屏
								Player.fullScreen();
							} else if (flag == 1) {
								//原始大小
								Player.originalScreen();
							}
						}
					}
				}

			});
			videoPanel.add(playerComponent, BorderLayout.CENTER);
			panel = new JPanel();
			videoPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new BorderLayout(0, 0));

			controlPanel = new JPanel();
			panel.add(controlPanel);
			
			playButton = new JButton(">");		
			playButton.addMouseListener(new MouseAdapter() {
				String btnText = ">";
				@Override
				public void mouseClicked(MouseEvent e) {
					if (playButton.getText() == ">") {
						//播放
						Player.play();
						btnText = "||";
						playButton.setText(btnText);
					} else {
						//暂停
						Player.pause();
						btnText = ">";
						playButton.setText(btnText);
					}

				}
			});		
			controlPanel.add(playButton);
			
			backwordButton = new JButton("快退");
			backwordButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//快退
					Player.jumpTo((float)((progressBar.getPercentComplete() * progressBar.getWidth() - 10) / progressBar.getWidth()));
				}
			});
			controlPanel.add(backwordButton);

			volumControlerSlider = new JSlider();
			volumControlerSlider.setPaintLabels(true);
			volumControlerSlider.setSnapToTicks(true);
			volumControlerSlider.setPaintTicks(true);
			volumControlerSlider.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					volumControlerSlider.setValue((int)(e.getX() * ((float)volumControlerSlider.getMaximum() / volumControlerSlider.getWidth())));
				}
				
			});
			volumControlerSlider.setValue(100);
			volumControlerSlider.setMaximum(120);
			volumControlerSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					//设置声音大小
					Player.setVolum(volumControlerSlider.getValue());
				}
			});
			
			forwardButton = new JButton("快进");
			forwardButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//快进
					Player.jumpTo((float)progressBar.getPercentComplete() + 0.1f);
				}
			});
			
			stopButton = new JButton("停止");
			stopButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//停止
					Player.stop();
					playButton.setText(">");
				}
			});		
							
			controlPanel.add(stopButton);
			controlPanel.add(forwardButton);
			
			FullScreenButton = new JButton("全屏");
			FullScreenButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//全屏
					Player.fullScreen();	
				}
			});
			
			controlPanel.add(FullScreenButton);
			controlPanel.add(volumControlerSlider);
			volumLabel = new JLabel("" + volumControlerSlider.getValue());
			controlPanel.add(volumLabel);
			

			progressTimePanel = new JPanel();
			panel.add(progressTimePanel, BorderLayout.NORTH);
			progressTimePanel.setLayout(new BorderLayout(0, 0));
			
			progressBar = new JProgressBar();
			progressTimePanel.add(progressBar, BorderLayout.CENTER);
			progressBar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int x = e.getX();
					//鼠标拖动进度条事件
					Player.jumpTo((float) x / progressBar.getWidth());
				}
			});
			
			currentLabel = new JLabel("00:00:00");
			progressTimePanel.add(currentLabel, BorderLayout.WEST);
			totalLabel = new JLabel("00:00:00");
			progressTimePanel.add(totalLabel, BorderLayout.EAST);
		}
		
		
		public EmbeddedMediaPlayer getMediaPlayer() {
			return playerComponent.getMediaPlayer();
		}

		public JProgressBar getProgressBar() {
			return progressBar;
		}
		
		public EmbeddedMediaPlayerComponent getPlayComponent(){
			return playerComponent;
		}
		
		public JButton getPlayButton(){
			return playButton;
		}

		public JPanel getControlPanel() {
			return controlPanel;
		}


		public void setFlag(int flag){
			this.flag = flag;
		}

		public int getFlag() {
			return flag;
		}

		public JSlider getVolumControlerSlider() {
			return volumControlerSlider;
		}

		public JLabel getVolumLabel() {
			return volumLabel;
		}

		public JLabel getCurrentLabel() {
			return currentLabel;
		}

		public JLabel getTotalLabel() {
			return totalLabel;
		}

		public JPanel getProgressTimePanel() {
			return progressTimePanel;
		}

}
