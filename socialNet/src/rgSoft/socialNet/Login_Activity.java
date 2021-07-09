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

public class Login_Activity extends Activity {
	
	
	private String idEdit;
	private ListView listUsers;
	private HttpClient client;
	private ArrayList<Usuario> users;	
	private Timer timer;
	private int exist;
	private EditText editMail;
	private EditText editPassword;
	private String mailTem;
	private String passwdTem;
	private String emailA;
	private String idTem;
	private String passwd;
	private String name;
	private String ip = "10.0.2.2:8080";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		exist=0;
		editMail = (EditText) findViewById(R.id.ed1);
		editPassword = (EditText) findViewById(R.id.ed2);
		idEdit = "";
		client = new DefaultHttpClient();
	}
	
	
	public void lanzaacceder(View view) {
		
		try
		{
			HttpGet peticion = new HttpGet("http://"+ip+"/usuarios");
			peticion.addHeader("content-type","application/json");
			HttpResponse respuesta = client.execute(peticion);
			String texto_respuesta = EntityUtils.toString(respuesta.getEntity());
			JSONArray usuarios_servidor = new JSONArray(texto_respuesta);
			users= new ArrayList<Usuario>();	
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
				
				mailTem=email;
				passwdTem=password;
				
				if(editMail.getText().toString().equals(mailTem) && editPassword.getText().toString().equals(passwdTem)){
					exist=1;
					emailA=email;
					passwd=password;
					idTem=id;
					name=nombre;
				}
				
				ids[i]= id;
				usuarios[i] = "       "+nombre;
				users.add(new Usuario(id,nombre,email,imagen,descripcion,password));
				
			}
												
		}catch(Exception ex){
			Log.e("excepcion", ex.getMessage());
			
		}
		
		//pregunta por la bandera de existente
		if(exist==1){	
        Intent i = new Intent(this, User_Activity.class );
        i.putExtra("emailvalido", emailA);
        i.putExtra("idvalido", idTem);
        //i.putExtra("nombrevalido",nombreA);
        startActivity(i);

		}
		else{
			editMail.setText("");
			editPassword.setText("");
			final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(this);
			alertaSimple.setTitle("No se pudo iniciar sesion");
			alertaSimple.setMessage("Usuario o contraseña invalido");
			alertaSimple.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
			}
			});
			
			alertaSimple.show();	
		
		}
    }
	
	

}
