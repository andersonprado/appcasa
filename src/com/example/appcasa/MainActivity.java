package com.example.appcasa;

import java.util.List;

import com.example.appcasa.ServiceSocketRetiraDados;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final int REQUEST_CODE = 1234;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inicializa();

	}

	// Pino 2
	public void onClick2(View view) {
		Button quarto = (Button) findViewById(R.id.button1);
		executa((char) 2, "" + quarto.getText());

	}

	// Pino 3
	public void onClick3(View view) {
		Button quarto = (Button) findViewById(R.id.button2);
		executa((char) 3, "" + quarto.getText());

	}

	// Pino 4
	public void onClick4(View view) {
		Button quarto = (Button) findViewById(R.id.button3);
		executa((char) 4, "" + quarto.getText());
	}

	// Pino 5 quintal
	public void onClick5(View view) {
		Button quarto = (Button) findViewById(R.id.button5);
		executa((char) 5, "" + quarto.getText());
	}

	// Pino 6 sala
	public void onClick6(View view) {
		Button quarto = (Button) findViewById(R.id.button6);
		executa((char) 6, "" + quarto.getText());
	}

	// Pino 8 cozinha
	public void onClick8(View view) {
		Button quarto = (Button) findViewById(R.id.button8);
		executa((char) 8, "" + quarto.getText());
	}

	public void executa(char valor, String nome) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		final char novo = valor;

		new Thread(new Runnable() {
			public void run() {
				synchronized (this) {
					new ServiceSocketRetiraDados().pegaDados(novo);
				}
			}
		}).start();

		Toast.makeText(MainActivity.this, "Acionando Interruptor do " + nome,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_voz:

			try {
				startVoiceRecognitionActivity();
			} catch (ActivityNotFoundException e) {

			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void inicializa() {

		// Mensagem caso microfone nao encontrado

		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			Toast.makeText(MainActivity.this, "Microfone ausente",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Inicializando o comando de voz
	 */

	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Acionamento por voz...");
		startActivityForResult(intent, REQUEST_CODE);
	}

	/**
	 * Resultados da pesquisa
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

			// Preencher a lista

			int quantidade = 0;

			boolean erro = false;

			for (String resultado : data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)) {

				if (quantidade++ > 6) {
					erro = true;
					break;
				}

				if (resultado.equalsIgnoreCase("quarto")
						|| resultado.equalsIgnoreCase("4")
						|| resultado.equalsIgnoreCase("quartos")
						|| resultado.equalsIgnoreCase("qarto")
						|| resultado.equalsIgnoreCase("quatro")
						|| resultado.equalsIgnoreCase("cuarto")) {

					executa((char) 2, "Quarto");
					break;
				} else if (resultado.equalsIgnoreCase("garagem")
						|| resultado.equalsIgnoreCase("garage")
						|| resultado.equalsIgnoreCase("garagens")
						|| resultado.equalsIgnoreCase("garagen")
						|| resultado.equalsIgnoreCase("garragem")) {

					executa((char) 3, "Garagem");
					break;
				} else if (resultado.equalsIgnoreCase("escada")
						|| resultado.equalsIgnoreCase("eskada")
						|| resultado.equalsIgnoreCase("escadas")
						|| resultado.equalsIgnoreCase("iscada")) {

					executa((char) 4, "Escada");
					break;
				} else if (resultado.equalsIgnoreCase("quintal")
						|| resultado.equalsIgnoreCase("quinto")
						|| resultado.equalsIgnoreCase("kintal")
						|| resultado.equalsIgnoreCase("quental")) {

					executa((char) 5, "Quintal");
					break;
				} else if (resultado.equalsIgnoreCase("sala")
						|| resultado.equalsIgnoreCase("chala")
						|| resultado.equalsIgnoreCase("salada")
						|| resultado.equalsIgnoreCase("chata")) {

					executa((char) 6, "Sala");
					break;
				} else if (resultado.equalsIgnoreCase("cozinha")
						|| resultado.equalsIgnoreCase("kozinha")
						|| resultado.equalsIgnoreCase("corzinha")
						|| resultado.equalsIgnoreCase("cor")) {

					executa((char) 8, "Cozinha");
					break;
				}

				if (erro)
					Toast.makeText(MainActivity.this,
							"Nao foi encontrado a palavra falada",
							Toast.LENGTH_SHORT).show();

			}

			// exibe mensagem

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
