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

public class ServerGui {
	
	private JLabel infoLbl;
	private JLabel instructionLbl;
	
	public JLabel getInfoLbl() {
		return infoLbl;
	}

	public JLabel getInstructionLbl() {
		return instructionLbl;
	}

	public ServerGui() throws IOException {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		instructionLbl = new JLabel();
		instructionLbl.setText("Please scan this code to connect your Mobile Phone to this computer");
		infoLbl = new JLabel();
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		
		// Get the path to the QRCode image file
		String qrCodePath = QRCodeModel.generateQrImg();
		ImageIcon QRCodePic = new ImageIcon(qrCodePath);
		infoLbl.setIcon(QRCodePic);
		
		centerPanel.add(infoLbl, BorderLayout.CENTER);
		centerPanel.add(instructionLbl, BorderLayout.SOUTH);
		
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public void displaySuccessfulConnectionState(String phoneName) {
		
		String imgPath = "img\\successfulConnection.png";
		ImageIcon connectedPic = new ImageIcon(imgPath);
		
		infoLbl.setIcon(connectedPic);
		
		instructionLbl.setText("Successfully connected to " + phoneName);
	}
	
}
