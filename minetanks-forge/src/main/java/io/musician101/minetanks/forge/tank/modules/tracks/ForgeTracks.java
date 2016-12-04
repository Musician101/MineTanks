package io.musician101.minetanks.forge.tank.modules.tracks;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonTracks;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.forge.tank.ForgeTankTypes;

import java.util.List;


public class ForgeTracks extends AbstractInfoStorage<ForgeTrackz>
{
    private ForgeTracks()
    {
        super(
                new ForgeTrackz(CommonTracks.ONE_21, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.ONE_13, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.AMX_13_TYPE_2D, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new ForgeTrackz(CommonTracks.AMX_30_B, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.AMX_50_B, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.AMX_50_FOCH_155, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.BAT_CHAT_25_T, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.BAT_CHAT_155_58, ForgeTankTypes.getTankType(CommonTankTypes.SPG)),
                new ForgeTrackz(CommonTracks.CONQUEROR_GUN_CARRIAGE, ForgeTankTypes.getTankType(CommonTankTypes.SPG)),
                new ForgeTrackz(CommonTracks.E_50_M, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.E_100, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.FV215, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.FV215B, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.FV4202, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.FV4005_STAGE_II, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.GW_E_100, ForgeTankTypes.getTankType(CommonTankTypes.SPG)),
                new ForgeTrackz(CommonTracks.IS_4M, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.IS_7, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.JAGDPANZER_E_100, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.LEOPARD_1, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.M60, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.MAUS, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.OBJECT_140, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.OBJECT_260_MOD_1945, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.OBJECT_261, ForgeTankTypes.getTankType(CommonTankTypes.SPG)),
                new ForgeTrackz(CommonTracks.OBJECT_263, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.OBJECT_268, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.OBJECT_430, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.OBJECT_907, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.SPAHPANZER_RU_251_PT_II, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new ForgeTrackz(CommonTracks.STB_1, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.T_54_ENHANCED_LTWT_VERSION, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new ForgeTrackz(CommonTracks.T_62A, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.T92_T80E1, ForgeTankTypes.getTankType(CommonTankTypes.SPG)),
                new ForgeTrackz(CommonTracks.T97, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.T97E2, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.T110E27, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.T110E3, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.T110E4, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.TVP_T_50_51, ForgeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new ForgeTrackz(CommonTracks.TYPE_5_HEAVY, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.VK_7201, ForgeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new ForgeTrackz(CommonTracks.WAFFENTRAGER_E_100, ForgeTankTypes.getTankType(CommonTankTypes.TD)),
                new ForgeTrackz(CommonTracks.WZ_132, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new ForgeTrackz(CommonTracks.XM551_TEST_BED, ForgeTankTypes.getTankType(CommonTankTypes.LIGHT)));
    }

    public static ForgeTrackz getTracks(String name)
    {
        return new ForgeTracks().get(name);
    }


    public List<ForgeTrackz> getValues()
    {
        return new ForgeTracks().values();
    }
}
