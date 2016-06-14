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
	
	JLabel qrCodeLbl;
	JLabel instructionLbl;
	
	public ServerGui() throws IOException {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		instructionLbl = new JLabel();
		instructionLbl.setText("Please scan this code to connect your Mobile Phone to this computer");
		qrCodeLbl = new JLabel();
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		
		// Get the path to the QRCode image file
		String qrCodePath = QRCodeModel.generateQrImg();
		ImageIcon QRCodePic = new ImageIcon(qrCodePath);
		qrCodeLbl.setIcon(QRCodePic);
		
		centerPanel.add(qrCodeLbl, BorderLayout.CENTER);
		centerPanel.add(instructionLbl, BorderLayout.SOUTH);
		
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
