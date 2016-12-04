package io.musician101.minetanks.forge.tank.modules.turret;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonTurrets;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.forge.tank.ForgeTankTypes;

import java.util.List;


public class ForgeTurrets extends AbstractInfoStorage<ForgeTurret>
{
    private ForgeTurrets()
    {
        super(
                new ForgeTurret(CommonTurrets.NONE_SPG, ForgeTankTypes.getTankType(CommonTankTypes.SPG), new Armor(0, 0, 0)),
                new ForgeTurret(CommonTurrets.NONE_TD, ForgeTankTypes.getTankType(CommonTankTypes.TD), new Armor(0, 0, 0)),
                new ForgeTurret(CommonTurrets.ONE_21, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 130, 60)),
                new ForgeTurret(CommonTurrets.ONE_13, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 160, 80)),
                new ForgeTurret(CommonTurrets.AMX_30_B, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(80, 40, 30)),
                new ForgeTurret(CommonTurrets.AMX_50_B, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(100, 50, 30)),
                new ForgeTurret(CommonTurrets.BAT_CHAT_25_T, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(50, 30, 15)),
                new ForgeTurret(CommonTurrets.E_50_M, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(185, 80, 80)),
                new ForgeTurret(CommonTurrets.E_100, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(250, 150, 150)),
                new ForgeTurret(CommonTurrets.FL_10_TYPE_D, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(40, 20, 20)),
                new ForgeTurret(CommonTurrets.FV215, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(254, 152.4, 101.6)),
                new ForgeTurret(CommonTurrets.FV215B, ForgeTankTypes.getTankType(CommonTankTypes.TD), new Armor(254, 101.6, 76.2)),
                new ForgeTurret(CommonTurrets.FV4202, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(196, 196, 35)),
                new ForgeTurret(CommonTurrets.FV4005_STAGE_II, ForgeTankTypes.getTankType(CommonTankTypes.TD), new Armor(14, 14, 14)),
                new ForgeTurret(CommonTurrets.IS_4M, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(250, 200, 170)),
                new ForgeTurret(CommonTurrets.IS_7, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 185, 94)),
                new ForgeTurret(CommonTurrets.LEOPARD_1, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(52, 60, 60)),
                new ForgeTurret(CommonTurrets.M60, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(177.8, 76.2, 50.8)),
                new ForgeTurret(CommonTurrets.M87, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(177.8, 76.2, 50.8)),
                new ForgeTurret(CommonTurrets.MAUS, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(240, 210, 210)),
                new ForgeTurret(CommonTurrets.OBJECT_140, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 187, 65)),
                new ForgeTurret(CommonTurrets.OBJECT_260_MOD_1945, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(350, 240, 100)),
                new ForgeTurret(CommonTurrets.OBJECT_430, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(248, 185, 63)),
                new ForgeTurret(CommonTurrets.OBJECT_907, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(228, 180, 46)),
                new ForgeTurret(CommonTurrets.STB_1, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(132, 132, 37)),
                new ForgeTurret(CommonTurrets.SPAHPANZER_RU_251, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(20, 20, 20)),
                new ForgeTurret(CommonTurrets.T_62, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(240, 161, 65)),
                new ForgeTurret(CommonTurrets.T_54_LTWT_VERSION, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(160, 120, 60)),
                new ForgeTurret(CommonTurrets.T53, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(203.2, 127, 76.2)),
                new ForgeTurret(CommonTurrets.T169, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(127, 137, 50.8)),
                new ForgeTurret(CommonTurrets.T110E4, ForgeTankTypes.getTankType(CommonTankTypes.TD), new Armor(203, 152, 152)),
                new ForgeTurret(CommonTurrets.TVP_T_50_51, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM), new Armor(120, 80, 60)),
                new ForgeTurret(CommonTurrets.TYPE_5_HEAVY, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(260, 210, 200)),
                new ForgeTurret(CommonTurrets.VK_7201, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY), new Armor(200, 160, 120)),
                new ForgeTurret(CommonTurrets.WAFFENTRAGER_E_100, ForgeTankTypes.getTankType(CommonTankTypes.TD), new Armor(20, 10, 8)),
                new ForgeTurret(CommonTurrets.WZ_132, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(55, 35, 20)),
                new ForgeTurret(CommonTurrets.XM551_TEST_BED, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT), new Armor(38.1, 15.9, 15.9)));
    }

    public static ForgeTurret getTurret(String name)
    {
        return new ForgeTurrets().get(name);
    }


    public List<ForgeTurret> getValues()
    {
        return new ForgeTurrets().values();
    }
}
