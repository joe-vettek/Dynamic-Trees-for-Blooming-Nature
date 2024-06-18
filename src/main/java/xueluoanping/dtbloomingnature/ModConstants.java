package xueluoanping.dtbloomingnature;

import net.minecraft.world.level.block.Block;
import xueluoanping.dtbloomingnature.util.LazyGet;
import xueluoanping.dtbloomingnature.util.RegisterFinderUtil;


public class ModConstants {


    public static final LazyGet<Block> FAN_PALM_LEAVES = LazyGet.of(() -> RegisterFinderUtil.getBlock(DTBloomingNature.rl("fan_palm_leaves")));


}