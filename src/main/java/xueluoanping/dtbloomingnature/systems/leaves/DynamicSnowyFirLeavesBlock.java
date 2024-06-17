package xueluoanping.dtbloomingnature.systems.leaves;

import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.NotNull;

public class DynamicSnowyFirLeavesBlock extends DynamicLeavesBlock {
    public static final BooleanProperty SNOWY= SnowyDirtBlock.SNOWY;

    public DynamicSnowyFirLeavesBlock(LeavesProperties leavesProperties, Properties properties) {
        super(leavesProperties, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SNOWY, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(SNOWY));
    }

    private static boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(Blocks.SNOW) && blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState2.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(levelReader, blockState, blockPos, blockState2, blockPos2, Direction.UP, blockState2.getLightBlock(levelReader, blockPos2));
            return i < levelReader.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        return canBeGrass(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPos2).is(FluidTags.WATER);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (!canBeGrass(blockState, serverLevel, blockPos)) {
            serverLevel.setBlockAndUpdate(blockPos, this.defaultBlockState());
        } else if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9) {
            BlockState blockState2 = this.defaultBlockState();
            for(int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockPos2).is(this) && canPropagate(blockState2, serverLevel, blockPos2)) {
                    serverLevel.setBlockAndUpdate(blockPos2, blockState2.setValue(SNOWY, serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW)));
                }
            }
        }
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor accessor, BlockPos pos, BlockPos pos1) {
        return direction == Direction.UP ?
                state.setValue(SNOWY, isSnowySetting(blockState)) :
                super.updateShape(state, direction, blockState, accessor, pos, pos1);
    }


    private static boolean isSnowySetting(BlockState state) {
        return state.is(BlockTags.SNOW);
    }

}
