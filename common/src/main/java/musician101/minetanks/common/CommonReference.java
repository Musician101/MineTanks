package musician101.minetanks.common;

import musician101.minetanks.common.battlefield.AbstractBattleField;
import musician101.minetanks.common.tank.AbstractTank;
import musician101.minetanks.common.tank.AbstractTankType;

import java.io.File;

public class CommonReference
{
    public static final String DESCRIPTION = "Minecraft PvP plugin heavily based on World of Tanks.";
    public static final String ID = "minetanks";
    public static final String NAME = "MineTanks";
    public static final String VERSION = "2.0";

    public static final String MOVIN_ON_OUT = "Movin' on out. Shuck 'em up!";
    public static final String PACK_IT_UP = "Pack it up, boys. We're heading home.";
    public static final String TANK_SELECTION = "Tank Selection";

    public static class CommonCommands
    {
        public static final String MT_CMD = "/mt";

        public static final String CREATE_NAME = "create";
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

    public static class CommonCannons
    {
        public static final String _10_5_CM_BORDKANONE_L7A3 = "10,5 cm Bordkanone L7A3";
        public static final String _10_5_CM_KWK_L52_AUSF_K = "10,5 cm Kw.K. L/52 Ausf. K";
        public static final String _12_8_CM_KWK_44_L55 = "12,8 cm Kw.K. 44 L/55";
        public static final String _14_CM_50_3RD_YEAR_TYPE = "14 cm/50 3rd Year Type";
        public static final String _15_CM_KWK_L38 = "15 cm Kw.K. L/38";
        public static final String _17_CM_PAK = "17 cm Pak";
        public static final String _15_CM_PAK_L38 = "15 cm Pak L/38";
        public static final String _21_CM_MORSER_18_2 = "21 cm Morser 18/2";
        public static final String _90_MM_F3 = "90 mm F3";
        public static final String _90_MM_GUN_T185 = "90 mm Gun T185";
        public static final String _90_MM_RHEINMETALL_DM1 = "90 mm Rheinmetall DM1";
        public static final String _100_MM_60_100T = "100 mm 60-100T";
        public static final String _100_MM_AK1 = "100 mm AK1";
        public static final String _100_MM_D10T = "100 mm D10T mod. 1945";
        public static final String _100_MM_D_54TS = "100 mm D-54TS";
        public static final String _100_MM_8TS = "100 mm U-8TS";
        public static final String _105_MM_GUN_M68 = "105 mm Gun M68";
        public static final String _105_MM_MLE_57 = "105 mm mle. 57 (D. 1504)";
        public static final String _105_MM_MLE_F1 = "105 mm mle. F1";
        public static final String _105_MM_RIFLED_GUN = "105 mm Rifled Gun";
        public static final String _105_MM_ROYAL_ORDNANCE_L7A1 = "105 mm Royal Ordnance L7A1";
        public static final String _120_MM_GUN_M58 = "120 mm Gun M58";
        public static final String _120_MM_GUN_L1A1 = "120 mm Gun L1A1";
        public static final String _120_MM_GUN_T179 = "120 mm Gun T179";
        public static final String _120_MM_SA46 = "120 mm SA46";
        public static final String _122_MM_M62_T2 = "122 mm M62-T2";
        public static final String _122_MM_60_122T = "122 mm 60-122T";
        public static final String _122_MM_BL_13_1 = "122 mm BL-13-1";
        public static final String _130_MM_S_70 = "130 mm S-70";
        public static final String _130_MM_S_70A = "130 mm S-70A";
        public static final String _152_MM_M64 = "152 mm M64";
        public static final String _155_MM_AC_SA58 = "155 mm AC SA58";
        public static final String _155_MM_AT_GUN_T7E2 = "155 mm AT Gun T7E2";
        public static final String _180_MM_B_1_P = "183 mm B-1-P";
        public static final String _183_MM_L4 = "183 mm L4";
        public static final String _240_MM_HOWITZER = "240 mm Howitzer M1";
        public static final String BL_9_2_IN_HOWITZER = "B.L. 9.2-in. Howitzer Mk. II";
        public static final String CANNON_DE_155_MM = "Canon de 155 mm";
    }

