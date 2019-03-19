package player;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FullFrame extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JPanel progressTimepanel;
		
		private JButton playButton;
		private JButton backwordButton;
		private JButton smallButton;
		
		private JSlider volumControlerSlider;
		private JProgressBar progressBar;
		
		private JLabel volumLabel;
		private JLabel currentLabel;
		private JLabel totalLabel;
		

		/**
		 * Create the frame,a new control frame after enter full screen model.
		 */
		public FullFrame() {
			setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			setType(Type.UTILITY);
			setResizable(false);
			setUndecorated(true);
			setOpacity(0.5f);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 623, 66);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.CENTER);

			backwordButton = new JButton("快退");
			backwordButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Player.jumpTo((float)((progressBar.getPercentComplete() * progressBar.getWidth() - 10) / progressBar.getWidth()));
				}
			});

			playButton = new JButton(">");
			playButton.addMouseListener(new MouseAdapter() {
				String btnText = ">";
				@Override
				public void mouseClicked(MouseEvent e) {
					if (playButton.getText() == ">") {
						Player.play();
						btnText = "||";
						playButton.setText(btnText);
					} else {
						Player.pause();
						btnText = ">";
						playButton.setText(btnText);
					}
				}
			});
			panel.add(playButton);
			panel.add(backwordButton);

			JButton stopButton = new JButton("停止");
			stopButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Player.stop();
					playButton.setText(">");
				}
			});
			panel.add(stopButton);

			JButton forwardButton = new JButton("快进");
			forwardButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Player.jumpTo((float)progressBar.getPercentComplete() + 0.1f);
				}
			});
			panel.add(forwardButton);

			smallButton = new JButton("退出全屏");
			smallButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Player.originalScreen();
				}
			});
			panel.add(smallButton);

			volumControlerSlider = new JSlider();
			volumControlerSlider.setPaintTicks(true);
			volumControlerSlider.setSnapToTicks(true);
			volumControlerSlider.setPaintLabels(true);
			panel.add(volumControlerSlider);
			
			volumControlerSlider.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					volumControlerSlider.setValue(volumControlerSlider.getMaximum() / volumControlerSlider.getWidth() * e.getX());
					Player.getFrame().getVolumControlerSlider().setValue(volumControlerSlider.getValue());
				}
			});
			volumControlerSlider.setMaximum(120);

			volumLabel = new JLabel("0");
			panel.add(volumLabel);

			progressTimepanel = new JPanel();
			contentPane.add(progressTimepanel, BorderLayout.NORTH);
			progressTimepanel.setLayout(new BorderLayout(0, 0));

			progressBar = new JProgressBar();
			progressTimepanel.add(progressBar, BorderLayout.CENTER);
			progressBar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int x = e.getX();
					Player.jumpTo(((float) x / progressBar.getWidth()));
				}
			});

			currentLabel = new JLabel("00:00:00");
			progressTimepanel.add(currentLabel, BorderLayout.WEST);

			totalLabel = new JLabel("00:00:00");
			progressTimepanel.add(totalLabel, BorderLayout.EAST);
			volumControlerSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					Player.setVolum(volumControlerSlider.getValue());
				}
			});


		}

		public JProgressBar getProgressBar() {
			return progressBar;
		}

		public void setProgressBar(JProgressBar progressBar) {
			this.progressBar = progressBar;
		}

		public JButton getPlayButton() {
			return playButton;
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

}
