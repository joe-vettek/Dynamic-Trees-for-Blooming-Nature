package xueluoanping.dtbloomingnature.systems;


import com.ferreusveritas.dynamictrees.api.cell.CellKit;
import com.ferreusveritas.dynamictrees.api.registry.Registry;
import net.minecraft.resources.ResourceLocation;
import xueluoanping.dtbloomingnature.DTBloomingNature;
import xueluoanping.dtbloomingnature.systems.cell.AspenCellKit;


public class ModCellKit {
    public static final AspenCellKit ASPEN = new AspenCellKit(regName("aspen"));


    private static ResourceLocation regName(String name) {
        return DTBloomingNature.rl(name);
    }

    public static void register(final Registry<CellKit> registry) {
        registry.register(ASPEN);
    }
}