    public static class CommonConfig
    {
        public static final String AMPLIFIER = "amplifier";
        public static final String ARMOR = "armor.";
        public static final String BOOTS = "boots";
        public static final String CHESTPLATE = "chestplate";
        public static final String DURATION = "duration";
        public static final String EFFECTS = "effects";
        public static final String ENABLED = "enabled";
        public static final String GREEN_SPAWN = "greenSpawn";
        public static final String HELMET = "helmet";
        public static final String INVENTORY = "inventory.";
        public static final String LEGGINGS = "leggings";
        public static final String LOCATION = "location";
        public static final String MAX_X = "maxX";
        public static final String MIN_X = "minX";
        public static final String MAX_Y = "maxY";
        public static final String MIN_Y = "minY";
        public static final String MAX_Z = "maxZ";
        public static final String MIN_Z = "minZ";
        public static final String RED_SPAWN = "redSpawn";
        public static final String REGION = "region";
        public static final String SPECTATORS = "spectators";
        public static final String XP = "xp";
        public static final String TYPE = "type";
        public static final String WORLD = "world";

        public static <BattleField extends AbstractBattleField> String battlefieldFile(BattleField field)
        {
            return field.getName() + ".cfg";
        }
    }

    public static class CommonEngines
    {
        public static final String _12150L7 = "12150L7";
        public static final String _2X_V_16 = "2x V-16";
        public static final String _5TD = "5TD";
        public static final String _8V135 = "8V135";
        public static final String DB_603_A2 = "DB 603 A2";
        public static final String DETROIT_DIESEL_6V53T_S = "Detroit Diesel 6V53T-S";
        public static final String HISPANO_SUIZA_HS_110 = "Hispano-Suiza HS 110";
        public static final String CONTINENTAL_AOI_895_5 = "Continental AOI-895-5";
        public static final String CONTINENTAL_AOI_1490 = "Continental AOI-1490";
        public static final String CONTINENTAL_AV_1790_5C = "Continental AV-1790-5C";
        public static final String CONTINENTAL_AV_1790_7B = "Continental AV-1790-7B";
        public static final String CONTINENTAL_AVDS_1790_2 = "Continental AVDS-1790-2";
        public static final String FORD_GAF_T92 = "Ford GAF T92";
        public static final String KAWASAKI_TYPE_98 = "Kawasaki Type 98 V-12 1200 Kai";
        public static final String M_50T = "M-50T";
        public static final String MAYBACH_HL_234 = "Maybach HL 234 TRM P45";
        public static final String MAYBACH_HL_235 = "Maybach HL 235";
        public static final String MAYBACH_HL_295_F = "Maybach HL 295 F";
        public static final String MAYBACH_NEUES_PROJEKT = "Maybach Neues Projekt";
        public static final String MB_517 = "MB 517";
        public static final String MB_837_EA_600 = "MB 837 Ea 600";
        public static final String MITSUBISHI_10ZF21WT = "Mitsubishi 10ZF21WT";
        public static final String MTU_MB_838_CAM_500A = "MTU MB 838 CaM 500A";
        public static final String PRAGA_AXK = "Praga AXK";
        public static final String ROLLS_ROYCE_GRIFFON = "Rolls-Royce Griffon";
        public static final String ROLLS_ROYCE_METEOR_MK_12 = "Rolls-Royce Meteor Mk. 12";
        public static final String ROLLS_ROYCE_METEOR_M120 = "Rolls-Royce Meteor M120";
        public static final String ROLLS_ROYCE_METEOR_M120A = "Rolls-Royce Meteor M120A";
        public static final String ROLLS_ROYCE_METEOR_MK_IVB = "Rolls-Royce Meteor Mk. IVB";
        public static final String SAURER = "Saurer";
        public static final String TD_12 = "TD-12";
        public static final String V_12 = "V-12";
        public static final String V_12_5U = "V-12-5U";
        public static final String V_14 = "V-14";
        public static final String V_16FN = "V-16FN";
        public static final String V_2_54SC = "V-2-54SC";
        public static final String V_55 = "V-55";
    }

