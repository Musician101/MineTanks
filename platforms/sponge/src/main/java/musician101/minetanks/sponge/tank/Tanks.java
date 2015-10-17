package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.sponge.tank.module.Engine;
import musician101.minetanks.sponge.tank.module.Radio;
import musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import musician101.minetanks.sponge.tank.module.tracks.Tracks;
import musician101.minetanks.sponge.tank.module.turret.Turrets;

import java.util.Arrays;
import java.util.List;

public class Tanks
{
    /** China Lights */
    public static final Tank WZ_132 = new Tank("WZ-132", SpongeTankTypes.LIGHT, 1150, new Armor(50, 25, 20), 64, new SpongeCannon("100 mm 60-100T", 43, 7.57), new Engine("8V135", SpongeTankTypes.LIGHT), new Radio("A-220A", SpongeTankTypes.LIGHT), Tracks.WZ_132, Turrets.WZ_132, "Type: Light", "Very good agility and mobility.");

    /** China Mediums */
    public static final Tank _121 = new Tank("121", SpongeTankTypes.MEDIUM, 1950, new Armor(120, 80, 60), 56, new SpongeCannon("122 mm 60-122T", 40, 9.6), new Engine("12150L7", SpongeTankTypes.MEDIUM), new Radio("A-220B", SpongeTankTypes.MEDIUM), Tracks._121, Turrets._121, "Type: Medium", "Very high damage per shell and minute.");

    /** China Heavies */
    public static final Tank _113 = new Tank("113", SpongeTankTypes.HEAVY, 2300, new Armor(120, 90, 70), 50, new SpongeCannon("122 mm 60-122T", 34, 10.45), new Engine("V-2-54SC", SpongeTankTypes.HEAVY), new Radio("A-220B", SpongeTankTypes.HEAVY), Tracks._113, Turrets._113, "Type: Heavy", "Good mobility and high top speed.");

    /** France Lights */
    public static final Tank AMX_13_90 = new Tank("AMX 13 90", SpongeTankTypes.LIGHT, 1100, new Armor(40, 20, 15), 64, new SpongeAutoLoader("90 mm F3", 36, 42, 2.73, 6), new Engine("Detroit Diesel 6V53T-S", SpongeTankTypes.LIGHT), new Radio("SCR 528", SpongeTankTypes.LIGHT), Tracks.AMX_13_TYPE_2D, Turrets.FL_10, "Type: Light", "SpongeCannon has a clip for quick damage, but a long base reload time.");

    /** French Mediums */
    public static final Tank BAT_CHAT_25_T = new Tank("Bat.-Chatillon 25 t", SpongeTankTypes.MEDIUM, 1800, new Armor(60, 40, 30), 65, new SpongeAutoLoader("105 mm mle. 57 (D. 1504)", 30, 40, 2.73, 5), new Engine("Hispano-Suiza HS 110", SpongeTankTypes.MEDIUM), new Radio("SCR 528F", SpongeTankTypes.MEDIUM), Tracks.BAT_CHAT_25_T, Turrets.BAT_CHAT_25_T, "Type: Medium", "High top speed.", "SpongeCannon has a clip for quick damage, but a long base reload time.");
    public static final Tank AMX_30_B = new Tank("AMX 30 B", SpongeTankTypes.MEDIUM, 1900, new Armor(80, 35, 30), 65, new SpongeCannon("105 mm mle. F1", 50, 8.53), new Engine("Hispano-Suiza HS 110", SpongeTankTypes.MEDIUM), new Radio("TRVP-13-A", SpongeTankTypes.MEDIUM), Tracks.AMX_30_B, Turrets.AMX_30_B, "Type: Medium", "Slightly better armor than the Leopard 1.", "Very poor armor.");

    /** French Heavies */
    public static final Tank AMX_50_B = new Tank("AMX 50 B", SpongeTankTypes.HEAVY, 2100, new Armor(170, 30, 30), 65, new SpongeAutoLoader("120 mm SA46", 56, 30, 2.5, 4), new Engine("Maybach HL 295 F", SpongeTankTypes.HEAVY), new Radio("SCR 619", SpongeTankTypes.HEAVY), Tracks.AMX_50_B, Turrets.AMX_50_B, "Type: Heavy", "Excellent speed.", "SpongeCannon has a clip for quick damage, but a long base reload.");

