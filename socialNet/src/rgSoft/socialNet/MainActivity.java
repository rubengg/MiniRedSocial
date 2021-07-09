package rgSoft.socialNet;

import rgSoft.socialNet.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void lanzalogin(View view) {
        Intent i = new Intent(this, Login_Activity.class );
        startActivity(i);
    }
	
	public void lanzaregistro(View view) {
        Intent i = new Intent(this, Registro_Activity.class );
        startActivity(i);
    }
	
	public void cerrar(View view) {
    	finish();
    } 

}
