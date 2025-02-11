package li.cil.tis3d.common.tags;

import li.cil.tis3d.api.API;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class BlockTags {
    public static final TagKey<Block> COMPUTERS = tag("computers");

    // --------------------------------------------------------------------- //

    public static void initialize() {
    }

    // --------------------------------------------------------------------- //

    private static TagKey<Block> tag(final String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(API.MOD_ID, name));
    }
}
