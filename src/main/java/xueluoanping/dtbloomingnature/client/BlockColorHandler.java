package xueluoanping.dtbloomingnature.client;


import net.minecraft.client.Minecraft;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xueluoanping.dtbloomingnature.ModConstants;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockColorHandler {

    // Need to wait
    @SubscribeEvent
    public static void handleBlockColor(FMLClientSetupEvent event) {
        // event.enqueueWork(() -> {
        //     BlockState birchLeaves = Blocks.BIRCH_LEAVES.defaultBlockState();
        //     Minecraft.getInstance().getBlockColors().register((state, worlds, poss, i) -> {
        //         // Minecraft.getInstance().getBlockColors().getColor(birchLeaves, worlds, poss, i);
        //         // return FoliageColor.getEvergreenColor();
        //         return Minecraft.getInstance().getBlockColors().getColor(Blocks.JUNGLE_LEAVES.defaultBlockState(), worlds, poss, i);
        //     }, ModConstants.FAN_PALM_LEAVES.get());
        //
        // });
    }

}
