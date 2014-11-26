package musician101.minetanks.lib;

import musician101.minetanks.MineTanks;

import org.spongepowered.api.text.format.TextColors;

public class Reference
{
	public static final String ID = "minetanks";
	public static final String NAME = "MineTanks";
	public static final String VERSION = "1.2";
	
	public static class Messages
	{
		public static final String POSITIVE_PREFIX = TextColors.GREEN + MineTanks.getPrefix() + " ";
		public static final String NEGATIVE_PREFIX = TextColors.RED + MineTanks.getPrefix() + " ";
		
		public static final String FIELD_ALREADY_EXISTS = NEGATIVE_PREFIX + "%s already exists.";
		public static final String FIELD_DNE = NEGATIVE_PREFIX + "Sorry, that field doesn't exist.";
		public static final String FIELD_NOT_READY = NEGATIVE_PREFIX + "Sorry, this field is not ready.";
		public static final String LOCATION_NOT_IN_CUBOID = Messages.NEGATIVE_PREFIX + "Error: The location is outside of the cuboid.";
		public static final String MATCH_IN_PROGRESS = NEGATIVE_PREFIX + "Sorry, but the match has already started.";
		public static final String MISSING_CUBOID = Messages.NEGATIVE_PREFIX + "Error: The battlefield contains no cuboid or the cuboid in another world.";
		public static final String NO_PERMISSION = NEGATIVE_PREFIX + "Sorry, but you do not have permission for that.";
	}
	
	public static class Perms
	{
		public static final String PERMS_PREFIX = "minetanks.";
	}
}
