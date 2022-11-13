package practica2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import practica2.daos.VideoGameDao;
import practica2.models.VideoGame;

@RestController
public class VideoGameController {
	
	@Autowired
	private VideoGameDao vgdao;
	
	private ResponseEntity<VideoGame> checkStr(String str) {
		if( str == null) {
			return new ResponseEntity<VideoGame>(HttpStatus.UNPROCESSABLE_ENTITY);//422 ATTRS NOT VALID
		}
		return null;
	} 
	
	private ResponseEntity<VideoGame> checkName(String name) {
		ResponseEntity<VideoGame> entity_response = checkStr(name);
		if( entity_response == null) {
			if(!vgdao.isNameAvailable(name)) {
				System.out.println("Ya existe un videojuego con el nombre \""+name+"\"");
				return new ResponseEntity<VideoGame>(HttpStatus.NOT_ACCEPTABLE);//406 NAME IS ALREADY USED
			}			
		}
		return entity_response;
	}
	
	@PostMapping(path="videogames",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VideoGame> addVideoGame(@RequestBody VideoGame vg) {
		System.out.println("Creando: " + vg);
		ResponseEntity<VideoGame> entity_response = checkName(vg.getName()); // comprueba si el nombre es válido
		if(entity_response == null) {
			entity_response = checkStr(vg.getCompany()); // comprueba que la compañía es válida
		}
		if(entity_response == null) {
			entity_response = new ResponseEntity<VideoGame>(vgdao.add(vg),HttpStatus.CREATED);//201 CREATED
		}
		return entity_response;
	}
	
	@PutMapping(path="videogames/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VideoGame> updateVideogame(@PathVariable("id") int id,@RequestBody VideoGame vg) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Modificacion esperada: " + vg);
		ResponseEntity<VideoGame> entity_response = checkName(vg.getName());
		if(entity_response == null) {
			VideoGame vg_updated = vgdao.update(id, vg);
			if(vg_updated != null) {
				entity_response = new ResponseEntity<VideoGame>(vg_updated,HttpStatus.OK);//200 OK
			}else {
				entity_response = new ResponseEntity<VideoGame>(HttpStatus.INTERNAL_SERVER_ERROR);//500 UNEXPECTED ERROR
			}
		}
		return entity_response;
	}
	
	@GetMapping(path="videogames/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VideoGame>> getVideoGame(){
		System.out.println("GET videogames/");
		return new ResponseEntity<List<VideoGame>>(vgdao.get(), HttpStatus.OK);
	}
	
	@GetMapping(path="videogames/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VideoGame> getVideoGame(@PathVariable("id") int id){
		System.out.println("GET videogames/"+id);
		VideoGame vg = vgdao.get(id);
		if(vg != null) {
			return new ResponseEntity<VideoGame>(vg, HttpStatus.OK);
		}else {
			return new ResponseEntity<VideoGame>(HttpStatus.NOT_FOUND); //404 NOT FOUND
		}
	}
	
	@DeleteMapping(path="videogames/{id}")
	public ResponseEntity<VideoGame> deleteVideoGame(@PathVariable int id) {
		System.out.println("ID a borrar: " + id);
		VideoGame vg = vgdao.delete(id);
		if(vg != null) {
			return new ResponseEntity<VideoGame>(vg,HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<VideoGame>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
}