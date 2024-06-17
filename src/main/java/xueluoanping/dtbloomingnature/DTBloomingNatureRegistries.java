package xueluoanping.dtbloomingnature;

import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.dtbloomingnature.systems.ModFeatures;
import xueluoanping.dtbloomingnature.systems.ModGrowthLogicKits;
import xueluoanping.dtbloomingnature.systems.leaves.SnowyLeaveProperties;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTBloomingNatureRegistries {

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        DTBloomingNature.LOGGER.debug("registerLeavesPropertiesTypes");
        event.registerType(new ResourceLocation(DTBloomingNature.MOD_ID, "snowy_fir"), SnowyLeaveProperties.TYPE);

    }


    @SubscribeEvent
    public static void onFeatureCancellerRegistry(final RegistryEvent<FeatureCanceller> event) {
        // event.getRegistry().registerAll(FRUIT_TREES_CANCELLER);
    }

    @SubscribeEvent
    public static void onGenFeatureRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GenFeature> event) {
        ModFeatures.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onGrowthLogicKitsRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GrowthLogicKit> event) {
        ModGrowthLogicKits.register(event.getRegistry());
    }
}
