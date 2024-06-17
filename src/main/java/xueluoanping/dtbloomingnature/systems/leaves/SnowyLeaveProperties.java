package xueluoanping.dtbloomingnature.systems.leaves;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

import javax.annotation.Nonnull;

public class SnowyLeaveProperties extends LeavesProperties{
    public static final TypedRegistry.EntryType<LeavesProperties> TYPE = TypedRegistry.newType(SnowyLeaveProperties::new);

    public SnowyLeaveProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Nonnull
    protected DynamicLeavesBlock createDynamicLeaves(@Nonnull BlockBehaviour.Properties properties) {
        return new DynamicSnowyFirLeavesBlock(this, properties);
    }
}
