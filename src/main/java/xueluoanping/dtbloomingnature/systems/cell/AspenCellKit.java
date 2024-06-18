package xueluoanping.dtbloomingnature.systems.cell;

import com.ferreusveritas.dynamictrees.api.cell.Cell;
import com.ferreusveritas.dynamictrees.api.cell.CellKit;
import com.ferreusveritas.dynamictrees.api.cell.CellNull;
import com.ferreusveritas.dynamictrees.api.cell.CellSolver;
import com.ferreusveritas.dynamictrees.cell.CellKits;
import com.ferreusveritas.dynamictrees.cell.LeafClusters;
import com.ferreusveritas.dynamictrees.cell.MatrixCell;
import com.ferreusveritas.dynamictrees.cell.NormalCell;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.resources.ResourceLocation;

public class AspenCellKit extends CellKit {
    private final Cell[] normalCells = {
            CellNull.NULL_CELL,
            new NormalCell(1),
            new NormalCell(2),
            new NormalCell(3),
            new NormalCell(4),
            new NormalCell(5),
            new NormalCell(6),
            new NormalCell(7)
    };

    /**
     * Typical branch with hydration 5
     */
    private final Cell branchCell = new NormalCell(5);

    private final CellKits.BasicSolver deciduousSolver = new CellKits.BasicSolver(new short[]{0x0514,  0x0413, 0x0322, 0x0211, 0x0131});

    public AspenCellKit(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    public Cell getCellForLeaves(int hydro) {
        return normalCells[hydro];
    }

    @Override
    public Cell getCellForBranch(int radius, int meta) {
        return radius <= 3 ? branchCell : CellNull.NULL_CELL;
    }

    @Override
    public SimpleVoxmap getLeafCluster() {
        return LeafClusters.DECIDUOUS;
    }

    @Override
    public CellSolver getCellSolver() {
        return deciduousSolver;
    }

    @Override
    public int getDefaultHydration() {
        return 5;
    }

    public static class SingleCell extends MatrixCell {

        public SingleCell(int value) {
            super(value, valMap);
        }

        static final byte[] valMap = {
                0, 0, 0, 0, 0, 0, 0, 0, // D Maps * -> 0
                0, 0, 0, 3, 3, 0, 0, 0, // U Maps 3 -> 3, 4 -> 3, * -> 0
                0, 0, 0, 0, 0, 0, 0, 0, // N Maps * -> *
                0, 0, 0, 0, 0, 0, 0, 0, // S Maps * -> *
                0, 0, 0, 0, 0, 0, 0, 0, // W Maps * -> *
                0, 0, 0, 0, 0, 0, 0, 0  // E Maps * -> *
        };

    }

}
