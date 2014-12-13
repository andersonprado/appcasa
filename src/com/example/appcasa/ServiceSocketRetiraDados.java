package com.example.appcasa;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

public class ServiceSocketRetiraDados {

	private Socket socket;
	private PrintStream ps;
	private final int PORTA = 15000;
	private final String IP = "192.168.0.150";

	@SuppressLint("NewApi")
	public void pegaDados(char valor) {

		try {
			Log.v("Conectando socket ...:", "...");
			socket = new Socket(IP, PORTA);
			Log.v("Conectado socket ...:", "...");

			Log.v("O cliente ", socket.getLocalAddress().getHostAddress()
					+ " conectou ao servidor "
					+ socket.getInetAddress().getHostAddress());

			// Envio de dados para a placa arduino - se enviar 'a' = liga o led
			// se
			// enviar 'b' = apaga

			char saida = valor;

			ps = new PrintStream(socket.getOutputStream());
			ps.print(saida);

			fecha();

		} catch (UnknownHostException e1) {

			e1.printStackTrace();
			fecha();

		} catch (IOException e1) {

			e1.printStackTrace();
			fecha();

		} catch (Exception e) {
			e.printStackTrace();
			fecha();
		}

	}

	public void fecha() {
		try {
			Thread.interrupted();
			ps.close();
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
