package li.cil.tis3d.common.block;

import li.cil.tis3d.util.RegistryUtils;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class Blocks {
    private static final DeferredRegister<Block> BLOCKS = RegistryUtils.getInitializerFor(ForgeRegistries.BLOCKS);

    // --------------------------------------------------------------------- //

    public static final RegistryObject<CasingBlock> CASING = BLOCKS.register("casing", CasingBlock::new);
    public static final RegistryObject<ControllerBlock> CONTROLLER = BLOCKS.register("controller", ControllerBlock::new);

    // --------------------------------------------------------------------- //

    public static void initialize() {
    }
}
