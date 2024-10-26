package jogo;//package cliente;

import Comunicados.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Game 
implements KeyListener{
	private Snake eu;
	private Snake outroPlayer;
	private Snake playerLulu;
	private Snake player1;
	private Food food;
	private Graphics graphics;
	private Parceiro servidor;

	private JFrame window;
	
	public static final int width = 30;
	public static final int height = 30;
	public static final int dimension = 20;
	
	public Game(Parceiro servidor) throws Exception {
		window = new JFrame();

		if(servidor == null)
			throw new Exception("Porta inválida");
		this.servidor = servidor;

		playerLulu = new Snake(2,4, "DIREITA");
		player1 = new Snake(2, 2, "ESQUERDA");
		
		food = new Food();
		
		graphics = new Graphics(this);
		
		window.add(graphics);
		
		window.setTitle("Snake");
		window.setSize(width * dimension + 2, height * dimension + dimension + 4);
		window.setVisible(true);
		window.addWindowListener(new FechamentoDeJanela());
	}
	
	public synchronized void start(String cobrinha) {
		if(cobrinha.equals("PlayerLulu")) {
			eu = playerLulu;
			outroPlayer = player1;
		}
		else {
			eu = player1;
			outroPlayer = playerLulu;
		}
		graphics.setS(eu);
		graphics.setS1(outroPlayer);
		graphics.state = "RUNNING";
		update();
	}
	
	public void update() {
		if(graphics.state == "RUNNING") {
			if(check_food_collision()) {
				eu.grow();
				food.random_spawn(eu, outroPlayer);
				try {
					servidor.receba(new ComunicadoDeCrescimento(food.getX(), food.getY()));
				}
				catch (Exception e)
				{}
			}
			else if(check_wall_collision() || check_self_collision() || check_player_collision()) {
				graphics.setWhoDied("VOCE");
				graphics.state = "END";
				try{
					servidor.receba(new ComunicadoDeMorte());
				}
				catch (Exception e)
				{}
			}
			else
			{
				eu.move();
				try {
					servidor.receba(new ComunicadoDeMovimento("FRENTE"));
				}
				catch (Exception e)
				{}
				//outroPlayer.move();
			}
		}
	}
	
	private boolean check_wall_collision() {
		if(eu.getX() < 0 || eu.getX() >= width * dimension
				|| eu.getY() < 0|| eu.getY() >= height * dimension) {
			return true;
		}
		return false;
	}
	
	private boolean check_food_collision() {
		if(eu.getX() == food.getX() * dimension && eu.getY() == food.getY() * dimension) {
			return true;
		}
		return false;
	}
	
	private boolean check_self_collision() {
		for(int i = 1; i < eu.getBody().size(); i++) {
			if((eu.getX() == eu.getBody().get(i).x &&
					eu.getY() == eu.getBody().get(i).y)) {
				return true;
			}
		}
		return false;
	}

	private boolean check_player_collision() {
		for(int i = 0; i < getPlayer1().getBody().size(); i++)
		{
			if((eu.getX() == outroPlayer.getBody().get(i).x &&
					eu.getY() == outroPlayer.getBody().get(i).y)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		if(graphics.state == "RUNNING") {
			if(keyCode == KeyEvent.VK_UP && eu.getMove() != "DOWN") {
				eu.up();
				try {
					servidor.receba(new ComunicadoDeMovimento("CIMA"));
				}
				catch (Exception err)
				{}
			}
		
			if(keyCode == KeyEvent.VK_DOWN && eu.getMove() != "UP") {
				eu.down();
				try {
					servidor.receba(new ComunicadoDeMovimento("BAIXO"));
				}
				catch (Exception err)
				{}
			}
		
			if(keyCode == KeyEvent.VK_LEFT && eu.getMove() != "RIGHT") {
				eu.left();
				try {
					servidor.receba(new ComunicadoDeMovimento("ESQUERDA"));
				}
				catch (Exception err)
				{}
			}
		
			if(keyCode == KeyEvent.VK_RIGHT && eu.getMove() != "LEFT") {
				eu.right();
				try {
					servidor.receba(new ComunicadoDeMovimento("DIREITA"));
				}
				catch (Exception err)
				{}
			}
		}
		else if (graphics.state == "END") {
			if(keyCode == KeyEvent.VK_SPACE)
			{
				eu.restartSnake();
				outroPlayer.restartSnake();
				food.restartFood();
				graphics.state = "START";
				try {
					servidor.receba(new ComunicadoDeNovoJogo());
				} catch (Exception err)
				{}
			}
		}
	}

	public void restart()
	{
		graphics.state = "RUNNING";
		update();
	}

	@Override
	public void keyReleased(KeyEvent e) {	}

	public Snake getPlayerLulu() {
		return eu;
	}

	public Snake getPlayer1()
	{
		if(eu == playerLulu)
			return player1;
		else
			return playerLulu;
	}

	public Snake getCobrinhaInanimadaLulu()
	{
		return playerLulu;
	}

	public Snake getCobrinhaInanimada1() {
		return player1;
	}

	public void setPlayerLulu(Snake playerLulu) {
		this.playerLulu = playerLulu;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public JFrame getWindow() {
		return window;
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	protected class FechamentoDeJanela extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			try {
				servidor.receba(new PedidoParaSair());
				servidor.adeus();
			}
			catch (Exception err)
			{}
			System.exit(0);
		}
	}
}
