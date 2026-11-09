import java.io.*;
import java.net.*;

public class UDPClient {

	public static void main(String args[]) {
		try {
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

			DatagramSocket clientSocket = new DatagramSocket();

			InetAddress IPAddress = InetAddress.getByName("localhost");

			System.out.println("The UDP client is on. Please enter your input:");

			String sentence = inFromUser.readLine();

			while (sentence != null && !sentence.equals("exit")) {
				byte[] sendData = sentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6789);
				clientSocket.send(sendPacket);

				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);

				String reply = new String(receivePacket.getData(), 0, receivePacket.getLength());
				System.out.println("FROM SERVER: " + reply);

				sentence = inFromUser.readLine();
			}

			// send exit, wait 2 seconds, then close
			byte[] exitData = "exit".getBytes();
			clientSocket.send(new DatagramPacket(exitData, exitData.length, IPAddress, 6789));
			Thread.sleep(2000);
			clientSocket.close();
		} catch (Exception e) {
			System.out.println("Client error: " + e);
		}
	}
}
