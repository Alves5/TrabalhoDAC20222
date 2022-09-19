package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import entidade.Jogada;
import util.JPAUtil;
import java.util.Random;

public class JogadaDAO {
	public static void insert(Jogada j) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(j);
		em.getTransaction().commit();
		em.close();
	}
	public static void update(Jogada j) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(j);
		em.getTransaction().commit();
		em.close();
	}
	public static void remove(Jogada j) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		j = em.find(Jogada.class, j.getId());
		em.remove(j);
		em.getTransaction().commit();
		em.close();
	}
	public static Jogada buscarPorId(Integer id) {
		EntityManager em = JPAUtil.criarEntityManager();
		Jogada j1 = em.find(Jogada.class, id);
		em.close();
		return j1;
	}
	public static Jogada buscarVencedor() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query ultimo = em.createNativeQuery("SELECT MAX(id) FROM jogada");
		Integer ul = (Integer) ultimo.getSingleResult();
		Jogada vencedor = buscarPorId(ul);
		return vencedor;
	}
	public static List<Jogada> list(){
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select j from Jogada j");
		List<Jogada> lista = q.getResultList();
		em.close();
		return lista;
	}
	public static String resultadoVencedor(String jogada1, String jogada2, String jogador1, String jogador2) {
		String resultado;
		if(jogada1 == "Papel" && jogada2 == "Tesoura") {
			resultado = jogador2;
		}else if(jogada1 == "Papel" && jogada2 == "Pedra"){
			resultado = jogador1;
		}else if(jogada1 == "Pedra" && jogada2 == "Tesoura"){
			resultado = jogador1;
		}else if(jogada1 == "Tesoura" && jogada2 == "Papel") {
			resultado = jogador1;
		}else if(jogada1 == "Pedra" && jogada2 == "Papel"){
			resultado = jogador2;
		}else if(jogada1 == "Tesoura" && jogada2 == "Pedra"){
			resultado = jogador2;
		}else {
			resultado = "Empate";
		}
		return resultado;
	}
	public static String valoresJogada() {
		List<String> valores = new ArrayList<>();
		valores.add("Pedra");
		valores.add("Papel");
		valores.add("Tesoura");
		JogadaDAO jog = new JogadaDAO();
		String listResult = jog.getRandomElement(valores);
		return listResult;
	}
	public String getRandomElement(List<String> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
