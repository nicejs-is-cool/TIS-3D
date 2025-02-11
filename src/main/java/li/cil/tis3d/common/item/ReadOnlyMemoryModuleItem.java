package li.cil.tis3d.common.item;

import li.cil.tis3d.api.machine.Casing;
import li.cil.tis3d.common.container.ReadOnlyMemoryModuleContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public final class ReadOnlyMemoryModuleItem extends ModuleItem {
    private static final String TAG_DATA = "data";
    private static final byte[] EMPTY_DATA = new byte[0];

    public ReadOnlyMemoryModuleItem() {
        super(createProperties().stacksTo(1));
    }

    // --------------------------------------------------------------------- //
    // Item

    @Override
    public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand hand) {
        if (!level.isClientSide() && player instanceof final ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Override
                public AbstractContainerMenu createMenu(final int id, final Inventory playerInventory, final Player player) {
                    return new ReadOnlyMemoryModuleContainer(id, player, hand);
                }
            }, buffer -> buffer.writeEnum(hand));
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    @Override
    public boolean doesSneakBypassUse(final ItemStack stack, final LevelReader level, final BlockPos pos, final Player player) {
        return level.getBlockEntity(pos) instanceof Casing;
    }

    // --------------------------------------------------------------------- //

    /**
     * Load ROM data from the specified tag.
     *
     * @param tag the tag to load the data from.
     * @return the data loaded from the tag.
     */
    public static byte[] loadFromTag(@Nullable final CompoundTag tag) {
        if (tag != null) {
            return tag.getByteArray(TAG_DATA);
        }
        return EMPTY_DATA;
    }

    /**
     * Load ROM data from the specified item stack.
     *
     * @param stack the item stack to load the data from.
     * @return the data loaded from the stack.
     */
    public static byte[] loadFromStack(final ItemStack stack) {
        return loadFromTag(stack.getTag());
    }

    /**
     * Save the specified ROM data to the specified item stack.
     *
     * @param stack the item stack to save the data to.
     * @param data  the data to save to the item stack.
     */
    public static void saveToStack(final ItemStack stack, final byte[] data) {
        final CompoundTag tag = stack.getOrCreateTag();

        byte[] tagData = tag.getByteArray(TAG_DATA);
        if (tagData.length != data.length) {
            tagData = new byte[data.length];
        }

        System.arraycopy(data, 0, tagData, 0, data.length);
        tag.putByteArray(TAG_DATA, tagData);
    }
}