    public static class CommonItemText
    {
        public static final String AMMO = "Ammo";
        public static final String CANNON = "Your Cannon";
        public static final String ENGINE = "Your Engine";
        public static final String NOT_READY = "You are currently not ready.";
        public static final String OPEN_HANGAR = "Open Hangar";
        public static final String RADIO = "Your Radio";
        public static final String READY = "You are currently ready.";
        public static final String READY_UP = "Ready Up";
        public static final String TRACKS = "Your Tracks";
        public static final String TURRET = "Your Turret";
        public static final String UNREADY = "Unready";

        public static String clipReloadTime(double time)
        {
            return "Clip Reload Time: " + time;
        }

        public static String clipSize(int current, int max)
        {
            return "Clip Size: " + current + "/" + max;
        }

        public static String cycleTime(double time)
        {
            return "Cycle Time: " + time;
        }

        public static String reloadTime(double time)
        {
            return "Reload Time: " + time;
        }

        public static <T extends AbstractTank> String selectedTank(T tank)
        {
            return "Tank: " + (tank == null ? "None" : tank.getName());
        }

        public static String speedValue(int speed)
        {
            return "Speed Value: " + speed;
        }

        public static <T extends AbstractTankType> String tankType(T tankType)
        {
            return "Type: " + tankType.getName();
        }
    }

    public static class CommonMessages
    {
        public static final String PREFIX = "[" + CommonReference.NAME + "] ";
        public static final String CHECK_FIELD_STATUS = PREFIX + "Check the status of the battlefield by using /mt status.";
        public static final String FIELD_CREATED_STATUS = PREFIX + "Check the status of the battlefield by using /mt status.";
        public static final String FIELD_DNE = PREFIX + "Sorry, that field doesn't exist.";
        public static final String FIELD_DISABLED = PREFIX + "Sorry, this field is currently disabled.";
        public static final String FIELD_EXISTS = PREFIX + "Sorry, that field already exists.";
        public static final String FIELD_NOT_READY = PREFIX + "Sorry, this field is not ready.";
        public static final String FIELD_NOT_SPECIFIED = PREFIX + "Error: Field not specified.";
        public static final String GREEN_SPAWN_SET = PREFIX + "Green spawn point set.";
        public static final String GREEN_WINS = PREFIX + "Green ream wins!";
        public static final String INVALID_TANK = PREFIX + "Please click a valid tank.";
        public static final String LEFT_FIELD = PREFIX + "You have left the battlefield.";
        public static final String LOCATION_NOT_IN_REGION = PREFIX + "Error: The location is outside of the field's region.";
        public static final String LOGGED_OFF_WITH_ITEMS_STORED = PREFIX + "You logged off with items still stored away. They will be returned to you now.";
        public static final String MATCH_FORCE_ENDED = PREFIX + " The match was forcibly ended by an admin.";
        public static final String MATCH_IN_PROGRESS = PREFIX + "Sorry, but the match has already started.";
        public static final String MATCH_TERMINATED = PREFIX + "Match terminated.";
        public static final String MUST_UNREADY = PREFIX + "You must unready to change your tank.";
        public static final String NO_PERMISSION = PREFIX + "Sorry, but you do not have permission for that.";
        public static final String NOT_A_NUMBER_MULTI = PREFIX + "Error: One or more of the inputted radii is not a number.";
        public static final String NOT_ENOUGH_ARGS = PREFIX + "Error: Not enough arguments.";
        public static final String NOT_IN_A_FIELD = PREFIX + "You are not in a battlefield.";
        public static final String OUT_OF_BOUNDS = PREFIX + "Out of bounds!";
        public static final String PLAYER_ONLY = PREFIX + "Sorry, but this command is a player command only.";
        public static final String RED_SPAWN_SET = PREFIX + "RED spawn point set.";
        public static final String RED_WINS = PREFIX + "Red team wins!";
        public static final String REGION_SET = PREFIX + "Region set.";
        public static final String SPECTATORS_SPAWN_SET = PREFIX + "Spectators spawn point set.";
        public static final String TANK_SELECTION_2 = PREFIX + "If you wish to choose another tank, right click with the 'Open Hangar' item.";
        public static final String TANK_SELECTION_3 = PREFIX + "When you are ready, simply right click with the 'Ready' item.";

