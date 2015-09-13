package musician101.minetanks.spigot.tank.modules.turret;

import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.spigot.tank.TankTypes;

public class Turrets
{
    /**
     * Tanks w/o Turrets
     */
    public static final Turret NO_TURRET_TD = new Turret("None", TankTypes.TD, new Armor(0, 0, 0));
    public static final Turret NO_TURRET_ARTY = new Turret("None", TankTypes.ARTY, new Armor(0, 0, 0));
    /**
     * Chinese Light Turrets
     */
    public static final Turret FT_18 = new Turret("FT-18", TankTypes.LIGHT, new Armor(22, 22, 16));
    public static final Turret T_26_MODEL_1936 = new Turret("T-26 model 1936", TankTypes.LIGHT, new Armor(30, 15, 15));
    public static final Turret TYPE_97_KAI = new Turret("Type 97-Kai", TankTypes.LIGHT, new Armor(25, 25, 25));
    public static final Turret M5A1_C = new Turret("M5A1", TankTypes.LIGHT, new Armor(44, 31, 31));
    public static final Turret _131_1 = new Turret("131-1", TankTypes.LIGHT, new Armor(45, 30, 20));
    public static final Turret _132B = new Turret("132B", TankTypes.LIGHT, new Armor(45, 35, 20));
    public static final Turret WZ_132 = new Turret("WZ-132", TankTypes.LIGHT, new Armor(55, 35, 20));
    /**
     * Chinese Medium Turrets
     */
    public static final Turret TYPE_T_34M = new Turret("Type T-34M", TankTypes.MEDIUM, new Armor(52, 52, 45));
    public static final Turret TYPE_58 = new Turret("Type 58", TankTypes.MEDIUM, new Armor(90, 75, 52));
    public static final Turret T_34_1_MODEL_2 = new Turret("T-34_1 model 2", TankTypes.MEDIUM, new Armor(170, 120, 60));
    public static final Turret T_34_2_MODEL_2 = new Turret("T-34-2 model 2", TankTypes.MEDIUM, new Armor(180, 120, 60));
    public static final Turret WZ_120_1 = new Turret("WZ-120-1", TankTypes.MEDIUM, new Armor(220, 160, 60));
    public static final Turret _121 = new Turret("121", TankTypes.MEDIUM, new Armor(240, 130, 60));
    /**
     * Chinese Heavy Turrets
     */
    public static final Turret IS_2_LATE = new Turret("IS-2 late", TankTypes.HEAVY, new Armor(100, 90, 90));
    public static final Turret T_10 = new Turret("T-10", TankTypes.HEAVY, new Armor(201, 148, 50));
    public static final Turret WZ_111 = new Turret("WZ-111", TankTypes.HEAVY, new Armor(230, 120, 60));
    public static final Turret _113 = new Turret("113", TankTypes.HEAVY, new Armor(240, 160, 80));
    /**
     * French Light Turrets
     */
    public static final Turret FL_10 = new Turret("FL 10 Type D", TankTypes.LIGHT, new Armor(40, 20, 20));
    /**
     * French Medium Turrets
     */
    public static final Turret AMX_30_B = new Turret("AMX 30 B", TankTypes.MEDIUM, new Armor(80, 40, 30));
    public static final Turret BAT_CHAT_25_T = new Turret("Batignolles-Chatillon 25 t", TankTypes.MEDIUM, new Armor(50, 30, 15));
    /**
     * French Heavy Turrets
     */
    public static final Turret AMX_50_B = new Turret("AMX 50 B", TankTypes.HEAVY, new Armor(100, 50, 30));
    /**
     * German Light Turrets
     */
    public static final Turret SPAHPANZER_RU_251_DREHTURM_360 = new Turret("Spahpanzer Ru 251 Drehturm 360", TankTypes.LIGHT, new Armor(20, 20, 20));
    /**
     * German Medium Turrets
     */
    public static final Turret E_50_M = new Turret("E 50 Ausf. M", TankTypes.MEDIUM, new Armor(185, 80, 80));
    public static final Turret LEOPARD_1 = new Turret("Leopard 1", TankTypes.MEDIUM, new Armor(52, 60, 60));
    /**
     * German Heavy Turrets
     */
    public static final Turret E_100 = new Turret("E 100", TankTypes.HEAVY, new Armor(250, 150, 150));
    public static final Turret MAUS = new Turret("MAUS", TankTypes.HEAVY, new Armor(240, 210, 210));
    public static final Turret VK_7201 = new Turret("VK 72.01 (K)", TankTypes.HEAVY, new Armor(200, 160, 120));
    /**
     * German TD Turrets
     */
    public static final Turret WAFFENTRAGER_E_100 = new Turret("Waffentrager auf E 100", TankTypes.TD, new Armor(20, 10, 8));
    /**
     * Japanese Medium Turrets
     */
    public static final Turret STB_1 = new Turret("STB-1", TankTypes.MEDIUM, new Armor(132, 132, 37));
    /**
     * Japanese Heavy Turrets
     */
    public static final Turret TYPE_5_HEAVY = new Turret("Type 5 Heavy", TankTypes.HEAVY, new Armor(260, 210, 200));
    /**
     * British Medium Turrets
     */
    public static final Turret FV4202 = new Turret("FV4202", TankTypes.MEDIUM, new Armor(196, 196, 35));
    /**
     * British Heavy Turrets
     */
    public static final Turret FV215 = new Turret("FV215", TankTypes.HEAVY, new Armor(254, 152.4, 101.6));
    /**
     * British TD Turrets
     */
    public static final Turret FV215B = new Turret("FV215b", TankTypes.TD, new Armor(254, 101.6, 76.2));
    public static final Turret FV4005_STAGE_II = new Turret("FV4005 Stage II", TankTypes.TD, new Armor(14, 14, 14));
    /**
     * American Light Turrets
     */
    public static final Turret XM551_TEST_BED = new Turret("XM551 test bed", TankTypes.LIGHT, new Armor(38.1, 15.9, 15.9));
    /**
     * American Medium Turrets
     */
    public static final Turret M60 = new Turret("M60", TankTypes.MEDIUM, new Armor(177.8, 76.2, 50.8));
    public static final Turret M87 = new Turret("M87", TankTypes.MEDIUM, new Armor(177.8, 76.2, 50.8));
    /**
     * American Heavy Turrets
     */
    public static final Turret T169 = new Turret("T169", TankTypes.HEAVY, new Armor(127, 137, 50.8));
    public static final Turret T53 = new Turret("T53", TankTypes.HEAVY, new Armor(203.2, 127, 76.2));
    /**
     * American TD Turrets
     */
    public static final Turret T110E4 = new Turret("T110E4", TankTypes.TD, new Armor(203, 152, 152));
    /**
     * Russian Light Turrets
     */
    public static final Turret T_54_LTWT_VERSION = new Turret("T-54 ltwt. version", TankTypes.LIGHT, new Armor(160, 120, 60));
    /**
     * Russian Medium Turrets
     */
    public static final Turret OBJECT_140 = new Turret("Object 140", TankTypes.MEDIUM, new Armor(240, 187, 65));
    public static final Turret OBJECT_430 = new Turret("Object 430", TankTypes.MEDIUM, new Armor(248, 185, 63));
    public static final Turret OBJECT_907 = new Turret("Object 907", TankTypes.MEDIUM, new Armor(228, 180, 46));
    public static final Turret T_62A = new Turret("T-62A", TankTypes.MEDIUM, new Armor(240, 161, 65));
    /**
     * Russian Heavy Turrets
     */
    public static final Turret IS_4M = new Turret("IS-4M", TankTypes.HEAVY, new Armor(250, 200, 170));
    public static final Turret IS_7 = new Turret("IS-7", TankTypes.HEAVY, new Armor(240, 185, 94));
    public static final Turret OBJECT_260_MOD_1945 = new Turret("Object 260 mod 1946", TankTypes.HEAVY, new Armor(350, 240, 100));
}
