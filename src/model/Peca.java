package model;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Peca extends JLabel{
	private static final long serialVersionUID = 1L;
	public int id;
	public int posX;
	public int posY;
	public String img;
	public boolean arrastar = false;
	public boolean virada = false;
	public boolean foiJogada = false;
	public String nomeJogador = "";
//	public Peca(int posX, int posY, String img) {
//		this.posX = posX;
//		this.posY = posY;
//		this.img = img;
//		//this.setIcon(new ImageIcon(getClass().getResource(img)));
//	}
	
//	public Peca(int posX, int posY, boolean virada) {
//		this.posX = posX;
//		this.posY = posY;
//		this.virada = virada;
//	}
	
	public Peca(int id, int posX, int posY, boolean virada, String img) {
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.virada = virada;
		this.img = img;
	}
	
	public void setIcone(String img){
		if(this.virada) this.setIcon(new ImageIcon(getClass().getResource("/view/sprites/"+img)));
		else this.setIcon(new ImageIcon(getClass().getResource("/view/sprites/vertical/"+img)));
			
	}

}
