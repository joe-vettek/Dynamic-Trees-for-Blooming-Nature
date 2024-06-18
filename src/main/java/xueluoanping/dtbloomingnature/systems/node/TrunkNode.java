package xueluoanping.dtbloomingnature.systems.node;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.network.NodeInspector;
import com.ferreusveritas.dynamictrees.api.treedata.TreePart;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.dtbloomingnature.DTBloomingNature;

import java.util.List;
import java.util.Objects;

public class TrunkNode implements NodeInspector {

    private final List<BlockPos> endPoints;
    private BlockPos last;

    public TrunkNode(List<BlockPos> ends) {
        this.endPoints = ends;
        last = BlockPos.ZERO;
    }

    @Override
    public boolean run(BlockState state, LevelAccessor level, BlockPos pos, Direction fromDir) {
        TreePart treePart = TreeHelper.getTreePart(state);
        if (treePart.getTreePartType() == TreePart.TreePartType.BRANCH) {
            if (treePart.getRadius(state) >= 8) {
                endPoints.add(pos);
                return true;
            }
        } else if (treePart.getTreePartType() == TreePart.TreePartType.ROOT) {
            return true;
        }

        return false;

    }

    @Override
    public boolean returnRun(BlockState state, LevelAccessor level, BlockPos pos, Direction fromDir) {
        return false;
    }
}
