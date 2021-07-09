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


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class Perfil_Activity extends Activity {
	
	private String idEdit;
	private ListView ListUser;
	private HttpClient _cliente_web;
	private ArrayList<Usuario> _usuarios;	
	private Timer _temporizador;	
	private String emailA;
	private String extra;
	private String emailF;
	private String emailP;
	private String passwordF;
	private int existente;
	private EditText _nombre;
	private EditText _email;
	private EditText _descripcion;
	private EditText _imagen;
	private EditText _password;
	private String idA;
	private String ip = "10.0.2.2:8080";
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
		
		_nombre = (EditText) findViewById(R.id.editText1);
		_email = (EditText) findViewById(R.id.editText2);
		_descripcion = (EditText) findViewById(R.id.editText3);
		_password = (EditText) findViewById(R.id.editText4);
		
		idEdit = "";
		existente=0;
		
		ListUser = (ListView) findViewById(R.id.listView1);
		_cliente_web = new DefaultHttpClient();
		
		Bundle bundle = getIntent().getExtras();
        emailA=bundle.getString("emailvalido");
        idA=bundle.getString("idvalido");
        extra=bundle.getString("extra");

		/*final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(this);
		alertaSimple.setTitle("Error de inicio");
		alertaSimple.setMessage("emailvalido: "+emailA+" "+extra);
		alertaSimple.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
		}
		});
		
		alertaSimple.show();*/
        try
		{
			HttpGet peticion = new HttpGet("http://"+ip+"/usuarios");
			peticion.addHeader("content-type","application/json");
			HttpResponse respuesta = _cliente_web.execute(peticion);
			String texto_respuesta = EntityUtils.toString(respuesta.getEntity());
			JSONArray usuarios_servidor = new JSONArray(texto_respuesta);
			_usuarios= new ArrayList<Usuario>();	
			String[] usuarios = new String[usuarios_servidor.length()];
			String[] ids = new String[usuarios_servidor.length()];
			for(int i=0; i<usuarios_servidor.length();i++){
				JSONObject usuario_servidor = usuarios_servidor.getJSONObject(i);			
				
				String id = usuario_servidor.getString("_id");
				String nombre = usuario_servidor.getString("nombre");
				String email = usuario_servidor.getString("email");
				String password = usuario_servidor.getString("password");
				String imagen = usuario_servidor.getString("imagen");
				String descripcion = usuario_servidor.getString("descripcion");
				
				emailF=email;
				
				if(emailA.equals(emailF)){
					_nombre.setText(nombre);
					_email.setText(email);
					_descripcion.setText(descripcion);
					_password.setText(password);

				}
				
			}
												
		}catch(Exception ex){
			Log.e("excepcion", ex.getMessage());
			
		}
        
        
	}
	
	//METODO PARA EL BOTON PRINCIPAL
			public void principall(View view) {		
				Intent i = new Intent(this, Muro_Activity.class );
		        i.putExtra("emailvalido", emailA);
		        i.putExtra("idvalido", idA);
		        i.putExtra("extra","Prueba de Perfil");
		        startActivity(i);
		    }
	
	//metodo para el boton usuarios
	public void usuarios(View view) {
        Intent i = new Intent(this, User_Activity.class );
        i.putExtra("emailvalido", emailA);
        i.putExtra("idvalido", idA);
        i.putExtra("extra","Prueba de usuarios");
        startActivity(i);
    }
	
	//metodo para el boton salir
	public void mainn(View view) {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
	
	//metodo para eliminar el usuario
		public void eliminar(View vista) {
			
			try{
				String id = idA;
				HttpDelete peticion = new HttpDelete("http://"+ip+"/usuarios/"+id);
				peticion.addHeader("content-type","application/json");
				HttpResponse respuesta = _cliente_web.execute(peticion);
				
				final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(this);
				alertaSimple.setTitle("Usuario eliminado");
				alertaSimple.setMessage("Su cuenta a sido eliminada");
				alertaSimple.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
				}
				});
				
				alertaSimple.show();
				
				Intent i = new Intent(this, MainActivity.class );
		        startActivity(i);
				
			}catch(Exception ex){
			
			}
	        
	    }
		
		//metodo para el boton guardar que actualiza los datos
		public void guardar(View btnGuardar){		
			JSONObject usuario = new JSONObject();
			try
			{
				usuario.put("nombre", _nombre.getText());
				usuario.put("email", _email.getText());
				usuario.put("password", _password.getText());
				usuario.put("imagen", "sin imagen");
				usuario.put("descripcion",_descripcion.getText());
			
			StringEntity entidad = new StringEntity(usuario.toString());
					
					String id=idA;
					HttpPut peticionput = new HttpPut("http://"+ip+"/usuarios/"+id);
					peticionput.addHeader("content-type","application/json");	
					peticionput.setEntity(entidad);
					HttpResponse respuestaput = _cliente_web.execute(peticionput);
					
					Intent i = new Intent(this, User_Activity.class );
			        i.putExtra("emailvalido", emailA);
			        i.putExtra("idvalido", idA);
			        i.putExtra("extra","Prueba de usuarios");
			        startActivity(i);
								
			}catch (Exception ex){
			
			}		
		}
		
	

}
