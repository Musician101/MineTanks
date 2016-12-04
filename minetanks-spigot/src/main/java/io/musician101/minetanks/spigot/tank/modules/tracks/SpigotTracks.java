package io.musician101.minetanks.spigot.tank.modules.tracks;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonTracks;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;

import java.util.List;


public class SpigotTracks extends AbstractInfoStorage<SpigotTrackz>
{
    private SpigotTracks()
    {
        super(
                new SpigotTrackz(CommonTracks.ONE_21, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.ONE_13, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.AMX_13_TYPE_2D, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new SpigotTrackz(CommonTracks.AMX_30_B, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.AMX_50_B, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.AMX_50_FOCH_155, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.BAT_CHAT_25_T, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.BAT_CHAT_155_58, SpigotTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpigotTrackz(CommonTracks.CONQUEROR_GUN_CARRIAGE, SpigotTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpigotTrackz(CommonTracks.E_50_M, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.E_100, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.FV215, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.FV215B, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.FV4202, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.FV4005_STAGE_II, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.GW_E_100, SpigotTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpigotTrackz(CommonTracks.IS_4M, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.IS_7, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.JAGDPANZER_E_100, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.LEOPARD_1, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.M60, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.MAUS, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.OBJECT_140, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.OBJECT_260_MOD_1945, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.OBJECT_261, SpigotTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpigotTrackz(CommonTracks.OBJECT_263, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.OBJECT_268, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.OBJECT_430, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.OBJECT_907, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.SPAHPANZER_RU_251_PT_II, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new SpigotTrackz(CommonTracks.STB_1, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.T_54_ENHANCED_LTWT_VERSION, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new SpigotTrackz(CommonTracks.T_62A, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.T92_T80E1, SpigotTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpigotTrackz(CommonTracks.T97, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.T97E2, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.T110E27, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.T110E3, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.T110E4, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.TVP_T_50_51, SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpigotTrackz(CommonTracks.TYPE_5_HEAVY, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.VK_7201, SpigotTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpigotTrackz(CommonTracks.WAFFENTRAGER_E_100, SpigotTankTypes.getTankType(CommonTankTypes.TD)),
                new SpigotTrackz(CommonTracks.WZ_132, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new SpigotTrackz(CommonTracks.XM551_TEST_BED, SpigotTankTypes.getTankType(CommonTankTypes.LIGHT)));
    }

    public static SpigotTrackz getTracks(String name)
    {
        return new SpigotTracks().get(name);
    }


    public List<SpigotTrackz> getValues()
    {
        return new SpigotTracks().values();
    }
}
