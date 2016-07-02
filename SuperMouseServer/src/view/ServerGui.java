package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.QRCodeModel;

public class ServerGui extends JFrame{
	
	private JLabel infoLbl;
	private JLabel instructionLbl;
	
	public JLabel getInfoLbl() {
		return infoLbl;
	}

	public JLabel getInstructionLbl() {
		return instructionLbl;
	}

	public ServerGui() throws IOException {
		
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		instructionLbl = new JLabel();
		instructionLbl.setText("Please scan this code to connect your Mobile Phone to this computer");
		infoLbl = new JLabel();
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		
		centerPanel.add(infoLbl, BorderLayout.CENTER);
		centerPanel.add(instructionLbl, BorderLayout.SOUTH);
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void displaySuccessfulConnectionState(String phoneName) {
		
		String imgPath = "img\\successfulConnection.png";
		ImageIcon connectedPic = new ImageIcon(imgPath);
		
		infoLbl.setIcon(connectedPic);
		
		instructionLbl.setText("Successfully connected to " + phoneName);
	}
	
	public void setQRCodeImg(String hostAddr) throws IOException {
		// Get the path to the QRCode image file
		String qrCodePath = QRCodeModel.generateQrImg(hostAddr);
		ImageIcon QRCodePic = new ImageIcon(qrCodePath);
		infoLbl.setIcon(QRCodePic);
		this.repaint();
	}
}
