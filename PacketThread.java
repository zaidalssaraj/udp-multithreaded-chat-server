import java.io.*;
import java.net.*;

// The task for handling one datagram
public class PacketThread implements Runnable {

	private DatagramSocket serverSocket;
	private DatagramPacket receivePacket;

	public PacketThread(DatagramSocket s, DatagramPacket p) {
		serverSocket = s;
		receivePacket = p;
	}

	public void run() {
		try {
			String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();

			// exit message isn't counted, thread just ends
			if (sentence.equals("exit")) {
				System.out.println(IPAddress.getHostAddress() + ":" + port + " left.");
				return;
			}

			int total = UDPServer.addMessage();
			System.out.println("FROM " + IPAddress.getHostAddress() + ":" + port + " - " + sentence);

			String reply = "Total messages received: " + total;
			byte[] sendData = reply.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		} catch (Exception e) {
			System.out.println("Packet thread error: " + e);
		}
	}
}
