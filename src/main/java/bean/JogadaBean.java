package bean;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.hibernate.internal.build.AllowSysOut;

import static util.MessageUtil.addInfoMessage;
import static util.MessageUtil.addErrorMessage;
import dao.JogadaDAO;
import entidade.Jogada;

@ManagedBean
public class JogadaBean implements Serializable{
	private Jogada jogada = new Jogada();
	private List<Jogada> list;
	
	public String save() {
		try {
			jogada.setJogada1(JogadaDAO.valoresJogada());
			jogada.setJogada2(JogadaDAO.valoresJogada());
			jogada.setResultado(JogadaDAO.resultadoVencedor(jogada.getJogada1(), jogada.getJogada2(), jogada.getJogador1(), jogada.getJogador2()));
			jogada.setData(new Date());
			JogadaDAO.insert(jogada);
			addInfoMessage("Sucesso", "Jogada adiciona com sucesso.");
			Jogada v = JogadaDAO.buscarVencedor();
			addInfoMessage("Vencedor", "Jogador(a): "+v.getResultado());
		} catch (Exception e) {
			addErrorMessage("Erro", "Erro ao adicionar a jogada.");
		}
		return null;
	}
	
	public String update() {
		try {
			Jogada jog = JogadaDAO.buscarPorId(jogada.getId());
			jog.setJogador1(jogada.getJogador1());
			jog.setJogador2(jogada.getJogador2());
			JogadaDAO.update(jog);
			addInfoMessage("Sucesso", "Jogada atualizada com sucesso.");
		} catch (Exception e) {
			addErrorMessage("Erro", "Erro ao atualizar jogada.");
		}
		return null;
	}
	
	public String deletar() {
		try {
			JogadaDAO.remove(jogada);
			addInfoMessage("Deletada", "Jogada deletada com sucesso."); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			addErrorMessage("Erro", "Erro ao deletar jogada.");
		}
		return null;	
	}
	
	
	public Jogada getJogada() {
		return jogada;
	}
	public void setJogada(Jogada jogada) {
		this.jogada = jogada;
	}
	public List<Jogada> getList() {
		if(list == null) {
			list = JogadaDAO.list();
		}
		return list;
	}
	public void setList(List<Jogada> list) {
		this.list = list;
	}
}
