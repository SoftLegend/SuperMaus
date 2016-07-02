package model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QRCodeModel {
	public static String generateQrImg(String hostAddr) throws IOException {
		
		String QrImgPath = "code\\code.jpg";
		ByteArrayOutputStream out = QRCode.from(hostAddr).to(ImageType.JPG).stream();
		
		File f = new File(QrImgPath);
		FileOutputStream fos = new FileOutputStream(f);
		
		fos.write(out.toByteArray());
		fos.close();
		return QrImgPath;
	}
}
