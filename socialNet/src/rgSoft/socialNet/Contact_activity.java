package rgSoft.socialNet;

import java.util.ArrayList;
import java.util.Timer;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import rgSoft.socialNet.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class Contact_activity extends Activity {
	
	private String idEdit;
	private ListView listVUsuarios;
	private HttpClient client;
	private ArrayList<Usuario> usersArray;	
	private Timer timer;	
	private String emailA;
	private String extra;
	private String emailF;
	private String emailP;
	private String passwordF;
	private int existente;
	private EditText name;
	private EditText mail;
	private EditText description;
	private String idTem;
	private String descripTemp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	setContentView(R.layout.activity_contact);
	
	name = (EditText) findViewById(R.id.editText1);
	mail = (EditText) findViewById(R.id.editText2);
	description = (EditText) findViewById(R.id.editText3);
	
	idEdit = "";
	existente=0;
	
	listVUsuarios = (ListView) findViewById(R.id.listView1);
	client = new DefaultHttpClient();
	
	Bundle bundle = getIntent().getExtras();
	
    emailA=bundle.getString("emailvalido");
    idTem=bundle.getString("idvalido");
    descripTemp=bundle.getString("descripcion");
    
    name.setText(bundle.getString("nombre"));
    mail.setText(bundle.getString("email"));
    description.setText(bundle.getString("descripcion"));
	}
	
	//metodo para el boton usuarios
		public void usuarios(View view) {
	        Intent i = new Intent(this, User_Activity.class );
	        i.putExtra("emailvalido", emailA);
	        i.putExtra("idvalido", idTem);
	        i.putExtra("extra","Prueba de usuarios");
	        startActivity(i);
	    }

}
