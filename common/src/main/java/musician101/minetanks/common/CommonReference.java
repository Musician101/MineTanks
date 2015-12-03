package musician101.minetanks.common;

public class CommonReference
{
    public static final String DESCRIPTION = "${project.description}";
    public static final String ID = "minetanks";
    public static final String NAME = "MineTanks";
    public static final String VERSION = "${project.version}";

    public static final String MOVIN_ON_OUT = "Movin' on out. Shuck 'em up!";
    public static final String PACK_IT_UP = "Pack it up, boys. We're heading home.";
    public static final String TANK_SELECTION = "Tank Selection";

    public static class CommonCommands
    {
        public static final String MT = "mt";

        public static final String CREATE = "create";
        public static final String CREATE_DESC = "Create a new battlefield.";

        public static final String ENABLE_NAME = "enable";
        public static final String ENABLE_DESC = "Set the battlefield that you wish to edit.";

        public static final String FIELD = "field";

        public static final String FORCE_END_NAME = "forceend";
        public static final String FORCE_END_DESC = "Forcibly end the match at the specified field.";

        public static final String GREEN_SPAWN_NAME = "greenspawn";
        public static final String GREEN_SPAWN_DESC = "Set the green team's spawn point.";

        public static final String HELP = "help";

        public static final String JOIN_NAME = "join";
        public static final String JOIN_DESC = "Enter a battlefield.";

        public static final String LEAVE_NAME = "leave";
        public static final String LEAVE_DESC = "Leave the battlefield.";

        public static final String NAME = "name";

        public static final String RED_SPAWN_NAME = "redspawn";
        public static final String RED_SPAWN_DESC = "Set the red team's spawn point.";

        public static final String RADIUS = "radius | xradius yradius zradius";

        public static final String REGION_NAME = "region";
        public static final String REGION_DESC = "Set the region region of the currently selected battlefield.";

        public static final String REMOVE_NAME = "remove";
        public static final String REMOVE_DESC = "Remove the specified field.";

        public static final String SPECTATE_NAME = "spectate";
        public static final String SPECTATE_DESC = "Spectate a battlefield.";

        public static final String SPECTATORS_NAME = "spectators";
        public static final String SPECTATORS_DESC = "Set the spectators' spawn point.";

        public static final String STATUS_NAME = "status";
        public static final String STATUS_DESC = "View the status of the specified field.";
    }

    public static class CommonConfig
    {
        public static final String ARMOR = "armor.";
        public static final String ENABLED = "enabled";
        public static final String GREEN_SPAWN = "greenSpawn";
        public static final String INVENTORY = "inventory.";
        public static final String MAXX = "maxX";
        public static final String MINX = "minX";
        public static final String MAXY = "maxY";
        public static final String MINY = "minY";
        public static final String MAXZ = "maxZ";
        public static final String MINZ = "minZ";
        public static final String RED_SPAWN = "redSpawn";
        public static final String REGION = "region";
        public static final String SPECTATORS = "spectators";
        public static final String WORLD = "world";
    }

