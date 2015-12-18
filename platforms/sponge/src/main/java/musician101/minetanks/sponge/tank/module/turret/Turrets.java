package musician101.minetanks.sponge.tank.module.turret;

import musician101.minetanks.common.CommonReference.CommonTurrets;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.sponge.tank.SpongeTankTypes;

public class Turrets
{
    /**
     * Tanks w/o Turrets
     */
    public static final Turret NO_TURRET_TD = new Turret(CommonTurrets.NONE, SpongeTankTypes.TD, new Armor(0, 0, 0));
    public static final Turret NO_TURRET_ARTY = new Turret(CommonTurrets.NONE, SpongeTankTypes.ARTY, new Armor(0, 0, 0));
    /**
     * Chinese Light Turrets
     */
    public static final Turret WZ_132 = new Turret(CommonTurrets.WZ_132, SpongeTankTypes.LIGHT, new Armor(55, 35, 20));
    /**
     * Chinese Medium Turrets
     */
    public static final Turret _121 = new Turret(CommonTurrets._121, SpongeTankTypes.MEDIUM, new Armor(240, 130, 60));
    /**
     * Chinese Heavy Turrets
     */
    public static final Turret _113 = new Turret(CommonTurrets._113, SpongeTankTypes.HEAVY, new Armor(240, 160, 80));
    /**
     * French Light Turrets
     */
    public static final Turret FL_10 = new Turret(CommonTurrets.FL_10_TYPE_D, SpongeTankTypes.LIGHT, new Armor(40, 20, 20));
    /**
     * French Medium Turrets
     */
    public static final Turret AMX_30_B = new Turret(CommonTurrets.AMX_30_B, SpongeTankTypes.MEDIUM, new Armor(80, 40, 30));
    public static final Turret BAT_CHAT_25_T = new Turret(CommonTurrets.BAT_CHAT_25_T, SpongeTankTypes.MEDIUM, new Armor(50, 30, 15));
    /**
     * French Heavy Turrets
     */
    public static final Turret AMX_50_B = new Turret(CommonTurrets.AMX_50_B, SpongeTankTypes.HEAVY, new Armor(100, 50, 30));
    /**
     * German Light Turrets
     */
    public static final Turret SPAHPANZER_RU_251_DREHTURM_360 = new Turret(CommonTurrets.SPAHPANZER_RU_251, SpongeTankTypes.LIGHT, new Armor(20, 20, 20));
    /**
     * German Medium Turrets
     */
    public static final Turret E_50_M = new Turret(CommonTurrets.E_50_M, SpongeTankTypes.MEDIUM, new Armor(185, 80, 80));
    public static final Turret LEOPARD_1 = new Turret(CommonTurrets.LEOPARD_1, SpongeTankTypes.MEDIUM, new Armor(52, 60, 60));
    /**
     * German Heavy Turrets
     */
    public static final Turret E_100 = new Turret(CommonTurrets.E_100, SpongeTankTypes.HEAVY, new Armor(250, 150, 150));
    public static final Turret MAUS = new Turret(CommonTurrets.MAUS, SpongeTankTypes.HEAVY, new Armor(240, 210, 210));
    public static final Turret VK_7201 = new Turret(CommonTurrets.VK_7201, SpongeTankTypes.HEAVY, new Armor(200, 160, 120));
    /**
     * German TD Turrets
     */
    public static final Turret WAFFENTRAGER_E_100 = new Turret(CommonTurrets.WAFFENTRAGER_E_100, SpongeTankTypes.TD, new Armor(20, 10, 8));
    /**
     * Japanese Medium Turrets
     */
    public static final Turret STB_1 = new Turret(CommonTurrets.STB_1, SpongeTankTypes.MEDIUM, new Armor(132, 132, 37));
    /**
     * Japanese Heavy Turrets
     */
    public static final Turret TYPE_5_HEAVY = new Turret(CommonTurrets.TYPE_5_HEAVY, SpongeTankTypes.HEAVY, new Armor(260, 210, 200));
    /**
     * British Medium Turrets
     */
    public static final Turret FV4202 = new Turret(CommonTurrets.FV4202, SpongeTankTypes.MEDIUM, new Armor(196, 196, 35));
    /**
     * British Heavy Turrets
     */
    public static final Turret FV215 = new Turret(CommonTurrets.FV215, SpongeTankTypes.HEAVY, new Armor(254, 152.4, 101.6));
    /**
     * British TD Turrets
     */
    public static final Turret FV215B = new Turret(CommonTurrets.FV215B, SpongeTankTypes.TD, new Armor(254, 101.6, 76.2));
    public static final Turret FV4005_STAGE_II = new Turret(CommonTurrets.FV4005_STAGE_II, SpongeTankTypes.TD, new Armor(14, 14, 14));
    /**
     * American Light Turrets
     */
    public static final Turret XM551_TEST_BED = new Turret(CommonTurrets.XM551_TEST_BED, SpongeTankTypes.LIGHT, new Armor(38.1, 15.9, 15.9));
    /**
     * American Medium Turrets
     */
    public static final Turret M60 = new Turret(CommonTurrets.M60, SpongeTankTypes.MEDIUM, new Armor(177.8, 76.2, 50.8));
    public static final Turret M87 = new Turret(CommonTurrets.M87, SpongeTankTypes.MEDIUM, new Armor(177.8, 76.2, 50.8));
    /**
     * American Heavy Turrets
     */
    public static final Turret T169 = new Turret(CommonTurrets.T169, SpongeTankTypes.HEAVY, new Armor(127, 137, 50.8));
    public static final Turret T53 = new Turret(CommonTurrets.T53, SpongeTankTypes.HEAVY, new Armor(203.2, 127, 76.2));
    /**
     * American TD Turrets
     */
    public static final Turret T110E4 = new Turret(CommonTurrets.T110E4, SpongeTankTypes.TD, new Armor(203, 152, 152));
    /**
     * Russian Light Turrets
     */
    public static final Turret T_54_LTWT_VERSION = new Turret(CommonTurrets.T_54_LTWT_VERSION, SpongeTankTypes.LIGHT, new Armor(160, 120, 60));
    /**
     * Russian Medium Turrets
     */
    public static final Turret OBJECT_140 = new Turret(CommonTurrets.OBJECT_140, SpongeTankTypes.MEDIUM, new Armor(240, 187, 65));
    public static final Turret OBJECT_430 = new Turret(CommonTurrets.OBJECT_430, SpongeTankTypes.MEDIUM, new Armor(248, 185, 63));
    public static final Turret OBJECT_907 = new Turret(CommonTurrets.OBJECT_907, SpongeTankTypes.MEDIUM, new Armor(228, 180, 46));
    public static final Turret T_62A = new Turret(CommonTurrets.T_62, SpongeTankTypes.MEDIUM, new Armor(240, 161, 65));
    /**
     * Russian Heavy Turrets
     */
    public static final Turret IS_4M = new Turret(CommonTurrets.IS_4M, SpongeTankTypes.HEAVY, new Armor(250, 200, 170));
    public static final Turret IS_7 = new Turret(CommonTurrets.IS_7, SpongeTankTypes.HEAVY, new Armor(240, 185, 94));
    public static final Turret OBJECT_260_MOD_1945 = new Turret(CommonTurrets.OBJECT_260_MOD_1946, SpongeTankTypes.HEAVY, new Armor(350, 240, 100));
}
