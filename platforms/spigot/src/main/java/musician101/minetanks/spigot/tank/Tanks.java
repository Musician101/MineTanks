package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.spigot.tank.modules.Engine;
import musician101.minetanks.spigot.tank.modules.Radio;
import musician101.minetanks.spigot.tank.modules.cannon.AutoLoader;
import musician101.minetanks.spigot.tank.modules.cannon.Cannon;
import musician101.minetanks.spigot.tank.modules.tracks.Tracks;
import musician101.minetanks.spigot.tank.modules.turret.Turrets;

public class Tanks
{
	/** China Lights */
	public static final Tank WZ_132 = new Tank("WZ-132", TankTypes.LIGHT, 1150, new Armor(50, 25, 20), 64, new Cannon("100 mm 60-100T", 43, 7.57), new Engine("8V135", TankTypes.LIGHT), new Radio("A-220A", TankTypes.LIGHT), Tracks.WZ_132, Turrets.WZ_132, "Type: Light", "Very good agility and mobility.");
	/** China Mediums */
    public static final Tank _121 = new Tank("121", TankTypes.MEDIUM, 1950, new Armor(120, 80, 60), 56, new Cannon("122 mm 60-122T", 40, 9.6), new Engine("12150L7", TankTypes.MEDIUM), new Radio("A-220B", TankTypes.MEDIUM), Tracks._121, Turrets._121, "Type: Medium", "Very high damage per shell and minute.");
	/** China Heavies */
    public static final Tank _113 = new Tank("113", TankTypes.HEAVY, 2300, new Armor(120, 90, 70), 50, new Cannon("122 mm 60-122T", 34, 10.45), new Engine("V-2-54SC", TankTypes.HEAVY), new Radio("A-220B", TankTypes.HEAVY), Tracks._113, Turrets._113, "Type: Heavy", "Good mobility and high top speed.");
	/** France Lights */
    public static final Tank AMX_13_90 = new Tank("AMX 13 90", TankTypes.LIGHT, 1100, new Armor(40, 20, 15), 64, new AutoLoader("90 mm F3", 36, 42, 2.73, 6), new Engine("Detroit Diesel 6V53T-S", TankTypes.LIGHT), new Radio("SCR 528", TankTypes.LIGHT), Tracks.AMX_13_TYPE_2D, Turrets.FL_10, "Type: Light", "Cannon has a clip for quick damage, but a long base reload time.");
	/** French Mediums */
    public static final Tank BAT_CHAT_25_T = new Tank("Bat.-Chatillon 25 t", TankTypes.MEDIUM, 1800, new Armor(60, 40, 30), 65, new AutoLoader("105 mm mle. 57 (D. 1504)", 30, 40, 2.73, 5), new Engine("Hispano-Suiza HS 110", TankTypes.MEDIUM), new Radio("SCR 528F", TankTypes.MEDIUM), Tracks.BAT_CHAT_25_T, Turrets.BAT_CHAT_25_T, "Type: Medium", "High top speed.", "Cannon has a clip for quick damage, but a long base reload time.");
    public static final Tank AMX_30_B = new Tank("AMX 30 B", TankTypes.MEDIUM, 1900, new Armor(80, 35, 30), 65, new Cannon("105 mm mle. F1", 50, 8.53), new Engine("Hispano-Suiza HS 110", TankTypes.MEDIUM), new Radio("TRVP-13-A", TankTypes.MEDIUM), Tracks.AMX_30_B, Turrets.AMX_30_B, "Type: Medium", "Slightly better armor than the Leopard 1.", "Very poor armor.");
	/** French Heavies */
    public static final Tank AMX_50_B = new Tank("AMX 50 B", TankTypes.HEAVY, 2100, new Armor(170, 30, 30), 65, new AutoLoader("120 mm SA46", 56, 30, 2.5, 4), new Engine("Maybach HL 295 F", TankTypes.HEAVY), new Radio("SCR 619", TankTypes.HEAVY), Tracks.AMX_50_B, Turrets.AMX_50_B, "Type: Heavy", "Excellent speed.", "Cannon has a clip for quick damage, but a long base reload.");
	/** French TDs */
    public static final Tank AMX_50_FOCH_155 = new Tank("AMX 50 Foch (155)", TankTypes.TD, 1850, new Armor(180, 40, 40), 50, new AutoLoader("155 mm AC SA58", 30, 47.94, 5, 3), new Engine("Saurer", TankTypes.TD), new Radio("SCR 619", TankTypes.TD), Tracks.AMX_50_FOCH_155, Turrets.NO_TURRET_TD, "Type: TD", "High damage per shot.", "Cannon has a clip for quick damage, but a long base reload.");
	/** French Arty */
    public static final Tank BAT_CHAT_155_58 = new Tank("Bat.-Chatillon 155 58", TankTypes.ARTY, 490, new Armor(50, 30, 20), 62, new AutoLoader("Canon de 155 mm", 36, 76.71, 6, 4), new Engine("Hispano-Suiza HS 110", TankTypes.ARTY), new Radio("SCR 528F", TankTypes.ARTY), Tracks.BAT_CHAT_155_58, Turrets.NO_TURRET_ARTY, "Type: SPG", "Very mobile.", "Cannon has a clip for quick damage, but a long base reload.");
	/** German Lights */
    public static final Tank SPAHPANZER_RU_251 = new Tank("Spahpanzer Ru 251", TankTypes.LIGHT, 500, new Armor(25, 20, 8), 80, new Cannon("90 mm Rheinmetall DM1", 41, 5.94), new Engine("MB 837 Ea 600", TankTypes.LIGHT), new Radio("AN/GRC-7", TankTypes.LIGHT), Tracks.SPAHPANZER_RU_251_PT_I, Turrets.SPAHPANZER_RU_251_DREHTURM_360, "Type: Light", "Thick armor for a light tank.", "Good for ramming.");
	/** German Mediums */
    public static final Tank E_50_M = new Tank("E 50 Ausf. M", TankTypes.MEDIUM, 2050, new Armor(150, 80, 80), 60, new Cannon("10,5 cm Kw.K. L/52 Ausf. K", 48, 9.2), new Engine("Maybach HL 235", TankTypes.MEDIUM), new Radio("10WSc", TankTypes.MEDIUM), Tracks.E_50_M, Turrets.E_50_M, "Type: Medium", "Extraordinary gun.", "Excellent for ramming.");
    public static final Tank LEOPARD_1 = new Tank("Leopard 1", TankTypes.MEDIUM, 1950, new Armor(70, 35, 25), 65, new Cannon("10,5 cm Bordkanone L7A3", 60, 8.34), new Engine("MTU MB 838 CaM 500A", TankTypes.MEDIUM), new Radio("SEM 25A", TankTypes.MEDIUM), Tracks.LEOPARD_1, Turrets.LEOPARD_1, "Type: Medium", "Great speed and mobility.", "Very weak armor.");
	/** German Heavies */
    public static final Tank E_100 = new Tank("E 100", TankTypes.HEAVY, 2700, new Armor(200, 120, 150), 30, new Cannon("15 cm Kw.K. L/38", 50, 19.18), new Engine("Maybach Neues Projekt", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.E_100, Turrets.E_100, "Type: Heavy", "Strong armor all around.", "Third highest hit point pool of any tank.");
    public static final Tank MAUS = new Tank("Maus", TankTypes.HEAVY, 3000, new Armor(200, 185, 160), 20, new Cannon("12,8 cm Kw.K. 44 L/55", 68, 14.29), new Engine("DB 603 A2", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.MAUS, Turrets.MAUS, "Type: Heavy", "Excellent armor.", "Highest hit point pool of any tank.");
    public static final Tank VK_7201 = new Tank("VK 72.01 (K)", TankTypes.HEAVY, 2500, new Armor(200, 160, 120), 43, new Cannon("15 cm Kw.K L/38", 24, 21.09), new Engine("MB 517", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.VK_7201, Turrets.VK_7201, "Type: Heavy", "Good ramming tank.", "High damage per shot.");
	/** German TDs */
    public static final Tank JAGDPANZER_E_100 = new Tank("Jagdpanzer E 100", TankTypes.TD, 2200, new Armor(200, 120, 150), 30, new Cannon("17 cm Pak", 24, 24.64), new Engine("Maybach Neues Projekt", TankTypes.TD), new Radio("10WSc", TankTypes.TD), Tracks.JAGDPANZER_E_100, Turrets.NO_TURRET_TD, "Type: TD", "Incredible single shot power.", "Very strong all around armor with large hit point pool.");
    public static final Tank WAFFENTRAGER_E_100 = new Tank("Waffentrager auf E 100", TankTypes.TD, 2000, new Armor(200, 120, 150), 40, new AutoLoader("15 cm Pak L/38", 60, 57.94, 3, 4), new Engine("Maybach Neues Projekt", TankTypes.TD), new Radio("10WSc", TankTypes.TD), Tracks.WAFFENTRAGER_E_100, Turrets.WAFFENTRAGER_E_100, "Type: TD", "Cannon has a clip for quick damage, but a long base reload.");
	/** German Arty */
    public static final Tank GW_E_100 = new Tank("G.W. E 100", TankTypes.ARTY, 550, new Armor(80, 50, 40), 40, new Cannon("21 cm Morser 18/2", 30, 40.75), new Engine("Maybach HL 234 TRM P45", TankTypes.ARTY), new Radio("10WSc", TankTypes.ARTY), Tracks.GW_E_100, Turrets.NO_TURRET_ARTY, "Type: SPG", "Good balance of damage, reload, and accuracy.", "Better damage per minute than the T92.");
	/** Japanese Mediums */
    public static final Tank STB_1 = new Tank("STB-1", TankTypes.MEDIUM, 1950, new Armor(110, 35, 25), 53, new Cannon("105 mm Rifled Gun", 50, 7.67), new Engine("Mitsubishi 10ZF21WT", TankTypes.MEDIUM), new Radio("Type 67", TankTypes.MEDIUM), Tracks.STB_1, Turrets.STB_1, "Type: Medium", "Fastest reload on a 105mm cannon.", "Reasonably mobile.");
    /** Japanese Heavies */
    public static final Tank TYPE_5_HEAVY = new Tank("Type 5 Heavy", TankTypes.HEAVY, 2800, new Armor(260, 140, 150), 25, new Cannon("14 cm/50 3rd Year Type", 60, 16.97), new Engine("Kawasaki Type 98 V-12 1200 Kai", TankTypes.HEAVY), new Radio("Type 3 Ko", TankTypes.HEAVY), Tracks.TYPE_5_HEAVY, Turrets.TYPE_5_HEAVY, "Type: Heavy", "Incredible armor.", "Lowest damage per minute.");
	/** British Mediums */
    public static final Tank FV4202 = new Tank("FV4202", TankTypes.MEDIUM, 1950, new Armor(121, 51, 32), 40, new Cannon("105 mm Royal Ordnance L7A1", 72, 8.25), new Engine("Rolls-Royce Meteor M120A", TankTypes.MEDIUM), new Radio("SR C45", TankTypes.MEDIUM), Tracks.FV4202, Turrets.FV4202, "Type: Medium", "Very good mobility.", "Limited top speed.");
	/** British Heavies */
    public static final Tank FV215B = new Tank("FV215b", TankTypes.HEAVY, 2500, new Armor(152.4, 101.6, 76.2), 34, new Cannon("120 mm Gun L1A1", 35, 8.34), new Engine("Rolls-Royce Griffon", TankTypes.HEAVY), new Radio("SR C45", TankTypes.HEAVY), Tracks.FV215, Turrets.FV215, "Type: Heavy", "High health pool.", "Weak all around armor for a heavy tank.");
	/** British TDs */
    public static final Tank FV215B_183 = new Tank("FV215b (183)", TankTypes.TD, 2000, new Armor(152.4, 50.8, 76.2), 34, new Cannon("183 mm L4", 12, 28.77), new Engine("Rolls-Royce Meteor Mk. 12", TankTypes.TD), new Radio("SR C45", TankTypes.TD), Tracks.FV215B, Turrets.FV215B, "Type: TD", "Unbelievable damage per shot.", "Long reloading cycle.");
    public static final Tank FV4005_STAGE_II = new Tank("FV4005 Stage II", TankTypes.TD, 1850, new Armor(76.2, 50.8, 38.1), 35, new Cannon("183 mm L4", 12, 28.77), new Engine("Rolls-Royce Meteor Mk. IVB", TankTypes.TD), new Radio("SR C45", TankTypes.TD), Tracks.FV4005_STAGE_II, Turrets.FV4005_STAGE_II, "Type: TD", "Better mobility and traverse than FV215b (183).");
	/** British Arty */
    public static final Tank CONQUEROR_GC = new Tank("Conqueror Gun Carriage", TankTypes.ARTY, 530, new Armor(152.4, 50.8, 76.2), 34, new Cannon("B.L. 9.2-in. Howitzer Mk. II", 24, 55.71), new Engine("Rolls-Royce Meteor M120", TankTypes.ARTY), new Radio("SR C45", TankTypes.ARTY), Tracks.CONQUEROR_GC, Turrets.NO_TURRET_ARTY, "Type: SPG", "Very high damage.");
	/** American Lights */
    public static final Tank T49 = new Tank("T49", TankTypes.LIGHT, 1000, new Armor(25.4, 25.4, 19.1), 72, new Cannon("90 mm Gun T185", 22, 22.05), new Engine("Continental AOI-895-5", TankTypes.LIGHT), new Radio("AN/GRC-7", TankTypes.LIGHT), Tracks.XM551_TEST_BED, Turrets.XM551_TEST_BED, "Type: Light", "Poor mobility for a light tank.");
	/** American Mediums */
    public static final Tank M48A1_PATTON = new Tank("M48A1 Patton", TankTypes.MEDIUM, 2000, new Armor(152.4, 76.2, 38.1), 45, new Cannon("105 mm Gun M68", 57, 8.44), new Engine("Continental AV-1790-7B", TankTypes.MEDIUM), new Radio("AN/VRC-7", TankTypes.MEDIUM), Tracks.T97E2_M, Turrets.M87, "Type: Medium", "Low top speed.", "Good damage.");
    public static final Tank M60 = new Tank("M60", TankTypes.MEDIUM, 2000, new Armor(93.2, 73.7, 25.4), 48, new Cannon("105 mm Gun M68", 57, 8.44), new Engine("Continental AVDS-1790-2", TankTypes.MEDIUM), new Radio("AN/VRC-12", TankTypes.MEDIUM), Tracks.M60, Turrets.M60, "Type: Medium", "Great mobility and rate of fire.", "Relatively thin armor.");
	/** American Heavies */
    public static final Tank T110E5 = new Tank("T110E5", TankTypes.HEAVY, 2200, new Armor(254, 76.2, 38.1), 37, new Cannon("120 mm Gun M58", 42, 9.59), new Engine("Continental AOI-1490", TankTypes.HEAVY), new Radio("AN/VRC-3", TankTypes.HEAVY), Tracks.T110E27, Turrets.T53, "Type: Heavy", "Excellent damage per minute.", "Low side and rear armor.");
    public static final Tank T57_HT = new Tank("T57 Heavy Tank", TankTypes.HEAVY, 2250, new Armor(203, 50.8, 38.1), 35, new AutoLoader("120 mm Gun T179", 36, 23.97, 2, 4), new Engine("Continental AV-1790-5C", TankTypes.HEAVY), new Radio("AN/VRC-3", TankTypes.HEAVY), Tracks.T97, Turrets.T169, "Type: Heavy", "Highest damage per minute of all heavy tanks.", "Cannon has a clip for quick damage, but a long base reload.");
	/** American TDs */
    public static final Tank T110E3 = new Tank("T110E3", TankTypes.TD, 2050, new Armor(305, 76, 38), 30, new Cannon("155 mm AT Gun T7E2", 27, 16.3), new Engine("Continental AOI-1490", TankTypes.TD), new Radio("AN/VRC-3", TankTypes.TD), Tracks.T110E3, Turrets.NO_TURRET_TD, "Type: TD", "Some of the toughest armor.", "Poor mobility.");
    public static final Tank T110E4 = new Tank("T110E4", TankTypes.TD, 2000, new Armor(260, 76, 38), 35, new Cannon("155 mm AT Gun T7E2", 27, 18.7), new Engine("Continental AOI-1490", TankTypes.TD), new Radio("AN/VRC-3", TankTypes.TD), Tracks.T110E4, Turrets.T110E4, "Type: TD", "Decent mobility.");
	/** American Arty */
    public static final Tank T92 = new Tank("T92", TankTypes.ARTY, 500, new Armor(25.4, 25.4, 21.7), 32, new Cannon("240 mm Howitzer M1", 16, 50.82), new Engine("Ford GAF T92", TankTypes.ARTY), new Radio("SCR 528A", TankTypes.ARTY), Tracks.T92_T80E1, Turrets.NO_TURRET_ARTY, "Type: SPG", "Most powerful gun in the game.", "Huge splash damage.");
    /** Russian Lights */
    public static final Tank T_54_LTWT = new Tank("T-54 ltwt.", TankTypes.LIGHT, 1250, new Armor(80, 60, 45), 69, new Cannon("100 mm D10T mod. 1945", 34, 7.19), new Engine("V-14", TankTypes.LIGHT), new Radio("10RT-26", TankTypes.LIGHT), Tracks.T_54_ENHANCED_LTWT_VERSION, Turrets.T_54_LTWT_VERSION, "Type: Light", "Strong turret and hull armor for a light tank.");
	/** Russian Mediums */
    public static final Tank OBJECT_140 = new Tank("Object 140", TankTypes.MEDIUM, 1900, new Armor(100, 80, 55), 40, new Cannon("100 mm U-8TS", 50, 6.33), new Engine("TD-12", TankTypes.MEDIUM), new Radio("R-113M", TankTypes.MEDIUM), Tracks.OBJECT_140, Turrets.OBJECT_140, "Type: Medium", "Very high damage per minute.", "Good top speed.");
    public static final Tank OBJECT_430 = new Tank("Object 430", TankTypes.MEDIUM, 1900, new Armor(120, 80, 40), 55, new Cannon("100 mm U-8TS", 50, 6.14), new Engine("5TD", TankTypes.MEDIUM), new Radio("R-113M", TankTypes.MEDIUM), Tracks.OBJECT_430, Turrets.OBJECT_430, "Type: Medium", "Very high damage per minute.", "Tied for the lowest average damage per shot.");
    public static final Tank OBJECT_907 = new Tank("Object 907", TankTypes.MEDIUM, 2000, new Armor(100, 85, 45), 55, new Cannon("100 mm D-54TS", 45, 6.23), new Engine("V-12-5U", TankTypes.MEDIUM), new Radio("R-123", TankTypes.MEDIUM), Tracks.OBJECT_907, Turrets.OBJECT_907, "Type: Medium", "High damage per minute.", "Low ammunition count.");
    public static final Tank T_62A = new Tank("T-62A", TankTypes.MEDIUM, 1950, new Armor(100, 80, 45), 50, new Cannon("100 mm U-8TS", 50, 6.33), new Engine("V-55", TankTypes.MEDIUM), new Radio("R-123", TankTypes.MEDIUM), Tracks.T_62A, Turrets.T_62A, "Type: Medium", "Very high damage per minute.", "Very strong turret armor.");
	/** Russian Heavies */
    public static final Tank IS_4 = new Tank("IS-4", TankTypes.HEAVY, 2500, new Armor(140, 160, 100), 43, new Cannon("122 mm M62-T2", 30, 11.51), new Engine("V-12", TankTypes.HEAVY), new Radio("10RK-26", TankTypes.HEAVY), Tracks.IS_4M, Turrets.IS_4M, "Type: Heavy", "High health pool.", "Lacks mobility and damage per minute.");
    public static final Tank IS_7 = new Tank("IS-7", TankTypes.HEAVY, 2150, new Armor(150, 150, 100), 60, new Cannon("130 mm S-70", 30, 13.14), new Engine("M-50T", TankTypes.HEAVY), new Radio("10RK-26", TankTypes.HEAVY), Tracks.IS_7, Turrets.IS_7, "Type: Heavy", "Amazingly thick armor.");
    public static final Tank OBJECT_260 = new Tank("Object 260", TankTypes.HEAVY, 2100, new Armor(150, 150, 70), 60, new Cannon("122 mm BL-13-1", 30, 11.03), new Engine("2x V-16", TankTypes.HEAVY), new Radio("10RK-26", TankTypes.HEAVY), Tracks.OBJECT_260_MOD_1945, Turrets.OBJECT_260_MOD_1945, "Type: Heavy", "Superior gun to the IS-4.", "Low health point pool for a heavy tank.");
	/** Russian TDs */
    public static final Tank OBJECT_263 = new Tank("Object 263", TankTypes.TD, 1900, new Armor(250, 80, 50), 55, new Cannon("130 mm S-70A", 42, 10.26), new Engine("M-50T", TankTypes.TD), new Radio("10RK-26", TankTypes.TD), Tracks.OBJECT_263, Turrets.NO_TURRET_TD, "Type: TD", "Great mobility.", "Low damage.");
    public static final Tank OBJECT_268 = new Tank("Object 268", TankTypes.TD, 1950, new Armor(187, 100, 50), 48, new Cannon("152 mm M64", 35, 15.82), new Engine("V-16FN", TankTypes.TD), new Radio("R-113M", TankTypes.TD), Tracks.OBJECT_268, Turrets.NO_TURRET_TD, "Type: TD", "Good damage per minute.", "Good top speed.");
	/** Russian Arty */
    public static final Tank OBJECT_261 = new Tank("Object 261", TankTypes.ARTY, 510, new Armor(75, 50, 30), 50, new Cannon("180 mm B-1-P", 18, 33.56), new Engine("M-50T", TankTypes.ARTY), new Radio("10RK-26", TankTypes.ARTY), Tracks.OBJECT_261, Turrets.NO_TURRET_ARTY, "Type: SPG", "Fairly mobile.");
}
