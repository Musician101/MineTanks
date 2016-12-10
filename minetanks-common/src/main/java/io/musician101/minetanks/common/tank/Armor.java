package io.musician101.minetanks.common.tank;


public class Armor {

    private final double front;
    private final double rear;
    private final double side;

    public Armor(double front, double side, double rear) {
        this.front = front;
        this.side = side;
        this.rear = rear;
    }

    public double getArmorValue() {
        return (front + side + rear) / 3;
    }

    public double getFrontalArmor() {
        return front;
    }

    public double getRearArmor() {
        return rear;
    }

    public double getSideArmor() {
        return side;
    }
}