    /** French TDs */
    public static final Tank AMX_50_FOCH_155 = new Tank("AMX 50 Foch (155)", SpongeTankTypes.TD, 1850, new Armor(180, 40, 40), 50, new SpongeAutoLoader("155 mm AC SA58", 30, 47.94, 5, 3), new Engine("Saurer", SpongeTankTypes.TD), new Radio("SCR 619", SpongeTankTypes.TD), Tracks.AMX_50_FOCH_155, Turrets.NO_TURRET_TD, "Type: TD", "High damage per shot.", "SpongeCannon has a clip for quick damage, but a long base reload.");

    /** French Arty */
    public static final Tank BAT_CHAT_155_58 = new Tank("Bat.-Chatillon 155 58", SpongeTankTypes.ARTY, 490, new Armor(50, 30, 20), 62, new SpongeAutoLoader("Canon de 155 mm", 36, 76.71, 6, 4), new Engine("Hispano-Suiza HS 110", SpongeTankTypes.ARTY), new Radio("SCR 528F", SpongeTankTypes.ARTY), Tracks.BAT_CHAT_155_58, Turrets.NO_TURRET_ARTY, "Type: SPG", "Very mobile.", "SpongeCannon has a clip for quick damage, but a long base reload.");

    /** German Lights */
    public static final Tank SPAHPANZER_RU_251 = new Tank("Spahpanzer Ru 251", SpongeTankTypes.LIGHT, 500, new Armor(25, 20, 8), 80, new SpongeCannon("90 mm Rheinmetall DM1", 41, 5.94), new Engine("MB 837 Ea 600", SpongeTankTypes.LIGHT), new Radio("AN/GRC-7", SpongeTankTypes.LIGHT), Tracks.SPAHPANZER_RU_251_PT_I, Turrets.SPAHPANZER_RU_251_DREHTURM_360, "Type: Light", "Thick armor for a light tank.", "Good for ramming.");

    /** German Mediums */
    public static final Tank E_50_M = new Tank("E 50 Ausf. M", SpongeTankTypes.MEDIUM, 2050, new Armor(150, 80, 80), 60, new SpongeCannon("10,5 cm Kw.K. L/52 Ausf. K", 48, 9.2), new Engine("Maybach HL 235", SpongeTankTypes.MEDIUM), new Radio("10WSc", SpongeTankTypes.MEDIUM), Tracks.E_50_M, Turrets.E_50_M, "Type: Medium", "Extraordinary gun.", "Excellent for ramming.");
    public static final Tank LEOPARD_1 = new Tank("Leopard 1", SpongeTankTypes.MEDIUM, 1950, new Armor(70, 35, 25), 65, new SpongeCannon("10,5 cm Bordkanone L7A3", 60, 8.34), new Engine("MTU MB 838 CaM 500A", SpongeTankTypes.MEDIUM), new Radio("SEM 25A", SpongeTankTypes.MEDIUM), Tracks.LEOPARD_1, Turrets.LEOPARD_1, "Type: Medium", "Great speed and mobility.", "Very weak armor.");

    /** German Heavies */
    public static final Tank E_100 = new Tank("E 100", SpongeTankTypes.HEAVY, 2700, new Armor(200, 120, 150), 30, new SpongeCannon("15 cm Kw.K. L/38", 50, 19.18), new Engine("Maybach Neues Projekt", SpongeTankTypes.HEAVY), new Radio("10WSc", SpongeTankTypes.HEAVY), Tracks.E_100, Turrets.E_100, "Type: Heavy", "Strong armor all around.", "Third highest hit point pool of any tank.");
    public static final Tank MAUS = new Tank("Maus", SpongeTankTypes.HEAVY, 3000, new Armor(200, 185, 160), 20, new SpongeCannon("12,8 cm Kw.K. 44 L/55", 68, 14.29), new Engine("DB 603 A2", SpongeTankTypes.HEAVY), new Radio("10WSc", SpongeTankTypes.HEAVY), Tracks.MAUS, Turrets.MAUS, "Type: Heavy", "Excellent armor.", "Highest hit point pool of any tank.");
    public static final Tank VK_7201 = new Tank("VK 72.01 (K)", SpongeTankTypes.HEAVY, 2500, new Armor(200, 160, 120), 43, new SpongeCannon("15 cm Kw.K L/38", 24, 21.09), new Engine("MB 517", SpongeTankTypes.HEAVY), new Radio("10WSc", SpongeTankTypes.HEAVY), Tracks.VK_7201, Turrets.VK_7201, "Type: Heavy", "Good ramming tank.", "High damage per shot.");

