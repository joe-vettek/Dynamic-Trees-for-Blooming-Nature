package xueluoanping.dtbloomingnature.systems.featuregen;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.configuration.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.compat.season.SeasonHelper;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGrowContext;
import com.ferreusveritas.dynamictrees.systems.nodemapper.FindEndsNode;
import com.ferreusveritas.dynamictrees.systems.nodemapper.PodGenerationNode;
import com.ferreusveritas.dynamictrees.systems.pod.Pod;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.util.LevelContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.NotNull;
import xueluoanping.dtbloomingnature.DTBloomingNature;
import xueluoanping.dtbloomingnature.systems.node.TrunkNode;
import xueluoanping.dtbloomingnature.util.PlaceUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DecorateTrunkFeature extends GenFeature {

    public static final ConfigurationProperty<Block> BLOCK = ConfigurationProperty.property("block", Block.class);
    public static final BooleanProperty[] sideVineStates = new BooleanProperty[]{VineBlock.NORTH, VineBlock.SOUTH, VineBlock.WEST, VineBlock.EAST};
    public static final List<Direction> horDirections = List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);


    public DecorateTrunkFeature(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(BLOCK);
    }

    @Override
    protected @NotNull GenFeatureConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(BLOCK, Blocks.VINE);
    }

    @Override
    protected boolean postGrow(GenFeatureConfiguration configuration, PostGrowContext context) {
        final LevelAccessor level = context.level();
        if (context.natural() && PlaceUtil.isTrunk(level, context.treePos()) && level.getRandom().nextFloat() <= 0.105) {
            Block pod = configuration.get(BLOCK);
            this.place(pod, level, context.pos(), false);
        }
        return false;
    }


    @Override
    protected boolean postGenerate(GenFeatureConfiguration configuration, PostGenerationContext context) {
        if (context.random().nextFloat() <= 0.6) {
            Block pod = configuration.get(BLOCK);
            this.place(pod, context.level(), context.pos(), true);
            return true;
        }
        return false;
    }


    protected void place(Block block, LevelAccessor level, BlockPos rootPos, boolean worldGen) {
        List<BlockPos> ends = new ArrayList<>();
        TreeHelper.startAnalysisFromRoot(level, rootPos,
                new MapSignal(new TrunkNode(ends)));
        if (ends.size() > 5) {
            for (int i = 0; i < (worldGen ? 3 : 1); i++) {
                int choose = level.getRandom().nextInt(4);
                var d = horDirections.get(choose).getOpposite();
                rootPos = ends.get(ends.size() - 1 - level.getRandom().nextInt(3)).relative(d);
                var state = block.defaultBlockState();
                if (PlaceUtil.isAirAt(level, rootPos)) {
                    if (state.hasProperty(sideVineStates[choose])) {
                        state = state.setValue(sideVineStates[choose], true);
                    }
                    level.setBlock(rootPos, state, Block.UPDATE_CLIENTS);
                } else if (level.isEmptyBlock(rootPos.below())) {
                    if (state.hasProperty(VineBlock.UP)) {
                        state = state.setValue(VineBlock.UP, true);
                    }
                    level.setBlock(rootPos, state, Block.UPDATE_CLIENTS);
                }
            }
        }


    }

}
