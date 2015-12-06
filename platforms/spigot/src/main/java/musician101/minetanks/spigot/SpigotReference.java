package musician101.minetanks.spigot;

import musician101.minetanks.common.CommonReference.Constants;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.tank.Tank;

import java.io.File;

public class SpigotReference
{
    //TODO move all methods to CommonReference
    public static String autoLoaderClipSize(String message, int current, int max)
    {
        return message.replace(Constants.CURRENT_CLIP.toString(), current + "").replace(Constants.MAX_CLIP.toString(), max + "");
    }

    public static String battleField(String message, BattleField field)
    {
        return message.replace(Constants.FIELD.toString(), field.getName());
    }

    public static String enabled(String message, BattleField field)
    {
        return message.replace(Constants.ENABLED.toString(), field.isEnabled() ? "Enabled" : "Disabled");
    }

    public static String file(String message, File file)
    {
        return message.replace(Constants.FILE.toString(), file.getName());
    }

    public static String number(String message, double number)
    {
        return message.replace(Constants.NUMBER.toString(), number + "");
    }

    public static String number(String message, int number)
    {
        return message.replace(Constants.NUMBER.toString(), number + "");
    }

    public static String set(String message, boolean isSet)
    {
        return message.replace(Constants.SET.toString(), (isSet ? "Not " : "") + "Set");
    }

    public static String string(String message, String string)
    {
        return message.replace(Constants.STRING.toString(), string);
    }

    public static String tank(String message, Tank tank)
    {
        return message.replace(Constants.TANK.toString(), tank == null ? "None" : tank.getName());
    }
}
