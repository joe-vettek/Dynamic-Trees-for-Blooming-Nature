package xueluoanping.dtbloomingnature.systems;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import net.minecraft.resources.ResourceLocation;
import xueluoanping.dtbloomingnature.DTBloomingNature;
import xueluoanping.dtbloomingnature.systems.growthlogic.*;


public class ModGrowthLogicKits {
    public static final GrowthLogicKit WISTERIA = new TenaneaTreeLogic(new ResourceLocation(DTBloomingNature.MOD_ID, "tenanea"));
    public static final GrowthLogicKit LUCERNIA = new LucerniaTreeLogic(DTBloomingNature.rl("lucernia"));
    public static final GrowthLogicKit DRAGON_TREE = new DragonTreeLogic(DTBloomingNature.rl("dragon_tree"));
    public static final GrowthLogicKit LACUGROVE = new LacugroveTreeLogic(DTBloomingNature.rl("lacugrove"));
    public static final GrowthLogicKit PYTHADENDRON = new PythadendronTreeLogic(DTBloomingNature.rl("pythadendron"));

    public static void register(final Registry<GrowthLogicKit> registry) {
        registry.register(WISTERIA);
        registry.register(LUCERNIA);
        registry.register(DRAGON_TREE);
        registry.register(LACUGROVE);
        registry.register(PYTHADENDRON);
    }
}
