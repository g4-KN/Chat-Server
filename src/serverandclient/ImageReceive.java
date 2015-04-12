package serverandclient;

import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ImageReceive implements Runnable {

	@Override
	public void run() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(9995);
			Socket socket;
			while ((socket = serverSocket.accept()) != null) {
				InputStream inputStream = socket.getInputStream();
				DataInputStream dis = new DataInputStream(inputStream);
				System.out.println("Reading: " + System.currentTimeMillis());

				// byte[] sizeAr = new byte[4];
				// inputStream.read(sizeAr);
				// int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
				// System.out.println("size: "+size);
				// byte[] imageAr = new byte[size];
				// inputStream.read(imageAr);
				//
				int len = dis.readInt();
				System.out.println("len:" + len);
				byte[] imageAr = new byte[len];
				if (len > 0) {
					dis.readFully(imageAr);
				}
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(
						imageAr));

				System.out.println("Received " + image.getHeight() + "x"
						+ image.getWidth() + ": " + System.currentTimeMillis());
				File test1 = new File("test1.jpg");
				ImageIO.write(image, "jpg", test1);
				BufferedImage image2 = new BufferedImage(320, 240,
						BufferedImage.TYPE_INT_RGB);
				image2.createGraphics().drawImage(
						image.getScaledInstance(320, 240, image.SCALE_SMOOTH),
						0, 0, null);

				File test2 = new File("test2.bmp");
				ImageIO.write(image2, "bmp", test2);
				test2.createNewFile();
			}

			//serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
