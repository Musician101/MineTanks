package musician101.minetanks.spigot.handlers;

import org.bukkit.entity.Arrow;

import java.util.ArrayList;
import java.util.List;

public class ExplosionTracker
{
    static List<Arrow> tracker = new ArrayList<Arrow>();

    public static void addArrow(Arrow arrow)
    {
        tracker.add(arrow);
    }

    public static void removeArrow(Arrow arrow)
    {
        tracker.remove(arrow);
    }

    public static List<Arrow> getTracker()
    {
        return tracker;
    }
}