        public static <B extends AbstractBattleField> String fieldCreated(B battlefield)
        {
            return PREFIX + battlefield.getName() + " successfully created.";
        }

        public static <B extends AbstractBattleField> String fieldDeleted(B battlefield)
        {
            return PREFIX + battlefield.getName() + " has been deleted.";
        }

        public static <B extends AbstractBattleField> String fieldEnabled(B battlefield)
        {
            return PREFIX + battlefield.getName() + " has been " + (battlefield.isEnabled() ? "enable" : "disable") + "d.";
        }

        public static <B extends AbstractBattleField> String fieldSpectating(B battlefield)
        {
            return PREFIX + "You are now spectating in " + battlefield + ".";
        }

        public static String fileCreateFailed(File file)
        {
            return "Error: Failed to create file: " + file.getName();
        }

        public static String fileLoadFailed(File file)
        {
            return "Failed to load " + file.getName();
        }

        public static String fileSaveFailed(File file)
        {
            return "Error: Could not save " + file.getName();
        }

        public static String notANumber(String string)
        {
            return PREFIX + "Error: " + string + " is not a number.";
        }

        public static <B extends AbstractBattleField> String statusOfField(B battlefield)
        {
            return PREFIX + "Status of " + battlefield.getName();
        }

        public static <B extends AbstractBattleField> String statusOfFieldEnabled(B battlefield)
        {
            return "Enabled: " + (battlefield.isEnabled() ? "Enable" : "Disable") + "d";
        }

        public static <B extends AbstractBattleField> String statusOfFieldRegion(B battlefield)
        {
            return "Region: " + (battlefield.getRegion() == null ? "Not " : "") + "Set";
        }

        public static <B extends AbstractBattleField> String statusOfFieldGreenSpawn(B battlefield)
        {
            return "Green Spawn: " + (battlefield.getGreenSpawn() == null ? "Not " : "") + "Set";
        }

        public static <B extends AbstractBattleField> String statusOfFieldRedSpawn(B battlefield)
        {
            return "Red Spawn: " + (battlefield.getRedSpawn() == null ? "Not " : "") + "Set";
        }

        public static <B extends AbstractBattleField> String statusOfFieldSpectatorsSpawn(B battlefield)
        {
            return "Spectators Spawn: " + (battlefield.getSpectators() == null ? "Not " : "") + "Set";
        }

        public static <T extends AbstractTank> String tankSelection1(T tank)
        {
            return PREFIX + "You have chosen the " + tank.getName() + ".";
        }
    }

    public static class CommonPermissions
    {
        public static final String EDIT_PERM = ID + ".edit";
        public static final String PARTICIPATE_PERM = ID + ".participate";
    }

