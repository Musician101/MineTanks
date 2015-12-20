package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.CommonReference.CommonCannons;
import musician101.minetanks.common.CommonReference.CommonEngines;
import musician101.minetanks.common.CommonReference.CommonRadios;
import musician101.minetanks.common.CommonReference.CommonTanks;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.spigot.tank.modules.SpigotEngine;
import musician101.minetanks.spigot.tank.modules.SpigotRadio;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import musician101.minetanks.spigot.tank.modules.tracks.SpigotTracks;
import musician101.minetanks.spigot.tank.modules.turret.SpigotTurrets;

import java.util.Arrays;
import java.util.List;

public class SpigotTanks
{
    /** China Lights */
    public static final SpigotTank WZ_132 = new SpigotTank(CommonTanks.WZ_132, SpigotTankTypes.LIGHT, 1150, new Armor(50, 25, 20), 64, new SpigotCannon(CommonCannons._100_MM_60_100T, 43, 7.57), new SpigotEngine(CommonEngines._8V135, SpigotTankTypes.LIGHT), new SpigotRadio(CommonRadios.A_220A, SpigotTankTypes.LIGHT), SpigotTracks.WZ_132, SpigotTurrets.WZ_132);
    /** China Mediums */
    public static final SpigotTank _121 = new SpigotTank(CommonTanks._121, SpigotTankTypes.MEDIUM, 1950, new Armor(120, 80, 60), 56, new SpigotCannon(CommonCannons._122_MM_60_122T, 40, 9.6), new SpigotEngine(CommonEngines._12150L7, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.A_220B, SpigotTankTypes.MEDIUM), SpigotTracks._121, SpigotTurrets._121);
    /** China Heavies */
    public static final SpigotTank _113 = new SpigotTank(CommonTanks._113, SpigotTankTypes.HEAVY, 2300, new Armor(120, 90, 70), 50, new SpigotCannon(CommonCannons._122_MM_60_122T, 34, 10.45), new SpigotEngine(CommonEngines.V_2_54SC, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios.A_220B, SpigotTankTypes.HEAVY), SpigotTracks._113, SpigotTurrets._113);
    /** France Lights */
    public static final SpigotTank AMX_13_90 = new SpigotTank(CommonTanks.AMX_13_90, SpigotTankTypes.LIGHT, 1100, new Armor(40, 20, 15), 64, new SpigotAutoLoader(CommonCannons._90_MM_F3, 36, 42, 2.73, 6), new SpigotEngine(CommonEngines.DETROIT_DIESEL_6V53T_S, SpigotTankTypes.LIGHT), new SpigotRadio(CommonRadios.SCR_528, SpigotTankTypes.LIGHT), SpigotTracks.AMX_13_TYPE_2D, SpigotTurrets.FL_10);
    /** French Mediums */
    public static final SpigotTank BAT_CHAT_25_T = new SpigotTank(CommonTanks.BAT_CHAT_25_T, SpigotTankTypes.MEDIUM, 1800, new Armor(60, 40, 30), 65, new SpigotAutoLoader(CommonCannons._105_MM_MLE_57, 30, 40, 2.73, 5), new SpigotEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.SCR_528F, SpigotTankTypes.MEDIUM), SpigotTracks.BAT_CHAT_25_T, SpigotTurrets.BAT_CHAT_25_T);
    public static final SpigotTank AMX_30_B = new SpigotTank(CommonTanks.AMX_30_B, SpigotTankTypes.MEDIUM, 1900, new Armor(80, 35, 30), 65, new SpigotCannon(CommonCannons._105_MM_MLE_F1, 50, 8.53), new SpigotEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.TRVP_13_A, SpigotTankTypes.MEDIUM), SpigotTracks.AMX_30_B, SpigotTurrets.AMX_30_B);
    /** French Heavies */
    public static final SpigotTank AMX_50_B = new SpigotTank(CommonTanks.AMX_50_B, SpigotTankTypes.HEAVY, 2100, new Armor(170, 30, 30), 65, new SpigotAutoLoader(CommonCannons._120_MM_SA46, 56, 30, 2.5, 4), new SpigotEngine(CommonEngines.MAYBACH_HL_295_F, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios.SCR_619, SpigotTankTypes.HEAVY), SpigotTracks.AMX_50_B, SpigotTurrets.AMX_50_B);
    /** French TDs */
    public static final SpigotTank AMX_50_FOCH_155 = new SpigotTank(CommonTanks.AMX_50_FOCH_155, SpigotTankTypes.TD, 1850, new Armor(180, 40, 40), 50, new SpigotAutoLoader(CommonCannons._155_MM_AC_SA58, 30, 47.94, 5, 3), new SpigotEngine(CommonEngines.SAURER, SpigotTankTypes.TD), new SpigotRadio(CommonRadios.SCR_619, SpigotTankTypes.TD), SpigotTracks.AMX_50_FOCH_155, SpigotTurrets.NO_TURRET_TD);
    /** French Arty */
    public static final SpigotTank BAT_CHAT_155_58 = new SpigotTank(CommonTanks.BAT_CHAT_155_58, SpigotTankTypes.ARTY, 490, new Armor(50, 30, 20), 62, new SpigotAutoLoader(CommonCannons.CANNON_DE_155_MM, 36, 76.71, 6, 4), new SpigotEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpigotTankTypes.ARTY), new SpigotRadio(CommonRadios.SCR_528F, SpigotTankTypes.ARTY), SpigotTracks.BAT_CHAT_155_58, SpigotTurrets.NO_TURRET_ARTY);
    /** German Lights */
    public static final SpigotTank SPAHPANZER_RU_251 = new SpigotTank(CommonTanks.SPAHPANZER_RU_251, SpigotTankTypes.LIGHT, 500, new Armor(25, 20, 8), 80, new SpigotCannon(CommonCannons._90_MM_RHEINMETALL_DM1, 41, 5.94), new SpigotEngine(CommonEngines.MB_837_EA_600, SpigotTankTypes.LIGHT), new SpigotRadio(CommonRadios.AN_GRC_7, SpigotTankTypes.LIGHT), SpigotTracks.SPAHPANZER_RU_251_PT_II, SpigotTurrets.SPAHPANZER_RU_251_DREHTURM_360);
    /** German Mediums */
    public static final SpigotTank E_50_M = new SpigotTank(CommonTanks.E_50_M, SpigotTankTypes.MEDIUM, 2050, new Armor(150, 80, 80), 60, new SpigotCannon(CommonCannons._10_5_CM_KWK_L52_AUSF_K, 48, 9.2), new SpigotEngine(CommonEngines.MAYBACH_HL_235, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios._10WSC, SpigotTankTypes.MEDIUM), SpigotTracks.E_50_M, SpigotTurrets.E_50_M);
    public static final SpigotTank LEOPARD_1 = new SpigotTank(CommonTanks.LEOPARD_1, SpigotTankTypes.MEDIUM, 1950, new Armor(70, 35, 25), 65, new SpigotCannon(CommonCannons._10_5_CM_BORDKANONE_L7A3, 60, 8.34), new SpigotEngine(CommonEngines.MTU_MB_838_CAM_500A, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.SEM_25A, SpigotTankTypes.MEDIUM), SpigotTracks.LEOPARD_1, SpigotTurrets.LEOPARD_1);
    /** German Heavies */
    public static final SpigotTank E_100 = new SpigotTank(CommonTanks.E_100, SpigotTankTypes.HEAVY, 2700, new Armor(200, 120, 150), 30, new SpigotCannon(CommonCannons._15_CM_KWK_L38, 50, 19.18), new SpigotEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios._10WSC, SpigotTankTypes.HEAVY), SpigotTracks.E_100, SpigotTurrets.E_100);
    public static final SpigotTank MAUS = new SpigotTank(CommonTanks.MAUS, SpigotTankTypes.HEAVY, 3000, new Armor(200, 185, 160), 20, new SpigotCannon(CommonCannons._12_8_CM_KWK_44_L55, 68, 14.29), new SpigotEngine(CommonEngines.DB_603_A2, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios._10WSC, SpigotTankTypes.HEAVY), SpigotTracks.MAUS, SpigotTurrets.MAUS);
    public static final SpigotTank VK_7201 = new SpigotTank(CommonTanks.VK_7201, SpigotTankTypes.HEAVY, 2500, new Armor(200, 160, 120), 43, new SpigotCannon("15 cm Kw.K L/38", 24, 21.09), new SpigotEngine(CommonEngines.MB_517, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios._10WSC, SpigotTankTypes.HEAVY), SpigotTracks.VK_7201, SpigotTurrets.VK_7201);
    /** German TDs */
    public static final SpigotTank JAGDPANZER_E_100 = new SpigotTank(CommonTanks.JAGDPANZER_E_100, SpigotTankTypes.TD, 2200, new Armor(200, 120, 150), 30, new SpigotCannon(CommonCannons._17_CM_PAK, 24, 24.64), new SpigotEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpigotTankTypes.TD), new SpigotRadio(CommonRadios._10WSC, SpigotTankTypes.TD), SpigotTracks.JAGDPANZER_E_100, SpigotTurrets.NO_TURRET_TD);
    public static final SpigotTank WAFFENTRAGER_E_100 = new SpigotTank(CommonTanks.WAFFENTRAGER_E_100, SpigotTankTypes.TD, 2000, new Armor(200, 120, 150), 40, new SpigotAutoLoader(CommonCannons._15_CM_PAK_L38, 60, 57.94, 3, 4), new SpigotEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpigotTankTypes.TD), new SpigotRadio(CommonRadios._10WSC, SpigotTankTypes.TD), SpigotTracks.WAFFENTRAGER_E_100, SpigotTurrets.WAFFENTRAGER_E_100);
    /** German Arty */
    public static final SpigotTank GW_E_100 = new SpigotTank(CommonTanks.GW_E_100, SpigotTankTypes.ARTY, 550, new Armor(80, 50, 40), 40, new SpigotCannon(CommonCannons._21_CM_MORSER_18_2, 30, 40.75), new SpigotEngine(CommonEngines.MAYBACH_HL_234, SpigotTankTypes.ARTY), new SpigotRadio(CommonRadios._10WSC, SpigotTankTypes.ARTY), SpigotTracks.GW_E_100, SpigotTurrets.NO_TURRET_ARTY);
    /** Japanese Mediums */
    public static final SpigotTank STB_1 = new SpigotTank(CommonTanks.STB_1, SpigotTankTypes.MEDIUM, 1950, new Armor(110, 35, 25), 53, new SpigotCannon(CommonCannons._105_MM_RIFLED_GUN, 50, 7.67), new SpigotEngine(CommonEngines.MITSUBISHI_10ZF21WT, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.TYPE_67, SpigotTankTypes.MEDIUM), SpigotTracks.STB_1, SpigotTurrets.STB_1);
    /** Japanese Heavies */
    public static final SpigotTank TYPE_5_HEAVY = new SpigotTank(CommonTanks.TYPE_5_HEAVY, SpigotTankTypes.HEAVY, 2800, new Armor(260, 140, 150), 25, new SpigotCannon(CommonCannons._14_CM_50_3RD_YEAR_TYPE, 60, 16.97), new SpigotEngine(CommonEngines.KAWASAKI_TYPE_98, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios.TYPE_3_KO, SpigotTankTypes.HEAVY), SpigotTracks.TYPE_5_HEAVY, SpigotTurrets.TYPE_5_HEAVY);
    /** British Mediums */
    public static final SpigotTank FV4202 = new SpigotTank(CommonTanks.FV4202, SpigotTankTypes.MEDIUM, 1950, new Armor(121, 51, 32), 40, new SpigotCannon(CommonCannons._105_MM_ROYAL_ORDNANCE_L7A1, 72, 8.25), new SpigotEngine(CommonEngines.ROLLS_ROYCE_METEOR_M120A, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.MEDIUM), SpigotTracks.FV4202, SpigotTurrets.FV4202);
    /** British Heavies */
    public static final SpigotTank FV215B = new SpigotTank(CommonTanks.FV215B, SpigotTankTypes.HEAVY, 2500, new Armor(152.4, 101.6, 76.2), 34, new SpigotCannon(CommonCannons._120_MM_GUN_L1A1, 35, 8.34), new SpigotEngine(CommonEngines.ROLLS_ROYCE_GRIFFON, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.HEAVY), SpigotTracks.FV215, SpigotTurrets.FV215);
    /** British TDs */
    public static final SpigotTank FV215B_183 = new SpigotTank(CommonTanks.FV215B_183, SpigotTankTypes.TD, 2000, new Armor(152.4, 50.8, 76.2), 34, new SpigotCannon(CommonCannons._183_MM_L4, 12, 28.77), new SpigotEngine(CommonEngines.ROLLS_ROYCE_METEOR_MK_12, SpigotTankTypes.TD), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.TD), SpigotTracks.FV215B, SpigotTurrets.FV215B);
    public static final SpigotTank FV4005_STAGE_II = new SpigotTank(CommonTanks.FV4005_STAGE_II, SpigotTankTypes.TD, 1850, new Armor(76.2, 50.8, 38.1), 35, new SpigotCannon(CommonCannons._183_MM_L4, 12, 28.77), new SpigotEngine(CommonEngines.ROLLS_ROYCE_METEOR_MK_IVB, SpigotTankTypes.TD), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.TD), SpigotTracks.FV4005_STAGE_II, SpigotTurrets.FV4005_STAGE_II);
    /** British Arty */
    public static final SpigotTank CONQUEROR_GC = new SpigotTank(CommonTanks.CONQUEROR_GC, SpigotTankTypes.ARTY, 530, new Armor(152.4, 50.8, 76.2), 34, new SpigotCannon(CommonCannons.BL_9_2_IN_HOWITZER, 24, 55.71), new SpigotEngine(CommonEngines.ROLLS_ROYCE_METEOR_M120, SpigotTankTypes.ARTY), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.ARTY), SpigotTracks.CONQUEROR_GC, SpigotTurrets.NO_TURRET_ARTY);
    /** American Lights */
    public static final SpigotTank T49 = new SpigotTank(CommonTanks.T49, SpigotTankTypes.LIGHT, 1000, new Armor(25.4, 25.4, 19.1), 72, new SpigotCannon(CommonCannons._90_MM_GUN_T185, 22, 22.05), new SpigotEngine(CommonEngines.CONTINENTAL_AOI_895_5, SpigotTankTypes.LIGHT), new SpigotRadio(CommonRadios.AN_GRC_7, SpigotTankTypes.LIGHT), SpigotTracks.XM551_TEST_BED, SpigotTurrets.XM551_TEST_BED);
    /** American Mediums */
    public static final SpigotTank M48A1_PATTON = new SpigotTank(CommonTanks.M48A1_Patton, SpigotTankTypes.MEDIUM, 2000, new Armor(152.4, 76.2, 38.1), 45, new SpigotCannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new SpigotEngine(CommonEngines.CONTINENTAL_AV_1790_7B, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.AN_VRC_7, SpigotTankTypes.MEDIUM), SpigotTracks.T97E2, SpigotTurrets.M87);
    public static final SpigotTank M60 = new SpigotTank(CommonTanks.M60, SpigotTankTypes.MEDIUM, 2000, new Armor(93.2, 73.7, 25.4), 48, new SpigotCannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new SpigotEngine(CommonEngines.CONTINENTAL_AVDS_1790_2, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.AN_VRC_12, SpigotTankTypes.MEDIUM), SpigotTracks.M60, SpigotTurrets.M60);
    /** American Heavies */
    public static final SpigotTank T110E5 = new SpigotTank(CommonTanks.T110E5, SpigotTankTypes.HEAVY, 2200, new Armor(254, 76.2, 38.1), 37, new SpigotCannon(CommonCannons._120_MM_GUN_M58, 42, 9.59), new SpigotEngine(CommonEngines.CONTINENTAL_AOI_1490, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios.AN_VRC_3, SpigotTankTypes.HEAVY), SpigotTracks.T110E27, SpigotTurrets.T53);
    public static final SpigotTank T57 = new SpigotTank(CommonTanks.T57, SpigotTankTypes.HEAVY, 2250, new Armor(203, 50.8, 38.1), 35, new SpigotAutoLoader(CommonCannons._120_MM_GUN_T179, 36, 23.97, 2, 4), new SpigotEngine(CommonEngines.CONTINENTAL_AV_1790_5C, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios.AN_VRC_3, SpigotTankTypes.HEAVY), SpigotTracks.T97, SpigotTurrets.T169);
    /** American TDs */
    public static final SpigotTank T110E3 = new SpigotTank(CommonTanks.T110E3, SpigotTankTypes.TD, 2050, new Armor(305, 76, 38), 30, new SpigotCannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 16.3), new SpigotEngine(CommonEngines.CONTINENTAL_AOI_1490, SpigotTankTypes.TD), new SpigotRadio(CommonRadios.AN_VRC_3, SpigotTankTypes.TD), SpigotTracks.T110E3, SpigotTurrets.NO_TURRET_TD);
    public static final SpigotTank T110E4 = new SpigotTank(CommonTanks.T110E4, SpigotTankTypes.TD, 2000, new Armor(260, 76, 38), 35, new SpigotCannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 18.7), new SpigotEngine(CommonEngines.CONTINENTAL_AOI_1490, SpigotTankTypes.TD), new SpigotRadio(CommonRadios.AN_VRC_3, SpigotTankTypes.TD), SpigotTracks.T110E4, SpigotTurrets.T110E4);
    /** American Arty */
    public static final SpigotTank T92 = new SpigotTank(CommonTanks.T92, SpigotTankTypes.ARTY, 500, new Armor(25.4, 25.4, 21.7), 32, new SpigotCannon(CommonCannons._240_MM_HOWITZER, 16, 50.82), new SpigotEngine(CommonEngines.FORD_GAF_T92, SpigotTankTypes.ARTY), new SpigotRadio(CommonRadios.SCR_528A, SpigotTankTypes.ARTY), SpigotTracks.T92_T80E1, SpigotTurrets.NO_TURRET_ARTY);
    /** Russian Lights */
    public static final SpigotTank T_54_LTWT = new SpigotTank(CommonTanks.T_54_LTWT, SpigotTankTypes.LIGHT, 1250, new Armor(80, 60, 45), 69, new SpigotCannon(CommonCannons._100_MM_D10T, 34, 7.19), new SpigotEngine(CommonEngines.V_14, SpigotTankTypes.LIGHT), new SpigotRadio(CommonRadios._10RT_26, SpigotTankTypes.LIGHT), SpigotTracks.T_54_ENHANCED_LTWT_VERSION, SpigotTurrets.T_54_LTWT_VERSION);
    /** Russian Mediums */
    public static final SpigotTank OBJECT_140 = new SpigotTank(CommonTanks.OBJECT_140, SpigotTankTypes.MEDIUM, 1900, new Armor(100, 80, 55), 40, new SpigotCannon(CommonCannons._100_MM_8TS, 50, 6.33), new SpigotEngine(CommonEngines.TD_12, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.R_113M, SpigotTankTypes.MEDIUM), SpigotTracks.OBJECT_140, SpigotTurrets.OBJECT_140);
    public static final SpigotTank OBJECT_430 = new SpigotTank(CommonTanks.OBJECT_430, SpigotTankTypes.MEDIUM, 1900, new Armor(120, 80, 40), 55, new SpigotCannon(CommonCannons._100_MM_8TS, 50, 6.14), new SpigotEngine(CommonEngines._5TD, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.R_113M, SpigotTankTypes.MEDIUM), SpigotTracks.OBJECT_430, SpigotTurrets.OBJECT_430);
    public static final SpigotTank OBJECT_907 = new SpigotTank(CommonTanks.OBJECT_907, SpigotTankTypes.MEDIUM, 2000, new Armor(100, 85, 45), 55, new SpigotCannon(CommonCannons._100_MM_D_54TS, 45, 6.23), new SpigotEngine(CommonEngines.V_12_5U, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.R_123, SpigotTankTypes.MEDIUM), SpigotTracks.OBJECT_907, SpigotTurrets.OBJECT_907);
    public static final SpigotTank T_62A = new SpigotTank(CommonTanks.T_62A, SpigotTankTypes.MEDIUM, 1950, new Armor(100, 80, 45), 50, new SpigotCannon(CommonCannons._100_MM_8TS, 50, 6.33), new SpigotEngine(CommonEngines.V_55, SpigotTankTypes.MEDIUM), new SpigotRadio(CommonRadios.R_123, SpigotTankTypes.MEDIUM), SpigotTracks.T_62A, SpigotTurrets.T_62A);
    /** Russian Heavies */
    public static final SpigotTank IS_4 = new SpigotTank(CommonTanks.IS_4, SpigotTankTypes.HEAVY, 2500, new Armor(140, 160, 100), 43, new SpigotCannon(CommonCannons._122_MM_M62_T2, 30, 11.51), new SpigotEngine(CommonEngines.V_12, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios._10RK_26, SpigotTankTypes.HEAVY), SpigotTracks.IS_4M, SpigotTurrets.IS_4M);
    public static final SpigotTank IS_7 = new SpigotTank(CommonTanks.IS_7, SpigotTankTypes.HEAVY, 2150, new Armor(150, 150, 100), 60, new SpigotCannon(CommonCannons._130_MM_S_70, 30, 13.14), new SpigotEngine(CommonEngines.M_50T, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios._10RK_26, SpigotTankTypes.HEAVY), SpigotTracks.IS_7, SpigotTurrets.IS_7);
    public static final SpigotTank OBJECT_260 = new SpigotTank(CommonTanks.OBJECT_260, SpigotTankTypes.HEAVY, 2100, new Armor(150, 150, 70), 60, new SpigotCannon(CommonCannons._122_MM_BL_13_1, 30, 11.03), new SpigotEngine(CommonEngines._2X_V_16, SpigotTankTypes.HEAVY), new SpigotRadio(CommonRadios._10RK_26, SpigotTankTypes.HEAVY), SpigotTracks.OBJECT_260_MOD_1945, SpigotTurrets.OBJECT_260_MOD_1945);
    /** Russian TDs */
    public static final SpigotTank OBJECT_263 = new SpigotTank(CommonTanks.OBJECT_263, SpigotTankTypes.TD, 1900, new Armor(250, 80, 50), 55, new SpigotCannon(CommonCannons._130_MM_S_70A, 42, 10.26), new SpigotEngine(CommonEngines.M_50T, SpigotTankTypes.TD), new SpigotRadio(CommonRadios._10RK_26, SpigotTankTypes.TD), SpigotTracks.OBJECT_263, SpigotTurrets.NO_TURRET_TD);
    public static final SpigotTank OBJECT_268 = new SpigotTank(CommonTanks.OBJECT_268, SpigotTankTypes.TD, 1950, new Armor(187, 100, 50), 48, new SpigotCannon(CommonCannons._152_MM_M64, 35, 15.82), new SpigotEngine(CommonEngines.V_16FN, SpigotTankTypes.TD), new SpigotRadio(CommonRadios.R_113M, SpigotTankTypes.TD), SpigotTracks.OBJECT_268, SpigotTurrets.NO_TURRET_TD);
    /** Russian Arty */
    public static final SpigotTank OBJECT_261 = new SpigotTank(CommonTanks.OBJECT_261, SpigotTankTypes.ARTY, 510, new Armor(75, 50, 30), 50, new SpigotCannon(CommonCannons._180_MM_B_1_P, 18, 33.56), new SpigotEngine(CommonEngines.M_50T, SpigotTankTypes.ARTY), new SpigotRadio(CommonRadios._10RK_26, SpigotTankTypes.ARTY), SpigotTracks.OBJECT_261, SpigotTurrets.NO_TURRET_ARTY);

    public static final List<SpigotTank> tankList = Arrays.asList(
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
