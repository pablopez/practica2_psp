package practica2.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import practica2.models.VideoGame;

@Component
public class VideoGameDao {
	
	// elijo un mapa porque es la manera más eficiente de tener controlado un objeto por id
	public Map<Integer, VideoGame> videogames;
	// esta variable hará de id autoincremental, cada vez que se añada un objeto suma uno
	private int aiid; 
	
	public VideoGameDao() {
		this.aiid = 1;
		System.out.println("VideoGameDao: creando videojuegos");
		videogames = new HashMap<Integer, VideoGame>();
		loadExamples();
	}
	
	private void loadExamples() {
		this.add("FIFA 23", "Electronic Arts", 8.3);
		this.add("Pacman", "Konami", 7.0);
		this.add("Uncharted: El legado perdido", "Naughty Dog", 8.3);
		this.add("Age of Empires II", "Xbox Game Studios", 10);
		this.add("Mario Bros.", "Nintendo", 8.3);
	}
	
	public VideoGame add(String name, String company, double note) {
		if(this.isNameAvailable(name)) {
			VideoGame vg = new VideoGame(this.aiid, name, company, note);
			videogames.put(vg.getId(), vg);
			System.out.println("Anadido:"+vg.toString());
			this.aiid++; //next id
			return vg;
		}
		return null;
	}
	
	public VideoGame add(VideoGame vg) {
		return this.add(vg.getName(), vg.getCompany(), vg.getNote());
	}	
	
	public VideoGame delete(int id) {
		return videogames.remove(id);		
	}
	
	public VideoGame update(int id, VideoGame vg) {
		VideoGame vg_in_list = this.get(id);
		if(vg_in_list != null) {
			System.out.println("Actualiza:"+vg_in_list.toString());
			vg_in_list.update(vg);
			videogames.put(vg_in_list.getId(),vg_in_list);
			System.out.println("Actualizado:"+vg_in_list.toString());
		}
		return vg_in_list;
	}
	
	public boolean isNameAvailable(String name) {
		for(int id : videogames.keySet()) {
			VideoGame current_vg = this.videogames.get(id);
			if(current_vg.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	public VideoGame get(int id) {
		return videogames.get(id);
	}
	
	public ArrayList<VideoGame> get(){
		ArrayList<VideoGame> vg_list = new ArrayList<VideoGame>();
		for(int id : videogames.keySet()) {
			vg_list.add(videogames.get(id));
		}
		return vg_list;
	}
}
