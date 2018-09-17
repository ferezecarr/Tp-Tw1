package ar.edu.unlam.tallerweb1.persistencia;

import org.hibernate.Session;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public class UsuarioTest extends SpringTest{

	@Test
	@Transactional
	@Rollback
	public void eliminarUsuarioTest() {
		
		Usuario usuario = new Usuario();
		usuario.setEmail("email@usuario.com");
		usuario.setPassword("1234");
		usuario.setRol("usuario");
		
		Session session = getSession();
		session.save(usuario);
		
		usuario.setEmail("emailModificado@usuario.com");
		
		session.update(usuario);
		
		Usuario usuarioBuscado = session.get(Usuario.class,usuario.getId());
		assertThat(usuarioBuscado).isNotNull();
		
		session.delete(usuario);
	}
}
