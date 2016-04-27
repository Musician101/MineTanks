package io.musician101.minetanks.spigot.tank.modules.turret;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonTurrets;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;

public class SpigotTurrets
{
    public static final SpigotTurret NO_TURRET_ARTY = new SpigotTurret(CommonTurrets.NONE, SpigotTankTypes.getTankType(CommonTankTypes.SPG), new Armor(0, 0, 0));
    public static final SpigotTurret NO_TURRET_TD = new SpigotTurret(CommonTurrets.NONE, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(0, 0, 0));
    public static final SpigotTurret _121 = new SpigotTurret(CommonTurrets._121, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 130, 60));
    public static final SpigotTurret _113 = new SpigotTurret(CommonTurrets._113, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 160, 80));
    public static final SpigotTurret AMX_30_B = new SpigotTurret(CommonTurrets.AMX_30_B, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(80, 40, 30));
    public static final SpigotTurret AMX_50_B = new SpigotTurret(CommonTurrets.AMX_50_B, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(100, 50, 30));
    public static final SpigotTurret BAT_CHAT_25_T = new SpigotTurret(CommonTurrets.BAT_CHAT_25_T, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(50, 30, 15));
    public static final SpigotTurret E_50_M = new SpigotTurret(CommonTurrets.E_50_M, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(185, 80, 80));
    public static final SpigotTurret E_100 = new SpigotTurret(CommonTurrets.E_100, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(250, 150, 150));
    public static final SpigotTurret FL_10 = new SpigotTurret(CommonTurrets.FL_10_TYPE_D, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(40, 20, 20));
    public static final SpigotTurret FV215 = new SpigotTurret(CommonTurrets.FV215, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(254, 152.4, 101.6));
    public static final SpigotTurret FV215B = new SpigotTurret(CommonTurrets.FV215B, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(254, 101.6, 76.2));
    public static final SpigotTurret FV4202 = new SpigotTurret(CommonTurrets.FV4202, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(196, 196, 35));
    public static final SpigotTurret FV4005_STAGE_II = new SpigotTurret(CommonTurrets.FV4005_STAGE_II, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(14, 14, 14));
    public static final SpigotTurret IS_4M = new SpigotTurret(CommonTurrets.IS_4M, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(250, 200, 170));
    public static final SpigotTurret IS_7 = new SpigotTurret(CommonTurrets.IS_7, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 185, 94));
    public static final SpigotTurret LEOPARD_1 = new SpigotTurret(CommonTurrets.LEOPARD_1, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(52, 60, 60));
    public static final SpigotTurret M60 = new SpigotTurret(CommonTurrets.M60, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(177.8, 76.2, 50.8));
    public static final SpigotTurret M87 = new SpigotTurret(CommonTurrets.M87, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(177.8, 76.2, 50.8));
    public static final SpigotTurret MAUS = new SpigotTurret(CommonTurrets.MAUS, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 210, 210));
    public static final SpigotTurret OBJECT_140 = new SpigotTurret(CommonTurrets.OBJECT_140, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 187, 65));
    public static final SpigotTurret OBJECT_260_MOD_1945 = new SpigotTurret(CommonTurrets.OBJECT_260_MOD_1946, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(350, 240, 100));
    public static final SpigotTurret OBJECT_430 = new SpigotTurret(CommonTurrets.OBJECT_430, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(248, 185, 63));
    public static final SpigotTurret OBJECT_907 = new SpigotTurret(CommonTurrets.OBJECT_907, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(228, 180, 46));
    public static final SpigotTurret STB_1 = new SpigotTurret(CommonTurrets.STB_1, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(132, 132, 37));
    public static final SpigotTurret SPAHPANZER_RU_251_DREHTURM_360 = new SpigotTurret(CommonTurrets.SPAHPANZER_RU_251, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(20, 20, 20));
    public static final SpigotTurret T_62A = new SpigotTurret(CommonTurrets.T_62, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 161, 65));
    public static final SpigotTurret T_54_LTWT_VERSION = new SpigotTurret(CommonTurrets.T_54_LTWT_VERSION, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(160, 120, 60));
    public static final SpigotTurret T53 = new SpigotTurret(CommonTurrets.T53, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(203.2, 127, 76.2));
    public static final SpigotTurret T169 = new SpigotTurret(CommonTurrets.T169, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(127, 137, 50.8));
    public static final SpigotTurret T110E4 = new SpigotTurret(CommonTurrets.T110E4, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(203, 152, 152));
    public static final SpigotTurret TVP_T_50_51 = new SpigotTurret(CommonTurrets.TVP_T_50_51, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(120, 80, 60));
    public static final SpigotTurret TYPE_5_HEAVY = new SpigotTurret(CommonTurrets.TYPE_5_HEAVY, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(260, 210, 200));
    public static final SpigotTurret VK_7201 = new SpigotTurret(CommonTurrets.VK_7201, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(200, 160, 120));
    public static final SpigotTurret WAFFENTRAGER_E_100 = new SpigotTurret(CommonTurrets.WAFFENTRAGER_E_100, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(20, 10, 8));
    public static final SpigotTurret WZ_132 = new SpigotTurret(CommonTurrets.WZ_132, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(55, 35, 20));
    public static final SpigotTurret XM551_TEST_BED = new SpigotTurret(CommonTurrets.XM551_TEST_BED, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(38.1, 15.9, 15.9));

    private SpigotTurrets()
    {

    }
}
