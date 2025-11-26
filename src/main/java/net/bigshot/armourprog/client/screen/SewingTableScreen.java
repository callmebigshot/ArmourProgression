package net.bigshot.armourprog.client.screen;

import net.bigshot.armourprog.ArmourProg;
import net.bigshot.armourprog.menu.custom.SewingTableMenu;
import net.bigshot.armourprog.recipe.SewingRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SewingTableScreen extends AbstractContainerScreen<SewingTableMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ArmourProg.MOD_ID, "textures/gui/sewing_table.png");

    private static final int LIST_X = 60;
    private static final int LIST_Y = 13;

    private static final int SLOT_WIDTH = 16;
    private static final int SLOT_HEIGHT = 18;

    private static final int ICON_SIZE = 16;

    private static final int COLUMNS = 4;
    private static final int VISIBLE_ROWS = 3;

    private static final int SLOT_U = 0;
    private static final int SLOT_V_DEFAULT = 166;
    private static final int SLOT_V_SELECTED = 184;
    private static final int SLOT_V_HOVER = 202;

    private static final int SCROLLBAR_X = 127;
    private static final int SCROLLBAR_Y = 13;
    private static final int SCROLLBAR_WIDTH = 12;
    private static final int SCROLLBAR_HEIGHT = 15;
    private static final int SCROLLBAR_TRACK_HEIGHT = 53;

    private int scrollOffset = 0;

    public SewingTableScreen(SewingTableMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        gui.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        renderRecipeList(gui, mouseX, mouseY);
        renderScrollbar(gui);
    }

    private void renderScrollbar(GuiGraphics gui) {
        List<SewingRecipe> recipes = this.menu.getAvailableRecipes();
        if (recipes.isEmpty()) return;

        int totalRows = (recipes.size() + COLUMNS - 1) / COLUMNS;
        int maxOffset = Math.max(totalRows - VISIBLE_ROWS, 0);

        int x = leftPos + SCROLLBAR_X;
        int y = topPos + SCROLLBAR_Y;

        if (maxOffset <= 0) {
            gui.blit(TEXTURE, x, y, 188, 0, SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT);
            return;
        }

        float progress = (float) scrollOffset / maxOffset;
        int travel = SCROLLBAR_TRACK_HEIGHT - SCROLLBAR_HEIGHT;
        int knobOffset = (int) (progress * travel);

        gui.blit(TEXTURE, x, y + knobOffset, 176, 0, SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT);
    }

    private void renderRecipeList(GuiGraphics gui, int mouseX, int mouseY) {
        List<SewingRecipe> recipes = menu.getAvailableRecipes();
        if (recipes.isEmpty() || minecraft == null || minecraft.level == null) return;

        int total = recipes.size();
        int totalRows = (total + COLUMNS - 1) / COLUMNS;
        scrollOffset = Math.max(0, Math.min(scrollOffset, totalRows - VISIBLE_ROWS));

        int left = leftPos + LIST_X;
        int top = topPos + LIST_Y;

        int selectedIndex = menu.getSelectedRecipe();
        int maxVisible = COLUMNS * VISIBLE_ROWS;

        for (int i = 0; i < maxVisible; i++) {
            int recipeIndex = scrollOffset * COLUMNS + i;
            if (recipeIndex >= total) break;

            int row = i / COLUMNS;
            int col = i % COLUMNS;

            int slotX = left + col * SLOT_WIDTH;
            int slotY = top + row * SLOT_HEIGHT;

            boolean hovering = isMouseOverSlot(mouseX, mouseY, slotX, slotY);

            int v;
            if (recipeIndex == selectedIndex) {
                v = SLOT_V_SELECTED;
            } else if (hovering) {
                v = SLOT_V_HOVER;
            } else {
                v = SLOT_V_DEFAULT;
            }

            gui.blit(TEXTURE, slotX, slotY, SLOT_U, v, SLOT_WIDTH, SLOT_HEIGHT);

            ItemStack icon = recipes.get(recipeIndex).getResultItem(minecraft.level.registryAccess());
            gui.renderItem(icon, slotX, slotY + 1);

            if (hovering) {
                gui.renderTooltip(font, icon, mouseX, mouseY);
            }
        }
    }

    private boolean isMouseOverSlot(int mouseX, int mouseY, int x, int y) {
        return mouseX >= x && mouseX < x + SLOT_WIDTH &&
                mouseY >= y && mouseY < y + SLOT_HEIGHT;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return (button == 0 && handleRecipeClick(mouseX, mouseY))
                || super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean handleRecipeClick(double mouseX, double mouseY) {
        List<SewingRecipe> recipes = menu.getAvailableRecipes();
        if (recipes.isEmpty()) return false;

        int left = leftPos + LIST_X;
        int top = topPos + LIST_Y;

        int maxVisible = COLUMNS * VISIBLE_ROWS;

        for (int i = 0; i < maxVisible; i++) {
            int recipeIndex = scrollOffset * COLUMNS + i;
            if (recipeIndex >= recipes.size()) break;

            int row = i / COLUMNS;
            int col = i % COLUMNS;

            int x = left + col * SLOT_WIDTH;
            int y = top + row * SLOT_HEIGHT;

            if (isMouseOverSlot((int) mouseX, (int) mouseY, x, y)) {
                if (minecraft != null && minecraft.gameMode != null) {
                    minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, recipeIndex);
                }
                menu.setSelectedRecipe(recipeIndex);
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        List<SewingRecipe> recipes = menu.getAvailableRecipes();
        if (recipes.isEmpty()) return super.mouseScrolled(mouseX, mouseY, delta);

        int totalRows = (recipes.size() + COLUMNS - 1) / COLUMNS;
        int maxOffset = Math.max(totalRows - VISIBLE_ROWS, 0);

        if (delta < 0 && scrollOffset < maxOffset) scrollOffset++;
        else if (delta > 0 && scrollOffset > 0) scrollOffset--;

        return true;
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        gui.drawString(font, title, 8, 4, 4210752, false);
        gui.drawString(font, playerInventoryTitle, 8, imageHeight - 94, 4210752, false);
    }
}