    /** German TDs */
    public static final Tank JAGDPANZER_E_100 = new Tank("Jagdpanzer E 100", SpongeTankTypes.TD, 2200, new Armor(200, 120, 150), 30, new SpongeCannon("17 cm Pak", 24, 24.64), new Engine("Maybach Neues Projekt", SpongeTankTypes.TD), new Radio("10WSc", SpongeTankTypes.TD), Tracks.JAGDPANZER_E_100, Turrets.NO_TURRET_TD, "Type: TD", "Incredible single shot power.", "Very strong all around armor with large hit point pool.");
    public static final Tank WAFFENTRAGER_E_100 = new Tank("Waffentrager auf E 100", SpongeTankTypes.TD, 2000, new Armor(200, 120, 150), 40, new SpongeAutoLoader("15 cm Pak L/38", 60, 57.94, 3, 4), new Engine("Maybach Neues Projekt", SpongeTankTypes.TD), new Radio("10WSc", SpongeTankTypes.TD), Tracks.WAFFENTRAGER_E_100, Turrets.WAFFENTRAGER_E_100, "Type: TD", "SpongeCannon has a clip for quick damage, but a long base reload.");

    /** German Arty */
    public static final Tank GW_E_100 = new Tank("G.W. E 100", SpongeTankTypes.ARTY, 550, new Armor(80, 50, 40), 40, new SpongeCannon("21 cm Morser 18/2", 30, 40.75), new Engine("Maybach HL 234 TRM P45", SpongeTankTypes.ARTY), new Radio("10WSc", SpongeTankTypes.ARTY), Tracks.GW_E_100, Turrets.NO_TURRET_ARTY, "Type: SPG", "Good balance of damage, reload, and accuracy.", "Better damage per minute than the T92.");

    /** Japanese Mediums */
    public static final Tank STB_1 = new Tank("STB-1", SpongeTankTypes.MEDIUM, 1950, new Armor(110, 35, 25), 53, new SpongeCannon("105 mm Rifled Gun", 50, 7.67), new Engine("Mitsubishi 10ZF21WT", SpongeTankTypes.MEDIUM), new Radio("Type 67", SpongeTankTypes.MEDIUM), Tracks.STB_1, Turrets.STB_1, "Type: Medium", "Fastest reload on a 105mm SpongeCannon.", "Reasonably mobile.");

    /** Japanese Heavies */
    public static final Tank TYPE_5_HEAVY = new Tank("Type 5 Heavy", SpongeTankTypes.HEAVY, 2800, new Armor(260, 140, 150), 25, new SpongeCannon("14 cm/50 3rd Year Type", 60, 16.97), new Engine("Kawasaki Type 98 V-12 1200 Kai", SpongeTankTypes.HEAVY), new Radio("Type 3 Ko", SpongeTankTypes.HEAVY), Tracks.TYPE_5_HEAVY, Turrets.TYPE_5_HEAVY, "Type: Heavy", "Incredible armor.", "Lowest damage per minute.");

    /** British Mediums */
    public static final Tank FV4202 = new Tank("FV4202", SpongeTankTypes.MEDIUM, 1950, new Armor(121, 51, 32), 40, new SpongeCannon("105 mm Royal Ordnance L7A1", 72, 8.25), new Engine("Rolls-Royce Meteor M120A", SpongeTankTypes.MEDIUM), new Radio("SR C45", SpongeTankTypes.MEDIUM), Tracks.FV4202, Turrets.FV4202, "Type: Medium", "Very good mobility.", "Limited top speed.");

    /** British Heavies */
    public static final Tank FV215B = new Tank("FV215b", SpongeTankTypes.HEAVY, 2500, new Armor(152.4, 101.6, 76.2), 34, new SpongeCannon("120 mm Gun L1A1", 35, 8.34), new Engine("Rolls-Royce Griffon", SpongeTankTypes.HEAVY), new Radio("SR C45", SpongeTankTypes.HEAVY), Tracks.FV215, Turrets.FV215, "Type: Heavy", "High health pool.", "Weak all around armor for a heavy tank.");

