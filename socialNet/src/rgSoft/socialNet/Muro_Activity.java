package rgSoft.socialNet;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import rgSoft.socialNet.User_Activity.TareaTemporizador;


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
public class Muro_Activity extends Activity {
	
	
	private String idEdit;
	private ListView listVUsers;
	private HttpClient client;
	private ArrayList<Comentario> comentList;
	private ArrayList<Usuario> userList;
	private Timer timer;	
	private String emailA;
	private String extra;
	private String emailF;
	private String emailP;
	private String nombreF;
	private int existente;
	private String idA;
	private EditText edPublicacion;
	private String ip = "10.0.2.2:8080";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_muro);
		
		Bundle bundle = getIntent().getExtras();
        emailA=bundle.getString("emailvalido");
        idA=bundle.getString("idvalido");
        
        idEdit = "";
		existente=0;
		
		listVUsers = (ListView) findViewById(R.id.listView1);
		client = new DefaultHttpClient();
		
		idEdit = "";
		edPublicacion = (EditText) findViewById(R.id.editText1);

		timer = new Timer();		
		TareaTemporizador miTarea = new TareaTemporizador();
		timer.schedule(miTarea,1,1000);		
		
		try
		{
			HttpGet peticion = new HttpGet("http://"+ip+"/usuarios");
			peticion.addHeader("content-type","application/json");
			HttpResponse respuesta = client.execute(peticion);
			String texto_respuesta = EntityUtils.toString(respuesta.getEntity());
			JSONArray usuarios_servidor = new JSONArray(texto_respuesta);
			userList= new ArrayList<Usuario>();	
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
					nombreF=nombre;
					//edPublicacion.setText(nombreF+getFechaActual()+getHoraActual());
				}
				
			}
												
		}catch(Exception ex){
			Log.e("excepcion", ex.getMessage());
			
		}
			
	}
	
	private void actualizarTablaAlumnos(){
		try
		{
			HttpGet peticion = new HttpGet("http://"+ip+"/comentarios");
			peticion.addHeader("content-type","application/json");
			HttpResponse respuesta = client.execute(peticion);
			String texto_respuesta = EntityUtils.toString(respuesta.getEntity());
			JSONArray usuarios_servidor = new JSONArray(texto_respuesta);
			comentList= new ArrayList<Comentario>();	
			String[] usuarios = new String[usuarios_servidor.length()];
			String[] ids = new String[usuarios_servidor.length()];
			for(int i=0; i<usuarios_servidor.length();i++){
				JSONObject usuario_servidor = usuarios_servidor.getJSONObject(i);			
				
				String id = usuario_servidor.getString("_id");
				String publicacion = usuario_servidor.getString("publicacion");
				
				ids[i]= id;
				
				usuarios[i] = ""+publicacion;
				comentList.add(new Comentario(id,publicacion));
			}
												
			FilaListaAdaptador2 adaptador = new FilaListaAdaptador2(getApplicationContext(),R.layout.fila_lista2,usuarios,ids);						
			listVUsers.setAdapter(adaptador);
		}catch(Exception ex){
			Log.e("excepcion", ex.getMessage());
			final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(this);
			alertaSimple.setTitle("Error - diablos");
			alertaSimple.setMessage("algo esta fallando");
			alertaSimple.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
			}
			});
			
			alertaSimple.show();	
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
	
	//METODO DEL BOTON publicar
		public void publicar(View vista){		
			JSONObject comentario = new JSONObject();

			try
			{
				
				String hora=getHoraActual();
				String fecha=getFechaActual();
				String publicacionAmeter="("+fecha+") - "+hora+"\n"+nombreF+" :\n"+edPublicacion.getText().toString();
				
				comentario.put("publicacion",""+publicacionAmeter);
				
				
			StringEntity entidad = new StringEntity(comentario.toString());	
			HttpPost peticion = new HttpPost("http://"+ip+"/comentarios");
			peticion.addHeader("content-type","application/json");
			peticion.setEntity(entidad);
			HttpResponse respuesta = client.execute(peticion);
					
			//mensaje de alta correcta
/*					final AlertDialog.Builder alertaSimplev = new AlertDialog.Builder(this);
					alertaSimplev.setTitle("Publicado");
					alertaSimplev.setMessage("La publicacion fue correcta");
					alertaSimplev.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
					}
					}); //fin del mensaje correcto	
					
					alertaSimplev.show();
								*/
			}catch (Exception ex){
					
			}
			
			
			
		}
	
	//METODO DEL BOTON VER
	public void comentar(View vista){		
		Button ibEditar = (Button) vista;
		idEdit = (String)ibEditar.getTag();
		int indice_editado = BuscarEditado();
		
		JSONObject comentario = new JSONObject();
		try
		{	
			String existe=comentList.get(indice_editado).getPublicacion();
			String hora=getHoraActual();
			String fecha=getFechaActual();
			String publicacionAmeter=existe+"\n          ("+fecha+") - "+hora+"\n          "+nombreF+" :\n          "+edPublicacion.getText().toString();
			
		comentario.put("publicacion", publicacionAmeter);
		
		StringEntity entidad = new StringEntity(comentario.toString());
		
		if(indice_editado!=-1){
			
			HttpPut peticionput = new HttpPut("http://"+ip+"/comentarios/"+idEdit);
			peticionput.addHeader("content-type","application/json");	
			peticionput.setEntity(entidad);
			HttpResponse respuestaput = client.execute(peticionput);
		}
		
		}
		catch(Exception e){}
			
	}
	
	private int BuscarEditado(){
		for (int i=0;i<comentList.size();i++){
			if(comentList.get(i).getId()==idEdit){
				return i;
			}
		}
		return -1;
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
		
		
		//METODO PARA EL BOTON DE PERFIL
		public void perfill(View view) {		
			Intent i = new Intent(this, Perfil_Activity.class );
	        i.putExtra("emailvalido", emailA);
	        i.putExtra("idvalido", idA);
	        i.putExtra("extra","Prueba de Perfil");
	        startActivity(i);
	    }
		
		 public static String getFechaActual() {
		        Date ahora = new Date();
		        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
		        return formateador.format(ahora);
		    }

		    //Metodo usado para obtener la hora actual del sistema
		    public static String getHoraActual() {
		        Date ahora = new Date();
		        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
		        return formateador.format(ahora);
		    }
	
	

}
