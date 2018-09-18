package ar.edu.unlam.tallerweb1.modelo;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;

import ar.edu.unlam.tallerweb1.SpringTest;

public class TpTest extends SpringTest {
	
	private Continente continente , continenteDos , continenteTres;
	private Pais pais , paisDos , paisTres;
	private Ciudad ciudad , ciudadDos , ciudadTres;
	private Ubicacion ubicacion , ubicacionDos , ubicacionTres;
	private Session sesion;
	private List<Pais> paises;
	private List<Ciudad> ciudades;
	

	@Before
	public void init() {
		continente = new Continente();
		continenteDos = new Continente();
		continenteTres = new Continente();
		pais = new Pais();
		paisDos = new Pais();
		paisTres = new Pais();
		ciudad = new Ciudad();
		ciudadDos = new Ciudad();
		ciudadTres = new Ciudad();
		ubicacion = new Ubicacion();
		ubicacionDos = new Ubicacion();
		ubicacionTres = new Ubicacion();
		sesion = getSession();
		paises = new ArrayList<Pais>();
		ciudades = new ArrayList<Ciudad>();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodosLosPaisesDeHablaInglesa() {
		pais.setNombre("Inglaterra");
		pais.setIdioma("Ingles");
		pais.setCapital("Londres");
		
		paisDos.setNombre("España");
		paisDos.setIdioma("Español");
		paisDos.setCapital("Madrid");
		
		paisTres.setNombre("Estados Unidos");
		paisTres.setIdioma("Ingles");
		paisTres.setCapital("Washington");
		
		sesion.save(pais);
		sesion.save(paisDos);
		sesion.save(paisTres);
		
		//--- 1.Buscar todos los paises de habla inglesa ---//
		
		paises = sesion.createCriteria(Pais.class)
				.add(Restrictions.eq("idioma", "Ingles"))
				.list();
		
		assertThat(paises).hasSize(2);
		assertThat(paises).isNotEmpty();
		assertThat(paises.get(0).getIdioma()).isEqualTo("Ingles");
		assertThat(paises.get(0).getNombre());
		assertThat(paises.get(0).getCapital());
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodosLosPaisesDelContinenteEuropeo() {
		continente.setNombre("America");
		continenteDos.setNombre("Europa");
		continenteTres.setNombre("Asia");
		
		pais.setNombre("Argentina");
		pais.setIdioma("Argentino");
		pais.setCapital("Ciudad Autonoma de la ciudad de Buenos Aires");
		pais.setContinente(continente);
		
		paisDos.setNombre("Alemania");
		paisDos.setIdioma("Aleman");
		paisDos.setCapital("Berlin");
		paisDos.setContinente(continenteDos);
		
		paisTres.setNombre("Rusia");
		paisTres.setIdioma("Ruso");
		paisTres.setCapital("Moscu");
		paisTres.setContinente(continenteTres);
		
		sesion.save(pais);
		sesion.save(paisDos);
		sesion.save(paisTres);
		
		//--- 2.Buscar todos los paises del continente europeo ---//
		
		paises = sesion.createCriteria(Pais.class)
				.createAlias("continente", "con")
				.add(Restrictions.eq("con.nombre", "Europa"))
				.list();
		
		assertThat(paises).hasSize(1);
		assertThat(paises).isNotEmpty();
		assertThat(paises.get(0).getContinente().getNombre()).isEqualTo("Europa");
		assertThat(paises.get(0).getCapital());
		assertThat(paises.get(0).getNombre());
		assertThat(paises.get(0).getIdioma());
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodosLosPaisesCuyaCapitalEstanAlNorteDelTropicoDeCancer() {
		pais.setCapital("Otawa");
		
		ubicacion.setLatitud(24D);
		ubicacion.setLongitud(37D);
		
		sesion.save(pais);
		
		//--- 3.Buscar todos los paises cuya capital esta al norte del tropico de cancer ---//
		
		paises = sesion.createCriteria(Pais.class)
				.createAlias("pais.capital", "cap")
				.createAlias("cap.ubicacionGeografica", "ubi")
				.add(Restrictions.gt("ubi.latitud", 24D))
				.list();
		
		assertThat(paises).hasSize(1);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodasLasCiudadesDelHemisferioSur() {
		ciudad.setNombre("Buenos Aires");
		ubicacion.setLongitud(50D);
		
		ciudad.setUbicacion(ubicacion);
		
		sesion.save(ciudad);
		sesion.save(ubicacion);
		
		/*ciudades = sesion.createCriteria(Ciudad.class)
				.createAlias("ciudad.ubicacionGeografica", "ubi")
				.add(Restrictions.eq("ubi.longitud", 50D))
				.list();*/
		
		assertThat(ciudades).hasSize(1);
				
	}
	
}
