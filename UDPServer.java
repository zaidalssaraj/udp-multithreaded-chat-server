import java.io.*;
import java.net.*;

public class UDPServer {

	static int total = 0; // total messages the server has received
	static Object lock = new Object();

	// add 1 to the total, synchronized so threads don't step on each other
	public static int addMessage() {
		synchronized (lock) {
			total++;
			return total;
		}
	}

	public static void main(String args[]) {
		try {
			DatagramSocket serverSocket = new DatagramSocket(6789);
			System.out.println("The UDP Server is on.");

			while (true) {
				// new buffer for each datagram so threads don't share it
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);

				// new thread for each datagram
				Thread thread = new Thread(new PacketThread(serverSocket, receivePacket));
				thread.start();
			}
		} catch (Exception e) {
			System.out.println("Server error: " + e);
		}
	}
}
