package practica2.cliente;

import java.io.Closeable;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import practica2.cliente.entities.VideoGame;
import practica2.cliente.proxies.VideoGameProxy;

@SpringBootApplication
public class Application {
	
	@Autowired
	public static ApplicationContext context;
	
	// carga del objeto usado para hacer las peticiones http
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) throws IOException {
		// carga el contexto de spring
		context = SpringApplication.run(Application.class, args);
		VideoGameProxy vgp = context.getBean("videoGameProxy", VideoGameProxy.class);
		AppUserInterfaceVideoGames ui = context.getBean("appUserInterfaceVideoGames", AppUserInterfaceVideoGames.class);
		while(true) {
			int op = ui.chooseAction();
			if(op == 0) {
				break;
			}else if( op == 1) {
				// crea un videojuego y lo intenta añadir
				VideoGame vg = ui.create();
				vgp.add(vg);
			}else if( op == 2) {
				// solicita la id y si existe borra el videojuego
				int id = ui.requestId();
				if(vgp.delete(id)) {
					System.out.println("Videojuego "+id+" borrado");
				}else {
					System.out.println("Videojuego "+id+" no se pudo borrar");
				}
			}else if( op == 3) {
				// primero solicita una id, luego solicita el videojuego y luego lo actualiza
				int id = ui.requestId();
				VideoGame vg = vgp.get(id);
				if(vg != null) {
					vg = ui.update(vg);
					vgp.update(vg);
				}				
			}else if(op == 4) {
				int id = ui.requestId();
				VideoGame vg = vgp.get(id);
				if(vg != null) {
					System.out.println(vg);
				}
			}else if( op == 5) {
				VideoGame[] vgs = vgp.get();
				for(int i = 0; i < vgs.length; i++) {
					System.out.println(vgs[i]);
				}
			}
		}
		System.out.println("se ha terminado la ejecución");
		((Closeable) context).close();
	}
}