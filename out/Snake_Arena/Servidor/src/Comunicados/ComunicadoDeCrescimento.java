package Comunicados;

public class ComunicadoDeCrescimento extends Comunicado {
    private int foodX;
    private int foodY;
    public ComunicadoDeCrescimento(int posX, int posY)
    {
        this.foodX = posX;
        this.foodY = posY;
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void setFoodX(int foodX) {
        this.foodX = foodX;
    }

    public void setFoodY(int foodY) {
        this.foodY = foodY;
    }
}
