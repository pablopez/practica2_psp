package practica2.models;

public class VideoGame {
	private String name;
	private int id;
	private String company;
	private double note;
	
	public VideoGame(){
		super();
	}
	
	public VideoGame(int id, String name, String company, double note) {
		this.id = id;
		this.setName(name);
		this.setCompany(company);
		this.setNote(note);
	}
	
	public void setNote(double note) {
		this.note = note;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}	

	public String getCompany() {
		return company;
	}

	public int getId() {
		return id;
	}

	public double getNote() {
		return note;
	}
	
	public void update(VideoGame vg) {
		this.setCompany(vg.getCompany());
		this.setNote(vg.getNote());
		this.setName(vg.getName());
	}
	
	public String toString() {
		return this.getId() + "|" + this.getName() + "|" + this.getCompany() + "|" + this.getNote();
	}

	
}