    public static class CommonItemText
    {
        public static final String CANNON = "Your Cannon";
        public static final String CLIP_RELOAD_TIME = "Clip Reload Time: " + Constants.NUMBER;
        public static final String CLIP_SIZE = "Clip Size: " + Constants.CURRENT_CLIP + "/" + Constants.MAX_CLIP;
        public static final String CYCLE_TIME = "Cycle Time: " + Constants.NUMBER;
        public static final String ENGINE = "Your Engine";
        public static final String NOT_READY = "You are currently not ready.";
        public static final String OPEN_HANGAR = "Open Hangar";
        public static final String RADIO = "Your Radio";
        public static final String READY = "You are currently ready.";
        public static final String READY_UP = "Ready Up";
        public static final String RELOAD_TIME = "Reload Time: " + Constants.NUMBER;
        public static final String SELECTED_TANK = "Tank: " + Constants.TANK;
        public static final String SPEED_VALUE = "Speed Value: " + Constants.NUMBER;
        public static final String TRACKS = "Your Tracks";
        public static final String TURRET = "Your Turret";
        public static final String UNREADY = "Unready";
    }
    public static class CommonMessages
    {
        static final String PREFIX = "[" + CommonReference.NAME + "] ";
        public static final String CHECK_FIELD_STATUS = PREFIX + "Check the status of the battlefield by using /mt status.";
        public static final String FIELD_CREATED = PREFIX + Constants.FIELD + " successfully created.";
        public static final String FIELD_CREATED_2 = PREFIX + "Check the status of the battlefield by using /mt status.";
        public static final String FIELD_DELETED = PREFIX + Constants.FIELD + " has been deleted.";
        public static final String FIELD_DNE = PREFIX + "Sorry, that field doesn't exist.";
        public static final String FIELD_DISABLED = PREFIX + "Sorry, this field is currently disabled.";
        public static final String FIELD_ENABLED = PREFIX + Constants.FIELD + " has been " + Constants.ENABLED + ".";
        public static final String FIELD_EXISTS = PREFIX + "Sorry, that field already exists.";
        public static final String FIELD_NOT_READY = PREFIX + "Sorry, this field is not ready.";
        public static final String FIELD_NOT_SPECIFIED = PREFIX + "Error: Field not specified.";
        public static final String FIELD_SAVE_FAIL = "Error: Could not save " + Constants.FILE;
        public static final String FIELD_SPECTATING = PREFIX + "You are now spectating in " + Constants.FIELD + ".";
        public static final String FILE_CREATE_FAIL = "Error: Failed to create file: " + Constants.FILE;
        public static final String GREEN_SPAWN_SET = PREFIX + "Green spawn point set.";
        public static final String GREEN_WINS = PREFIX + "Green ream wins!";
        public static final String INVALID_TANK = PREFIX + "Please click a valid tank.";
        public static final String LEFT_FIELD = PREFIX + "You have left the battlefield.";
        public static final String LOCATION_NOT_IN_REGION = PREFIX + "Error: The location is outside of the field's region.";
        public static final String LOGGED_OFF_WITH_ITEMS_STORED = PREFIX + "You logged off with items still stored away. They will be returned to you now.";
        public static final String MATCH_FORCE_ENDED = PREFIX + " The match was forcibly ended by an admin.";
        public static final String MATCH_IN_PROGRESS = PREFIX + "Sorry, but the match has already started.";
        public static final String MATCH_TERMINATED = PREFIX + "Match terminated.";
        public static final String MISSING_CUBOID = PREFIX + "Error: The battlefield contains no cuboid or the cuboid in another world.";
        public static final String MUST_UNREADY = PREFIX + "You must unready to change your tank.";
        public static final String NO_PERMISSION = PREFIX + "Sorry, but you do not have permission for that.";
        public static final String NOT_A_NUMBER = PREFIX + "Error: " + Constants.STRING + " is not a number.";
        public static final String NOT_A_NUMBER_2 = PREFIX + "Error: One or more of the inputted radii is not a number.";
        public static final String NOT_ENOUGH_ARGS = PREFIX + "Error: Field not specified.";
        public static final String NOT_IN_A_FIELD = PREFIX + "You are not in a battlefield.";
        public static final String OUT_OF_BOUNDS = PREFIX + "Out of bounds!";
        public static final String PLAYER_ONLY = PREFIX + "Sorry, but this command is a player command only.";
        public static final String RED_SPAWN_SET = PREFIX + "RED spawn point set.";
        public static final String RED_WINS = PREFIX + "Red team wins!";
        public static final String REGION_SET = PREFIX + "Region set.";
        public static final String SPECTATORS_SPAWN_SET = PREFIX + "Spectators spawn point set.";
        public static final String STATUS_OF_FIELD = PREFIX + "Status of " + Constants.FIELD;
        public static final String STATUS_OF_FIELD_ENABLED = "Enabled: " + Constants.ENABLED;
        public static final String STATUS_OF_FIELD_REGION = "Region: " + Constants.SET;
        public static final String STATUS_OF_FIELD_GREEN = "Green Spawn: " + Constants.SET;
        public static final String STATUS_OF_FIELD_RED = "Red Spawn: " + Constants.SET;
        public static final String STATUS_OF_FIELD_SPECTATORS = "Spectators Spawn: " + Constants.SET;
        public static final String TANK_SELECTION_1 = PREFIX + "You have chosen the " + Constants.TANK + ".";
        public static final String TANK_SELECTION_2 = PREFIX + "If you wish to choose another tank, right click with the 'Open Hangar' item.";
        public static final String TANK_SELECTION_3 = PREFIX + "When you are ready, simply right click with the 'Ready' item.";
    }

    public static class CommonPermissions
    {
        public static final String EDIT_PERM = ID + ".edit";
        public static final String PARTICIPATE_PERM = ID + ".participate";
    }

    public static class CommonScoreboard
    {
        public static final String DUMMY = "dummy";
        public static final String GREEN_ID = "green";
        public static final String GREEN_NAME = "Green Team";
        public static final String HEALTH_ID = "health";
        public static final String HEALTH_NAME = "Health";
        public static final String RED_ID = "red";
        public static final String RED_NAME = "Read Team";
        public static final String TEAM_COUNT_ID = "teamcount";
        public static final String TEAM_COUNT_NAME = "Team Count";
    }

    public static class CommonStorage
    {
        public static final String BATTLEFIELDS = "battlefields";
        public static final String INVENTORY = "inventorystorage";
    }

    public static class CommonTankTypes
    {
        public static final String LIGHT = "Light";
        public static final String MEDIUM = "Medium";
        public static final String HEAVY = "Heavy";
        public static final String TD = "TD";
        public static final String SPG = "SPG";
    }

