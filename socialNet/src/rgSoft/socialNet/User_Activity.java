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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class User_Activity extends Activity {
	
	private String _id_editado;
	private ListView lvUsuarios;
	private HttpClient _cliente_web;
	private ArrayList<Usuario> _usuarios;	
	private Timer _temporizador;	
	private String emailA;
	private String extra;
	private String emailF;
	private String emailP;
	private String passwordF;
	private int existente;
	private String idA;
	private String ip = "10.0.2.2:8080";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);
		
		_id_editado = "";
		existente=0;
		
		lvUsuarios = (ListView) findViewById(R.id.listView1);
		_cliente_web = new DefaultHttpClient();
		
		Bundle bundle = getIntent().getExtras();
        emailA=bundle.getString("emailvalido");
        idA=bundle.getString("idvalido");
        extra=bundle.getString("extra");

        /*
	    final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(this);
		alertaSimple.setTitle("Error de inicio");
		alertaSimple.setMessage("emailvalido: "+emailA+" "+extra+" "+idA);
		alertaSimple.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
		}
		});
		
		alertaSimple.show();*/
		
		
		_temporizador = new Timer();		
		TareaTemporizador miTarea = new TareaTemporizador();
		_temporizador.schedule(miTarea,1,1000);		
			
	}
	
	private void actualizarTablaAlumnos(){
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
				
				ids[i]= id;
				usuarios[i] = "                   "+nombre;
				_usuarios.add(new Usuario(id,nombre,email,imagen,descripcion,password));
			}
												
			FilaListaAdaptador adaptador = new FilaListaAdaptador(getApplicationContext(),R.layout.fila_lista,usuarios,ids);						
			lvUsuarios.setAdapter(adaptador);
		}catch(Exception ex){
			Log.e("excepcion", ex.getMessage());
			final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(this);
			alertaSimple.setTitle("Error - diablos");
			alertaSimple.setMessage("algo esta fallando");
			alertaSimple.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
			}
			});
			
			//alertaSimple.show();	
		}
	}
	
	class TareaTemporizador extends TimerTask{
		public void run(){
			runOnUiThread(new Runnable(){
				public void run(){
					actualizarTablaAlumnos();
				}				
			});					
		}
	}
	
	//METODO DEL BOTON VER
	public void ver(View vista){		
		Button ibEditar = (Button) vista;
		_id_editado = (String)ibEditar.getTag();
		int indice_editado = BuscarEditado();
		if(indice_editado!=-1){
			String nombreP = _usuarios.get(indice_editado).getNombre();
			String emailP = _usuarios.get(indice_editado).getEmail();
			String descripcionP = _usuarios.get(indice_editado).getDescripcion();
			
			Intent i = new Intent(this, Contact_activity.class );
			i.putExtra("nombre", nombreP);
	        i.putExtra("email", emailP);
	        i.putExtra("descripcion", descripcionP);
	        i.putExtra("emailvalido", emailA);
	        i.putExtra("idvalido", idA);
	        startActivity(i);			
		}
	}
	
	private int BuscarEditado(){
		for (int i=0;i<_usuarios.size();i++){
			if(_usuarios.get(i).getId()==_id_editado){
				return i;
			}
		}
		return -1;
	}
	
	//METODO PARA EL BOTON PRINCIPAL
		public void principall(View view) {		
			Intent i = new Intent(this, Muro_Activity.class );
	        i.putExtra("emailvalido", emailA);
	        i.putExtra("idvalido", idA);
	        i.putExtra("extra","Prueba de Perfil");
	        startActivity(i);
	    }
	
	//METODO PARA EL BOTON DE PERFIL
	public void perfill(View view) {		
		Intent i = new Intent(this, Perfil_Activity.class );
        i.putExtra("emailvalido", emailA);
        i.putExtra("idvalido", idA);
        i.putExtra("extra","Prueba de Perfil");
        startActivity(i);
    }
	
	//METODO DEL BOTON SALIR
	public void mainn(View view) {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
	

}
