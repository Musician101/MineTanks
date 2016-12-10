package io.musician101.minetanks.spigot.tank.modules.turret;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonTurrets;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;
import java.util.List;


public class SpigotTurrets extends AbstractInfoStorage<SpigotTurret> {

    private SpigotTurrets() {
        super(
                new SpigotTurret(CommonTurrets.NONE_SPG, SpigotTankTypes.getTankType(CommonTankTypes.SPG), new Armor(0, 0, 0)),
                new SpigotTurret(CommonTurrets.NONE_TD, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(0, 0, 0)),
                new SpigotTurret(CommonTurrets.ONE_21, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 130, 60)),
                new SpigotTurret(CommonTurrets.ONE_13, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 160, 80)),
                new SpigotTurret(CommonTurrets.AMX_30_B, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(80, 40, 30)),
                new SpigotTurret(CommonTurrets.AMX_50_B, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(100, 50, 30)),
                new SpigotTurret(CommonTurrets.BAT_CHAT_25_T, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(50, 30, 15)),
                new SpigotTurret(CommonTurrets.E_50_M, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(185, 80, 80)),
                new SpigotTurret(CommonTurrets.E_100, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(250, 150, 150)),
                new SpigotTurret(CommonTurrets.FL_10_TYPE_D, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(40, 20, 20)),
                new SpigotTurret(CommonTurrets.FV215, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(254, 152.4, 101.6)),
                new SpigotTurret(CommonTurrets.FV215B, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(254, 101.6, 76.2)),
                new SpigotTurret(CommonTurrets.FV4202, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(196, 196, 35)),
                new SpigotTurret(CommonTurrets.FV4005_STAGE_II, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(14, 14, 14)),
                new SpigotTurret(CommonTurrets.IS_4M, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(250, 200, 170)),
                new SpigotTurret(CommonTurrets.IS_7, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 185, 94)),
                new SpigotTurret(CommonTurrets.LEOPARD_1, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(52, 60, 60)),
                new SpigotTurret(CommonTurrets.M60, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(177.8, 76.2, 50.8)),
                new SpigotTurret(CommonTurrets.M87, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(177.8, 76.2, 50.8)),
                new SpigotTurret(CommonTurrets.MAUS, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 210, 210)),
                new SpigotTurret(CommonTurrets.OBJECT_140, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 187, 65)),
                new SpigotTurret(CommonTurrets.OBJECT_260_MOD_1945, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(350, 240, 100)),
                new SpigotTurret(CommonTurrets.OBJECT_430, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(248, 185, 63)),
                new SpigotTurret(CommonTurrets.OBJECT_907, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(228, 180, 46)),
                new SpigotTurret(CommonTurrets.STB_1, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(132, 132, 37)),
                new SpigotTurret(CommonTurrets.SPAHPANZER_RU_251, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(20, 20, 20)),
                new SpigotTurret(CommonTurrets.T_62, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 161, 65)),
                new SpigotTurret(CommonTurrets.T_54_LTWT_VERSION, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(160, 120, 60)),
                new SpigotTurret(CommonTurrets.T53, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(203.2, 127, 76.2)),
                new SpigotTurret(CommonTurrets.T169, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(127, 137, 50.8)),
                new SpigotTurret(CommonTurrets.T110E4, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(203, 152, 152)),
                new SpigotTurret(CommonTurrets.TVP_T_50_51, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(120, 80, 60)),
                new SpigotTurret(CommonTurrets.TYPE_5_HEAVY, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(260, 210, 200)),
                new SpigotTurret(CommonTurrets.VK_7201, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(200, 160, 120)),
                new SpigotTurret(CommonTurrets.WAFFENTRAGER_E_100, SpigotTankTypes.getTankType(CommonTankTypes.TD), new Armor(20, 10, 8)),
                new SpigotTurret(CommonTurrets.WZ_132, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(55, 35, 20)),
                new SpigotTurret(CommonTurrets.XM551_TEST_BED, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(38.1, 15.9, 15.9)));
    }

    public static SpigotTurret getTurret(String name) {
        return new SpigotTurrets().get(name);
    }


    public List<SpigotTurret> getValues() {
        return new SpigotTurrets().values();
    }
}
