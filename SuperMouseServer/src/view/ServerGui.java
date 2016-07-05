package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import model.QRCodeModel;

public class ServerGui extends JFrame{
	
	private JLabel infoLbl;
	private JLabel instructionLbl;
	private JLabel statusLbl;
	private JFrame frame;
	private JButton disconnectBtn;
	private JLabel sensitivityLbl;
	private JSlider sensitivitySlider;
	
	public JLabel getInfoLbl() {
		return infoLbl;
	}

	public JLabel getInstructionLbl() {
		return instructionLbl;
	}
	
	public int getSensitivityValue() {
		return sensitivitySlider.getValue();
	}

	public ServerGui() throws IOException {
		
		JPanel centerPanel = new JPanel();
		JPanel toolPanel = new JPanel();		
		JButton exitBtn = new JButton("Exit Application");
		
		sensitivitySlider = new JSlider();
		sensitivityLbl = new JLabel("Sensitivity     ");
		disconnectBtn = new JButton("Disconnect");
		
		frame = new JFrame("SuperMaus");
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		instructionLbl = new JLabel();
		statusLbl = new JLabel();
		infoLbl = new JLabel();
		
		statusLbl.setText("Server Started");
		statusLbl.setHorizontalAlignment(JLabel.CENTER);
		statusLbl.setPreferredSize(new Dimension(500, 75));
		statusLbl.setBackground(Color.WHITE);
		statusLbl.setForeground(Color.BLUE);
		
		displayMainPage();
		
		// Main Panel Construction
		centerPanel.setBackground(Color.WHITE);
		centerPanel.add(infoLbl, BorderLayout.CENTER);
		centerPanel.add(instructionLbl, BorderLayout.SOUTH);
		
		// Tool Panel configuration
		sensitivityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		sensitivitySlider.setMaximum(10);
		sensitivitySlider.setMinimum(0);
		sensitivitySlider.setMajorTickSpacing(1);
		sensitivitySlider.setValue(0);
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// Tool Panel Construction
		toolPanel.setLayout(new GridLayout(1, 4));
		toolPanel.setPreferredSize(new Dimension(500, 80));
		toolPanel.add(sensitivityLbl);
		toolPanel.add(sensitivitySlider);
		toolPanel.add(disconnectBtn);
		toolPanel.add(exitBtn);
		
		// App Frame Construction
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(statusLbl, BorderLayout.NORTH);
		frame.add(toolPanel, BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public void displaySuccessfulConnectionState(String phoneName) {
		
		String imgPath = "img\\successfulConnection.png";
		ImageIcon connectedPic = new ImageIcon(imgPath);
		
		infoLbl.setIcon(connectedPic);
		
		instructionLbl.setText("Successfully connected to " + phoneName);
	}
	
	public void displayMainPage() {
		instructionLbl.setText("Please scan this code to connect your Mobile Phone to this computer");
		disconnectBtn.setVisible(false);
		try {
			setQRCodeImg(InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			setStatusLbl("Cannot establish host", true);
		} catch (IOException e) {
			setStatusLbl("Cannot create server, maybe it is already in used", true);
			e.printStackTrace();
		}
	}
	
	public void setQRCodeImg(String hostAddr) throws IOException {
		// Get the path to the QRCode image file
		String qrCodePath = QRCodeModel.generateQrImg(hostAddr);
		ImageIcon QRCodePic = new ImageIcon(qrCodePath);
		infoLbl.setIcon(QRCodePic);
		frame.repaint();
	}
	
	public void minimizeWindow() {
		long startTime = System.currentTimeMillis();
		Timer timer = new Timer();
		System.out.println("Minimize");
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				long timeLeft = 3 - (System.currentTimeMillis() - startTime) / 1000;
				if (timeLeft < 0) {
					frame.setState(Frame.ICONIFIED);
					frame.setExtendedState(Frame.ICONIFIED);
					statusLbl.setText("");
					this.cancel();
				} else {
					setStatusLbl("Minimize this application in " + timeLeft, false);
				}
			}
		}, 1000, 1000);
		
		
	}
	
	public void setStatusLbl(String msg, boolean isUrgent) {
		statusLbl.setText(msg);
		if (isUrgent) {
			statusLbl.setForeground(Color.RED);
		} else {
			statusLbl.setForeground(Color.BLUE);
		}
	}
	
	public void activateDisconnectBtn(Socket socket, String phoneName) {
		disconnectBtn.setText("Disconnect");
		disconnectBtn.setVisible(true);
		disconnectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					socket.close();
					statusLbl.setText("Disconnect " + phoneName + " from Server");
					displayMainPage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
}
