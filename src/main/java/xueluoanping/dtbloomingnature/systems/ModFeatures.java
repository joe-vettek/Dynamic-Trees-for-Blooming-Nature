package xueluoanping.dtbloomingnature.systems;


import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import net.minecraft.resources.ResourceLocation;
import xueluoanping.dtbloomingnature.DTBloomingNature;
import xueluoanping.dtbloomingnature.systems.featuregen.AltGroundFeature;
import xueluoanping.dtbloomingnature.systems.featuregen.DecorateTrunkFeature;


public class ModFeatures {
    public static final GenFeature ALT_GROUND = new AltGroundFeature(regName("alt_ground"));
    public static final DecorateTrunkFeature DECORATE_TRUNK = new DecorateTrunkFeature(regName("decorate_trunk"));

    private static ResourceLocation regName(String name) {
        return new ResourceLocation(DTBloomingNature.MOD_ID, name);
    }

    public static void register(final Registry<GenFeature> registry) {
        registry.register(ALT_GROUND);
        registry.register(DECORATE_TRUNK);
        // AlterGroundDecorator
    }
}