    /** British TDs */
    public static final Tank FV215B_183 = new Tank("FV215b (183)", SpongeTankTypes.TD, 2000, new Armor(152.4, 50.8, 76.2), 34, new SpongeCannon("183 mm L4", 12, 28.77), new Engine("Rolls-Royce Meteor Mk. 12", SpongeTankTypes.TD), new Radio("SR C45", SpongeTankTypes.TD), Tracks.FV215B, Turrets.FV215B, "Type: TD", "Unbelievable damage per shot.", "Long reloading cycle.");
    public static final Tank FV4005_STAGE_II = new Tank("FV4005 Stage II", SpongeTankTypes.TD, 1850, new Armor(76.2, 50.8, 38.1), 35, new SpongeCannon("183 mm L4", 12, 28.77), new Engine("Rolls-Royce Meteor Mk. IVB", SpongeTankTypes.TD), new Radio("SR C45", SpongeTankTypes.TD), Tracks.FV4005_STAGE_II, Turrets.FV4005_STAGE_II, "Type: TD", "Better mobility and traverse than FV215b (183).");

    /** British Arty */
    public static final Tank CONQUEROR_GC = new Tank("Conqueror Gun Carriage", SpongeTankTypes.ARTY, 530, new Armor(152.4, 50.8, 76.2), 34, new SpongeCannon("B.L. 9.2-in. Howitzer Mk. II", 24, 55.71), new Engine("Rolls-Royce Meteor M120", SpongeTankTypes.ARTY), new Radio("SR C45", SpongeTankTypes.ARTY), Tracks.CONQUEROR_GC, Turrets.NO_TURRET_ARTY, "Type: SPG", "Very high damage.");

    /** American Lights */
    public static final Tank T49 = new Tank("T49", SpongeTankTypes.LIGHT, 1000, new Armor(25.4, 25.4, 19.1), 72, new SpongeCannon("90 mm Gun T185", 22, 22.05), new Engine("Continental AOI-895-5", SpongeTankTypes.LIGHT), new Radio("AN/GRC-7", SpongeTankTypes.LIGHT), Tracks.XM551_TEST_BED, Turrets.XM551_TEST_BED, "Type: Light", "Poor mobility for a light tank.");

    /** American Mediums */
    public static final Tank M48A1_PATTON = new Tank("M48A1 Patton", SpongeTankTypes.MEDIUM, 2000, new Armor(152.4, 76.2, 38.1), 45, new SpongeCannon("105 mm Gun M68", 57, 8.44), new Engine("Continental AV-1790-7B", SpongeTankTypes.MEDIUM), new Radio("AN/VRC-7", SpongeTankTypes.MEDIUM), Tracks.T97E2_M, Turrets.M87, "Type: Medium", "Low top speed.", "Good damage.");
    public static final Tank M60 = new Tank("M60", SpongeTankTypes.MEDIUM, 2000, new Armor(93.2, 73.7, 25.4), 48, new SpongeCannon("105 mm Gun M68", 57, 8.44), new Engine("Continental AVDS-1790-2", SpongeTankTypes.MEDIUM), new Radio("AN/VRC-12", SpongeTankTypes.MEDIUM), Tracks.M60, Turrets.M60, "Type: Medium", "Great mobility and rate of fire.", "Relatively thin armor.");

    /** American Heavies */
    public static final Tank T110E5 = new Tank("T110E5", SpongeTankTypes.HEAVY, 2200, new Armor(254, 76.2, 38.1), 37, new SpongeCannon("120 mm Gun M58", 42, 9.59), new Engine("Continental AOI-1490", SpongeTankTypes.HEAVY), new Radio("AN/VRC-3", SpongeTankTypes.HEAVY), Tracks.T110E27, Turrets.T53, "Type: Heavy", "Excellent damage per minute.", "Low side and rear armor.");
    public static final Tank T57_HT = new Tank("T57 Heavy Tank", SpongeTankTypes.HEAVY, 2250, new Armor(203, 50.8, 38.1), 35, new SpongeAutoLoader("120 mm Gun T179", 36, 23.97, 2, 4), new Engine("Continental AV-1790-5C", SpongeTankTypes.HEAVY), new Radio("AN/VRC-3", SpongeTankTypes.HEAVY), Tracks.T97, Turrets.T169, "Type: Heavy", "Highest damage per minute of all heavy tanks.", "SpongeCannon has a clip for quick damage, but a long base reload.");

