package musician101.minetanks.tank;

import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.tank.modules.Engine;
import musician101.minetanks.tank.modules.Radio;
import musician101.minetanks.tank.modules.cannon.AutoLoader;
import musician101.minetanks.tank.modules.cannon.Cannon;
import musician101.minetanks.tank.modules.tracks.Tracks;
import musician101.minetanks.tank.modules.turret.Turrets;

public class Tanks
{
	/** China Lights */
	public static final Tank WZ_132 = new Tank("WZ-132", TankTypes.LIGHT, 950, new Armor(50, 25, 20), 64, new Cannon("100 mm 60-100T", 43, 8.5), new Engine("8V135", TankTypes.LIGHT), new Radio("A-220A", TankTypes.LIGHT), Tracks.WZ_132, Turrets.WZ_132, "Type: Light", "Best agility and mobility.");
	/** China Mediums */
    public static final Tank _121 = new Tank("121", TankTypes.MEDIUM, 1950, new Armor(120, 80, 60), 56, new Cannon("122 mm 60-122T", 40, 9.6), new Engine("12150L7", TankTypes.MEDIUM), new Radio("A-220B", TankTypes.MEDIUM), Tracks._121, Turrets._121, "Type: Medium", "Very good high damage per shell and minute.");
	/** China Heavies */
    public static final Tank _113 = new Tank("113", TankTypes.HEAVY, 2300, new Armor(120, 90, 70), 50, new Cannon("122 mm 60-122T", 34, 10.9), new Engine("V-2-54SC", TankTypes.HEAVY), new Radio("A-220B", TankTypes.HEAVY), Tracks._113, Turrets._113, "Type: Heavy", "Good mobility and hight top speed.");
	/** France Lights */
    public static final Tank AMX_13_90 = new Tank("AMX 13 90", TankTypes.LIGHT, 1100, new Armor(40, 20, 15), 64, new AutoLoader("90 mm F3", 36, 42, 2.73, 6), new Engine("Detroit Diesel 6V53T-S", TankTypes.LIGHT), new Radio("SCR 528", TankTypes.LIGHT), Tracks.AMX_13_TYPE_2D, Turrets.FL_10, "Type: Light", "High speed.", "Cannon has a clip for quick damage, but a long base reload.");
	/** French Mediums */
    public static final Tank BAT_CHAT_25_T = new Tank("Bat.-Chatillon 25 t", TankTypes.MEDIUM, 1800, new Armor(60, 40, 30), 65, new AutoLoader("105 mm mle. 57 (D. 1504)", 30, 40, 2.73, 5), new Engine("Hispano-Suiza HS 110", TankTypes.MEDIUM), new Radio("SCR 528", TankTypes.MEDIUM), Tracks.BAT_CHAT_25_T, Turrets.BAT_CHAT_25_T, "Type: Medium", "High mobility.", "Cannon has a clip for quick damage, but a long base reload.");
    public static final Tank AMX_30_B = new Tank("AMX 30 B", TankTypes.MEDIUM, 1900, new Armor(80, 35, 30), 65, new Cannon("105 mm mle. F1", 50, 8.53), new Engine("Hispano-Suiza HS 110", TankTypes.MEDIUM), new Radio("TRVP-13-A", TankTypes.MEDIUM), Tracks.AMX_30_B, Turrets.AMX_30_B, "Type: Medium", "Really high camo value", "Very poor armor.");
	/** French Heavies */
    public static final Tank AMX_50_B = new Tank("AMX 50 B", TankTypes.HEAVY, 2100, new Armor(170, 30, 30), 65, new AutoLoader("120 mm SA46", 56, 30, 2.5, 4), new Engine("Maybach HL 295 F", TankTypes.HEAVY), new Radio("SCR 619", TankTypes.HEAVY), Tracks.AMX_50_B, Turrets.AMX_50_B, "Type: Heavy", "Excellent speed.", "Cannon has a clip for quick damage, but a long base reload.");
	/** French TDs */
    public static final Tank AMX_50_FOCH_155 = new Tank("AMX 50 Foch (155)", TankTypes.TD, 1850, new Armor(180, 40, 40), 50, new AutoLoader("Canon de 155 mm", 30, 50, 5, 3), new Engine("Saurer", TankTypes.TD), new Radio("SCR 619", TankTypes.TD), Tracks.AMX_50_FOCH_155, Turrets.NO_TURRET_TD, "Type: TD", "High damage per shot.", "Cannon has a clip for quick damage, but a long base reload.");
	/** French Arty */
    public static final Tank BAT_CHAT_155_58 = new Tank("Bat.-Chatillon 155 58", TankTypes.ARTY, 490, new Armor(50, 30, 20), 62, new AutoLoader("180 mm B-1-P", 36, 80, 6, 4), new Engine("Hispano-Suiza HS 110", TankTypes.ARTY), new Radio("SCR 528", TankTypes.ARTY), Tracks.BAT_CHAT_155_58, Turrets.NO_TURRET_ARTY, "Type: SPG", "Very mobile.", "Cannon has a clip for quick damage, but a long base reload.");
	/** German Lights */
    public static final Tank SPAHPANZER_RU_251 = new Tank("Spahpanzer Ru 251", TankTypes.LIGHT, 500, new Armor(25, 20, 8), 80, new Cannon("90 mm Rheinmetall DM1", 41, 5.94), new Engine("MB 837 Ea 600", TankTypes.LIGHT), new Radio("AN/GRC-7", TankTypes.LIGHT), Tracks.SPAHPANZER_RU_251_PT_I, Turrets.SPAHPANZER_RU_251_DREHTURM_360, "Type: Light", "Thick armor for a light tank.", "Good for ramming.");
	/** German Mediums */
    public static final Tank E_50_M = new Tank("E 50 Ausf. M", TankTypes.MEDIUM, 2050, new Armor(150, 80, 80), 60, new Cannon("10,5 cm Kw.K. L/52 Ausf. K", 48, 9.6), new Engine("Maybach HL 235", TankTypes.MEDIUM), new Radio("10WSc", TankTypes.MEDIUM), Tracks.E_50_M, Turrets.E_50_M, "Type: Medium", "Extraordinary gun.", "Excellent for ramming.");
    public static final Tank LEOPARD_1 = new Tank("Leopard 1", TankTypes.MEDIUM, 1950, new Armor(70, 35, 25), 65, new Cannon("10,5 cm Bodkanone L7A3", 60, 8.7), new Engine("MTU MB 838 CaM 500A", TankTypes.MEDIUM), new Radio("SEM 25A", TankTypes.MEDIUM), Tracks.LEOPARD_1, Turrets.LEOPARD_1, "Type: Medium", "Great speed and mobility.");
	/** German Heavies */
    public static final Tank E_100 = new Tank("E 100", TankTypes.HEAVY, 2700, new Armor(200, 120, 150), 30, new Cannon("15 cm Kw.K. L/38", 50, 20), new Engine("Maybach Neues Projekt", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.E_100, Turrets.E_100, "Type: Heavy", "Strong armor all around.", "Second highest hit point pool of any tank.");
    public static final Tank MAUS = new Tank("Maus", TankTypes.HEAVY, 3000, new Armor(200, 185, 160), 20, new Cannon("12,8 cm Kw.K. 44 L/55", 68, 14.9), new Engine("DB 603 A2", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.MAUS, Turrets.MAUS, "Type: Heavy", "Excellent armor.", "Highest hit point pool of any tank.");
    public static final Tank VK_7201 = new Tank("VK 72.01 (K)", TankTypes.HEAVY, 2500, new Armor(200, 160, 120), 43, new Cannon("15 cm Kw.K L/38", 24, 22), new Engine("MB 517", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.VK_7201, Turrets.VK_7201, "Type: Heavy", "Good ramming tank.", "High damage per shot.");
	/** German TDs */
    public static final Tank JAGDPZ_E_100 = new Tank("Jagdpanzer E 100", TankTypes.TD, 2200, new Armor(200, 120, 150), 30, new Cannon("17 cm Pak", 24, 25.7), new Engine("Maybach Neues Projekt", TankTypes.TD), new Radio("10WSc", TankTypes.TD), Tracks.JAGDPZ_E_100, Turrets.NO_TURRET_TD, "Type: TD", "Incredible single shot power.", "Very strong all around armor with large hit point pool.");
    public static final Tank WAFF_E_100 = new Tank("Waffentrager auf E 100", TankTypes.TD, 2000, new Armor(200, 120, 150), 40, new AutoLoader("15 cm Pak L/38", 60, 50, 3, 4), new Engine("Maybach Neues Projekt", TankTypes.TD), new Radio("10WSc", TankTypes.TD), Tracks.WAFF_E_100, Turrets.NO_TURRET_TD, "Type: TD", "Large health pool for a TD.", "Cannon has a clip for quick damage, but a long base reload.");
	/** German Arty */
    public static final Tank GW_E_100 = new Tank("G.W. E 100", TankTypes.ARTY, 550, new Armor(80, 50, 40), 40, new Cannon("21 cm Morser 18/2", 30, 42.5), new Engine("Maybach HL 234 TRM P45", TankTypes.ARTY), new Radio("10WSc", TankTypes.ARTY), Tracks.GW_E_100, Turrets.NO_TURRET_ARTY, "Type: SPG", "Immune to ramming from most medium tanks.", "Better damage per minute than the T92.");
	/** Japanese Mediums */
    public static final Tank STB_1 = new Tank("STB-1", TankTypes.MEDIUM, 1950, new Armor(110, 35, 25), 53, new Cannon("105 cm Rifled Gun", 50, 8), new Engine("Mitsubishi 10ZF21WT", TankTypes.MEDIUM), new Radio("Type 67", TankTypes.MEDIUM), Tracks.STB_1, Turrets.STB_1, "Type: Medium", "Highest damage per minute out of all the mediums.", "Reasonably mobile.");
	/** British Mediums */
    public static final Tank FV4202 = new Tank("FV4202", TankTypes.MEDIUM, 1950, new Armor(121, 51, 32), 40, new Cannon("105 mm Royal Ornance L7A1", 72, 8.6), new Engine("Rolls-Royce Meteor M120A", TankTypes.MEDIUM), new Radio("SR C45", TankTypes.MEDIUM), Tracks.FV4202, Turrets.FV4202, "Type: Medium", "Very good mobility.");
	/** British Heavies */
    public static final Tank FV215B = new Tank("FV215b", TankTypes.HEAVY, 2500, new Armor(152.4, 101.6, 76.2), 34, new Cannon("120 mm Gun L1A1", 35, 8.7), new Engine("Rolls-Royce Griffon", TankTypes.HEAVY), new Radio("SR C45", TankTypes.HEAVY), Tracks.FV215, Turrets.FV215, "Type: Heavy", "High damage per minute.", "Fastest rate of fire among the heavies (exlcuding tanks with clips).");
	/** British TDs */
    public static final Tank FV215B_183 = new Tank("FV215b (183)", TankTypes.TD, 2000, new Armor(152.4, 50.8, 76.2), 34, new Cannon("183 mm L4", 12, 30), new Engine("Rolls-Royce Meteor Mk. 12", TankTypes.TD), new Radio("SR C45", TankTypes.TD), Tracks.FV215B, Turrets.NO_TURRET_TD, "Type: TD", "Unbelievable damage per shot.", "Nicknamed: The Death Star");
	/** British Arty */
    public static final Tank CONQUEROR_GC = new Tank("Conqueror Gun Carriage", TankTypes.ARTY, 530, new Armor(152.4, 50.8, 76.2), 34, new Cannon("B.L. 9.2-in. Howitzer Mk. II", 24, 58.1), new Engine("Rolls-Royce Meteor M120", TankTypes.ARTY), new Radio("SR C45", TankTypes.ARTY), Tracks.CONQUEROR_GC, Turrets.NO_TURRET_ARTY, "Type: SPG", "Very high damage.", "Beats the T92 in all aspects except reload time.");
	/** American Lights */
    public static final Tank T71 = new Tank("T71", TankTypes.LIGHT, 840, new Armor(25.4, 22.2, 19.1), 64, new AutoLoader("76 mm Gun T185", 60, 20, 2, 6), new Engine("Continental AOI-628-2", TankTypes.LIGHT), new Radio("AN/GRC-7", TankTypes.LIGHT), Tracks.T71A1, Turrets.T71, "Type: Light", "Acceptable clip reload time.", "Excellent mobility.");
	/** American Mediums */
    public static final Tank M48A1_PATTON = new Tank("M48A1 Patton", TankTypes.MEDIUM, 2000, new Armor(110, 76.2, 35.1), 45, new Cannon("105 mm Gun M68", 57, 8.8), new Engine("Continental AV-1790-7B", TankTypes.MEDIUM), new Radio("AN/VRC-7", TankTypes.MEDIUM), Tracks.T97E2_M, Turrets.T87, "Type: Medium", "Highly maneuverable.", "Good damage.");
    public static final Tank M60 = new Tank("M60", TankTypes.MEDIUM, 2000, new Armor(93.2, 73.7, 25.4), 48, new Cannon("105 mm Gun M68", 57, 8.8), new Engine("Continental AVDS-1790-2", TankTypes.MEDIUM), new Radio("AN/VRC-12", TankTypes.MEDIUM), Tracks.M60, Turrets.M60, "Type: Medium", "Great mobility and rate of fire.");
	/** American Heavies */
    public static final Tank T57_HT = new Tank("T57 Heavy Tank", TankTypes.HEAVY, 2250, new Armor(203, 50.8, 38.1), 35, new AutoLoader("120 mm Gun T179", 36, 25, 2, 4), new Engine("Continental AV-1790-5C", TankTypes.HEAVY), new Radio("AN/VRC-3", TankTypes.HEAVY), Tracks.T97, Turrets.T169, "Type: Heavy", "Highest damage per minute of all heavy tanks.", "Cannon has a clip for quick damage, but a long base reload.");
    public static final Tank T110E5 = new Tank("T110E5", TankTypes.HEAVY, 2200, new Armor(254, 76, 38), 37, new Cannon("120 mm Gun M58", 42, 10), new Engine("Continental AOI-1490", TankTypes.HEAVY), new Radio("AN/VRC-3", TankTypes.HEAVY), Tracks.T110E27, Turrets.T53, "Type: Heavy", "Agile compared to other heavy tanks.", "Good damage output.");
	/** American TDs */
    public static final Tank T110E3 = new Tank("T110E3", TankTypes.TD, 2050, new Armor(305, 76, 38), 30, new Cannon("155 mm AT Gun T7E2", 27, 17), new Engine("Continental AOI-1490", TankTypes.TD), new Radio("AN/VRC-3", TankTypes.TD), Tracks.T110E3, Turrets.NO_TURRET_TD, "Type: TD", "Some of the toughest armor.", "Powerful gun.");
    public static final Tank T110E4 = new Tank("T110E4", TankTypes.TD, 2000, new Armor(254, 76, 38), 335, new Cannon("155 mm AT Gun T7E2", 27, 19.5), new Engine("Continental AOI-1490", TankTypes.TD), new Radio("AN/VRC-3", TankTypes.TD), Tracks.T110E4, Turrets.T110E4, "Type: TD", "Decent mobility.", "Acceptable frontal armor.");
	/** American Arty */
    public static final Tank T92 = new Tank("T92", TankTypes.ARTY, 500, new Armor(25.4, 25.4, 21.7), 32, new Cannon("240 mm Howitzer M1", 16, 53), new Engine("Ford GAF T92", TankTypes.ARTY), new Radio("SCR 528A", TankTypes.ARTY), Tracks.T92_T80E1, Turrets.NO_TURRET_ARTY, "Type: SPG", "Most powerful gun in the game.", "Huge splash damage.");
	/** Russian Mediums */
    public static final Tank OBJECT_140 = new Tank("Object 140", TankTypes.MEDIUM, 1900, new Armor(100, 80, 50), 40, new Cannon("100 mm U-8TS", 50, 6.6), new Engine("TD-12", TankTypes.MEDIUM), new Radio("R-113M", TankTypes.MEDIUM), Tracks.OBJECT_140, Turrets.OBJECT_140, "Type: Medium", "Highest rate of fire out of all mediums.", "Fast and agile.");
    public static final Tank OBJECT_430 = new Tank("Object 430", TankTypes.MEDIUM, 1900, new Armor(120, 80, 40), 55, new Cannon("100 mm U-8TS", 50, 6.9), new Engine("5TD", TankTypes.MEDIUM), new Radio("R-113M", TankTypes.MEDIUM), Tracks.OBJECT_430, Turrets.OBJECT_430, "Type: Medium", "Great damage per minute.", "Great for skirmishing.");
    public static final Tank T_62A = new Tank("T-62A", TankTypes.MEDIUM, 1950, new Armor(102, 75, 45), 50, new Cannon("100 mm U-8TS", 50, 6.6), new Engine("V-55", TankTypes.MEDIUM), new Radio("R-123", TankTypes.MEDIUM), Tracks.T_62A, Turrets.T_62A, "Type: Medium", "Very high damage per minute.", "Very strong turret armor.");
	/** Russian Heavies */
    public static final Tank IS_4 = new Tank("IS-4", TankTypes.HEAVY, 2500, new Armor(140, 160, 100), 43, new Cannon("122 mm M62-T2", 30, 12), new Engine("V-12", TankTypes.HEAVY), new Radio("10RK-26", TankTypes.HEAVY), Tracks.IS_4M, Turrets.IS_4M, "Type: Heavy", "High health pool.", "Thick armor.");
    public static final Tank IS_7 = new Tank("IS-7", TankTypes.HEAVY, 2150, new Armor(150, 150, 100), 60, new Cannon("130 mm S-70", 30, 13.7), new Engine("M-50T", TankTypes.HEAVY), new Radio("10RK-26", TankTypes.HEAVY), Tracks.IS_7, Turrets.IS_7, "Type: Heavy", "Excellent armor.", "High top speed.");
	/** Russian TDs */
    public static final Tank OBJECT_263 = new Tank("Object 263", TankTypes.TD, 1900, new Armor(250, 80, 50), 55, new Cannon("130 mm S-70A", 42, 10.7), new Engine("M-50T", TankTypes.TD), new Radio("10RK-26", TankTypes.TD), Tracks.OBJECT_263, Turrets.NO_TURRET_TD, "Type: TD", "Better damage per minute than the Object 268.", "Great mobility.");
    public static final Tank OBJECT_268 = new Tank("Object 268", TankTypes.TD, 1950, new Armor(187, 100, 50), 48, new Cannon("152 mm M64", 35, 16.5), new Engine("V-16FM", TankTypes.TD), new Radio("R-113M", TankTypes.TD), Tracks.OBJECT_268, Turrets.NO_TURRET_TD, "Type: TD", "Extremely good damager per minute.", "Good mobility.");
	/** Russian Arty */
    public static final Tank OBJECT_261 = new Tank("Object 261", TankTypes.ARTY, 510, new Armor(75, 50, 30), 50, new Cannon("180 mm B-1-P", 18, 35), new Engine("M-50T", TankTypes.ARTY), new Radio("10RK-26", TankTypes.ARTY), Tracks.OBJECT_261, Turrets.NO_TURRET_ARTY, "Type: SPG", "Fairly mobile.");
}
