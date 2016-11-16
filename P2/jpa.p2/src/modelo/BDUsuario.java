package modelo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BDUsuario {
	private static final String PERSISTENCE_UNIT_NAME = "usuario";
	private static EntityManagerFactory factoria;
	
	public static void insert(Usuario usuario) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		//Abrimos una transacción
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		q.setParameter("email", usuario.getEmail());
		//Si no existe ese usuario se guarda y se hace commit
		if(q.getResultList().size() == 0)
		{
			em.persist(usuario);
			em.getTransaction().commit();
		}
		//Cerramos la transacción
		em.close();
	}
	
	public static void update(Usuario usuario) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		//Abrimos una transacción
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		q.setParameter("email", usuario.getEmail());
		if(q.getResultList().size() == 1)
		{
			Usuario user = (Usuario) q.getSingleResult();
			user.setNombre(usuario.getNombre());
			user.setApellido(usuario.getApellido());
			em.merge(user);
			em.getTransaction().commit();
		}
		//Cerramos la transacción
		em.close();
	}
	
	public static void delete(Usuario usuario) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		//Abrimos una transacción
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		q.setParameter("email", usuario.getEmail());
		if(q.getResultList().size() == 1)
		{
			Usuario user = (Usuario) q.getSingleResult();
			em.remove(user);
			em.getTransaction().commit();	
		}
		//Cerramos la transacción
		em.close();
	}
	
	public static Usuario select(String email) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		Usuario user = null;
		//Abrimos una transacción
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		q.setParameter("email", email);
		if(q.getResultList().size() == 1)
			user = (Usuario) q.getSingleResult();
		//Cerramos la transacción
		em.close();
		return user;
	}

	public static List<Usuario> listarUsuarios() {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();	
		Query q = em.createQuery("SELECT u FROM Usuario u");
		@SuppressWarnings("unchecked")
		List<Usuario> users = q.getResultList();
		em.close();
		return users;
	}
	
	public static boolean existeUsuario(Usuario usuario)
	{
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		//Abrimos una transacción
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		q.setParameter("email", usuario.getEmail());
		if(q.getResultList().size() == 1)
			return true;
		else
			return false;
	}
	
}
