package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.CommonReference.CommonCannons;
import musician101.minetanks.common.CommonReference.CommonEngines;
import musician101.minetanks.common.CommonReference.CommonRadios;
import musician101.minetanks.common.CommonReference.CommonTanks;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.spigot.tank.modules.Engine;
import musician101.minetanks.spigot.tank.modules.Radio;
import musician101.minetanks.spigot.tank.modules.cannon.AutoLoader;
import musician101.minetanks.spigot.tank.modules.cannon.Cannon;
import musician101.minetanks.spigot.tank.modules.tracks.Tracks;
import musician101.minetanks.spigot.tank.modules.turret.Turrets;

import java.util.Arrays;
import java.util.List;

public class Tanks
{
    /** China Lights */
    public static final Tank WZ_132 = new Tank(CommonTanks.WZ_132, TankTypes.LIGHT, 1150, new Armor(50, 25, 20), 64, new Cannon(CommonCannons._100_MM_60_100T, 43, 7.57), new Engine(CommonEngines._8V135,TankTypes.LIGHT), new Radio(CommonRadios.A_220A,TankTypes.LIGHT), Tracks.WZ_132, Turrets.WZ_132);
    /** China Mediums */
    public static final Tank _121 = new Tank(CommonTanks._121, TankTypes.MEDIUM, 1950, new Armor(120, 80, 60), 56, new Cannon(CommonCannons._122_MM_60_122T, 40, 9.6), new Engine(CommonEngines._12150L7, TankTypes.MEDIUM), new Radio(CommonRadios.A_220B, TankTypes.MEDIUM), Tracks._121, Turrets._121);
    /** China Heavies */
    public static final Tank _113 = new Tank(CommonTanks._113, TankTypes.HEAVY, 2300, new Armor(120, 90, 70), 50, new Cannon(CommonCannons._122_MM_60_122T, 34, 10.45), new Engine(CommonEngines.V_2_54SC, TankTypes.HEAVY), new Radio(CommonRadios.A_220B, TankTypes.HEAVY), Tracks._113, Turrets._113);
    /** France Lights */
    public static final Tank AMX_13_90 = new Tank(CommonTanks.AMX_13_90, TankTypes.LIGHT, 1100, new Armor(40, 20, 15), 64, new AutoLoader(CommonCannons._90_MM_F3, 36, 42, 2.73, 6), new Engine(CommonEngines.DETROIT_DIESEL_6V53T_S, TankTypes.LIGHT), new Radio(CommonRadios.SCR_528, TankTypes.LIGHT), Tracks.AMX_13_TYPE_2D, Turrets.FL_10);
    /** French Mediums */
    public static final Tank BAT_CHAT_25_T = new Tank(CommonTanks.BAT_CHAT_25_T, TankTypes.MEDIUM, 1800, new Armor(60, 40, 30), 65, new AutoLoader(CommonCannons._105_MM_MLE_57, 30, 40, 2.73, 5), new Engine(CommonEngines.HISPANO_SUIZA_HS_110, TankTypes.MEDIUM), new Radio(CommonRadios.SCR_528F, TankTypes.MEDIUM), Tracks.BAT_CHAT_25_T, Turrets.BAT_CHAT_25_T);
    public static final Tank AMX_30_B = new Tank(CommonTanks.AMX_30_B, TankTypes.MEDIUM, 1900, new Armor(80, 35, 30), 65, new Cannon(CommonCannons._105_MM_MLE_F1, 50, 8.53), new Engine(CommonEngines.HISPANO_SUIZA_HS_110, TankTypes.MEDIUM), new Radio(CommonRadios.TRVP_13_A, TankTypes.MEDIUM), Tracks.AMX_30_B, Turrets.AMX_30_B);
    /** French Heavies */
    public static final Tank AMX_50_B = new Tank(CommonTanks.AMX_50_B, TankTypes.HEAVY, 2100, new Armor(170, 30, 30), 65, new AutoLoader(CommonCannons._120_MM_SA46, 56, 30, 2.5, 4), new Engine(CommonEngines.MAYBACH_HL_295_F, TankTypes.HEAVY), new Radio(CommonRadios.SCR_619, TankTypes.HEAVY), Tracks.AMX_50_B, Turrets.AMX_50_B);
    /** French TDs */
    public static final Tank AMX_50_FOCH_155 = new Tank(CommonTanks.AMX_50_FOCH_155, TankTypes.TD, 1850, new Armor(180, 40, 40), 50, new AutoLoader(CommonCannons._155_MM_AC_SA58, 30, 47.94, 5, 3), new Engine(CommonEngines.SAURER, TankTypes.TD), new Radio(CommonRadios.SCR_619, TankTypes.TD), Tracks.AMX_50_FOCH_155, Turrets.NO_TURRET_TD);
    /** French Arty */
    public static final Tank BAT_CHAT_155_58 = new Tank(CommonTanks.BAT_CHAT_155_58, TankTypes.ARTY, 490, new Armor(50, 30, 20), 62, new AutoLoader(CommonCannons.CANNON_DE_155_MM, 36, 76.71, 6, 4), new Engine(CommonEngines.HISPANO_SUIZA_HS_110, TankTypes.ARTY), new Radio(CommonRadios.SCR_528F, TankTypes.ARTY), Tracks.BAT_CHAT_155_58, Turrets.NO_TURRET_ARTY);
    /** German Lights */
    public static final Tank SPAHPANZER_RU_251 = new Tank(CommonTanks.SPAHPANZER_RU_251, TankTypes.LIGHT, 500, new Armor(25, 20, 8), 80, new Cannon(CommonCannons._90_MM_RHEINMETALL_DM1, 41, 5.94), new Engine(CommonEngines.MB_837_EA_600, TankTypes.LIGHT), new Radio(CommonRadios.AN_GRC_7, TankTypes.LIGHT), Tracks.SPAHPANZER_RU_251_PT_II, Turrets.SPAHPANZER_RU_251_DREHTURM_360);
    /** German Mediums */
    public static final Tank E_50_M = new Tank(CommonTanks.E_50_M, TankTypes.MEDIUM, 2050, new Armor(150, 80, 80), 60, new Cannon(CommonCannons._10_5_CM_KWK_L52_AUSF_K, 48, 9.2), new Engine(CommonEngines.MAYBACH_HL_235, TankTypes.MEDIUM), new Radio(CommonRadios._10WSC, TankTypes.MEDIUM), Tracks.E_50_M, Turrets.E_50_M);
    public static final Tank LEOPARD_1 = new Tank(CommonTanks.LEOPARD_1, TankTypes.MEDIUM, 1950, new Armor(70, 35, 25), 65, new Cannon(CommonCannons._10_5_CM_BORDKANONE_L7A3, 60, 8.34), new Engine(CommonEngines.MTU_MB_838_CAM_500A, TankTypes.MEDIUM), new Radio(CommonRadios.SEM_25A, TankTypes.MEDIUM), Tracks.LEOPARD_1, Turrets.LEOPARD_1);
    /** German Heavies */
    public static final Tank E_100 = new Tank(CommonTanks.E_100, TankTypes.HEAVY, 2700, new Armor(200, 120, 150), 30, new Cannon(CommonCannons._15_CM_KWK_L38, 50, 19.18), new Engine(CommonEngines.MAYBACH_NEUES_PROJEKT, TankTypes.HEAVY), new Radio(CommonRadios._10WSC, TankTypes.HEAVY), Tracks.E_100, Turrets.E_100);
    public static final Tank MAUS = new Tank(CommonTanks.MAUS, TankTypes.HEAVY, 3000, new Armor(200, 185, 160), 20, new Cannon(CommonCannons._12_8_CM_KWK_44_L55, 68, 14.29), new Engine(CommonEngines.DB_603_A2, TankTypes.HEAVY), new Radio(CommonRadios._10WSC, TankTypes.HEAVY), Tracks.MAUS, Turrets.MAUS);
    public static final Tank VK_7201 = new Tank(CommonTanks.VK_7201, TankTypes.HEAVY, 2500, new Armor(200, 160, 120), 43, new Cannon("15 cm Kw.K L/38", 24, 21.09), new Engine(CommonEngines.MB_517, TankTypes.HEAVY), new Radio(CommonRadios._10WSC, TankTypes.HEAVY), Tracks.VK_7201, Turrets.VK_7201);
    /** German TDs */
    public static final Tank JAGDPANZER_E_100 = new Tank(CommonTanks.JAGDPANZER_E_100, TankTypes.TD, 2200, new Armor(200, 120, 150), 30, new Cannon(CommonCannons._17_CM_PAK, 24, 24.64), new Engine(CommonEngines.MAYBACH_NEUES_PROJEKT, TankTypes.TD), new Radio(CommonRadios._10WSC, TankTypes.TD), Tracks.JAGDPANZER_E_100, Turrets.NO_TURRET_TD);
    public static final Tank WAFFENTRAGER_E_100 = new Tank(CommonTanks.WAFFENTRAGER_E_100, TankTypes.TD, 2000, new Armor(200, 120, 150), 40, new AutoLoader(CommonCannons._15_CM_PAK_L38, 60, 57.94, 3, 4), new Engine(CommonEngines.MAYBACH_NEUES_PROJEKT, TankTypes.TD), new Radio(CommonRadios._10WSC, TankTypes.TD), Tracks.WAFFENTRAGER_E_100, Turrets.WAFFENTRAGER_E_100);
    /** German Arty */
    public static final Tank GW_E_100 = new Tank(CommonTanks.GW_E_100, TankTypes.ARTY, 550, new Armor(80, 50, 40), 40, new Cannon(CommonCannons._21_CM_MORSER_18_2, 30, 40.75), new Engine(CommonEngines.MAYBACH_HL_234, TankTypes.ARTY), new Radio(CommonRadios._10WSC, TankTypes.ARTY), Tracks.GW_E_100, Turrets.NO_TURRET_ARTY);
    /** Japanese Mediums */
    public static final Tank STB_1 = new Tank(CommonTanks.STB_1, TankTypes.MEDIUM, 1950, new Armor(110, 35, 25), 53, new Cannon(CommonCannons._105_MM_RIFLED_GUN, 50, 7.67), new Engine(CommonEngines.MITSUBISHI_10ZF21WT, TankTypes.MEDIUM), new Radio(CommonRadios.TYPE_67, TankTypes.MEDIUM), Tracks.STB_1, Turrets.STB_1);
    /** Japanese Heavies */
    public static final Tank TYPE_5_HEAVY = new Tank(CommonTanks.TYPE_5_HEAVY, TankTypes.HEAVY, 2800, new Armor(260, 140, 150), 25, new Cannon(CommonCannons._14_CM_50_3RD_YEAR_TYPE, 60, 16.97), new Engine(CommonEngines.KAWASAKI_TYPE_98, TankTypes.HEAVY), new Radio(CommonRadios.TYPE_3_KO, TankTypes.HEAVY), Tracks.TYPE_5_HEAVY, Turrets.TYPE_5_HEAVY);
    /** British Mediums */
    public static final Tank FV4202 = new Tank(CommonTanks.FV4202, TankTypes.MEDIUM, 1950, new Armor(121, 51, 32), 40, new Cannon(CommonCannons._105_MM_ROYAL_ORDNANCE_L7A1, 72, 8.25), new Engine(CommonEngines.ROLLS_ROYCE_METEOR_M120A, TankTypes.MEDIUM), new Radio(CommonRadios.SR_C45, TankTypes.MEDIUM), Tracks.FV4202, Turrets.FV4202);
    /** British Heavies */
    public static final Tank FV215B = new Tank(CommonTanks.FV215B, TankTypes.HEAVY, 2500, new Armor(152.4, 101.6, 76.2), 34, new Cannon(CommonCannons._120_MM_GUN_L1A1, 35, 8.34), new Engine(CommonEngines.ROLLS_ROYCE_GRIFFON, TankTypes.HEAVY), new Radio(CommonRadios.SR_C45, TankTypes.HEAVY), Tracks.FV215, Turrets.FV215);
    /** British TDs */
    public static final Tank FV215B_183 = new Tank(CommonTanks.FV215B_183, TankTypes.TD, 2000, new Armor(152.4, 50.8, 76.2), 34, new Cannon(CommonCannons._183_MM_L4, 12, 28.77), new Engine(CommonEngines.ROLLS_ROYCE_METEOR_MK_12, TankTypes.TD), new Radio(CommonRadios.SR_C45, TankTypes.TD), Tracks.FV215B, Turrets.FV215B);
    public static final Tank FV4005_STAGE_II = new Tank(CommonTanks.FV4005_STAGE_II, TankTypes.TD, 1850, new Armor(76.2, 50.8, 38.1), 35, new Cannon(CommonCannons._183_MM_L4, 12, 28.77), new Engine(CommonEngines.ROLLS_ROYCE_METEOR_MK_IVB, TankTypes.TD), new Radio(CommonRadios.SR_C45, TankTypes.TD), Tracks.FV4005_STAGE_II, Turrets.FV4005_STAGE_II);
    /** British Arty */
    public static final Tank CONQUEROR_GC = new Tank(CommonTanks.CONQUEROR_GC, TankTypes.ARTY, 530, new Armor(152.4, 50.8, 76.2), 34, new Cannon(CommonCannons.BL_9_2_IN_HOWITZER, 24, 55.71), new Engine(CommonEngines.ROLLS_ROYCE_METEOR_M120, TankTypes.ARTY), new Radio(CommonRadios.SR_C45, TankTypes.ARTY), Tracks.CONQUEROR_GC, Turrets.NO_TURRET_ARTY);
    /** American Lights */
    public static final Tank T49 = new Tank(CommonTanks.T49, TankTypes.LIGHT, 1000, new Armor(25.4, 25.4, 19.1), 72, new Cannon(CommonCannons._90_MM_GUN_T185, 22, 22.05), new Engine(CommonEngines.CONTINENTAL_AOI_895_5, TankTypes.LIGHT), new Radio(CommonRadios.AN_GRC_7, TankTypes.LIGHT), Tracks.XM551_TEST_BED, Turrets.XM551_TEST_BED);
    /** American Mediums */
    public static final Tank M48A1_PATTON = new Tank(CommonTanks.M48A1_Patton, TankTypes.MEDIUM, 2000, new Armor(152.4, 76.2, 38.1), 45, new Cannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new Engine(CommonEngines.CONTINENTAL_AV_1790_7B, TankTypes.MEDIUM), new Radio(CommonRadios.AN_VRC_7, TankTypes.MEDIUM), Tracks.T97E2, Turrets.M87);
    public static final Tank M60 = new Tank(CommonTanks.M60, TankTypes.MEDIUM, 2000, new Armor(93.2, 73.7, 25.4), 48, new Cannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new Engine(CommonEngines.CONTINENTAL_AVDS_1790_2, TankTypes.MEDIUM), new Radio(CommonRadios.AN_VRC_12, TankTypes.MEDIUM), Tracks.M60, Turrets.M60);
    /** American Heavies */
    public static final Tank T110E5 = new Tank(CommonTanks.T110E5, TankTypes.HEAVY, 2200, new Armor(254, 76.2, 38.1), 37, new Cannon(CommonCannons._120_MM_GUN_M58, 42, 9.59), new Engine(CommonEngines.CONTINENTAL_AOI_1490, TankTypes.HEAVY), new Radio(CommonRadios.AN_VRC_3, TankTypes.HEAVY), Tracks.T110E27, Turrets.T53);
    public static final Tank T57 = new Tank(CommonTanks.T57, TankTypes.HEAVY, 2250, new Armor(203, 50.8, 38.1), 35, new AutoLoader(CommonCannons._120_MM_GUN_T179, 36, 23.97, 2, 4), new Engine(CommonEngines.CONTINENTAL_AV_1790_5C, TankTypes.HEAVY), new Radio(CommonRadios.AN_VRC_3, TankTypes.HEAVY), Tracks.T97, Turrets.T169);
    /** American TDs */
    public static final Tank T110E3 = new Tank(CommonTanks.T110E3, TankTypes.TD, 2050, new Armor(305, 76, 38), 30, new Cannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 16.3), new Engine(CommonEngines.CONTINENTAL_AOI_1490, TankTypes.TD), new Radio(CommonRadios.AN_VRC_3, TankTypes.TD), Tracks.T110E3, Turrets.NO_TURRET_TD);
    public static final Tank T110E4 = new Tank(CommonTanks.T110E4, TankTypes.TD, 2000, new Armor(260, 76, 38), 35, new Cannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 18.7), new Engine(CommonEngines.CONTINENTAL_AOI_1490, TankTypes.TD), new Radio(CommonRadios.AN_VRC_3, TankTypes.TD), Tracks.T110E4, Turrets.T110E4);
    /** American Arty */
    public static final Tank T92 = new Tank(CommonTanks.T92, TankTypes.ARTY, 500, new Armor(25.4, 25.4, 21.7), 32, new Cannon(CommonCannons._240_MM_HOWITZER, 16, 50.82), new Engine(CommonEngines.FORD_GAF_T92, TankTypes.ARTY), new Radio(CommonRadios.SCR_528A, TankTypes.ARTY), Tracks.T92_T80E1, Turrets.NO_TURRET_ARTY);
    /** Russian Lights */
    public static final Tank T_54_LTWT = new Tank(CommonTanks.T_54_LTWT, TankTypes.LIGHT, 1250, new Armor(80, 60, 45), 69, new Cannon(CommonCannons._100_MM_D10T, 34, 7.19), new Engine(CommonEngines.V_14, TankTypes.LIGHT), new Radio(CommonRadios._10RT_26, TankTypes.LIGHT), Tracks.T_54_ENHANCED_LTWT_VERSION, Turrets.T_54_LTWT_VERSION);
    /** Russian Mediums */
    public static final Tank OBJECT_140 = new Tank(CommonTanks.OBJECT_140, TankTypes.MEDIUM, 1900, new Armor(100, 80, 55), 40, new Cannon(CommonCannons._100_MM_8TS, 50, 6.33), new Engine(CommonEngines.TD_12, TankTypes.MEDIUM), new Radio(CommonRadios.R_113M, TankTypes.MEDIUM), Tracks.OBJECT_140, Turrets.OBJECT_140);
    public static final Tank OBJECT_430 = new Tank(CommonTanks.OBJECT_430, TankTypes.MEDIUM, 1900, new Armor(120, 80, 40), 55, new Cannon(CommonCannons._100_MM_8TS, 50, 6.14), new Engine(CommonEngines._5TD, TankTypes.MEDIUM), new Radio(CommonRadios.R_113M, TankTypes.MEDIUM), Tracks.OBJECT_430, Turrets.OBJECT_430);
    public static final Tank OBJECT_907 = new Tank(CommonTanks.OBJECT_907, TankTypes.MEDIUM, 2000, new Armor(100, 85, 45), 55, new Cannon(CommonCannons._100_MM_D_54TS, 45, 6.23), new Engine(CommonEngines.V_12_5U, TankTypes.MEDIUM), new Radio(CommonRadios.R_123, TankTypes.MEDIUM), Tracks.OBJECT_907, Turrets.OBJECT_907);
    public static final Tank T_62A = new Tank(CommonTanks.T_62A, TankTypes.MEDIUM, 1950, new Armor(100, 80, 45), 50, new Cannon(CommonCannons._100_MM_8TS, 50, 6.33), new Engine(CommonEngines.V_55, TankTypes.MEDIUM), new Radio(CommonRadios.R_123, TankTypes.MEDIUM), Tracks.T_62A, Turrets.T_62A);
    /** Russian Heavies */
    public static final Tank IS_4 = new Tank(CommonTanks.IS_4, TankTypes.HEAVY, 2500, new Armor(140, 160, 100), 43, new Cannon(CommonCannons._122_MM_M62_T2, 30, 11.51), new Engine(CommonEngines.V_12, TankTypes.HEAVY), new Radio(CommonRadios._10RK_26, TankTypes.HEAVY), Tracks.IS_4M, Turrets.IS_4M);
    public static final Tank IS_7 = new Tank(CommonTanks.IS_7, TankTypes.HEAVY, 2150, new Armor(150, 150, 100), 60, new Cannon(CommonCannons._130_MM_S_70, 30, 13.14), new Engine(CommonEngines.M_50T, TankTypes.HEAVY), new Radio(CommonRadios._10RK_26, TankTypes.HEAVY), Tracks.IS_7, Turrets.IS_7);
    public static final Tank OBJECT_260 = new Tank(CommonTanks.OBJECT_260, TankTypes.HEAVY, 2100, new Armor(150, 150, 70), 60, new Cannon(CommonCannons._122_MM_BL_13_1, 30, 11.03), new Engine(CommonEngines._2X_V_16, TankTypes.HEAVY), new Radio(CommonRadios._10RK_26, TankTypes.HEAVY), Tracks.OBJECT_260_MOD_1945, Turrets.OBJECT_260_MOD_1945);
    /** Russian TDs */
    public static final Tank OBJECT_263 = new Tank(CommonTanks.OBJECT_263, TankTypes.TD, 1900, new Armor(250, 80, 50), 55, new Cannon(CommonCannons._130_MM_S_70A, 42, 10.26), new Engine(CommonEngines.M_50T, TankTypes.TD), new Radio(CommonRadios._10RK_26, TankTypes.TD), Tracks.OBJECT_263, Turrets.NO_TURRET_TD);
    public static final Tank OBJECT_268 = new Tank(CommonTanks.OBJECT_268, TankTypes.TD, 1950, new Armor(187, 100, 50), 48, new Cannon(CommonCannons._152_MM_M64, 35, 15.82), new Engine(CommonEngines.V_16FN, TankTypes.TD), new Radio(CommonRadios.R_113M, TankTypes.TD), Tracks.OBJECT_268, Turrets.NO_TURRET_TD);
    /** Russian Arty */
    public static final Tank OBJECT_261 = new Tank(CommonTanks.OBJECT_261, TankTypes.ARTY, 510, new Armor(75, 50, 30), 50, new Cannon(CommonCannons._180_MM_B_1_P, 18, 33.56), new Engine(CommonEngines.M_50T, TankTypes.ARTY), new Radio(CommonRadios._10RK_26, TankTypes.ARTY), Tracks.OBJECT_261, Turrets.NO_TURRET_ARTY);

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
            T110E5, T57,
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
