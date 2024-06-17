package xueluoanping.dtbloomingnature.systems;


import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import net.minecraft.resources.ResourceLocation;
import xueluoanping.dtbloomingnature.DTBloomingNature;
import xueluoanping.dtbloomingnature.systems.featuregen.AltGroundFeature;


public class ModFeatures {
    public static final GenFeature ALT_GROUND = new AltGroundFeature(regName("alt_ground"));
    private static ResourceLocation regName(String name) {
        return new ResourceLocation(DTBloomingNature.MOD_ID, name);
    }

    public static void register(final Registry<GenFeature> registry) {
        registry.registerAll(ALT_GROUND);
        // AlterGroundDecorator
    }
}