    /** American TDs */
    public static final Tank T110E3 = new Tank("T110E3", SpongeTankTypes.TD, 2050, new Armor(305, 76, 38), 30, new SpongeCannon("155 mm AT Gun T7E2", 27, 16.3), new Engine("Continental AOI-1490", SpongeTankTypes.TD), new Radio("AN/VRC-3", SpongeTankTypes.TD), Tracks.T110E3, Turrets.NO_TURRET_TD, "Type: TD", "Some of the toughest armor.", "Poor mobility.");
    public static final Tank T110E4 = new Tank("T110E4", SpongeTankTypes.TD, 2000, new Armor(260, 76, 38), 35, new SpongeCannon("155 mm AT Gun T7E2", 27, 18.7), new Engine("Continental AOI-1490", SpongeTankTypes.TD), new Radio("AN/VRC-3", SpongeTankTypes.TD), Tracks.T110E4, Turrets.T110E4, "Type: TD", "Decent mobility.");

    /** American Arty */
    public static final Tank T92 = new Tank("T92", SpongeTankTypes.ARTY, 500, new Armor(25.4, 25.4, 21.7), 32, new SpongeCannon("240 mm Howitzer M1", 16, 50.82), new Engine("Ford GAF T92", SpongeTankTypes.ARTY), new Radio("SCR 528A", SpongeTankTypes.ARTY), Tracks.T92_T80E1, Turrets.NO_TURRET_ARTY, "Type: SPG", "Most powerful gun in the game.", "Huge splash damage.");

    /** Russian Lights */
    public static final Tank T_54_LTWT = new Tank("T-54 ltwt.", SpongeTankTypes.LIGHT, 1250, new Armor(80, 60, 45), 69, new SpongeCannon("100 mm D10T mod. 1945", 34, 7.19), new Engine("V-14", SpongeTankTypes.LIGHT), new Radio("10RT-26", SpongeTankTypes.LIGHT), Tracks.T_54_ENHANCED_LTWT_VERSION, Turrets.T_54_LTWT_VERSION, "Type: Light", "Strong turret and hull armor for a light tank.");

    /** Russian Mediums */
    public static final Tank OBJECT_140 = new Tank("Object 140", SpongeTankTypes.MEDIUM, 1900, new Armor(100, 80, 55), 40, new SpongeCannon("100 mm U-8TS", 50, 6.33), new Engine("TD-12", SpongeTankTypes.MEDIUM), new Radio("R-113M", SpongeTankTypes.MEDIUM), Tracks.OBJECT_140, Turrets.OBJECT_140, "Type: Medium", "Very high damage per minute.", "Good top speed.");
    public static final Tank OBJECT_430 = new Tank("Object 430", SpongeTankTypes.MEDIUM, 1900, new Armor(120, 80, 40), 55, new SpongeCannon("100 mm U-8TS", 50, 6.14), new Engine("5TD", SpongeTankTypes.MEDIUM), new Radio("R-113M", SpongeTankTypes.MEDIUM), Tracks.OBJECT_430, Turrets.OBJECT_430, "Type: Medium", "Very high damage per minute.", "Tied for the lowest average damage per shot.");
    public static final Tank OBJECT_907 = new Tank("Object 907", SpongeTankTypes.MEDIUM, 2000, new Armor(100, 85, 45), 55, new SpongeCannon("100 mm D-54TS", 45, 6.23), new Engine("V-12-5U", SpongeTankTypes.MEDIUM), new Radio("R-123", SpongeTankTypes.MEDIUM), Tracks.OBJECT_907, Turrets.OBJECT_907, "Type: Medium", "High damage per minute.", "Low ammunition count.");
    public static final Tank T_62A = new Tank("T-62A", SpongeTankTypes.MEDIUM, 1950, new Armor(100, 80, 45), 50, new SpongeCannon("100 mm U-8TS", 50, 6.33), new Engine("V-55", SpongeTankTypes.MEDIUM), new Radio("R-123", SpongeTankTypes.MEDIUM), Tracks.T_62A, Turrets.T_62A, "Type: Medium", "Very high damage per minute.", "Very strong turret armor.");

