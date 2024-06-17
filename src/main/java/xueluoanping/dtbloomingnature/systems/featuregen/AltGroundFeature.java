package xueluoanping.dtbloomingnature.systems.featuregen;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.configuration.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGrowContext;
import com.ferreusveritas.dynamictrees.systems.nodemapper.FindEndsNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AltGroundFeature extends GenFeature {

    public static final ConfigurationProperty<Block> BLOCK = ConfigurationProperty.property("block", Block.class);

    public BlockState getAltBlock(GenFeatureConfiguration configuration) {
        return configuration.get(BLOCK).defaultBlockState();
    }

    public AltGroundFeature(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(BLOCK);
    }

    @Override
    protected @NotNull GenFeatureConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(BLOCK, Blocks.DIRT);
    }

    @Override
    protected boolean postGenerate(@NotNull GenFeatureConfiguration configuration, PostGenerationContext context) {
        final LevelAccessor world = context.level();
        replace_ground(world, context.pos(), getAltBlock(configuration), true);
        final Set<BlockPos> newPoints = new HashSet<>();
        for (BlockPos endPoint : context.endPoints()) {
            var pos = findGround(world, endPoint);
            newPoints.add(pos);
        }
        newPoints.forEach(pos ->
                replace_ground(world, pos, getAltBlock(configuration), true));
        return true;
    }

    @Override
    protected boolean postGrow(@NotNull GenFeatureConfiguration configuration, PostGrowContext context) {
        var result = false;
        if (context.natural() && context.fertility() > 0
                && context.level().getRandom().nextFloat() > 0.25) {
            final LevelAccessor world = context.level();
            final FindEndsNode endFinder = new FindEndsNode();
            TreeHelper.startAnalysisFromRoot(world, context.pos(), new MapSignal(endFinder));
            final List<BlockPos> endPoints = endFinder.getEnds();
            final Set<BlockPos> newPoints = new HashSet<>();
            for (BlockPos endPoint : endPoints) {
                var pos = findGround(world, endPoint);
                newPoints.add(pos);
            }
            if (!newPoints.isEmpty())
                newPoints.forEach(pos ->
                {

                    replace_ground(world, pos, getAltBlock(configuration), false);
                });

            result = true;
        }
        return result;
    }

    public boolean isAirAt(LevelAccessor accessor, BlockPos pos) {
        return accessor.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
    }

    public BlockPos findGround(LevelAccessor accessor, BlockPos endPos) {
        var mulPos = new BlockPos.MutableBlockPos(endPos.getX(), endPos.getY() - 1, endPos.getZ());
        int count = 0;
        while (!isAirAt(accessor, endPos) && !Feature.isGrassOrDirt(accessor, mulPos)
                && mulPos.getY() > accessor.getMinBuildHeight() && count < 15) {
            mulPos.move(Direction.DOWN);
            count++;
        }
        return mulPos;
    }

    public void replace_ground(LevelAccessor accessor, BlockPos rootPos, BlockState state, boolean worldGen) {
        placeCircle(accessor, rootPos.west(1 - accessor.getRandom().nextInt(3))
                .north(1 - accessor.getRandom().nextInt(3)), state, worldGen);
    }

    protected void placeCircle(LevelAccessor accessor, BlockPos pos, BlockState blockState, boolean worldGen) {
        if (worldGen)
            for (int i = -3; i <= 3; ++i) {
                for (int j = -3; j <= 3; ++j) {
                    if (Math.abs(i) != 3 || Math.abs(j) != 3) {
                        if (accessor.getRandom().nextFloat() > 0.85)
                            this.placeBlockAt(accessor, pos.offset(i, 0, j), blockState);
                    }
                }
            }
        else {
            this.placeBlockAt(accessor, pos.west(1 - accessor.getRandom().nextInt(3))
                    .north(1 - accessor.getRandom().nextInt(3)), blockState);
        }
    }

    private void placeBlockAt(LevelAccessor accessor, BlockPos pos, BlockState state) {
        for (int i = 2; i >= -3; --i) {
            BlockPos blockpos = pos.above(i);
            // && !accessor.getBlockState(pos).is(state.getBlock())
            if (Feature.isGrassOrDirt(accessor, blockpos)) {
                accessor.setBlock(blockpos, state, Block.UPDATE_CLIENTS);
                break;
            }
            if (!isAirAt(accessor, pos) && i < 0) {
                break;
            }
        }

    }


}
