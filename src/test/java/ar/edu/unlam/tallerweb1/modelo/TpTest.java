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
	
	private Continente continente;
	private Pais pais;
	private Ciudad ciudad;
	private Ubicacion ubicacion;
	private Session sesion;
	private List<Pais> paises;
	

	@Before
	public void init() {
		continente = new Continente();
		pais = new Pais();
		ciudad = new Ciudad();
		ubicacion = new Ubicacion();
		sesion = getSession();
		paises = new ArrayList<Pais>();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodosLosPaisesDeHablaInglesa() {
		pais.setIdioma("ingles");
		
		sesion.save(pais);
		
		//--- 1.Buscar todos los paises de habla inglesa ---//
		
		paises = sesion.createCriteria(Pais.class)
				.add(Restrictions.eq("idioma", "ingles"))
				.list();
		
		assertThat(paises).hasSize(1);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodosLosPaisesDelContinenteEuropeo() {
		continente.setNombre("Europa");
		pais.setContinente(continente);
		
		sesion.save(pais);
		
		//--- 2.Buscar todos los paises del continente europeo ---//
		
		paises = sesion.createCriteria(Pais.class)
				.createAlias("continente", "con")
				.add(Restrictions.eq("con.nombre", "Europa"))
				.list();
		
		assertThat(paises).hasSize(1);
	}
	
	/*@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodosLosPaisesCuyaCapitalEstanAlNorteDelTropicoDeCancer() {
		pais.setCapital("Otawa");
		
		sesion.save(pais);
		
		paises = sesion.createCriteria(Pais.class)
				.add(Restrictions.eq("", value))
	}*/
	
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodasLasCiudadesDelHemisferioSur() {
		
	}
	
	
}