    /** Russian Heavies */
    public static final Tank IS_4 = new Tank("IS-4", SpongeTankTypes.HEAVY, 2500, new Armor(140, 160, 100), 43, new SpongeCannon("122 mm M62-T2", 30, 11.51), new Engine("V-12", SpongeTankTypes.HEAVY), new Radio("10RK-26", SpongeTankTypes.HEAVY), Tracks.IS_4M, Turrets.IS_4M, "Type: Heavy", "High health pool.", "Lacks mobility and damage per minute.");
    public static final Tank IS_7 = new Tank("IS-7", SpongeTankTypes.HEAVY, 2150, new Armor(150, 150, 100), 60, new SpongeCannon("130 mm S-70", 30, 13.14), new Engine("M-50T", SpongeTankTypes.HEAVY), new Radio("10RK-26", SpongeTankTypes.HEAVY), Tracks.IS_7, Turrets.IS_7, "Type: Heavy", "Amazingly thick armor.");
    public static final Tank OBJECT_260 = new Tank("Object 260", SpongeTankTypes.HEAVY, 2100, new Armor(150, 150, 70), 60, new SpongeCannon("122 mm BL-13-1", 30, 11.03), new Engine("2x V-16", SpongeTankTypes.HEAVY), new Radio("10RK-26", SpongeTankTypes.HEAVY), Tracks.OBJECT_260_MOD_1945, Turrets.OBJECT_260_MOD_1945, "Type: Heavy", "Superior gun to the IS-4.", "Low health point pool for a heavy tank.");

    /** Russian TDs */
    public static final Tank OBJECT_263 = new Tank("Object 263", SpongeTankTypes.TD, 1900, new Armor(250, 80, 50), 55, new SpongeCannon("130 mm S-70A", 42, 10.26), new Engine("M-50T", SpongeTankTypes.TD), new Radio("10RK-26", SpongeTankTypes.TD), Tracks.OBJECT_263, Turrets.NO_TURRET_TD, "Type: TD", "Great mobility.", "Low damage.");
    public static final Tank OBJECT_268 = new Tank("Object 268", SpongeTankTypes.TD, 1950, new Armor(187, 100, 50), 48, new SpongeCannon("152 mm M64", 35, 15.82), new Engine("V-16FN", SpongeTankTypes.TD), new Radio("R-113M", SpongeTankTypes.TD), Tracks.OBJECT_268, Turrets.NO_TURRET_TD, "Type: TD", "Good damage per minute.", "Good top speed.");

    /** Russian Arty */
    public static final Tank OBJECT_261 = new Tank("Object 261", SpongeTankTypes.ARTY, 510, new Armor(75, 50, 30), 50, new SpongeCannon("180 mm B-1-P", 18, 33.56), new Engine("M-50T", SpongeTankTypes.ARTY), new Radio("10RK-26", SpongeTankTypes.ARTY), Tracks.OBJECT_261, Turrets.NO_TURRET_ARTY, "Type: SPG", "Fairly mobile.");

    public static final List<Tank> tankList = Arrays.asList(
            /** China Lights */
            WZ_132,
            /** China Mediums */
            _121,
            /** China Heavies */
            _113,
            /** France Lights */
            AMX_13_90,
            /** French Mediums */
            BAT_CHAT_25_T, AMX_30_B,
            /** French Heavies */
            AMX_50_B,
            /** French TDs */
            AMX_50_FOCH_155,
            /** French Arty */
            BAT_CHAT_155_58,
            /** German Lights */
            SPAHPANZER_RU_251,
            /** German Mediums */
            E_50_M, LEOPARD_1,
            /** German Heavies */
            E_100, MAUS, VK_7201,
            /** German TDs */
            JAGDPANZER_E_100, WAFFENTRAGER_E_100,
            /** German Arty */
            GW_E_100,
            /** Japanese Mediums */
            STB_1,
            /** Japanese Heavies */
            TYPE_5_HEAVY,
            /** British Mediums */
            FV4202,
            /** British Heavies */
            FV215B,
            /** British TDs */
            FV215B_183, FV4005_STAGE_II,
            /** British Arty */
            CONQUEROR_GC,
            /** American Lights */
            T49,
            /** American Mediums */
            M48A1_PATTON, M60,
            /** American Heavies */
            T110E5, T57_HT,
            /** American TDs */
            T110E3, T110E4,
            /** American Arty */
            T92,
            /** Russian Lights */
            T_54_LTWT,
            /** Russian Mediums */
            OBJECT_140, OBJECT_430, OBJECT_907, T_62A,
            /** Russian Heavies */
            IS_4, IS_7, OBJECT_260,
            /** Russian TDs */
            OBJECT_263, OBJECT_268,
            /** Russian Arty */
            OBJECT_261);
}
