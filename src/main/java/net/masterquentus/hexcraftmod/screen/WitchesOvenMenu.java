package net.masterquentus.hexcraftmod.screen;


import net.masterquentus.hexcraftmod.block.HexcraftBlocks;
import net.masterquentus.hexcraftmod.block.entity.WitchesOvenBlockEntity;
import net.masterquentus.hexcraftmod.recipe.WitchesOvenRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class WitchesOvenMenu extends AbstractFurnaceMenu {
    private final WitchesOvenBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    //private final ContainerData data;

    public WitchesOvenMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf friendlyByteBuf) {
        this(pContainerId, pPlayerInventory, pPlayerInventory.player.level().getBlockEntity(friendlyByteBuf.readBlockPos()), (ContainerData) new SimpleContainer(5));
    }

    public WitchesOvenMenu(int pContainerId, Inventory pPlayerInventory, BlockEntity blockEntity, ContainerData data) {
        super(HexcraftMenuTypes.WITCHES_OVEN_MENU.get(), WitchesOvenRecipe.Type.INSTANCE, RecipeBookType.FURNACE, pContainerId, pPlayerInventory);

        if(blockEntity instanceof WitchesOvenBlockEntity be){
            this.blockEntity = be;
        }else{
            throw new IllegalStateException("Incorrect blockEntity class (%s) passed into ExmapleMenu!"
                    .formatted(blockEntity.getClass().getCanonicalName()));
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        createPlayerHotbar(pPlayerInventory);
        createPlayerInventory(pPlayerInventory);
        createBlockEntityinventory(be);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot fromSlot = getSlot(pIndex);
        ItemStack fromStack = fromSlot.getItem();

        if(fromStack.getCount() <= 0)
            fromSlot.set(ItemStack.EMPTY);

        if(!fromSlot.hasItem())
            return ItemStack.EMPTY;

        ItemStack copyFromStack = fromStack.copy();

        if(pIndex < 36) {
            // We are inside of the player's inventory
            if(!moveItemStackTo(fromStack, 36, 63, false))
                return ItemStack.EMPTY;
        } else if (pIndex < 63) {
            // We are inside of the block entity inventory
            if(!moveItemStackTo(fromStack, 0, 36, false))
                return ItemStack.EMPTY;
        } else {
            System.err.println("Invalid slot index: " + pIndex);
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(pPlayer, fromStack);

        return copyFromStack;
    }

    @Override
    public boolean stillValid(Player pPlayer){
        return stillValid(this.levelAccess, pPlayer, HexcraftBlocks.WITCHES_OVEN.get());
    }

    private void createBlockEntityinventory(WitchesOvenBlockEntity be) {
        blockEntity.getOptional().ifPresent(inventory -> {
            for(int row = 0; row < 3; row++) {
                for(int column = 0; column < 9; column++) {
                    addSlot(new SlotItemHandler(inventory, column + row * 9, 8 + column * 18, 18 + row * 18));
                }
            }
        });
    }

    private void createPlayerInventory(Inventory pPlayerInventory) {
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(pPlayerInventory,9 + column + (row * 9), 8 + (column * 18), 84 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory pPlayerInventory) {
        for(int colummn = 0; colummn < 9; colummn++){
            addSlot(new Slot(pPlayerInventory, colummn, 8 + (colummn * 18), 142));
        }
    }

    public WitchesOvenBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

}
