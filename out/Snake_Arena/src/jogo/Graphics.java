package jogo;//package cliente;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Graphics
extends JPanel
implements ActionListener{
	private Timer t = new Timer(100, this);
	public String state;
	private String whoDied = "NOBODY";
	
	private Snake s;
	private Snake s1;
	private Food f;
	private Game game;
	
	public Graphics(Game g) {
		t.start();
		state = "START";
		
		game = g;
		s = g.getCobrinhaInanimadaLulu();
		s1 = g.getCobrinhaInanimada1();
		f = g.getFood();
		
		//add a keyListner 
		this.addKeyListener(g);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
	}
	
	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, Game.width * Game.dimension + 5, Game.height * Game.dimension + 5);
		
		if(state == "START") {
			g2d.setColor(Color.white);
			g2d.drawString("Esperando jogador", Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension - 20);
		}
		else if(state == "RUNNING") {
			g2d.setColor(Color.red);
			g2d.fillRect(f.getX() * Game.dimension, f.getY() * Game.dimension, Game.dimension, Game.dimension);
		
			g2d.setColor(Color.BLUE);
			for(Rectangle r : s.getBody()) {
				g2d.fill(r);
			}
			g2d.setColor(Color.MAGENTA);
			g2d.fill(s.getBody().get(0));

			g2d.setColor(Color.GREEN);
			for(Rectangle r : s1.getBody()) {
				g2d.fill(r);
			}
		}
		else {
			g2d.setColor(Color.white);
			g2d.drawString("Sua pontuação: " + (s.getBody().size() - 3), Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension - 20);
			g2d.drawString("Pontuação do adversário: " + (s1.getBody().size() - 3), Game.width/2 * Game.dimension - 40, (Game.height / 2 + 2) * Game.dimension - 20);
			g2d.drawString("Pressione [ESPAÇO] para jogar de novo", Game.width/2 * Game.dimension - 70, (Game.height / 2 + 4) * Game.dimension - 20);
			if(whoDied.equals("VOCE"))
				g2d.drawString("Você morreu!", Game.width/2 * Game.dimension - 40, (Game.height / 2 + 6) * Game.dimension - 20);
			if(whoDied.equals("OUTRO"))
				g2d.drawString("Seu adversário morreu!", Game.width/2 * Game.dimension - 70, (Game.height / 2 + 6) * Game.dimension - 20);

		}
	}

	public void setS(Snake s) {
		this.s = s;
	}

	public void setS1(Snake s1) {
		this.s1 = s1;
	}

	public void setWhoDied(String player)
	{
		if(player.equals("VOCE"))
			this.whoDied = player;
		else if(player.equals("OUTRO"))
			this.whoDied = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		game.update();
	}
	
}
