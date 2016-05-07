package io.musician101.minetanks.sponge.tank.module.turret;

import io.musician101.minetanks.common.CommonReference.CommonTurrets;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.sponge.tank.SpongeTankTypes;

public class SpongeTurrets extends AbstractInfoStorage<SpongeTurret>
{
    public static final SpongeTurret NO_TURRET_ARTY = new SpongeTurret(CommonTurrets.NONE_SPG, SpongeTankTypes.ARTY, new Armor(0, 0, 0));
    public static final SpongeTurret NO_TURRET_TD = new SpongeTurret(CommonTurrets.NONE_TD, SpongeTankTypes.TD, new Armor(0, 0, 0));
    public static final SpongeTurret _121 = new SpongeTurret(CommonTurrets._121, SpongeTankTypes.MEDIUM, new Armor(240, 130, 60));
    public static final SpongeTurret _113 = new SpongeTurret(CommonTurrets._113, SpongeTankTypes.HEAVY, new Armor(240, 160, 80));
    public static final SpongeTurret AMX_30_B = new SpongeTurret(CommonTurrets.AMX_30_B, SpongeTankTypes.MEDIUM, new Armor(80, 40, 30));
    public static final SpongeTurret AMX_50_B = new SpongeTurret(CommonTurrets.AMX_50_B, SpongeTankTypes.HEAVY, new Armor(100, 50, 30));
    public static final SpongeTurret BAT_CHAT_25_T = new SpongeTurret(CommonTurrets.BAT_CHAT_25_T, SpongeTankTypes.MEDIUM, new Armor(50, 30, 15));
    public static final SpongeTurret E_50_M = new SpongeTurret(CommonTurrets.E_50_M, SpongeTankTypes.MEDIUM, new Armor(185, 80, 80));
    public static final SpongeTurret E_100 = new SpongeTurret(CommonTurrets.E_100, SpongeTankTypes.HEAVY, new Armor(250, 150, 150));
    public static final SpongeTurret FL_10 = new SpongeTurret(CommonTurrets.FL_10_TYPE_D, SpongeTankTypes.LIGHT, new Armor(40, 20, 20));
    public static final SpongeTurret FV215 = new SpongeTurret(CommonTurrets.FV215, SpongeTankTypes.HEAVY, new Armor(254, 152.4, 101.6));
    public static final SpongeTurret FV215B = new SpongeTurret(CommonTurrets.FV215B, SpongeTankTypes.TD, new Armor(254, 101.6, 76.2));
    public static final SpongeTurret FV4202 = new SpongeTurret(CommonTurrets.FV4202, SpongeTankTypes.MEDIUM, new Armor(196, 196, 35));
    public static final SpongeTurret FV4005_STAGE_II = new SpongeTurret(CommonTurrets.FV4005_STAGE_II, SpongeTankTypes.TD, new Armor(14, 14, 14));
    public static final SpongeTurret IS_4M = new SpongeTurret(CommonTurrets.IS_4M, SpongeTankTypes.HEAVY, new Armor(250, 200, 170));
    public static final SpongeTurret IS_7 = new SpongeTurret(CommonTurrets.IS_7, SpongeTankTypes.HEAVY, new Armor(240, 185, 94));
    public static final SpongeTurret LEOPARD_1 = new SpongeTurret(CommonTurrets.LEOPARD_1, SpongeTankTypes.MEDIUM, new Armor(52, 60, 60));
    public static final SpongeTurret M60 = new SpongeTurret(CommonTurrets.M60, SpongeTankTypes.MEDIUM, new Armor(177.8, 76.2, 50.8));
    public static final SpongeTurret M87 = new SpongeTurret(CommonTurrets.M87, SpongeTankTypes.MEDIUM, new Armor(177.8, 76.2, 50.8));
    public static final SpongeTurret MAUS = new SpongeTurret(CommonTurrets.MAUS, SpongeTankTypes.HEAVY, new Armor(240, 210, 210));
    public static final SpongeTurret OBJECT_140 = new SpongeTurret(CommonTurrets.OBJECT_140, SpongeTankTypes.MEDIUM, new Armor(240, 187, 65));
    public static final SpongeTurret OBJECT_260_MOD_1945 = new SpongeTurret(CommonTurrets.OBJECT_260_MOD_1945, SpongeTankTypes.HEAVY, new Armor(350, 240, 100));
    public static final SpongeTurret OBJECT_430 = new SpongeTurret(CommonTurrets.OBJECT_430, SpongeTankTypes.MEDIUM, new Armor(248, 185, 63));
    public static final SpongeTurret OBJECT_907 = new SpongeTurret(CommonTurrets.OBJECT_907, SpongeTankTypes.MEDIUM, new Armor(228, 180, 46));
    public static final SpongeTurret STB_1 = new SpongeTurret(CommonTurrets.STB_1, SpongeTankTypes.MEDIUM, new Armor(132, 132, 37));
    public static final SpongeTurret SPAHPANZER_RU_251_DREHTURM_360 = new SpongeTurret(CommonTurrets.SPAHPANZER_RU_251, SpongeTankTypes.LIGHT, new Armor(20, 20, 20));
    public static final SpongeTurret T_62A = new SpongeTurret(CommonTurrets.T_62, SpongeTankTypes.MEDIUM, new Armor(240, 161, 65));
    public static final SpongeTurret T_54_LTWT_VERSION = new SpongeTurret(CommonTurrets.T_54_LTWT_VERSION, SpongeTankTypes.LIGHT, new Armor(160, 120, 60));
    public static final SpongeTurret T53 = new SpongeTurret(CommonTurrets.T53, SpongeTankTypes.HEAVY, new Armor(203.2, 127, 76.2));
    public static final SpongeTurret T169 = new SpongeTurret(CommonTurrets.T169, SpongeTankTypes.HEAVY, new Armor(127, 137, 50.8));
    public static final SpongeTurret T110E4 = new SpongeTurret(CommonTurrets.T110E4, SpongeTankTypes.TD, new Armor(203, 152, 152));
    public static final SpongeTurret TVP_T_50_51 = new SpongeTurret(CommonTurrets.TVP_T_50_51, SpongeTankTypes.MEDIUM, new Armor(120, 80, 60));
    public static final SpongeTurret TYPE_5_HEAVY = new SpongeTurret(CommonTurrets.TYPE_5_HEAVY, SpongeTankTypes.HEAVY, new Armor(260, 210, 200));
    public static final SpongeTurret VK_7201 = new SpongeTurret(CommonTurrets.VK_7201, SpongeTankTypes.HEAVY, new Armor(200, 160, 120));
    public static final SpongeTurret WAFFENTRAGER_E_100 = new SpongeTurret(CommonTurrets.WAFFENTRAGER_E_100, SpongeTankTypes.TD, new Armor(20, 10, 8));
    public static final SpongeTurret WZ_132 = new SpongeTurret(CommonTurrets.WZ_132, SpongeTankTypes.LIGHT, new Armor(55, 35, 20));
    public static final SpongeTurret XM551_TEST_BED = new SpongeTurret(CommonTurrets.XM551_TEST_BED, SpongeTankTypes.LIGHT, new Armor(38.1, 15.9, 15.9));
}
