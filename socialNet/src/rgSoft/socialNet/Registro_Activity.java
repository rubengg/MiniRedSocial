package rgSoft.socialNet;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import rgSoft.socialNet.R;


import android.app.AlertDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class Registro_Activity extends Activity {
	
	private String _id_editado;
	private EditText edNombre;
	private EditText edEmail;
	private EditText edPassword;
	private EditText edRPassword;
	private HttpClient _cliente_web;
	//private ArrayList<Alumno> _alumnos;	
	private Timer _temporizador;
	private String ip = "10.0.2.2:8080";
	//private String ip = "10.151.110.97";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		_id_editado = "";
		edNombre = (EditText) findViewById(R.id.editText1);
		edEmail = (EditText) findViewById(R.id.editText2);
		edPassword = (EditText) findViewById(R.id.editText3);
		edRPassword = (EditText) findViewById(R.id.editText4);
		_cliente_web = new DefaultHttpClient();
		
		
	}
	
	
	public void Registrar(View button1){		
		JSONObject usuario = new JSONObject();
		String a,b;
		a=edPassword.getText().toString();
		b=edRPassword.getText().toString();
		if(a.equals(b)){
		try
		{
			
			usuario.put("nombre", edNombre.getText());
			usuario.put("email", edEmail.getText());
			usuario.put("password", edPassword.getText());
			usuario.put("imagen", "sin imagen");
			usuario.put("descripcion","sin descripcion");
			
			
		StringEntity entidad = new StringEntity(usuario.toString());	
		//HttpPost peticion = new HttpPost("http://10.151.110.97:8080/usuarios");
		HttpPost peticion = new HttpPost("http://"+ip+"/usuarios");
		peticion.addHeader("content-type","application/json");
		peticion.setEntity(entidad);
		HttpResponse respuesta = _cliente_web.execute(peticion);
				
		//mensaje de alta correcta
				final AlertDialog.Builder alertaSimplev = new AlertDialog.Builder(this);
				alertaSimplev.setTitle("Bienvenido a la red");
				alertaSimplev.setMessage("Su cuenta se ha creado con exito");
				alertaSimplev.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
				}
				}); //fin del mensaje correcto	
				
				alertaSimplev.show();
				
				Intent i = new Intent(this, MainActivity.class );
		        startActivity(i);
							
		}catch (Exception ex){
				
		}
		}
		else{			
			final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(this);
			alertaSimple.setTitle("Error Password");
			alertaSimple.setMessage("Las contraseñas no coinciden");
			alertaSimple.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
			}
			});
			
			alertaSimple.show();		
		}
	}
	
	
	
	

}
