package com.example.appcasa;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.net.UnknownHostException;

public class ServiceSocketUPD {

	private final int PORTA = 15000;
	private final String IP = "192.168.0.150";

	public void conectar(char entrada) {
		DatagramSocket socket;
		try {
			String messageStr = String.valueOf(entrada);
			socket = new DatagramSocket();
			InetAddress local = InetAddress.getByName(IP);
			int msg_length = messageStr.length();
			byte[] message = messageStr.getBytes();
			DatagramPacket p = new DatagramPacket(message, msg_length, local,
					PORTA);

			socket.send(p);
			socket.close();

		} catch (UnknownHostException e1) {

			e1.printStackTrace();

		} catch (IOException e1) {

			e1.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
