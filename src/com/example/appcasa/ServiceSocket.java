package com.example.appcasa;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServiceSocket {

	private Socket socket;

	private final int PORTA = 5001;
//	private final String IP = "189.68.221.82";
	private final String IP = "192.168.0.150";
	public void conectar(String entrada) {
		
		
	
		try {
			
			socket = new Socket(IP, PORTA);
	
			
			DataOutputStream dataOutputStream = null;
			
		
			
			dataOutputStream = new DataOutputStream(socket.getOutputStream());

			dataOutputStream.writeUTF("Estou enviando dados para o servidor: "
					+ entrada);

			// ObjectOutputStream saida = new ObjectOutputStream(
			// socket.getOutputStream());
			// saida.writeObject("teste");

		} catch (UnknownHostException e1) {

			e1.printStackTrace();

		} catch (IOException e1) {

			e1.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
