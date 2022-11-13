package practica2.cliente;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import practica2.cliente.entities.VideoGame;

@Component
public class AppUserInterfaceVideoGames {
	private final Scanner sc = new Scanner(System.in);
	
	private final String menustr = 
		"Seleccione una opción introduciendo el número marcado: \n\n"+ 	
			"\t1. Dar de alta un videojuego \n"+
			"\t2. Dar de baja un videojuego por ID \n"+
			"\t3. Modificar un videojuego por ID \n"+
			"\t4. Obtener un videojuego por ID \n"+
			"\t5. Listar todos los videojuegos \n\n"+
			" Si desea salir pulse intro";
	
	public VideoGame create() {
		VideoGame vg = new VideoGame();
		vg.setName(getValidString("nombre"));
		vg.setCompany(getValidString("compania"));
		vg.setNote(getValidNote());
		return vg;
	}
	
 	public VideoGame update(VideoGame vg) {
		//System.out.println("Introduzca la ID del videojuego:");
		//int id = Integer.parseInt(sc.nextLine());
		System.out.println("Introduzca un nuevo nombre o deje en blanco para que el nombre siga siendo "+vg.getName()+":");
		String name = sc.nextLine();
		if(!name.equals("")) {
			System.out.println("Nombre actualizado");
			vg.setName(name);
		}
		System.out.println("Introduzca la nueva companía o deje en blanco para que el nombre siga siendo "+vg.getCompany()+":");
		String company = sc.nextLine();
		if(!company.equals("")) {
			System.out.println("Companía actualizada");
			vg.setCompany(company);
		}
		double note = -1;
		do {
			System.out.println("Introduzca una nueva nota de 0 a 10 o deje en blanco para que la nota siga siendo "+vg.getNote()+":");
			try {
				String notestr = sc.nextLine();
				if(!notestr.equals("")) {
					note = Double.parseDouble(notestr);
					if(note >= 0 && note <= 10) {
						System.out.println("Nota actualizada");
						vg.setNote(note);
						break;
					}
					System.out.println("Nota no válida");
				}
			}catch(NumberFormatException e){  
				System.out.println("Nota no válida");  
			 } 
			
		}while(note == -1);
		return vg;
	}
 	
 	private double getValidNote() {
 		while(true) {
			System.out.println("Introduzca nota de 0 a 10");
			try {
				double note = Double.parseDouble(sc.nextLine());
				if(note >= 0 && note <= 10) {
					return note;
				}
				System.out.println("Nota no válida");
			}catch(NumberFormatException e){  
				System.out.println("Nota no válida");  
			 }			
		}
 	}
 	
 	private String getValidString(String type) {
 		while(true) {
 			System.out.println("Introduzca "+type+":");
 			String text = sc.nextLine();
 			if(text.equals("")) {
 				System.out.println(" debe de indicar "+type);
 			}else {
 				return text;
 			}
 		}
 	}
 	
 	public int requestId() {
 		System.out.println("Introduzca una id (entero mayor que 0)");
 		while(true) {
 			try {
	 			int id = Integer.parseInt(sc.nextLine());
	 			
	 			if(id < 1) {
	 				System.out.println("La ID debe ser mayor que cero");
	 			}else {
	 				return id;
	 			}
 			}catch(NumberFormatException e){  
				System.out.println("La ID debe ser numérica");  
			} 			
 		}
 	}
	
	public int chooseAction() {
		System.out.println(menustr);
		while(true) {
			try {
				String opstr = sc.nextLine();
				if(opstr.equals("")) {
					return 0;
				}else {
					int op = Integer.parseInt(opstr);
					if(op > 0 && op <=5) {
						return op;
					}
				}
			}catch(NumberFormatException e){  
				System.out.println("Introduzca una opción válida o pulse intro para salir");  
			}			
		}
	}
}