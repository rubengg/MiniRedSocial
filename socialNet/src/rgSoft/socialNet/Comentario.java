package rgSoft.socialNet;

public class Comentario {
	private String idComentario;
	private String comentario;
	
	public Comentario(){ }
	
	public void setPublicacion(String comentario){ this.comentario=comentario; }
	
	public String getPublicacion(){ return comentario; }
	
	public String getId(){ return idComentario;	}
	
	public Comentario(String id, String comentario){
		idComentario = id;
		this.comentario = comentario;
	}	
	
}
