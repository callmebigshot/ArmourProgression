package net.bigshot.armourprog.client.screen;

import net.bigshot.armourprog.ArmourProg;
import net.bigshot.armourprog.menu.custom.SewingTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SewingTableScreen extends AbstractContainerScreen<SewingTableMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ArmourProg.MOD_ID, "textures/gui/sewing_table.png");

    private final Inventory playerInventory;

    public SewingTableScreen(SewingTableMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.playerInventory = playerInv;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 8, 6, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventory.getDisplayName(), 8, this.imageHeight - 94, 4210752, false);
    }
}
