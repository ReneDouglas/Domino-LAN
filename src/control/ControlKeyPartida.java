package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import view.Partida;

public class ControlKeyPartida extends KeyAdapter{
	
	private Partida pane;
	
	public ControlKeyPartida(Partida pane) {
		this.pane = pane;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
//		System.out.println(this.pane.isSuaVez());
		
		if (this.pane.isSuaVez()) {
			if(e.getKeyCode() == KeyEvent.VK_Q){
				for (int i = 0; i < pane.pecas.size(); i++) {
					if(pane.pecas.get(i).virada == false && pane.pecas.get(i).arrastar == true){
//						System.out.println(pane.pecas.get(i).id+"/img: "+pane.pecas.get(i).img);
						pane.pecas.get(i).virada = true;
						pane.pecas.get(i).setIcone(pane.pecas.get(i).img);
						pane.pecas.get(i).setBounds(pane.pecas.get(i).posX, pane.pecas.get(i).posY, pane.pecas.get(i).getIcon().getIconWidth(), pane.pecas.get(i).getIcon().getIconHeight());
						break;
					}
					else if(pane.pecas.get(i).virada == true && pane.pecas.get(i).arrastar == true){
						pane.pecas.get(i).virada = false;
						pane.pecas.get(i).setIcone(pane.pecas.get(i).img);
						pane.pecas.get(i).setBounds(pane.pecas.get(i).posX, pane.pecas.get(i).posY, pane.pecas.get(i).getIcon().getIconWidth(), pane.pecas.get(i).getIcon().getIconHeight());
						break;
					}
				}
			}
			
		}
		
		/*if(e.getKeyCode() == KeyEvent.VK_Q && pane.test.virada == false){
			pane.test.setIcone("/view/sprites/peca_18.png");
			pane.test.setBounds(pane.test.posX, pane.test.posY, pane.test.getIcon().getIconWidth(), pane.test.getIcon().getIconHeight());
			pane.test.virada = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_Q && pane.test.virada == true){
			pane.test.setIcone("/view/sprites/vertical/peca_18.png");
			pane.test.setBounds(pane.test.posX, pane.test.posY, pane.test.getIcon().getIconWidth(), pane.test.getIcon().getIconHeight());
			pane.test.virada = false;
		}*/
	}

}
