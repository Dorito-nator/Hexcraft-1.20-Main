package net.masterquentus.hexcraftmod.screen;

import net.masterquentus.hexcraftmod.HexcraftMod;
import net.masterquentus.hexcraftmod.recipe.WitchesOvenRecipeBookComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class WitchesOvenScreen extends AbstractFurnaceScreen<WitchesOvenMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(HexcraftMod.MOD_ID, "textures/gui/withes_oven_updated.png");


    public WitchesOvenScreen(WitchesOvenMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new WitchesOvenRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE);

        this.imageWidth = 176;
        this.imageHeight = 166;

    }
//    @Override
//    public void init() {
//        super.init();
//        this.inventoryLabelY = 10000;
//        this.titleLabelY = 10000;
//    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY){
        renderDirtBackground(pGuiGraphics);
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0,0, this.imageWidth, this.imageHeight);
    }

    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick){
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
