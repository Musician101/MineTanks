package io.musician101.minetanks.sponge.tank;

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
import io.musician101.minetanks.sponge.tank.module.SpongeEngine;
import io.musician101.minetanks.sponge.tank.module.SpongeModules;
import io.musician101.minetanks.sponge.tank.module.SpongeRadio;
import io.musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import io.musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import io.musician101.minetanks.sponge.tank.module.tracks.SpongeTracks;
import io.musician101.minetanks.sponge.tank.module.turret.SpongeTurrets;
import java.util.List;


public class SpongeTanks extends AbstractInfoStorage<SpongeTank> {

    private SpongeTanks() {
        super(
                //China Lights
                new SpongeTank(CommonTanks.WZ_132, SpongeCountries.getCountry(CommonCountries.CHINA), SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), 1150, new Armor(50, 25, 20), 64, new SpongeModules(new SpongeCannon(CommonCannons._100_MM_60_100T, 43, 7.57), new SpongeEngine(CommonEngines.EIGHT_V135, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpongeRadio(CommonRadios.A_220A, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), SpongeTracks.getTracks(CommonTracks.WZ_132), SpongeTurrets.getTurret(CommonTurrets.WZ_132))),
                //China Mediums
                new SpongeTank(CommonTanks.ONE_21, SpongeCountries.getCountry(CommonCountries.CHINA), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(120, 80, 60), 56, new SpongeModules(new SpongeCannon(CommonCannons._122_MM_60_122T, 40, 9.6), new SpongeEngine(CommonEngines.TWELVE_150L7, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.A_220B, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.ONE_21), SpongeTurrets.getTurret(CommonTurrets.ONE_21))),
                //China Heavies
                new SpongeTank(CommonTanks.ONE_13, SpongeCountries.getCountry(CommonCountries.CHINA), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2300, new Armor(120, 90, 70), 50, new SpongeModules(new SpongeCannon(CommonCannons._122_MM_60_122T, 34, 10.45), new SpongeEngine(CommonEngines.V_2_54SC, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.A_220B, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.ONE_13), SpongeTurrets.getTurret(CommonTurrets.ONE_13))),
                //Czechoslovakian Mediums
                new SpongeTank(CommonTanks.TVP_T_50_51, SpongeCountries.getCountry(CommonCountries.CZECH), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1800, new Armor(65, 40, 30), 60, new SpongeModules(new SpongeAutoLoader(CommonCannons._100_MM_AK1, 48, 23.97, 1.5, 4), new SpongeEngine(CommonEngines.PRAGA_AXK, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.RADIOSTANICE_RM_31T, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.TVP_T_50_51), SpongeTurrets.getTurret(CommonTurrets.TVP_T_50_51))),
                //France Lights
                new SpongeTank(CommonTanks.AMX_13_90, SpongeCountries.getCountry(CommonCountries.FRANCE), SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), 1100, new Armor(40, 20, 15), 64, new SpongeModules(new SpongeAutoLoader(CommonCannons._90_MM_F3, 36, 42, 2.73, 6), new SpongeEngine(CommonEngines.DETROIT_DIESEL_6V53T_S, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpongeRadio(CommonRadios.SCR_528, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), SpongeTracks.getTracks(CommonTracks.AMX_13_TYPE_2D), SpongeTurrets.getTurret(CommonTurrets.FL_10_TYPE_D))),
                //French Mediums
                new SpongeTank(CommonTanks.BAT_CHAT_25_T, SpongeCountries.getCountry(CommonCountries.FRANCE), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1800, new Armor(60, 40, 30), 65, new SpongeModules(new SpongeAutoLoader(CommonCannons._105_MM_MLE_57, 30, 40, 2.73, 5), new SpongeEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.SCR_528F, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.BAT_CHAT_25_T), SpongeTurrets.getTurret(CommonTurrets.BAT_CHAT_25_T))),
                new SpongeTank(CommonTanks.AMX_30_B, SpongeCountries.getCountry(CommonCountries.FRANCE), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1900, new Armor(80, 35, 30), 65, new SpongeModules(new SpongeCannon(CommonCannons._105_MM_MLE_F1, 50, 8.53), new SpongeEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.TRVP_13_A, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.AMX_30_B), SpongeTurrets.getTurret(CommonTurrets.AMX_30_B))),
                //French Heavies
                new SpongeTank(CommonTanks.AMX_50_B, SpongeCountries.getCountry(CommonCountries.FRANCE), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2100, new Armor(170, 30, 30), 65, new SpongeModules(new SpongeAutoLoader(CommonCannons._120_MM_SA46, 56, 30, 2.5, 4), new SpongeEngine(CommonEngines.MAYBACH_HL_295_F, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.SCR_619, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.AMX_50_B), SpongeTurrets.getTurret(CommonTurrets.AMX_50_B))),
                //French TDs
                new SpongeTank(CommonTanks.AMX_50_FOCH_155, SpongeCountries.getCountry(CommonCountries.FRANCE), SpongeTankTypes.getTankType(CommonTankTypes.TD), 1850, new Armor(180, 40, 40), 50, new SpongeModules(new SpongeAutoLoader(CommonCannons._155_MM_AC_SA58, 30, 47.94, 5, 3), new SpongeEngine(CommonEngines.SAURER, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.SCR_619, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.AMX_50_FOCH_155), SpongeTurrets.getTurret(CommonTurrets.NONE_TD))),
                //French Arty
                new SpongeTank(CommonTanks.BAT_CHAT_155_58, SpongeCountries.getCountry(CommonCountries.FRANCE), SpongeTankTypes.getTankType(CommonTankTypes.SPG), 490, new Armor(50, 30, 20), 62, new SpongeModules(new SpongeAutoLoader(CommonCannons.CANNON_DE_155_MM, 36, 76.71, 6, 4), new SpongeEngine(CommonEngines.HISPANO_SUIZA_HS_110, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), new SpongeRadio(CommonRadios.SCR_528F, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), SpongeTracks.getTracks(CommonTracks.BAT_CHAT_155_58), SpongeTurrets.getTurret(CommonTurrets.NONE_SPG))),
                //German Lights
                new SpongeTank(CommonTanks.SPAHPANZER_RU_251, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), 500, new Armor(25, 20, 8), 80, new SpongeModules(new SpongeCannon(CommonCannons._90_MM_RHEINMETALL_DM1, 41, 5.94), new SpongeEngine(CommonEngines.MB_837_EA_600, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpongeRadio(CommonRadios.AN_GRC_7, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), SpongeTracks.getTracks(CommonTracks.SPAHPANZER_RU_251_PT_II), SpongeTurrets.getTurret(CommonTurrets.SPAHPANZER_RU_251))),
                //German Mediums
                new SpongeTank(CommonTanks.E_50_M, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 2050, new Armor(150, 80, 80), 60, new SpongeModules(new SpongeCannon(CommonCannons._10_5_CM_KWK_L52_AUSF_K, 48, 9.2), new SpongeEngine(CommonEngines.MAYBACH_HL_235, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.TEN_WSC, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.E_50_M), SpongeTurrets.getTurret(CommonTurrets.E_50_M))),
                new SpongeTank(CommonTanks.LEOPARD_1, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(70, 35, 25), 65, new SpongeModules(new SpongeCannon(CommonCannons._10_5_CM_BORDKANONE_L7A3, 60, 8.34), new SpongeEngine(CommonEngines.MTU_MB_838_CAM_500A, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.SEM_25A, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.LEOPARD_1), SpongeTurrets.getTurret(CommonTurrets.LEOPARD_1))),
                //German Heavies
                new SpongeTank(CommonTanks.E_100, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2700, new Armor(200, 120, 150), 30, new SpongeModules(new SpongeCannon(CommonCannons._15_CM_KWK_L38, 50, 19.18), new SpongeEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.TEN_WSC, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.E_100), SpongeTurrets.getTurret(CommonTurrets.E_100))),
                new SpongeTank(CommonTanks.MAUS, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 3000, new Armor(200, 185, 160), 20, new SpongeModules(new SpongeCannon(CommonCannons._12_8_CM_KWK_44_L55, 68, 14.29), new SpongeEngine(CommonEngines.DB_603_A2, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.TEN_WSC, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.MAUS), SpongeTurrets.getTurret(CommonTurrets.MAUS))),
                new SpongeTank(CommonTanks.VK_7201, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2500, new Armor(200, 160, 120), 43, new SpongeModules(new SpongeCannon(CommonCannons._15_CM_KWK_L38, 24, 21.09), new SpongeEngine(CommonEngines.MB_517, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.TEN_WSC, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.VK_7201), SpongeTurrets.getTurret(CommonTurrets.VK_7201))),
                //German TDs
                new SpongeTank(CommonTanks.JAGDPANZER_E_100, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.TD), 2200, new Armor(200, 120, 150), 30, new SpongeModules(new SpongeCannon(CommonCannons._17_CM_PAK, 24, 24.64), new SpongeEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.TEN_WSC, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.JAGDPANZER_E_100), SpongeTurrets.getTurret(CommonTurrets.NONE_TD))),
                new SpongeTank(CommonTanks.WAFFENTRAGER_E_100, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.TD), 2000, new Armor(200, 120, 150), 40, new SpongeModules(new SpongeAutoLoader(CommonCannons._15_CM_PAK_L38, 60, 57.94, 3, 4), new SpongeEngine(CommonEngines.MAYBACH_NEUES_PROJEKT, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.TEN_WSC, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.WAFFENTRAGER_E_100), SpongeTurrets.getTurret(CommonTurrets.WAFFENTRAGER_E_100))),
                //German Arty
                new SpongeTank(CommonTanks.GW_E_100, SpongeCountries.getCountry(CommonCountries.GERMANY), SpongeTankTypes.getTankType(CommonTankTypes.SPG), 550, new Armor(80, 50, 40), 40, new SpongeModules(new SpongeCannon(CommonCannons._21_CM_MORSER_18_2, 30, 40.75), new SpongeEngine(CommonEngines.MAYBACH_HL_234, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), new SpongeRadio(CommonRadios.TEN_WSC, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), SpongeTracks.getTracks(CommonTracks.GW_E_100), SpongeTurrets.getTurret(CommonTurrets.NONE_SPG))),
                //Japanese Mediums
                new SpongeTank(CommonTanks.STB_1, SpongeCountries.getCountry(CommonCountries.JAPAN), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(110, 35, 25), 53, new SpongeModules(new SpongeCannon(CommonCannons._105_MM_RIFLED_GUN, 50, 7.67), new SpongeEngine(CommonEngines.MITSUBISHI_10ZF21WT, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.TYPE_67, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.STB_1), SpongeTurrets.getTurret(CommonTurrets.STB_1))),
                //Japanese Heavies
                new SpongeTank(CommonTanks.TYPE_5_HEAVY, SpongeCountries.getCountry(CommonCountries.JAPAN), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2800, new Armor(260, 140, 150), 25, new SpongeModules(new SpongeCannon(CommonCannons._14_CM_50_3RD_YEAR_TYPE, 60, 16.97), new SpongeEngine(CommonEngines.KAWASAKI_TYPE_98, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.TYPE_3_KO, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.TYPE_5_HEAVY), SpongeTurrets.getTurret(CommonTurrets.TYPE_5_HEAVY))),
                //British Mediums
                new SpongeTank(CommonTanks.FV4202, SpongeCountries.getCountry(CommonCountries.UK), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(121, 51, 32), 40, new SpongeModules(new SpongeCannon(CommonCannons._105_MM_ROYAL_ORDNANCE_L7A1, 72, 8.25), new SpongeEngine(CommonEngines.ROLLS_ROYCE_METEOR_M120A, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.FV4202), SpongeTurrets.getTurret(CommonTurrets.FV4202))),
                //British Heavies
                new SpongeTank(CommonTanks.FV215B, SpongeCountries.getCountry(CommonCountries.UK), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2500, new Armor(152.4, 101.6, 76.2), 34, new SpongeModules(new SpongeCannon(CommonCannons._120_MM_GUN_L1A1, 35, 8.34), new SpongeEngine(CommonEngines.ROLLS_ROYCE_GRIFFON, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.FV215), SpongeTurrets.getTurret(CommonTurrets.FV215))),
                //British TDs
                new SpongeTank(CommonTanks.FV215B_183, SpongeCountries.getCountry(CommonCountries.UK), SpongeTankTypes.getTankType(CommonTankTypes.TD), 2000, new Armor(152.4, 50.8, 76.2), 34, new SpongeModules(new SpongeCannon(CommonCannons._183_MM_L4, 12, 28.77), new SpongeEngine(CommonEngines.ROLLS_ROYCE_METEOR_MK_12, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.FV215B), SpongeTurrets.getTurret(CommonTurrets.FV215B))),
                new SpongeTank(CommonTanks.FV4005_STAGE_II, SpongeCountries.getCountry(CommonCountries.UK), SpongeTankTypes.getTankType(CommonTankTypes.TD), 1850, new Armor(76.2, 50.8, 38.1), 35, new SpongeModules(new SpongeCannon(CommonCannons._183_MM_L4, 12, 28.77), new SpongeEngine(CommonEngines.ROLLS_ROYCE_METEOR_MK_IVB, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.FV4005_STAGE_II), SpongeTurrets.getTurret(CommonTurrets.FV4005_STAGE_II))),
                //British Arty
                new SpongeTank(CommonTanks.CONQUEROR_GC, SpongeCountries.getCountry(CommonCountries.UK), SpongeTankTypes.getTankType(CommonTankTypes.SPG), 530, new Armor(152.4, 50.8, 76.2), 34, new SpongeModules(new SpongeCannon(CommonCannons.BL_9_2_IN_HOWITZER, 24, 55.71), new SpongeEngine(CommonEngines.ROLLS_ROYCE_METEOR_M120, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), new SpongeRadio(CommonRadios.SR_C45, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), SpongeTracks.getTracks(CommonTracks.CONQUEROR_GUN_CARRIAGE), SpongeTurrets.getTurret(CommonTurrets.NONE_SPG))),
                //American Lights
                new SpongeTank(CommonTanks.T49, SpongeCountries.getCountry(CommonCountries.USA), SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), 1000, new Armor(25.4, 25.4, 19.1), 72, new SpongeModules(new SpongeCannon(CommonCannons._90_MM_GUN_T185, 22, 22.05), new SpongeEngine(CommonEngines.CONTINENTAL_AOI_895_5, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpongeRadio(CommonRadios.AN_GRC_7, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), SpongeTracks.getTracks(CommonTracks.XM551_TEST_BED), SpongeTurrets.getTurret(CommonTurrets.XM551_TEST_BED))),
                //American Mediums
                new SpongeTank(CommonTanks.M48A1_PATTON, SpongeCountries.getCountry(CommonCountries.USA), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 2000, new Armor(152.4, 76.2, 38.1), 45, new SpongeModules(new SpongeCannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new SpongeEngine(CommonEngines.CONTINENTAL_AV_1790_7B, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.AN_VRC_7, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.T97E2), SpongeTurrets.getTurret(CommonTurrets.M87))),
                new SpongeTank(CommonTanks.M60, SpongeCountries.getCountry(CommonCountries.USA), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 2000, new Armor(93.2, 73.7, 25.4), 48, new SpongeModules(new SpongeCannon(CommonCannons._105_MM_GUN_M68, 57, 8.44), new SpongeEngine(CommonEngines.CONTINENTAL_AVDS_1790_2, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.AN_VRC_12, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.M60), SpongeTurrets.getTurret(CommonTurrets.M60))),
                //American Heavies
                new SpongeTank(CommonTanks.T110E5, SpongeCountries.getCountry(CommonCountries.USA), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2200, new Armor(254, 76.2, 38.1), 37, new SpongeModules(new SpongeCannon(CommonCannons._120_MM_GUN_M58, 42, 9.59), new SpongeEngine(CommonEngines.CONTINENTAL_AOI_1490, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.AN_VRC_3, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.T110E27), SpongeTurrets.getTurret(CommonTurrets.T53))),
                new SpongeTank(CommonTanks.T57, SpongeCountries.getCountry(CommonCountries.USA), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2250, new Armor(203, 50.8, 38.1), 35, new SpongeModules(new SpongeAutoLoader(CommonCannons._120_MM_GUN_T179, 36, 23.97, 2, 4), new SpongeEngine(CommonEngines.CONTINENTAL_AV_1790_5C, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.AN_VRC_3, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.T97), SpongeTurrets.getTurret(CommonTurrets.T169))),
                //American TDs
                new SpongeTank(CommonTanks.T110E3, SpongeCountries.getCountry(CommonCountries.USA), SpongeTankTypes.getTankType(CommonTankTypes.TD), 2050, new Armor(305, 76, 38), 30, new SpongeModules(new SpongeCannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 16.3), new SpongeEngine(CommonEngines.CONTINENTAL_AOI_1490, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.AN_VRC_3, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.T110E3), SpongeTurrets.getTurret(CommonTurrets.NONE_TD))),
                new SpongeTank(CommonTanks.T110E4, SpongeCountries.getCountry(CommonCountries.USA), SpongeTankTypes.getTankType(CommonTankTypes.TD), 2000, new Armor(260, 76, 38), 35, new SpongeModules(new SpongeCannon(CommonCannons._155_MM_AT_GUN_T7E2, 27, 18.7), new SpongeEngine(CommonEngines.CONTINENTAL_AOI_1490, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.AN_VRC_3, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.T110E4), SpongeTurrets.getTurret(CommonTurrets.T110E4))),
                //American Arty
                new SpongeTank(CommonTanks.T92, SpongeCountries.getCountry(CommonCountries.USA), SpongeTankTypes.getTankType(CommonTankTypes.SPG), 500, new Armor(25.4, 25.4, 21.7), 32, new SpongeModules(new SpongeCannon(CommonCannons._240_MM_HOWITZER, 16, 50.82), new SpongeEngine(CommonEngines.FORD_GAF_T92, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), new SpongeRadio(CommonRadios.SCR_528A, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), SpongeTracks.getTracks(CommonTracks.T92_T80E1), SpongeTurrets.getTurret(CommonTurrets.NONE_SPG))),
                //Russian Lights
                new SpongeTank(CommonTanks.T_54_LTWT, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), 1250, new Armor(80, 60, 45), 69, new SpongeModules(new SpongeCannon(CommonCannons._100_MM_D10T, 34, 7.19), new SpongeEngine(CommonEngines.V_14, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), new SpongeRadio(CommonRadios.TEN_RT_26, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)), SpongeTracks.getTracks(CommonTracks.T_54_ENHANCED_LTWT_VERSION), SpongeTurrets.getTurret(CommonTurrets.T_54_LTWT_VERSION))),
                //Russian Mediums
                new SpongeTank(CommonTanks.OBJECT_140, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1900, new Armor(100, 80, 55), 40, new SpongeModules(new SpongeCannon(CommonCannons._100_MM_8TS, 50, 6.33), new SpongeEngine(CommonEngines.TD_12, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.R_113M, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.OBJECT_140), SpongeTurrets.getTurret(CommonTurrets.OBJECT_140))),
                new SpongeTank(CommonTanks.OBJECT_430, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1900, new Armor(120, 80, 40), 55, new SpongeModules(new SpongeCannon(CommonCannons._100_MM_8TS, 50, 6.14), new SpongeEngine(CommonEngines.FIVE_TD, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.R_113M, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.OBJECT_430), SpongeTurrets.getTurret(CommonTurrets.OBJECT_430))),
                new SpongeTank(CommonTanks.OBJECT_907, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 2000, new Armor(100, 85, 45), 55, new SpongeModules(new SpongeCannon(CommonCannons._100_MM_D_54TS, 45, 6.23), new SpongeEngine(CommonEngines.V_12_5U, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.R_123, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.OBJECT_907), SpongeTurrets.getTurret(CommonTurrets.OBJECT_907))),
                new SpongeTank(CommonTanks.T_62A, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), 1950, new Armor(100, 80, 45), 50, new SpongeModules(new SpongeCannon(CommonCannons._100_MM_8TS, 50, 6.33), new SpongeEngine(CommonEngines.V_55, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), new SpongeRadio(CommonRadios.R_123, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)), SpongeTracks.getTracks(CommonTracks.T_62A), SpongeTurrets.getTurret(CommonTurrets.T_62))),
                //Russian Heavies
                new SpongeTank(CommonTanks.IS_4, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2500, new Armor(140, 160, 100), 43, new SpongeModules(new SpongeCannon(CommonCannons._122_MM_M62_T2, 30, 11.51), new SpongeEngine(CommonEngines.V_12, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.TEN_RK_26, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.IS_4M), SpongeTurrets.getTurret(CommonTurrets.IS_4M))),
                new SpongeTank(CommonTanks.IS_7, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2150, new Armor(150, 150, 100), 60, new SpongeModules(new SpongeCannon(CommonCannons._130_MM_S_70, 30, 13.14), new SpongeEngine(CommonEngines.M_50T, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.TEN_RK_26, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.IS_7), SpongeTurrets.getTurret(CommonTurrets.IS_7))),
                new SpongeTank(CommonTanks.OBJECT_260, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), 2100, new Armor(150, 150, 70), 60, new SpongeModules(new SpongeCannon(CommonCannons._122_MM_BL_13_1, 30, 11.03), new SpongeEngine(CommonEngines.TWO_X_V_16, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), new SpongeRadio(CommonRadios.TEN_RK_26, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)), SpongeTracks.getTracks(CommonTracks.OBJECT_260_MOD_1945), SpongeTurrets.getTurret(CommonTurrets.OBJECT_260_MOD_1945))),
                //Russian TDs
                new SpongeTank(CommonTanks.OBJECT_263, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.TD), 1900, new Armor(250, 80, 50), 55, new SpongeModules(new SpongeCannon(CommonCannons._130_MM_S_70A, 42, 10.26), new SpongeEngine(CommonEngines.M_50T, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.TEN_RK_26, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.OBJECT_263), SpongeTurrets.getTurret(CommonTurrets.NONE_TD))),
                new SpongeTank(CommonTanks.OBJECT_268, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.TD), 1950, new Armor(187, 100, 50), 48, new SpongeModules(new SpongeCannon(CommonCannons._152_MM_M64, 35, 15.82), new SpongeEngine(CommonEngines.V_16FN, SpongeTankTypes.getTankType(CommonTankTypes.TD)), new SpongeRadio(CommonRadios.R_113M, SpongeTankTypes.getTankType(CommonTankTypes.TD)), SpongeTracks.getTracks(CommonTracks.OBJECT_268), SpongeTurrets.getTurret(CommonTurrets.NONE_TD))),
                //Russian Arty
                new SpongeTank(CommonTanks.OBJECT_261, SpongeCountries.getCountry(CommonCountries.USSR), SpongeTankTypes.getTankType(CommonTankTypes.SPG), 510, new Armor(75, 50, 30), 50, new SpongeModules(new SpongeCannon(CommonCannons._180_MM_B_1_P, 18, 33.56), new SpongeEngine(CommonEngines.M_50T, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), new SpongeRadio(CommonRadios.TEN_RK_26, SpongeTankTypes.getTankType(CommonTankTypes.SPG)), SpongeTracks.getTracks(CommonTracks.OBJECT_261), SpongeTurrets.getTurret(CommonTurrets.NONE_SPG))));
    }


    public static SpongeTank getTank(String name) {
        return new SpongeTanks().get(name);
    }

    public static List<SpongeTank> getValues() {
        return new SpongeTanks().values();
    }
}
