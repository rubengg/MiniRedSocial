package rgSoft.socialNet;

public class Usuario {
	private String _id;
	private String _nombre;
	private String _email;
	private String _imagen;
	private String _descripcion;
	private String _password;
	
	
	public Usuario(String id, String nombre, String email, String imagen, String descripcion, String password){
		_id = id;
		_nombre = nombre;
		_email=email;
		_imagen=imagen;
		_descripcion=descripcion;
		_password=password;
	}	
	
	public Usuario(){
	
	}
	
	public String getDescripcion(){
		return _descripcion;
	}
	
	public String getId(){
		return _id;
	}
	
	public String getNombre(){
		return _nombre;
	}
	
	public String getEmail(){
		return _email;
	}
}
