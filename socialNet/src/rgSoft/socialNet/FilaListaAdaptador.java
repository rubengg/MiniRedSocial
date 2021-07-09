package rgSoft.socialNet;


import rgSoft.socialNet.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class FilaListaAdaptador extends BaseAdapter {
	
	private String[] id;
	private String[] filas;
	
	public FilaListaAdaptador(Context contexto, int id_recurso, String[] filas, String[] id){
		this.filas=filas;
		this.id=id;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub		
		return filas.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub		
		return filas[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int posicion, View vista_conversion, ViewGroup padre) {
		// TODO Auto-generated method stub
		String cadena_alumno = filas[posicion];
		String id_alumno = id[posicion];
		View vista_fila = null;
		
		if (vista_conversion == null) {
			LayoutInflater inflador = (LayoutInflater) padre.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vista_fila = inflador.inflate(R.layout.fila_lista,null);
		} else {
			vista_fila = vista_conversion;
		}

		TextView tvFila = (TextView) vista_fila.findViewById(R.id.tvFila);
		tvFila.setText(cadena_alumno);
		
		Button ibEditar = (Button) vista_fila.findViewById(R.id.button1);
		ibEditar.setTag(id_alumno);
		return vista_fila;
	}
}
