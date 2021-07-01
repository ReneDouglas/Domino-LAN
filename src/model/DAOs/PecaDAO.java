package model.DAOs;

import java.util.ArrayList;

import model.Peca;

public class PecaDAO{

	private static ArrayList<Peca> pecas = new ArrayList<Peca>();
	
	
	public static void inserir(Peca pecaVO){
		pecas.add(pecaVO);
	}
	
	public static void excluir(Peca pecaVO){
		pecas.remove(pecaVO);
	}
	
	public static ArrayList<Peca> listar(){
		
		return pecas;
		
	}

	
}
