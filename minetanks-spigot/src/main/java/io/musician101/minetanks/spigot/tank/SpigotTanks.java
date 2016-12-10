package io.musician101.minetanks.spigot.tank;

import io.musician101.minetanks.common.CommonReference.CommonCannons;
import io.musician101.minetanks.common.CommonReference.CommonCountries;
import io.musician101.minetanks.common.CommonReference.CommonEngines;
import io.musician101.minetanks.common.CommonReference.CommonRadios;
import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonTanks;
import io.musician101.minetanks.common.CommonReference.CommonTracks;
import io.musician101.minetanks.common.CommonReference.CommonTurrets;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.spigot.tank.modules.SpigotEngine;
import io.musician101.minetanks.spigot.tank.modules.SpigotModules;
import io.musician101.minetanks.spigot.tank.modules.SpigotRadio;
import io.musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import io.musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import io.musician101.minetanks.spigot.tank.modules.tracks.SpigotTracks;
import io.musician101.minetanks.spigot.tank.modules.turret.SpigotTurrets;
import java.util.List;


public class SpigotTanks extends AbstractInfoStorage<SpigotTank> {

    private SpigotTanks() {
        super(
                // China Lights
                new SpigotTank(CommonTanks.WZ_132, SpigotCountries.getCountry(CommonCountries.CHINA), SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), 1150, new Armor(50, 25, 20), 64, new SpigotModules(new SpigotCannon(CommonCannons._100_MM_60_100T, 43, 7.57), new SpigotEngine(CommonEngines.EIGHT_V135, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpigotRadio(CommonRadios.A_220A, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), SpigotTracks.getTracks(CommonTracks.WZ_132), SpigotTurrets.getTurret(CommonTurrets.WZ_132))),
                // China Mediums
                new SpigotTank(CommonTanks.ONE_21, SpigotCountries.getCountry(CommonCountries.CHINA), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(120, 80, 60), 56, new SpigotModules(new SpigotCannon(CommonCannons._122_MM_60_122T, 40, 9.6), new SpigotEngine(CommonEngines.TWELVE_150L7, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.A_220B, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.ONE_21), SpigotTurrets.getTurret(CommonTurrets.ONE_21))),
                // China Heavies
                new SpigotTank(CommonTanks.ONE_13, SpigotCountries.getCountry(CommonCountries.CHINA), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2300, new Armor(120, 90, 70), 50, new SpigotModules(new SpigotCannon(CommonCannons._122_MM_60_122T, 34, 10.45), new SpigotEngine(CommonEngines.V_2_54SC, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.A_220B, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.ONE_13), SpigotTurrets.getTurret(CommonTurrets.ONE_13))),
                // Czechoslovakian Mediums
                new SpigotTank(CommonTanks.TVP_T_50_51, SpigotCountries.getCountry(CommonCountries.CZECH), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1800, new Armor(65, 40, 30), 60, new SpigotModules(new SpigotAutoLoader(CommonCannons._100_MM_AK1, 48, 23.97, 1.5, 4), new SpigotEngine(CommonEngines.PRAGA_AXK, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.RADIOSTANICE_RM_31T, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.TVP_T_50_51), SpigotTurrets.getTurret(CommonTurrets.TVP_T_50_51))),
                // France Lights
                new SpigotTank(CommonTanks.AMX_13_90, SpigotCountries.getCountry(CommonCountries.FRANCE), SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), 1100, new Armor(40, 20, 15), 64, new SpigotModules(new SpigotAutoLoader(CommonCannons._90_MM_F3, 36, 42, 2.73, 6), new SpigotEngine(CommonEngines.DETROIT_DIESEL_6V53T_S, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpigotRadio(CommonRadios.SCR_528, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), SpigotTracks.getTracks(CommonTracks.AMX_13_TYPE_2D), SpigotTurrets.getTurret(CommonTurrets.FL_10_TYPE_D))),
                // French Mediums
                new SpigotTank(CommonTanks.BAT_CHAT_25_T, SpigotCountries.getCountry(CommonCountries.FRANCE), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1800, new Armor(60, 40, 30), 65, new SpigotModules(new SpigotAutoLoader(CommonCannons._105_MM_MLE_57, 30, 40, 2.73, 5), new SpigotEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.SCR_528F, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.BAT_CHAT_25_T), SpigotTurrets.getTurret(CommonTurrets.BAT_CHAT_25_T))),
                new SpigotTank(CommonTanks.AMX_30_B, SpigotCountries.getCountry(CommonCountries.FRANCE), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1900, new Armor(80, 35, 30), 65, new SpigotModules(new SpigotCannon(CommonCannons._105_MM_MLE_F1, 50, 8.53), new SpigotEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.TRVP_13_A, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.AMX_30_B), SpigotTurrets.getTurret(CommonTurrets.AMX_30_B))),
                //French Heavies
                new SpigotTank(CommonTanks.AMX_50_B, SpigotCountries.getCountry(CommonCountries.FRANCE), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2100, new Armor(170, 30, 30), 65, new SpigotModules(new SpigotAutoLoader(CommonCannons._120_MM_SA46, 56, 30, 2.5, 4), new SpigotEngine(CommonEngines.MAYBACH_HL_295_F, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.SCR_619, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.AMX_50_B), SpigotTurrets.getTurret(CommonTurrets.AMX_50_B))),
                //French TDs
                new SpigotTank(CommonTanks.AMX_50_FOCH_155, SpigotCountries.getCountry(CommonCountries.FRANCE), SpigotTankTypes.getTankType(CommonTankTypes.TD), 1850, new Armor(180, 40, 40), 50, new SpigotModules(new SpigotAutoLoader(CommonCannons._155_MM_AC_SA58, 30, 47.94, 5, 3), new SpigotEngine(CommonEngines.SAURER, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.SCR_619, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.AMX_50_FOCH_155), SpigotTurrets.getTurret(CommonTurrets.NONE_TD))),
                //French Arty
                new SpigotTank(CommonTanks.BAT_CHAT_155_58, SpigotCountries.getCountry(CommonCountries.FRANCE), SpigotTankTypes.getTankType(CommonTankTypes.SPG), 490, new Armor(50, 30, 20), 62, new SpigotModules(new SpigotAutoLoader(CommonCannons.CANNON_DE_155_MM, 36, 76.71, 6, 4), new SpigotEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), new SpigotRadio(CommonRadios.SCR_528F, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), SpigotTracks.getTracks(CommonTracks.BAT_CHAT_155_58), SpigotTurrets.getTurret(CommonTurrets.NONE_SPG))),
                //German Lights
                new SpigotTank(CommonTanks.SPAHPANZER_RU_251, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), 500, new Armor(25, 20, 8), 80, new SpigotModules(new SpigotCannon(CommonCannons._90_MM_RHEINMETALL_DM1, 41, 5.94), new SpigotEngine(CommonEngines.MB_837_EA_600, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpigotRadio(CommonRadios.AN_GRC_7, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), SpigotTracks.getTracks(CommonTracks.SPAHPANZER_RU_251_PT_II), SpigotTurrets.getTurret(CommonTurrets.SPAHPANZER_RU_251))),
                //German Mediums
                new SpigotTank(CommonTanks.E_50_M, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 2050, new Armor(150, 80, 80), 60, new SpigotModules(new SpigotCannon(CommonCannons._10_5_CM_KWK_L52_AUSF_K, 48, 9.2), new SpigotEngine(CommonEngines.MAYBACH_HL_235, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.TEN_WSC, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.E_50_M), SpigotTurrets.getTurret(CommonTurrets.E_50_M))),
                new SpigotTank(CommonTanks.LEOPARD_1, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(70, 35, 25), 65, new SpigotModules(new SpigotCannon(CommonCannons._10_5_CM_BORDKANONE_L7A3, 60, 8.34), new SpigotEngine(CommonEngines.MTU_MB_838_CAM_500A, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.SEM_25A, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.LEOPARD_1), SpigotTurrets.getTurret(CommonTurrets.LEOPARD_1))),
                //German Heavies
                new SpigotTank(CommonTanks.E_100, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2700, new Armor(200, 120, 150), 30, new SpigotModules(new SpigotCannon(CommonCannons._15_CM_KWK_L38, 50, 19.18), new SpigotEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.TEN_WSC, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.E_100), SpigotTurrets.getTurret(CommonTurrets.E_100))),
                new SpigotTank(CommonTanks.MAUS, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 3000, new Armor(200, 185, 160), 20, new SpigotModules(new SpigotCannon(CommonCannons._12_8_CM_KWK_44_L55, 68, 14.29), new SpigotEngine(CommonEngines.DB_603_A2, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.TEN_WSC, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.MAUS), SpigotTurrets.getTurret(CommonTurrets.MAUS))),
                new SpigotTank(CommonTanks.VK_7201, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2500, new Armor(200, 160, 120), 43, new SpigotModules(new SpigotCannon("15 cm Kw.K L/38", 24, 21.09), new SpigotEngine(CommonEngines.MB_517, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.TEN_WSC, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.VK_7201), SpigotTurrets.getTurret(CommonTurrets.VK_7201))),
                //German TDs
                new SpigotTank(CommonTanks.JAGDPANZER_E_100, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.TD), 2200, new Armor(200, 120, 150), 30, new SpigotModules(new SpigotCannon(CommonCannons._17_CM_PAK, 24, 24.64), new SpigotEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.TEN_WSC, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.JAGDPANZER_E_100), SpigotTurrets.getTurret(CommonTurrets.NONE_TD))),
                new SpigotTank(CommonTanks.WAFFENTRAGER_E_100, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.TD), 2000, new Armor(200, 120, 150), 40, new SpigotModules(new SpigotAutoLoader(CommonCannons._15_CM_PAK_L38, 60, 57.94, 3, 4), new SpigotEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.TEN_WSC, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.WAFFENTRAGER_E_100), SpigotTurrets.getTurret(CommonTurrets.WAFFENTRAGER_E_100))),
                //German Arty
                new SpigotTank(CommonTanks.GW_E_100, SpigotCountries.getCountry(CommonCountries.GERMANY), SpigotTankTypes.getTankType(CommonTankTypes.SPG), 550, new Armor(80, 50, 40), 40, new SpigotModules(new SpigotCannon(CommonCannons._21_CM_MORSER_18_2, 30, 40.75), new SpigotEngine(CommonEngines.MAYBACH_HL_234, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), new SpigotRadio(CommonRadios.TEN_WSC, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), SpigotTracks.getTracks(CommonTracks.GW_E_100), SpigotTurrets.getTurret(CommonTurrets.NONE_SPG))),
                //Japanese Mediums
                new SpigotTank(CommonTanks.STB_1, SpigotCountries.getCountry(CommonCountries.JAPAN), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(110, 35, 25), 53, new SpigotModules(new SpigotCannon(CommonCannons._105_MM_RIFLED_GUN, 50, 7.67), new SpigotEngine(CommonEngines.MITSUBISHI_10ZF21WT, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.TYPE_67, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.STB_1), SpigotTurrets.getTurret(CommonTurrets.STB_1))),
                //Japanese Heavies
                new SpigotTank(CommonTanks.TYPE_5_HEAVY, SpigotCountries.getCountry(CommonCountries.JAPAN), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2800, new Armor(260, 140, 150), 25, new SpigotModules(new SpigotCannon(CommonCannons._14_CM_50_3RD_YEAR_TYPE, 60, 16.97), new SpigotEngine(CommonEngines.KAWASAKI_TYPE_98, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.TYPE_3_KO, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.TYPE_5_HEAVY), SpigotTurrets.getTurret(CommonTurrets.TYPE_5_HEAVY))),
                //British Mediums
                new SpigotTank(CommonTanks.FV4202, SpigotCountries.getCountry(CommonCountries.UK), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(121, 51, 32), 40, new SpigotModules(new SpigotCannon(CommonCannons._105_MM_ROYAL_ORDNANCE_L7A1, 72, 8.25), new SpigotEngine(CommonEngines.ROLLS_ROYCE_METEOR_M120A, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.FV4202), SpigotTurrets.getTurret(CommonTurrets.FV4202))),
                //British Heavies
                new SpigotTank(CommonTanks.FV215B, SpigotCountries.getCountry(CommonCountries.UK), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2500, new Armor(152.4, 101.6, 76.2), 34, new SpigotModules(new SpigotCannon(CommonCannons._120_MM_GUN_L1A1, 35, 8.34), new SpigotEngine(CommonEngines.ROLLS_ROYCE_GRIFFON, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.FV215), SpigotTurrets.getTurret(CommonTurrets.FV215))),
                //British TDs
                new SpigotTank(CommonTanks.FV215B_183, SpigotCountries.getCountry(CommonCountries.UK), SpigotTankTypes.getTankType(CommonTankTypes.TD), 2000, new Armor(152.4, 50.8, 76.2), 34, new SpigotModules(new SpigotCannon(CommonCannons._183_MM_L4, 12, 28.77), new SpigotEngine(CommonEngines.ROLLS_ROYCE_METEOR_MK_12, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.FV215B), SpigotTurrets.getTurret(CommonTurrets.FV215B))),
                new SpigotTank(CommonTanks.FV4005_STAGE_II, SpigotCountries.getCountry(CommonCountries.UK), SpigotTankTypes.getTankType(CommonTankTypes.TD), 1850, new Armor(76.2, 50.8, 38.1), 35, new SpigotModules(new SpigotCannon(CommonCannons._183_MM_L4, 12, 28.77), new SpigotEngine(CommonEngines.ROLLS_ROYCE_METEOR_MK_IVB, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.FV4005_STAGE_II), SpigotTurrets.getTurret(CommonTurrets.FV4005_STAGE_II))),
                //British Arty
                new SpigotTank(CommonTanks.CONQUEROR_GC, SpigotCountries.getCountry(CommonCountries.UK), SpigotTankTypes.getTankType(CommonTankTypes.SPG), 530, new Armor(152.4, 50.8, 76.2), 34, new SpigotModules(new SpigotCannon(CommonCannons.BL_9_2_IN_HOWITZER, 24, 55.71), new SpigotEngine(CommonEngines.ROLLS_ROYCE_METEOR_M120, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), new SpigotRadio(CommonRadios.SR_C45, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), SpigotTracks.getTracks(CommonTracks.CONQUEROR_GUN_CARRIAGE), SpigotTurrets.getTurret(CommonTurrets.NONE_SPG))),
                //American Lights
                new SpigotTank(CommonTanks.T49, SpigotCountries.getCountry(CommonCountries.USA), SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), 1000, new Armor(25.4, 25.4, 19.1), 72, new SpigotModules(new SpigotCannon(CommonCannons._90_MM_GUN_T185, 22, 22.05), new SpigotEngine(CommonEngines.CONTINENTAL_AOI_895_5, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpigotRadio(CommonRadios.AN_GRC_7, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), SpigotTracks.getTracks(CommonTracks.XM551_TEST_BED), SpigotTurrets.getTurret(CommonTurrets.XM551_TEST_BED))),
                //American Mediums
                new SpigotTank(CommonTanks.M48A1_PATTON, SpigotCountries.getCountry(CommonCountries.USA), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 2000, new Armor(152.4, 76.2, 38.1), 45, new SpigotModules(new SpigotCannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new SpigotEngine(CommonEngines.CONTINENTAL_AV_1790_7B, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.AN_VRC_7, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.T97E2), SpigotTurrets.getTurret(CommonTurrets.M87))),
                new SpigotTank(CommonTanks.M60, SpigotCountries.getCountry(CommonCountries.USA), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 2000, new Armor(93.2, 73.7, 25.4), 48, new SpigotModules(new SpigotCannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new SpigotEngine(CommonEngines.CONTINENTAL_AVDS_1790_2, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.AN_VRC_12, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.M60), SpigotTurrets.getTurret(CommonTurrets.M60))),
                //American Heavies
                new SpigotTank(CommonTanks.T110E5, SpigotCountries.getCountry(CommonCountries.USA), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2200, new Armor(254, 76.2, 38.1), 37, new SpigotModules(new SpigotCannon(CommonCannons._120_MM_GUN_M58, 42, 9.59), new SpigotEngine(CommonEngines.CONTINENTAL_AOI_1490, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.AN_VRC_3, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.T110E27), SpigotTurrets.getTurret(CommonTurrets.T53))),
                new SpigotTank(CommonTanks.T57, SpigotCountries.getCountry(CommonCountries.USA), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2250, new Armor(203, 50.8, 38.1), 35, new SpigotModules(new SpigotAutoLoader(CommonCannons._120_MM_GUN_T179, 36, 23.97, 2, 4), new SpigotEngine(CommonEngines.CONTINENTAL_AV_1790_5C, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.AN_VRC_3, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.T97), SpigotTurrets.getTurret(CommonTurrets.T169))),
                //American TDs
                new SpigotTank(CommonTanks.T110E3, SpigotCountries.getCountry(CommonCountries.USA), SpigotTankTypes.getTankType(CommonTankTypes.TD), 2050, new Armor(305, 76, 38), 30, new SpigotModules(new SpigotCannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 16.3), new SpigotEngine(CommonEngines.CONTINENTAL_AOI_1490, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.AN_VRC_3, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.T110E3), SpigotTurrets.getTurret(CommonTurrets.NONE_TD))),
                new SpigotTank(CommonTanks.T110E4, SpigotCountries.getCountry(CommonCountries.USA), SpigotTankTypes.getTankType(CommonTankTypes.TD), 2000, new Armor(260, 76, 38), 35, new SpigotModules(new SpigotCannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 18.7), new SpigotEngine(CommonEngines.CONTINENTAL_AOI_1490, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.AN_VRC_3, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.T110E4), SpigotTurrets.getTurret(CommonTurrets.T110E4))),
                //American Arty
                new SpigotTank(CommonTanks.T92, SpigotCountries.getCountry(CommonCountries.USA), SpigotTankTypes.getTankType(CommonTankTypes.SPG), 500, new Armor(25.4, 25.4, 21.7), 32, new SpigotModules(new SpigotCannon(CommonCannons._240_MM_HOWITZER, 16, 50.82), new SpigotEngine(CommonEngines.FORD_GAF_T92, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), new SpigotRadio(CommonRadios.SCR_528A, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), SpigotTracks.getTracks(CommonTracks.T92_T80E1), SpigotTurrets.getTurret(CommonTurrets.NONE_SPG))),
                //Russian Lights
                new SpigotTank(CommonTanks.T_54_LTWT, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), 1250, new Armor(80, 60, 45), 69, new SpigotModules(new SpigotCannon(CommonCannons._100_MM_D10T, 34, 7.19), new SpigotEngine(CommonEngines.V_14, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpigotRadio(CommonRadios.TEN_RT_26, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)), SpigotTracks.getTracks(CommonTracks.T_54_ENHANCED_LTWT_VERSION), SpigotTurrets.getTurret(CommonTurrets.T_54_LTWT_VERSION))),
                //Russian Mediums
                new SpigotTank(CommonTanks.OBJECT_140, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1900, new Armor(100, 80, 55), 40, new SpigotModules(new SpigotCannon(CommonCannons._100_MM_8TS, 50, 6.33), new SpigotEngine(CommonEngines.TD_12, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.R_113M, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.OBJECT_140), SpigotTurrets.getTurret(CommonTurrets.OBJECT_140))),
                new SpigotTank(CommonTanks.OBJECT_430, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1900, new Armor(120, 80, 40), 55, new SpigotModules(new SpigotCannon(CommonCannons._100_MM_8TS, 50, 6.14), new SpigotEngine(CommonEngines.FIVE_TD, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.R_113M, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.OBJECT_430), SpigotTurrets.getTurret(CommonTurrets.OBJECT_430))),
                new SpigotTank(CommonTanks.OBJECT_907, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 2000, new Armor(100, 85, 45), 55, new SpigotModules(new SpigotCannon(CommonCannons._100_MM_D_54TS, 45, 6.23), new SpigotEngine(CommonEngines.V_12_5U, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.R_123, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.OBJECT_907), SpigotTurrets.getTurret(CommonTurrets.OBJECT_907))),
                new SpigotTank(CommonTanks.T_62A, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(100, 80, 45), 50, new SpigotModules(new SpigotCannon(CommonCannons._100_MM_8TS, 50, 6.33), new SpigotEngine(CommonEngines.V_55, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpigotRadio(CommonRadios.R_123, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpigotTracks.getTracks(CommonTracks.T_62A), SpigotTurrets.getTurret(CommonTurrets.T_62))),
                //Russian Heavies
                new SpigotTank(CommonTanks.IS_4, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2500, new Armor(140, 160, 100), 43, new SpigotModules(new SpigotCannon(CommonCannons._122_MM_M62_T2, 30, 11.51), new SpigotEngine(CommonEngines.V_12, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.TEN_RK_26, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.IS_4M), SpigotTurrets.getTurret(CommonTurrets.IS_4M))),
                new SpigotTank(CommonTanks.IS_7, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2150, new Armor(150, 150, 100), 60, new SpigotModules(new SpigotCannon(CommonCannons._130_MM_S_70, 30, 13.14), new SpigotEngine(CommonEngines.M_50T, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.TEN_RK_26, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.IS_7), SpigotTurrets.getTurret(CommonTurrets.IS_7))),
                new SpigotTank(CommonTanks.OBJECT_260, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), 2100, new Armor(150, 150, 70), 60, new SpigotModules(new SpigotCannon(CommonCannons._122_MM_BL_13_1, 30, 11.03), new SpigotEngine(CommonEngines.TWO_X_V_16, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpigotRadio(CommonRadios.TEN_RK_26, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)), SpigotTracks.getTracks(CommonTracks.OBJECT_260_MOD_1945), SpigotTurrets.getTurret(CommonTurrets.OBJECT_260_MOD_1945))),
                //Russian TDs
                new SpigotTank(CommonTanks.OBJECT_263, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.TD), 1900, new Armor(250, 80, 50), 55, new SpigotModules(new SpigotCannon(CommonCannons._130_MM_S_70A, 42, 10.26), new SpigotEngine(CommonEngines.M_50T, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.TEN_RK_26, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.OBJECT_263), SpigotTurrets.getTurret(CommonTurrets.NONE_TD))),
                new SpigotTank(CommonTanks.OBJECT_268, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.TD), 1950, new Armor(187, 100, 50), 48, new SpigotModules(new SpigotCannon(CommonCannons._152_MM_M64, 35, 15.82), new SpigotEngine(CommonEngines.V_16FN, SpigotTankTypes.getTankType(CommonTankTypes.TD)), new SpigotRadio(CommonRadios.R_113M, SpigotTankTypes.getTankType(CommonTankTypes.TD)), SpigotTracks.getTracks(CommonTracks.OBJECT_268), SpigotTurrets.getTurret(CommonTurrets.NONE_TD))),
                //Russian Arty
                new SpigotTank(CommonTanks.OBJECT_261, SpigotCountries.getCountry(CommonCountries.USSR), SpigotTankTypes.getTankType(CommonTankTypes.SPG), 510, new Armor(75, 50, 30), 50, new SpigotModules(new SpigotCannon(CommonCannons._180_MM_B_1_P, 18, 33.56), new SpigotEngine(CommonEngines.M_50T, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), new SpigotRadio(CommonRadios.TEN_RK_26, SpigotTankTypes.getTankType(CommonTankTypes.SPG)), SpigotTracks.getTracks(CommonTracks.OBJECT_261), SpigotTurrets.getTurret(CommonTurrets.NONE_SPG))));
    }


    public static SpigotTank getTank(String name) {
        return new SpigotTanks().get(name);
    }

    public static List<SpigotTank> getValues() {
        return new SpigotTanks().values();
    }
}
