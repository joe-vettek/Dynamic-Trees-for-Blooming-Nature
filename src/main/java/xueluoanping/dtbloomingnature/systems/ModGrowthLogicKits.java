package xueluoanping.dtbloomingnature.systems;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import xueluoanping.dtbloomingnature.DTBloomingNature;
import xueluoanping.dtbloomingnature.systems.growthlogic.*;


public class ModGrowthLogicKits {
    public static final GrowthLogicKit BAOBAB = new BaobabTreeLogic(DTBloomingNature.rl("baobab"));
    public static final GrowthLogicKit ASPEN = new AspenTreeLogic(DTBloomingNature.rl("aspen"));

    public static void register(final Registry<GrowthLogicKit> registry) {
        registry.register(BAOBAB);
        registry.register(ASPEN);
    }
}
