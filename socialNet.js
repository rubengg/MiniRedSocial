var express = require("express");
var mongodb = require("mongodb");
var BSON = mongodb.BSONPure;
var server = express();
server.use(express.static(__dirname+"/publico"));
server.use(express.bodyParser());

var cliente_mongodb = mongodb.MongoClient;

cliente_mongodb.connect("mongodb://localhost/redsocial9", function(err,db){
	if(err){
		console.log("Error de conexion - "+err);
	}
	else{
		console.log("Bienvenido a RubenBook");
        var coleccionAlumnos = db.collection("usuarios");

		server.get("/usuarios",function(peticion,respuesta){
			coleccionAlumnos.find().toArray(function(err, elementos){
				respuesta.send(elementos);			
			});
		});

		server.get("/usuarios/:id",function(peticion,respuesta){
			console.log(peticion.params);
			var id = new BSON.ObjectID(peticion.params.id);
			coleccionAlumnos.findOne({'_id':id},function(err, elemento){
				respuesta.send(elemento);			
			});
		});
		
		server.post("/usuarios",function(peticion,respuesta){
			var alumno_insertar = {
				nombre:peticion.body.nombre,
				email:peticion.body.email,
				password:peticion.body.password,
				imagen:peticion.body.imagen,
				descripcion:peticion.body.descripcion
			}
			coleccionAlumnos.insert(alumno_insertar,function(err,resultado){
				console.log("Se inserto: "+resultado);	
				respuesta.send(resultado);		
			});
		});

		server.delete("/usuarios/:id",function(peticion,respuesta){
			var alumno_eliminar={
				_id:new BSON.ObjectID(peticion.params.id)
			};
			coleccionAlumnos.remove(alumno_eliminar,function(err,resultado){
				if(err){
					cosole.log("Error:\n"+err);
				}
				else{
					console.log("Se elimino el usuario: "+resultado);
					respuesta.send("Exito"+resultado);
				}
			});
		});

		server.put("/usuarios/:id",function (peticion,respuesta){
				var id_editado={
					_id:new BSON.ObjectID(peticion.params.id)
				}				

				var alumno_editar={
					$set:
					{					
						nombre:peticion.body.nombre,
						email:peticion.body.email,
						password:peticion.body.password,
						imagen:peticion.body.imagen,
						descripcion:peticion.body.descripcion
					}
				}
				coleccionAlumnos.update(id_editado,alumno_editar,function(err,resultado){
					if(err){
						cosole.log("Error:\n"+err);
					}
					else{
						console.log("Se Actualizo el usuario: "+resultado);
						respuesta.send("Exito"+resultado);				
					}
				});
		});
		
		
		
		var coleccionAlumnos2 = db.collection("comentarios");

		server.get("/comentarios",function(peticion,respuesta){
			coleccionAlumnos2.find().toArray(function(err, elementos){
				respuesta.send(elementos);			
			});
		});

		server.get("/comentarios/:id",function(peticion,respuesta){
			console.log(peticion.params);
			var id = new BSON.ObjectID(peticion.params.id);
			coleccionAlumnos2.findOne({'_id':id},function(err, elemento){
				respuesta.send(elemento);			
			});
		});
		
		server.post("/comentarios",function(peticion,respuesta){
			var alumno_insertar = {
				publicacion:peticion.body.publicacion
			}
			coleccionAlumnos2.insert(alumno_insertar,function(err,resultado){
				console.log("El comentario insertado es : "+resultado);	
				respuesta.send(resultado);		
			});
		});

		server.delete("/comentarios/:id",function(peticion,respuesta){
			var alumno_eliminar={
				_id:new BSON.ObjectID(peticion.params.id)
			};
			coleccionAlumnos2.remove(alumno_eliminar,function(err,resultado){
				if(err){
					cosole.log("Error:\n"+err);
				}
				else{
					console.log("comentario eliminado: "+resultado);
					respuesta.send("Exito"+resultado);
				}
			});
		});

		server.put("/comentarios/:id",function (peticion,respuesta){
				var id_editado={
					_id:new BSON.ObjectID(peticion.params.id)
				}				

				var alumno_editar={
					$set:
					{					
						publicacion:peticion.body.publicacion
					}
				}
				coleccionAlumnos2.update(id_editado,alumno_editar,function(err,resultado){
					if(err){
						cosole.log("Error:\n"+err);
					}
					else{
						console.log("Comentario Actualizado: "+resultado);
						respuesta.send("Exito"+resultado);				
					}
				});
		});
		
		

	}
});

server.get("/",function(peticion,respuesta){
	respuesta.send("Este es el index");
});

server.listen(8080,function(){
	console.log("RedSocial Esperando Usuarios . . .");
});
