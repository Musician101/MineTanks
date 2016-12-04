package io.musician101.minetanks.common.tank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AbstractInfoStorage<I extends Info>
{
    private final Map<String, I> map = new HashMap<>();

    @SafeVarargs
    protected AbstractInfoStorage(I... infos)
    {
        for (I info : infos)
            map.put(info.getName(), info);
    }


    public I get(String name)
    {
        for (Entry<String, I> entry : map.entrySet())
            if (name.equalsIgnoreCase(entry.getKey()))
                return entry.getValue();

        return null;
    }


    public List<I> values()
    {
        return new ArrayList<>(map.values());
    }
}
