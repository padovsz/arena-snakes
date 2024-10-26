package jogo;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Snake {
	private ArrayList<Rectangle> body;
	private int w = Game.width;
	private int h = Game.height;
	private int d = Game.dimension;
	private int x;
	private int y;

	private String pos;
	private String move; //NOTHING, UP, DOWN, LEFT, RIGHT
	
	public Snake(int x, int y, String pos) {
		body = new ArrayList<>();

		Rectangle temp = new Rectangle(Game.dimension, Game.dimension);
		temp.setLocation((Game.width / x) * Game.dimension, (Game.height / y) * Game.dimension);
		body.add(temp);
		
		temp = new Rectangle(d, d);
		if(pos == "DIREITA")
			temp.setLocation((w / x + 1) * d, (h / y) * d);
		else
			temp.setLocation((w / x - 1) * d, (h / y) * d);
		body.add(temp);
		
		temp = new Rectangle(d, d);
		if(pos == "ESQUERDA")
			temp.setLocation((w / x - 2) * d, (h / y) * d);
		else
			temp.setLocation((w / x + 2) * d, (h / y) * d);
		body.add(temp);

		if(pos == "DIREITA")
			move = "LEFT";
		else
			move = "RIGHT";

		this.pos = pos;
		this.x = x;
		this.y = y;
	}
	//do outro lado
	// Snake cobrinha1 = Snake(3,4, "DIREITA");
	
	public void move() {
		if(move != "NOTHING") {
			Rectangle first = body.get(0);
			
			Rectangle temp = new Rectangle(Game.dimension, Game.dimension);
			
			if(move == "UP") {
				temp.setLocation(first.x, first.y - Game.dimension);
			}
			else if(move == "DOWN") {
				temp.setLocation(first.x, first.y + Game.dimension);
			}
			else if(move == "LEFT") {
				temp.setLocation(first.x - Game.dimension, first.y);
			}
			else{
				temp.setLocation(first.x + Game.dimension, first.y);
			}
			
			body.add(0, temp);
			body.remove(body.size()-1);
		}
	}
	
	public void grow() {
		Rectangle first = body.get(0);
		
		Rectangle temp = new Rectangle(Game.dimension, Game.dimension);
		
		if(move == "UP") {
			temp.setLocation(first.x, first.y - Game.dimension);
		}
		else if(move == "DOWN") {
			temp.setLocation(first.x, first.y + Game.dimension);
		}
		else if(move == "LEFT") {
			temp.setLocation(first.x - Game.dimension, first.y);
		}
		else{
			temp.setLocation(first.x + Game.dimension, first.y);
		}
		
		body.add(0, temp);
	}

	public void restartSnake() {
		body = new ArrayList<>();

		Rectangle temp = new Rectangle(Game.dimension, Game.dimension);
		temp.setLocation((Game.width / x) * Game.dimension, (Game.height / y) * Game.dimension);
		body.add(temp);

		temp = new Rectangle(d, d);
		if(pos == "DIREITA")
			temp.setLocation((w / x + 1) * d, (h / y) * d);
		else
			temp.setLocation((w / x - 1) * d, (h / y) * d);
		body.add(temp);

		temp = new Rectangle(d, d);
		if(pos == "ESQUERDA")
			temp.setLocation((w / x - 2) * d, (h / y) * d);
		else
			temp.setLocation((w / x + 2) * d, (h / y) * d);
		body.add(temp);

		if(pos == "DIREITA")
			move = "LEFT";
		else
			move = "RIGHT";
	}

	public ArrayList<Rectangle> getBody() {
		return body;
	}
	

	public void setBody(ArrayList<Rectangle> body) {
		this.body = body;
	}
	
	public int getX() {
		return body.get(0).x;
	}
	
	public int getY() {
		return body.get(0).y ;
	}
	
	public String getMove() {
		return move;
	}
	
	public void up() {
		move = "UP";
	}
	public void down() {
		move = "DOWN";
	}
	public void left() {
		move = "LEFT";
	}
	public void right() {
		move = "RIGHT";
	}
}