    public static class CommonTanks
    {
        public static final String _113 = "113";
        public static final String _121 = "121";
        public static final String AMX_13_90 = "AMX 13 90";
        public static final String BAT_CHAT_25_T = "Bat.-Chatillon 25 t";
        public static final String AMX_30_B = "AMX 30 B";
        public static final String AMX_50_B = "AMX 50 B";
        public static final String AMX_50_FOCH_155 = "AMX 50 Foch (155)";
        public static final String BAT_CHAT_155_58 = "Bat.-Chatillon 155 58";
        public static final String CONQUEROR_GC = "Conqueror Gun Carriage";
        public static final String E_50_M = "E 50 Ausf. M";
        public static final String E_100 = "E 100";
        public static final String FV4202 = "FV4202";
        public static final String FV215B = "FV215b";
        public static final String FV215B_183 = "FV215b (183)";
        public static final String FV4005_STAGE_II = "FV4005 Stage II";
        public static final String GW_E_100 = "G.W. E 100";
        public static final String JAGDPANZER_E_100 = "Jagdpanzer E 100";
        public static final String IS_4 = "IS-4";
        public static final String IS_7 = "IS-7";
        public static final String LEOPARD_1 = "Leopard 1";
        public static final String M48A1_Patton = "M48A1 Patton";
        public static final String M60 = "M60";
        public static final String MAUS = "Maus";
        public static final String OBJECT_140 = "Object 140";
        public static final String OBJECT_260 = "Object 260";
        public static final String OBJECT_261 = "Object 261";
        public static final String OBJECT_263 = "Object 263";
        public static final String OBJECT_268 = "Object 268";
        public static final String OBJECT_430 = "Object 430";
        public static final String OBJECT_907 = "Object 907";
        public static final String SPAHPANZER_RU_251 = "Spahpanzer Ru 251";
        public static final String STB_1 = "STB-1";
        public static final String T_54_LTWT = "T-54 ltwt.";
        public static final String T_62A = "T-62A";
        public static final String T110E3 = "T110E3";
        public static final String T110E4 = "T110E4";
        public static final String T110E5 = "T110E5";
        public static final String T49 = "T49";
        public static final String T57 = "T57 Heavy Tank";
        public static final String T92 = "T92";
        public static final String TYPE_5_HEAVY = "Type 5 Heavy";
        public static final String VK_7201 = "VK 72.01 (K)";
        public static final String WAFFENTRAGER_E_100 = "Waffentrager E 100";
        public static final String WZ_132 = "WZ-132";
    }

    public static class CommonTracks
    {
        public static final String AMX_13_TYPE_2D = "AMX 13 Type 2D";
        public static final String AMX_30_B = "AMX 30 B";
        public static final String AMX_50_B = "AMX 50 B";
        public static final String AMX_50_FOCH_155 = "AMX 50 Foch (155)";
        public static final String BAT_CHAT_25_T = "Batignolles-Chatillon 25 t";
        public static final String BAT_CHAT_155_58 = "Batignolles-Chatillon 155 mle. 58";
        public static final String CONQUEROR_GUN_CARRIAGE = "Conqueror Gun Carriage";
        public static final String E_50_M = "E 50 Ausf. M";
        public static final String E_100 = "E 100";
        public static final String GW_E_100 = "G.W. E 100";
        public static final String JAGDPANZER_E_100 = "Jagdpanzer E 100";
        public static final String FV215 = "FV215";
        public static final String FV215B = "FV215b";
        public static final String FV4202 = "FV4202";
        public static final String FV4005_STAGE_II = "FV4005 Stage II";
        public static final String IS_4M = "IS-4M";
        public static final String IS_7 = "IS-7";
        public static final String LEOPARD_1 = "Leopard 1";
        public static final String MAUS = "Maus";
        public static final String M60 = "M60";
        public static final String OBJECT_140 = "Object 140";
        public static final String OBJECT_260_MOD_1945 = "Object 260 mod. 1945";
        public static final String OBJECT_261 = "Object 261";
        public static final String OBJECT_263 = "Object 263";
        public static final String OBJECT_268 = "Object 268";
        public static final String OBJECT_430 = "Object 430";
        public static final String OBJECT_907 = "Object 907";
        public static final String SPAHPANZER_RU_251_PT_II = "Spahpanzer RU 251 PT II";
        public static final String STB_1 = "STB-1";
        public static final String TYPE_5_HEAVY = "Type 5 Heavy";
        public static final String T_54_ENCHANCED_LTWT_VERSION = "T-54 enhanced ltwt. version";
        public static final String T_62A = "T-62A";
        public static final String T92_T80E1 = "T92 T80E1";
        public static final String T97 = "T97";
        public static final String T97E2 = "T97E2";
        public static final String T110E4 = "T110E4";
        public static final String T110E3 = "T110E3";
        public static final String T110E27 = "T110E27";
        public static final String VK_7201 = "VK 72.01 (k)";
        public static final String WAFFENTRAGER_E_100 = "Waffentrager auf E 100";
        public static final String WZ_132 = "WZ-132";
        public static final String XM551_TEST_BED = "XM551 test bed";
        public static final String _113 = "113";
        public static final String _121 = "121";
    }

    public enum Constants
    {
        CURRENT_CLIP,
        ENABLED,
        FIELD,
        FILE,
        MAX_CLIP,
        NUMBER,
        SET,
        STRING,
        TANK;

        @Override
        public String toString()
        {
            return "%" + super.toString() + "%";
        }
    }
}