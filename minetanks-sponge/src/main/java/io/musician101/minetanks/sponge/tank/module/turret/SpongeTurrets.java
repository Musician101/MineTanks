package io.musician101.minetanks.sponge.tank.module.turret;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonTurrets;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.sponge.tank.SpongeTankTypes;


public class SpongeTurrets extends AbstractInfoStorage<SpongeTurret> {

    private SpongeTurrets() {
        super(
                new SpongeTurret(CommonTurrets.NONE_SPG, SpongeTankTypes.getTankType(CommonTankTypes.SPG), new Armor(0, 0, 0)),
                new SpongeTurret(CommonTurrets.NONE_TD, SpongeTankTypes.getTankType(CommonTankTypes.TD), new Armor(0, 0, 0)),
                new SpongeTurret(CommonTurrets.ONE_21, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 130, 60)),
                new SpongeTurret(CommonTurrets.ONE_13, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 160, 80)),
                new SpongeTurret(CommonTurrets.AMX_30_B, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(80, 40, 30)),
                new SpongeTurret(CommonTurrets.AMX_50_B, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(100, 50, 30)),
                new SpongeTurret(CommonTurrets.BAT_CHAT_25_T, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(50, 30, 15)),
                new SpongeTurret(CommonTurrets.E_50_M, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(185, 80, 80)),
                new SpongeTurret(CommonTurrets.E_100, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(250, 150, 150)),
                new SpongeTurret(CommonTurrets.FL_10_TYPE_D, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(40, 20, 20)),
                new SpongeTurret(CommonTurrets.FV215, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(254, 152.4, 101.6)),
                new SpongeTurret(CommonTurrets.FV215B, SpongeTankTypes.getTankType(CommonTankTypes.TD), new Armor(254, 101.6, 76.2)),
                new SpongeTurret(CommonTurrets.FV4202, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(196, 196, 35)),
                new SpongeTurret(CommonTurrets.FV4005_STAGE_II, SpongeTankTypes.getTankType(CommonTankTypes.TD), new Armor(14, 14, 14)),
                new SpongeTurret(CommonTurrets.IS_4M, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(250, 200, 170)),
                new SpongeTurret(CommonTurrets.IS_7, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 185, 94)),
                new SpongeTurret(CommonTurrets.LEOPARD_1, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(52, 60, 60)),
                new SpongeTurret(CommonTurrets.M60, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(177.8, 76.2, 50.8)),
                new SpongeTurret(CommonTurrets.M87, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(177.8, 76.2, 50.8)),
                new SpongeTurret(CommonTurrets.MAUS, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 210, 210)),
                new SpongeTurret(CommonTurrets.OBJECT_140, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 187, 65)),
                new SpongeTurret(CommonTurrets.OBJECT_260_MOD_1945, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(350, 240, 100)),
                new SpongeTurret(CommonTurrets.OBJECT_430, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(248, 185, 63)),
                new SpongeTurret(CommonTurrets.OBJECT_907, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(228, 180, 46)),
                new SpongeTurret(CommonTurrets.STB_1, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(132, 132, 37)),
                new SpongeTurret(CommonTurrets.SPAHPANZER_RU_251, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(20, 20, 20)),
                new SpongeTurret(CommonTurrets.T_62, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 161, 65)),
                new SpongeTurret(CommonTurrets.T_54_LTWT_VERSION, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(160, 120, 60)),
                new SpongeTurret(CommonTurrets.T53, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(203.2, 127, 76.2)),
                new SpongeTurret(CommonTurrets.T169, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(127, 137, 50.8)),
                new SpongeTurret(CommonTurrets.T110E4, SpongeTankTypes.getTankType(CommonTankTypes.TD), new Armor(203, 152, 152)),
                new SpongeTurret(CommonTurrets.TVP_T_50_51, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(120, 80, 60)),
                new SpongeTurret(CommonTurrets.TYPE_5_HEAVY, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(260, 210, 200)),
                new SpongeTurret(CommonTurrets.VK_7201, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(200, 160, 120)),
                new SpongeTurret(CommonTurrets.WAFFENTRAGER_E_100, SpongeTankTypes.getTankType(CommonTankTypes.TD), new Armor(20, 10, 8)),
                new SpongeTurret(CommonTurrets.WZ_132, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(55, 35, 20)),
                new SpongeTurret(CommonTurrets.XM551_TEST_BED, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(38.1, 15.9, 15.9)));
    }


    public static SpongeTurret getTurret(String name) {
        return new SpongeTurrets().get(name);
    }
}
