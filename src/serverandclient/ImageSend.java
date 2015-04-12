package serverandclient;

import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ImageSend {

	 public static void main(String[] args) throws Exception {
	        Socket socket = new Socket("localhost", 9995);
	        OutputStream outputStream = socket.getOutputStream();

	        BufferedImage image = ImageIO.read(new File("01c.jpg"));

	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        ImageIO.write(image, "jpg", byteArrayOutputStream);

	        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
	        outputStream.write(size);
	        outputStream.write(byteArrayOutputStream.toByteArray());
	        outputStream.flush();
	        System.out.println("Flushed: " + System.currentTimeMillis());

	        Thread.sleep(120000);
	        System.out.println("Closing: " + System.currentTimeMillis());
	        socket.close();
	    }
}
