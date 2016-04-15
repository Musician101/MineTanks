package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.CommonReference.CommonCannons;
import musician101.minetanks.common.CommonReference.CommonEngines;
import musician101.minetanks.common.CommonReference.CommonRadios;
import musician101.minetanks.common.CommonReference.CommonTanks;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.sponge.tank.module.SpongeEngine;
import musician101.minetanks.sponge.tank.module.SpongeRadio;
import musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import musician101.minetanks.sponge.tank.module.tracks.SpongeTracks;
import musician101.minetanks.sponge.tank.module.turret.SpongeTurrets;

import java.util.Arrays;
import java.util.List;

public class SpongeTanks
{
    /** China Lights */
    public static final SpongeTank WZ_132 = new SpongeTank(CommonTanks.WZ_132, SpongeCountries.CHINA, SpongeTankTypes.LIGHT, 1150, new Armor(50, 25, 20), 64, new SpongeCannon(CommonCannons._100_MM_60_100T, 43, 7.57), new SpongeEngine(CommonEngines._8V135, SpongeTankTypes.LIGHT), new SpongeRadio(CommonRadios.A_220A, SpongeTankTypes.LIGHT), SpongeTracks.WZ_132, SpongeTurrets.WZ_132);
    /** China Mediums */
    public static final SpongeTank _121 = new SpongeTank(CommonTanks._121, SpongeCountries.CHINA, SpongeTankTypes.MEDIUM, 1950, new Armor(120, 80, 60), 56, new SpongeCannon(CommonCannons._122_MM_60_122T, 40, 9.6), new SpongeEngine(CommonEngines._12150L7, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.A_220B, SpongeTankTypes.MEDIUM), SpongeTracks._121, SpongeTurrets._121);
    /** China Heavies */
    public static final SpongeTank _113 = new SpongeTank(CommonTanks._113, SpongeCountries.CHINA, SpongeTankTypes.HEAVY, 2300, new Armor(120, 90, 70), 50, new SpongeCannon(CommonCannons._122_MM_60_122T, 34, 10.45), new SpongeEngine(CommonEngines.V_2_54SC, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios.A_220B, SpongeTankTypes.HEAVY), SpongeTracks._113, SpongeTurrets._113);
    /** Czechoslovakian Mediums */
    public static final SpongeTank TVP_T_50_51 = new SpongeTank(CommonTanks.TVP_T_50_51, SpongeCountries.CZECH, SpongeTankTypes.MEDIUM, 1800, new Armor(65, 40, 30), 60, new SpongeAutoLoader(CommonCannons._100_MM_AK1, 48, 23.97, 1.5, 4), new SpongeEngine(CommonEngines.PRAGA_AXK, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.RADIOSTANICE_RM_31T, SpongeTankTypes.MEDIUM), SpongeTracks.TVP_T_50_51, SpongeTurrets.TVP_T_50_51);
    /** France Lights */
    public static final SpongeTank AMX_13_90 = new SpongeTank(CommonTanks.AMX_13_90, SpongeCountries.FRANCE, SpongeTankTypes.LIGHT, 1100, new Armor(40, 20, 15), 64, new SpongeAutoLoader(CommonCannons._90_MM_F3, 36, 42, 2.73, 6), new SpongeEngine(CommonEngines.DETROIT_DIESEL_6V53T_S, SpongeTankTypes.LIGHT), new SpongeRadio(CommonRadios.SCR_528, SpongeTankTypes.LIGHT), SpongeTracks.AMX_13_TYPE_2D, SpongeTurrets.FL_10);
    /** French Mediums */
    public static final SpongeTank BAT_CHAT_25_T = new SpongeTank(CommonTanks.BAT_CHAT_25_T, SpongeCountries.FRANCE, SpongeTankTypes.MEDIUM, 1800, new Armor(60, 40, 30), 65, new SpongeAutoLoader(CommonCannons._105_MM_MLE_57, 30, 40, 2.73, 5), new SpongeEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.SCR_528F, SpongeTankTypes.MEDIUM), SpongeTracks.BAT_CHAT_25_T, SpongeTurrets.BAT_CHAT_25_T);
    public static final SpongeTank AMX_30_B = new SpongeTank(CommonTanks.AMX_30_B, SpongeCountries.FRANCE, SpongeTankTypes.MEDIUM, 1900, new Armor(80, 35, 30), 65, new SpongeCannon(CommonCannons._105_MM_MLE_F1, 50, 8.53), new SpongeEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.TRVP_13_A, SpongeTankTypes.MEDIUM), SpongeTracks.AMX_30_B, SpongeTurrets.AMX_30_B);
    /** French Heavies */
    public static final SpongeTank AMX_50_B = new SpongeTank(CommonTanks.AMX_50_B, SpongeCountries.FRANCE, SpongeTankTypes.HEAVY, 2100, new Armor(170, 30, 30), 65, new SpongeAutoLoader(CommonCannons._120_MM_SA46, 56, 30, 2.5, 4), new SpongeEngine(CommonEngines.MAYBACH_HL_295_F, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios.SCR_619, SpongeTankTypes.HEAVY), SpongeTracks.AMX_50_B, SpongeTurrets.AMX_50_B);
    /** French TDs */
    public static final SpongeTank AMX_50_FOCH_155 = new SpongeTank(CommonTanks.AMX_50_FOCH_155, SpongeCountries.FRANCE, SpongeTankTypes.TD, 1850, new Armor(180, 40, 40), 50, new SpongeAutoLoader(CommonCannons._155_MM_AC_SA58, 30, 47.94, 5, 3), new SpongeEngine(CommonEngines.SAURER, SpongeTankTypes.TD), new SpongeRadio(CommonRadios.SCR_619, SpongeTankTypes.TD), SpongeTracks.AMX_50_FOCH_155, SpongeTurrets.NO_TURRET_TD);
    /** French Arty */
    public static final SpongeTank BAT_CHAT_155_58 = new SpongeTank(CommonTanks.BAT_CHAT_155_58, SpongeCountries.FRANCE, SpongeTankTypes.ARTY, 490, new Armor(50, 30, 20), 62, new SpongeAutoLoader(CommonCannons.CANNON_DE_155_MM, 36, 76.71, 6, 4), new SpongeEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpongeTankTypes.ARTY), new SpongeRadio(CommonRadios.SCR_528F, SpongeTankTypes.ARTY), SpongeTracks.BAT_CHAT_155_58, SpongeTurrets.NO_TURRET_ARTY);
    /** German Lights */
    public static final SpongeTank SPAHPANZER_RU_251 = new SpongeTank(CommonTanks.SPAHPANZER_RU_251, SpongeCountries.GERMANY, SpongeTankTypes.LIGHT, 500, new Armor(25, 20, 8), 80, new SpongeCannon(CommonCannons._90_MM_RHEINMETALL_DM1, 41, 5.94), new SpongeEngine(CommonEngines.MB_837_EA_600, SpongeTankTypes.LIGHT), new SpongeRadio(CommonRadios.AN_GRC_7, SpongeTankTypes.LIGHT), SpongeTracks.SPAHPANZER_RU_251_PT_II, SpongeTurrets.SPAHPANZER_RU_251_DREHTURM_360);
    /** German Mediums */
    public static final SpongeTank E_50_M = new SpongeTank(CommonTanks.E_50_M, SpongeCountries.GERMANY, SpongeTankTypes.MEDIUM, 2050, new Armor(150, 80, 80), 60, new SpongeCannon(CommonCannons._10_5_CM_KWK_L52_AUSF_K, 48, 9.2), new SpongeEngine(CommonEngines.MAYBACH_HL_235, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios._10WSC, SpongeTankTypes.MEDIUM), SpongeTracks.E_50_M, SpongeTurrets.E_50_M);
    public static final SpongeTank LEOPARD_1 = new SpongeTank(CommonTanks.LEOPARD_1, SpongeCountries.GERMANY, SpongeTankTypes.MEDIUM, 1950, new Armor(70, 35, 25), 65, new SpongeCannon(CommonCannons._10_5_CM_BORDKANONE_L7A3, 60, 8.34), new SpongeEngine(CommonEngines.MTU_MB_838_CAM_500A, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.SEM_25A, SpongeTankTypes.MEDIUM), SpongeTracks.LEOPARD_1, SpongeTurrets.LEOPARD_1);
    /** German Heavies */
    public static final SpongeTank E_100 = new SpongeTank(CommonTanks.E_100, SpongeCountries.GERMANY, SpongeTankTypes.HEAVY, 2700, new Armor(200, 120, 150), 30, new SpongeCannon(CommonCannons._15_CM_KWK_L38, 50, 19.18), new SpongeEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios._10WSC, SpongeTankTypes.HEAVY), SpongeTracks.E_100, SpongeTurrets.E_100);
    public static final SpongeTank MAUS = new SpongeTank(CommonTanks.MAUS, SpongeCountries.GERMANY, SpongeTankTypes.HEAVY, 3000, new Armor(200, 185, 160), 20, new SpongeCannon(CommonCannons._12_8_CM_KWK_44_L55, 68, 14.29), new SpongeEngine(CommonEngines.DB_603_A2, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios._10WSC, SpongeTankTypes.HEAVY), SpongeTracks.MAUS, SpongeTurrets.MAUS);
    public static final SpongeTank VK_7201 = new SpongeTank(CommonTanks.VK_7201, SpongeCountries.GERMANY, SpongeTankTypes.HEAVY, 2500, new Armor(200, 160, 120), 43, new SpongeCannon(CommonCannons._15_CM_KWK_L38, 24, 21.09), new SpongeEngine(CommonEngines.MB_517, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios._10WSC, SpongeTankTypes.HEAVY), SpongeTracks.VK_7201, SpongeTurrets.VK_7201);
    /** German TDs */
    public static final SpongeTank JAGDPANZER_E_100 = new SpongeTank(CommonTanks.JAGDPANZER_E_100, SpongeCountries.GERMANY, SpongeTankTypes.TD, 2200, new Armor(200, 120, 150), 30, new SpongeCannon(CommonCannons._17_CM_PAK, 24, 24.64), new SpongeEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpongeTankTypes.TD), new SpongeRadio(CommonRadios._10WSC, SpongeTankTypes.TD), SpongeTracks.JAGDPANZER_E_100, SpongeTurrets.NO_TURRET_TD);
    public static final SpongeTank WAFFENTRAGER_E_100 = new SpongeTank(CommonTanks.WAFFENTRAGER_E_100, SpongeCountries.GERMANY, SpongeTankTypes.TD, 2000, new Armor(200, 120, 150), 40, new SpongeAutoLoader(CommonCannons._15_CM_PAK_L38, 60, 57.94, 3, 4), new SpongeEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpongeTankTypes.TD), new SpongeRadio(CommonRadios._10WSC, SpongeTankTypes.TD), SpongeTracks.WAFFENTRAGER_E_100, SpongeTurrets.WAFFENTRAGER_E_100);
    /** German Arty */
    public static final SpongeTank GW_E_100 = new SpongeTank(CommonTanks.GW_E_100, SpongeCountries.GERMANY, SpongeTankTypes.ARTY, 550, new Armor(80, 50, 40), 40, new SpongeCannon(CommonCannons._21_CM_MORSER_18_2, 30, 40.75), new SpongeEngine(CommonEngines.MAYBACH_HL_234, SpongeTankTypes.ARTY), new SpongeRadio(CommonRadios._10WSC, SpongeTankTypes.ARTY), SpongeTracks.GW_E_100, SpongeTurrets.NO_TURRET_ARTY);
    /** Japanese Mediums */
    public static final SpongeTank STB_1 = new SpongeTank(CommonTanks.STB_1, SpongeCountries.JAPAN, SpongeTankTypes.MEDIUM, 1950, new Armor(110, 35, 25), 53, new SpongeCannon(CommonCannons._105_MM_RIFLED_GUN, 50, 7.67), new SpongeEngine(CommonEngines.MITSUBISHI_10ZF21WT, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.TYPE_67, SpongeTankTypes.MEDIUM), SpongeTracks.STB_1, SpongeTurrets.STB_1);
    /** Japanese Heavies */
    public static final SpongeTank TYPE_5_HEAVY = new SpongeTank(CommonTanks.TYPE_5_HEAVY, SpongeCountries.JAPAN, SpongeTankTypes.HEAVY, 2800, new Armor(260, 140, 150), 25, new SpongeCannon(CommonCannons._14_CM_50_3RD_YEAR_TYPE, 60, 16.97), new SpongeEngine(CommonEngines.KAWASAKI_TYPE_98, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios.TYPE_3_KO, SpongeTankTypes.HEAVY), SpongeTracks.TYPE_5_HEAVY, SpongeTurrets.TYPE_5_HEAVY);
    /** British Mediums */
    public static final SpongeTank FV4202 = new SpongeTank(CommonTanks.FV4202, SpongeCountries.UK, SpongeTankTypes.MEDIUM, 1950, new Armor(121, 51, 32), 40, new SpongeCannon(CommonCannons._105_MM_ROYAL_ORDNANCE_L7A1, 72, 8.25), new SpongeEngine(CommonEngines.ROLLS_ROYCE_METEOR_M120A, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.MEDIUM), SpongeTracks.FV4202, SpongeTurrets.FV4202);
    /** British Heavies */
    public static final SpongeTank FV215B = new SpongeTank(CommonTanks.FV215B, SpongeCountries.UK, SpongeTankTypes.HEAVY, 2500, new Armor(152.4, 101.6, 76.2), 34, new SpongeCannon(CommonCannons._120_MM_GUN_L1A1, 35, 8.34), new SpongeEngine(CommonEngines.ROLLS_ROYCE_GRIFFON, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.HEAVY), SpongeTracks.FV215, SpongeTurrets.FV215);
    /** British TDs */
    public static final SpongeTank FV215B_183 = new SpongeTank(CommonTanks.FV215B_183, SpongeCountries.UK, SpongeTankTypes.TD, 2000, new Armor(152.4, 50.8, 76.2), 34, new SpongeCannon(CommonCannons._183_MM_L4, 12, 28.77), new SpongeEngine(CommonEngines.ROLLS_ROYCE_METEOR_MK_12, SpongeTankTypes.TD), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.TD), SpongeTracks.FV215B, SpongeTurrets.FV215B);
    public static final SpongeTank FV4005_STAGE_II = new SpongeTank(CommonTanks.FV4005_STAGE_II, SpongeCountries.UK, SpongeTankTypes.TD, 1850, new Armor(76.2, 50.8, 38.1), 35, new SpongeCannon(CommonCannons._183_MM_L4, 12, 28.77), new SpongeEngine(CommonEngines.ROLLS_ROYCE_METEOR_MK_IVB, SpongeTankTypes.TD), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.TD), SpongeTracks.FV4005_STAGE_II, SpongeTurrets.FV4005_STAGE_II);
    /** British Arty */
    public static final SpongeTank CONQUEROR_GC = new SpongeTank(CommonTanks.CONQUEROR_GC, SpongeCountries.UK, SpongeTankTypes.ARTY, 530, new Armor(152.4, 50.8, 76.2), 34, new SpongeCannon(CommonCannons.BL_9_2_IN_HOWITZER, 24, 55.71), new SpongeEngine(CommonEngines.ROLLS_ROYCE_METEOR_M120, SpongeTankTypes.ARTY), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.ARTY), SpongeTracks.CONQUEROR_GC, SpongeTurrets.NO_TURRET_ARTY);
    /** American Lights */
    public static final SpongeTank T49 = new SpongeTank(CommonTanks.T49, SpongeCountries.USA, SpongeTankTypes.LIGHT, 1000, new Armor(25.4, 25.4, 19.1), 72, new SpongeCannon(CommonCannons._90_MM_GUN_T185, 22, 22.05), new SpongeEngine(CommonEngines.CONTINENTAL_AOI_895_5, SpongeTankTypes.LIGHT), new SpongeRadio(CommonRadios.AN_GRC_7, SpongeTankTypes.LIGHT), SpongeTracks.XM551_TEST_BED, SpongeTurrets.XM551_TEST_BED);
    /** American Mediums */
    public static final SpongeTank M48A1_PATTON = new SpongeTank(CommonTanks.M48A1_Patton, SpongeCountries.USA, SpongeTankTypes.MEDIUM, 2000, new Armor(152.4, 76.2, 38.1), 45, new SpongeCannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new SpongeEngine(CommonEngines.CONTINENTAL_AV_1790_7B, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.AN_VRC_7, SpongeTankTypes.MEDIUM), SpongeTracks.T97E2, SpongeTurrets.M87);
    public static final SpongeTank M60 = new SpongeTank(CommonTanks.M60, SpongeCountries.USA, SpongeTankTypes.MEDIUM, 2000, new Armor(93.2, 73.7, 25.4), 48, new SpongeCannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new SpongeEngine(CommonEngines.CONTINENTAL_AVDS_1790_2, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.AN_VRC_12, SpongeTankTypes.MEDIUM), SpongeTracks.M60, SpongeTurrets.M60);
    /** American Heavies */
    public static final SpongeTank T110E5 = new SpongeTank(CommonTanks.T110E5, SpongeCountries.USA, SpongeTankTypes.HEAVY, 2200, new Armor(254, 76.2, 38.1), 37, new SpongeCannon(CommonCannons._120_MM_GUN_M58, 42, 9.59), new SpongeEngine(CommonEngines.CONTINENTAL_AOI_1490, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios.AN_VRC_3, SpongeTankTypes.HEAVY), SpongeTracks.T110E27, SpongeTurrets.T53);
    public static final SpongeTank T57 = new SpongeTank(CommonTanks.T57, SpongeCountries.USA, SpongeTankTypes.HEAVY, 2250, new Armor(203, 50.8, 38.1), 35, new SpongeAutoLoader(CommonCannons._120_MM_GUN_T179, 36, 23.97, 2, 4), new SpongeEngine(CommonEngines.CONTINENTAL_AV_1790_5C, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios.AN_VRC_3, SpongeTankTypes.HEAVY), SpongeTracks.T97, SpongeTurrets.T169);
    /** American TDs */
    public static final SpongeTank T110E3 = new SpongeTank(CommonTanks.T110E3, SpongeCountries.USA, SpongeTankTypes.TD, 2050, new Armor(305, 76, 38), 30, new SpongeCannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 16.3), new SpongeEngine(CommonEngines.CONTINENTAL_AOI_1490, SpongeTankTypes.TD), new SpongeRadio(CommonRadios.AN_VRC_3, SpongeTankTypes.TD), SpongeTracks.T110E3, SpongeTurrets.NO_TURRET_TD);
    public static final SpongeTank T110E4 = new SpongeTank(CommonTanks.T110E4, SpongeCountries.USA, SpongeTankTypes.TD, 2000, new Armor(260, 76, 38), 35, new SpongeCannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 18.7), new SpongeEngine(CommonEngines.CONTINENTAL_AOI_1490, SpongeTankTypes.TD), new SpongeRadio(CommonRadios.AN_VRC_3, SpongeTankTypes.TD), SpongeTracks.T110E4, SpongeTurrets.T110E4);
    /** American Arty */
    public static final SpongeTank T92 = new SpongeTank(CommonTanks.T92, SpongeCountries.USA, SpongeTankTypes.ARTY, 500, new Armor(25.4, 25.4, 21.7), 32, new SpongeCannon(CommonCannons._240_MM_HOWITZER, 16, 50.82), new SpongeEngine(CommonEngines.FORD_GAF_T92, SpongeTankTypes.ARTY), new SpongeRadio(CommonRadios.SCR_528A, SpongeTankTypes.ARTY), SpongeTracks.T92_T80E1, SpongeTurrets.NO_TURRET_ARTY);
    /** Russian Lights */
    public static final SpongeTank T_54_LTWT = new SpongeTank(CommonTanks.T_54_LTWT, SpongeCountries.USSR, SpongeTankTypes.LIGHT, 1250, new Armor(80, 60, 45), 69, new SpongeCannon(CommonCannons._100_MM_D10T, 34, 7.19), new SpongeEngine(CommonEngines.V_14, SpongeTankTypes.LIGHT), new SpongeRadio(CommonRadios._10RT_26, SpongeTankTypes.LIGHT), SpongeTracks.T_54_ENHANCED_LTWT_VERSION, SpongeTurrets.T_54_LTWT_VERSION);
    /** Russian Mediums */
    public static final SpongeTank OBJECT_140 = new SpongeTank(CommonTanks.OBJECT_140, SpongeCountries.USSR, SpongeTankTypes.MEDIUM, 1900, new Armor(100, 80, 55), 40, new SpongeCannon(CommonCannons._100_MM_8TS, 50, 6.33), new SpongeEngine(CommonEngines.TD_12, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.R_113M, SpongeTankTypes.MEDIUM), SpongeTracks.OBJECT_140, SpongeTurrets.OBJECT_140);
    public static final SpongeTank OBJECT_430 = new SpongeTank(CommonTanks.OBJECT_430, SpongeCountries.USSR, SpongeTankTypes.MEDIUM, 1900, new Armor(120, 80, 40), 55, new SpongeCannon(CommonCannons._100_MM_8TS, 50, 6.14), new SpongeEngine(CommonEngines._5TD, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.R_113M, SpongeTankTypes.MEDIUM), SpongeTracks.OBJECT_430, SpongeTurrets.OBJECT_430);
    public static final SpongeTank OBJECT_907 = new SpongeTank(CommonTanks.OBJECT_907, SpongeCountries.USSR, SpongeTankTypes.MEDIUM, 2000, new Armor(100, 85, 45), 55, new SpongeCannon(CommonCannons._100_MM_D_54TS, 45, 6.23), new SpongeEngine(CommonEngines.V_12_5U, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.R_123, SpongeTankTypes.MEDIUM), SpongeTracks.OBJECT_907, SpongeTurrets.OBJECT_907);
    public static final SpongeTank T_62A = new SpongeTank(CommonTanks.T_62A, SpongeCountries.USSR, SpongeTankTypes.MEDIUM, 1950, new Armor(100, 80, 45), 50, new SpongeCannon(CommonCannons._100_MM_8TS, 50, 6.33), new SpongeEngine(CommonEngines.V_55, SpongeTankTypes.MEDIUM), new SpongeRadio(CommonRadios.R_123, SpongeTankTypes.MEDIUM), SpongeTracks.T_62A, SpongeTurrets.T_62A);
    /** Russian Heavies */
    public static final SpongeTank IS_4 = new SpongeTank(CommonTanks.IS_4, SpongeCountries.USSR, SpongeTankTypes.HEAVY, 2500, new Armor(140, 160, 100), 43, new SpongeCannon(CommonCannons._122_MM_M62_T2, 30, 11.51), new SpongeEngine(CommonEngines.V_12, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios._10RK_26, SpongeTankTypes.HEAVY), SpongeTracks.IS_4M, SpongeTurrets.IS_4M);
    public static final SpongeTank IS_7 = new SpongeTank(CommonTanks.IS_7, SpongeCountries.USSR, SpongeTankTypes.HEAVY, 2150, new Armor(150, 150, 100), 60, new SpongeCannon(CommonCannons._130_MM_S_70, 30, 13.14), new SpongeEngine(CommonEngines.M_50T, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios._10RK_26, SpongeTankTypes.HEAVY), SpongeTracks.IS_7, SpongeTurrets.IS_7);
    public static final SpongeTank OBJECT_260 = new SpongeTank(CommonTanks.OBJECT_260, SpongeCountries.USSR, SpongeTankTypes.HEAVY, 2100, new Armor(150, 150, 70), 60, new SpongeCannon(CommonCannons._122_MM_BL_13_1, 30, 11.03), new SpongeEngine(CommonEngines._2X_V_16, SpongeTankTypes.HEAVY), new SpongeRadio(CommonRadios._10RK_26, SpongeTankTypes.HEAVY), SpongeTracks.OBJECT_260_MOD_1945, SpongeTurrets.OBJECT_260_MOD_1945);
    /** Russian TDs */
    public static final SpongeTank OBJECT_263 = new SpongeTank(CommonTanks.OBJECT_263, SpongeCountries.USSR, SpongeTankTypes.TD, 1900, new Armor(250, 80, 50), 55, new SpongeCannon(CommonCannons._130_MM_S_70A, 42, 10.26), new SpongeEngine(CommonEngines.M_50T, SpongeTankTypes.TD), new SpongeRadio(CommonRadios._10RK_26, SpongeTankTypes.TD), SpongeTracks.OBJECT_263, SpongeTurrets.NO_TURRET_TD);
    public static final SpongeTank OBJECT_268 = new SpongeTank(CommonTanks.OBJECT_268, SpongeCountries.USSR, SpongeTankTypes.TD, 1950, new Armor(187, 100, 50), 48, new SpongeCannon(CommonCannons._152_MM_M64, 35, 15.82), new SpongeEngine(CommonEngines.V_16FN, SpongeTankTypes.TD), new SpongeRadio(CommonRadios.R_113M, SpongeTankTypes.TD), SpongeTracks.OBJECT_268, SpongeTurrets.NO_TURRET_TD);
    /** Russian Arty */
    public static final SpongeTank OBJECT_261 = new SpongeTank(CommonTanks.OBJECT_261, SpongeCountries.USSR, SpongeTankTypes.ARTY, 510, new Armor(75, 50, 30), 50, new SpongeCannon(CommonCannons._180_MM_B_1_P, 18, 33.56), new SpongeEngine(CommonEngines.M_50T, SpongeTankTypes.ARTY), new SpongeRadio(CommonRadios._10RK_26, SpongeTankTypes.ARTY), SpongeTracks.OBJECT_261, SpongeTurrets.NO_TURRET_ARTY);

    public static final List<SpongeTank> ALL = Arrays.asList(
            /** China Lights */
            WZ_132,
            /** China Mediums */
            _121,
            /** China Heavies */
            _113,
            /** Czechoslovakian Mediums */
            TVP_T_50_51,
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
