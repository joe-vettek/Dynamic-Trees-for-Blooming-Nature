package xueluoanping.dtbloomingnature.util;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class PlaceUtil {
    public static boolean isAirAt(LevelAccessor accessor, BlockPos pos) {
        return accessor.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
    }

    public static boolean isGrassOrDirt(LevelAccessor accessor, BlockPos pos) {
        return Feature.isGrassOrDirt(accessor, pos);
    }

    public static boolean isTrunk(LevelAccessor accessor, BlockPos pos) {
        BlockState blockState = accessor.getBlockState(pos);
        BranchBlock branch = TreeHelper.getBranch(blockState);
        return branch != null && branch.getRadius(blockState) >= 8;
    }

    public static boolean isSingleTrunk(LevelAccessor accessor, BlockPos pos) {
         BlockState blockState = accessor.getBlockState(pos);
        BranchBlock branch = TreeHelper.getBranch(blockState);
        return branch != null && branch.getRadius(blockState) == 8;
    }
}
