package practica2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Cargando el cntexto de Spring");
		SpringApplication.run(Application.class, args);
		System.out.println("Contexto de Spring cargado");
	}

}
