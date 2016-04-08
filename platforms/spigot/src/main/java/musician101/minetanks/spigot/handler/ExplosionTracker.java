package musician101.minetanks.spigot.handler;

import org.bukkit.entity.Arrow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExplosionTracker
{
    private static final List<UUID> tracker = new ArrayList<>();
    private static Map<UUID, Integer> taskIds = new HashMap<>();

    public static void addArrow(Arrow arrow)
    {
        tracker.add(arrow.getUniqueId());
        /*taskIds.put(arrow.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () ->
        {
            for(World world : Bukkit.getWorlds())
            {
                for (Entity entity : world.getEntitiesByClass(Arrow.class))
                {
                    if (!(entity instanceof  Arrow))
                        continue;

                    Arrow a = (Arrow) entity;
                    if (!tracker.contains(a.getUniqueId()))
                        continue;

                    boolean isInField = false;
                    for (SpigotBattleField field : plugin.getFieldStorage().getFields().values())
                    {
                        if (field.getRegion().isInRegion(a.getLocation()))
                            isInField = true;
                    }

                    if (isInField)
                        continue;

                    tracker.
                }
            }
        }, 1, 1));*/
    }

    public static void removeArrow(Arrow arrow)
    {
        tracker.remove(arrow.getUniqueId());
    }

    public static List<UUID> getTracker()
    {
        return tracker;
    }
}