    public static class CommonRadios
    {
        public static final String _10RT_26 = "10RT-26";
        public static final String _10RK_26 = "10RK-26";
        public static final String _10WSC = "10WSc";
        public static final String A_220A = "A-220A";
        public static final String A_220B = "A-220B";
        public static final String AN_GRC_7 = "AN/GRC-7";
        public static final String AN_VRC_3 = "AN/VRC-3";
        public static final String AN_VRC_12 = "AN/VRC-12";
        public static final String AN_VRC_7 = "AN/VRC-7";
        public static final String R_113M = "R-113M";
        public static final String R_123 = "R-123";
        public static final String RADIOSTANICE_RM_31T = "Radiostanice RM-31T";
        public static final String SCR_528 = "SCR 528";
        public static final String SCR_528A = "SCR 528A";
        public static final String SCR_528F = "SCR 528F";
        public static final String SCR_619 = "SCR 619";
        public static final String SEM_25A = "SEM 25A";
        public static final String SR_C45 = "SR C45";
        public static final String TRVP_13_A = "TRVP-13-A";
        public static final String TYPE_3_KO = "Type 3 Ko";
        public static final String TYPE_67 = "Type 67";
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
        public static final String TVP_T_50_51 = "TVP T 50/51";
        public static final String TYPE_5_HEAVY = "Type 5 Heavy";
        public static final String VK_7201 = "VK 72.01 (K)";
        public static final String WAFFENTRAGER_E_100 = "Waffentrager E 100";
        public static final String WZ_132 = "WZ-132";
    }

    public static class CommonTankTypes
    {
        public static final String LIGHT = "Light";
        public static final String MEDIUM = "Medium";
        public static final String HEAVY = "Heavy";
        public static final String TD = "TD";
        public static final String SPG = "SPG";
    }

    public static class CommonTracks
    {
        public static final String _113 = "113";
        public static final String _121 = "121";
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
        public static final String T_54_ENHANCED_LTWT_VERSION = "T-54 enhanced ltwt. version";
        public static final String T_62A = "T-62A";
        public static final String T92_T80E1 = "T92 T80E1";
        public static final String T97 = "T97";
        public static final String T97E2 = "T97E2";
        public static final String T110E4 = "T110E4";
        public static final String T110E3 = "T110E3";
        public static final String T110E27 = "T110E27";
        public static final String TVP_T_50_51 = "TVP T 50/51";
        public static final String VK_7201 = "VK 72.01 (k)";
        public static final String WAFFENTRAGER_E_100 = "Waffentrager auf E 100";
        public static final String WZ_132 = "WZ-132";
        public static final String XM551_TEST_BED = "XM551 test bed";
    }

    public static class CommonTurrets
    {
        public static final String _113 = "_113";
        public static final String _121 = "121";
        public static final String AMX_30_B = "AMX 30 B";
        public static final String AMX_50_B = "AMX 50 B";
        public static final String BAT_CHAT_25_T = "Batignolles-Chatillon 25 t";
        public static final String E_50_M = "E 50 Ausf. M";
        public static final String E_100 = "E 100";
        public static final String FL_10_TYPE_D = "FL 10 Type D";
        public static final String FV215 = "FV215";
        public static final String FV215B = "FV215b";
        public static final String FV4202 = "FV4202";
        public static final String FV4005_STAGE_II = "FV4005 Stage II";
        public static final String IS_4M = "IS-4M";
        public static final String IS_7 = "IS-7";
        public static final String LEOPARD_1 = "Leopard 1";
        public static final String M60 = "M60";
        public static final String M87 = "M87";
        public static final String MAUS = "Maus";
        public static final String NONE = "None";
        public static final String OBJECT_140 = "Object 140";
        public static final String OBJECT_260_MOD_1946 = "Object 260 mod 1946";
        public static final String OBJECT_430 = "Object 430";
        public static final String OBJECT_907 = "Object 907";
        public static final String SPAHPANZER_RU_251 = "Spahpanzer Ru 251 Drehturm 360";
        public static final String STB_1 = "STB-1";
        public static final String T_54_LTWT_VERSION = "T-54 ltwt. version";
        public static final String T_62 = "T-62";
        public static final String T53 = "T53";
        public static final String T169 = "T169";
        public static final String T110E4 = "T110E4";
        public static final String TYPE_5_HEAVY = "Type 5 Heavy";
        public static final String TVP_T_50_51 = "TVP T 50/51";
        public static final String VK_7201 = "VK 72.01 (K)";
        public static final String WAFFENTRAGER_E_100 = "Waffentrager auf E 100";
        public static final String WZ_132 = "WZ-132";
        public static final String XM551_TEST_BED = "XM551 test bed";
    }
}
