package xueluoanping.dtbloomingnature.systems.growthlogic;

import com.ferreusveritas.dynamictrees.api.configuration.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionSelectionContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.PositionalSpeciesContext;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Random;

// Thanks to maxhyper
// signal_energy controls the maximum trunk length.
// The value of each position in the six directions of probMap affects the initial branch size,
// but in the end, only values greater than 0 will always grow unless the fertility is exhausted.
// Because each growth pulse is sent throughout the tree along the trunk.
public class TenaneaTreeLogic extends GrowthLogicKit {

    public static final ConfigurationProperty<Integer> TURNING_LENGTH = ConfigurationProperty.integer("turning_length");

    public TenaneaTreeLogic(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected GrowthLogicKitConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(TURNING_LENGTH, 0);
    }

    @Override
    protected void registerProperties() {
        this.register(TURNING_LENGTH);
    }

    @Override
    public int[] populateDirectionProbabilityMap(GrowthLogicKitConfiguration configuration, DirectionManipulationContext context) {
        final Species species = context.species();
        final Level world = context.level();
        final GrowSignal signal = context.signal();
        int[] probMap = new int[]{0, 2, 0, 0, 0, 0};
        final BlockPos pos = context.pos();
        var rootPos = signal.rootPos;
        Direction originDir = signal.dir.getOpposite();
        int currentHeight = signal.numSteps + 1;
        long seed = CoordUtils.coordHashCode(pos, 3) + ((ServerLevel) world).getSeed();
        Random random = new Random(seed);
        var delta = signal.delta;

        if (signal.numSteps == 5+random.nextInt(2)) {
            probMap = new int[]{0, 0, 1 + random.nextInt(2), 1 + random.nextInt(2), 1 + random.nextInt(2), 1 + random.nextInt(2)};
            // probMap = new int[]{0, 0, 0, 0, 0, 1};
        } else {
            // south
            float ran = random.nextFloat();
            if (delta.getX() >= 0 && delta.getZ() > 0) {
                probMap = new int[]{0, 0, 0, 1, 0, 0};
                if (ran < 0.332) {
                    probMap = new int[]{0, 10, 0, 1, 0, 0};
                } else if (ran < 0.52) {
                    probMap = new int[]{0, 0, 0, 1, 0, 10};
                }
            }
            // north
            else if (delta.getX() <= 0 && delta.getZ() < 0) {
                probMap = new int[]{0, 0, 1, 0, 0, 0};
                if (ran < 0.332) {
                    probMap = new int[]{0, 10, 1, 0, 0, 0};
                } else if (ran < 0.52) {
                    probMap = new int[]{0, 0, 0, 1, 10, 0};
                }
            }
            // west
            else if (delta.getX() < 0 && delta.getZ() >= 0) {
                probMap = new int[]{0, 0, 0, 0, 1, 0};
                if (ran < 0.332) {
                    probMap = new int[]{0, 10, 0, 0, 1, 0};
                } else if (ran < 0.52) {
                    probMap = new int[]{0, 0, 0, 10, 1, 0};
                }
            }
            // test east
            else if (delta.getX() > 0 && delta.getZ() <= 0) {
                probMap = new int[]{0, 0, 0, 0, 0, 1};
                if (ran < 0.332) {
                    probMap = new int[]{0, 10, 0, 0, 0, 1};
                } else if (ran < 0.52) {
                    probMap = new int[]{0, 0, 10, 0, 0, 1};
                }
            } else {
                probMap[Direction.UP.ordinal()] = 1;
                probMap[2 + random.nextInt(4)] = 4;
                probMap = new int[]{0, 3, 1 + random.nextInt(2), 1 + random.nextInt(2), 1 + random.nextInt(2), 1 + random.nextInt(2)};
            }

            // if (Mth.abs(delta.getX()) > 10 || Mth.abs(delta.getZ()) > 10)
            //     probMap[Direction.UP.ordinal()] = 10;


            if (delta.getY() > 15 + random.nextInt(7)) {
                probMap = new int[]{0, 0, random.nextInt(4), random.nextInt(4), random.nextInt(4), random.nextInt(4)};
            }

            // if (delta.getY() > 7 + random.nextInt(4))
            //     for (Direction direction : List.of(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH)) {
            //         probMap[direction.ordinal()] = 1;
            //     }

            // disable origin

            // if (delta.getX()!=0&&delta.getZ()!=0){
            //     if (delta.getX() % 2 == 0||delta.getZ() % 2 == 0) {
            //         probMap = new int[]{0, 1, 0, 0, 0, 0};
            //     }
            // }
            // if ((delta.getX() > 2 && delta.getX() % 2 == 0&&delta.getY() % 2 == 1 )||
            //         (delta.getY() % 2 == 0&&delta.getX() % 2 == 1)) {
            //     probMap = new int[]{0, 1, 0, 0, 0, 0};
            // }

            // DTBetterEnd.logger(ran, seed, delta);
        }
        probMap[originDir.ordinal()] = 0;
        // DTBetterEnd.logger(signal.delta, probMap);
        // if (delta.getY()>6){
        //     probMap = new int[]{100, 0, 0, 0, 0, 0};
        // }
        return probMap;
    }

    @Override
    public Direction selectNewDirection(GrowthLogicKitConfiguration configuration, DirectionSelectionContext context) {
        var d = super.selectNewDirection(configuration, context);
        // DTBetterEnd.logger(d,context.signal().delta);
        return d;
    }

    @Override
    public int getLowestBranchHeight(GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context) {
        return super.getLowestBranchHeight(configuration, context);
    }

    @Override
    public float getEnergy(GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context) {
        var en = super.getEnergy(configuration, context);
        return en;
    }
}
