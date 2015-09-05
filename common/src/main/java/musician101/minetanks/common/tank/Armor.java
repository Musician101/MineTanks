package musician101.minetanks.common.tank;

public class Armor
{
    double front;
    double side;
    double rear;

    public Armor(double front, double side, double rear)
    {
        this.front = front;
        this.side = side;
        this.rear = rear;
    }

    public double getFrontalArmor()
    {
        return front;
    }

    public double getRearArmor()
    {
        return rear;
    }

    public double getSideArmor()
    {
        return side;
    }

    public double getArmorValue()
    {
        return (front + side + rear) / 3;
    }
}
