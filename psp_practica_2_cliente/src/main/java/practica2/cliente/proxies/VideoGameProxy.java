package practica2.cliente.proxies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import practica2.cliente.entities.VideoGame;

@Service
public class VideoGameProxy {
	public static final String URL = "http://localhost:8080/videogames/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public VideoGame get(int id) {
		try {
			// realizamos el get
			ResponseEntity<VideoGame> response = restTemplate.getForEntity(URL + id, VideoGame.class);
			// si todo va bien devolvemos el videojuego
			if(response.getStatusCode() == HttpStatus.OK) {	
				return response.getBody();
			}else {
				System.out.println("UNKNOWN ERROR "+response.getStatusCode());
				return null;
			}
		}catch (HttpClientErrorException e) {
			System.out.println("ERROR "+e.getStatusCode()+": videojuego [" + id + "] no encontrado");
		    return null;
		}
	}
	
	public VideoGame[] get(){
		try {
			ResponseEntity<VideoGame[]> response = restTemplate.getForEntity(URL,VideoGame[].class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("ERROR "+e.getStatusCode());
		    return null;
		}
	}
	
	//añadir un videojuego
	public VideoGame add(VideoGame vg) {
		try {
			ResponseEntity<VideoGame> response = restTemplate.postForEntity(URL, vg, VideoGame.class);
			System.out.println("Videojuego añadido " + response.getStatusCode());
			return response.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("ERROR "+e.getStatusCode()+": videojuego [" + vg + "] no añadido");
		    return null;
		}
	}
	
	//actualizar un videojuego
	public boolean update(VideoGame vg) {
		try {
			restTemplate.put(URL + vg.getId(),vg, VideoGame.class);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("ERROR "+e.getStatusCode()+": videojuego [" + vg.getId() + "] no modificado");
		    return false;
		}
	}
	
	//borrar un videojuego
	public boolean delete(int id) {
		try {
			restTemplate.delete(URL + id);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("ERROR "+e.getStatusCode()+": videojuego [" + id + "] no borrado");
		    return false;
		}
	}
}