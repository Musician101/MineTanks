package io.musician101.minetanks.sponge.tank.module.tracks;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonTracks;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.sponge.tank.SpongeTankTypes;


public class SpongeTracks extends AbstractInfoStorage<SpongeTrackz> {

    private SpongeTracks() {
        super(
                new SpongeTrackz(CommonTracks.ONE_21, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.ONE_13, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.AMX_13_TYPE_2D, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new SpongeTrackz(CommonTracks.AMX_30_B, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.AMX_50_B, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.AMX_50_FOCH_155, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.BAT_CHAT_25_T, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.BAT_CHAT_155_58, SpongeTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpongeTrackz(CommonTracks.CONQUEROR_GUN_CARRIAGE, SpongeTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpongeTrackz(CommonTracks.E_50_M, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.E_100, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.FV215, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.FV215B, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.FV4202, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.FV4005_STAGE_II, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.GW_E_100, SpongeTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpongeTrackz(CommonTracks.IS_4M, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.IS_7, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.JAGDPANZER_E_100, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.M60, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.MAUS, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.LEOPARD_1, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.OBJECT_140, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.OBJECT_260_MOD_1945, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.OBJECT_261, SpongeTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpongeTrackz(CommonTracks.OBJECT_263, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.OBJECT_268, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.OBJECT_430, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.OBJECT_907, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.SPAHPANZER_RU_251_PT_II, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new SpongeTrackz(CommonTracks.STB_1, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.T_54_ENHANCED_LTWT_VERSION, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new SpongeTrackz(CommonTracks.T_62A, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.T92_T80E1, SpongeTankTypes.getTankType(CommonTankTypes.SPG)),
                new SpongeTrackz(CommonTracks.T97, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.T97E2, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.T110E3, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.T110E4, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.T110E27, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.TVP_T_50_51, SpongeTankTypes.getTankType(CommonTankTypes.MEDIUM)),
                new SpongeTrackz(CommonTracks.TYPE_5_HEAVY, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.VK_7201, SpongeTankTypes.getTankType(CommonTankTypes.HEAVY)),
                new SpongeTrackz(CommonTracks.WAFFENTRAGER_E_100, SpongeTankTypes.getTankType(CommonTankTypes.TD)),
                new SpongeTrackz(CommonTracks.WZ_132, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)),
                new SpongeTrackz(CommonTracks.XM551_TEST_BED, SpongeTankTypes.getTankType(CommonTankTypes.LIGHT)));
    }


    public static SpongeTrackz getTracks(String name) {
        return new SpongeTracks().get(name);
    }
}
