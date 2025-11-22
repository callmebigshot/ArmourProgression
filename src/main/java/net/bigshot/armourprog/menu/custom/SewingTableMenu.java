package net.bigshot.armourprog.menu.custom;

import net.bigshot.armourprog.block.ModBlocks;
import net.bigshot.armourprog.recipe.SewingRecipe;
import net.bigshot.armourprog.registry.ModMenuTypes;
import net.bigshot.armourprog.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SewingTableMenu extends AbstractContainerMenu {

    private static final Ingredient BASE_ITEMS =
            Ingredient.of(ItemTags.create(new ResourceLocation("armourprog", "sewing/base_items")));
    private static final Ingredient DYES =
            Ingredient.of(ItemTags.create(new ResourceLocation("armourprog", "sewing/dyes")));
    private static final Ingredient PADDING =
            Ingredient.of(ItemTags.create(new ResourceLocation("armourprog", "sewing/padding")));
    private static final Ingredient EMBELLISH =
            Ingredient.of(ItemTags.create(new ResourceLocation("armourprog", "sewing/embellishments")));

    private final Container input = new SimpleContainer(4) {
        @Override
        public void setChanged() {
            super.setChanged();
            updateAvailableRecipes();
        }
    };
    private final Container output = new SimpleContainer(1);
    private final ContainerLevelAccess access;
    private final Level level;

    private final List<SewingRecipe> availableRecipes = new ArrayList<>();
    private int selectedRecipe = -1;

    public SewingTableMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv, ContainerLevelAccess.NULL);
    }

    public SewingTableMenu(int windowId, Inventory playerInv, ContainerLevelAccess access) {
        super(ModMenuTypes.SEWING_TABLE_MENU.get(), windowId);
        this.access = access;
        this.level = playerInv.player.level();

        this.addSlot(new Slot(input, 0, 13, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return BASE_ITEMS.test(stack);
            }
        });

        this.addSlot(new Slot(input, 1, 33, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return DYES.test(stack);
            }
        });

        this.addSlot(new Slot(input, 2, 13, 45) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return PADDING.test(stack);
            }
        });

        this.addSlot(new Slot(input, 3, 33, 45) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return EMBELLISH.test(stack);
            }
        });

        this.addSlot(new Slot(output, 0, 145, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                consumeInputs();
                updateAvailableRecipes();
                super.onTake(player, stack);
            }
        });

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);
        updateAvailableRecipes();
    }

    private void updateAvailableRecipes() {
        availableRecipes.clear();

        if (level == null) {
            output.setItem(0, ItemStack.EMPTY);
            return;
        }

        List<SewingRecipe> all = level.getRecipeManager()
                .getAllRecipesFor(ModRecipeTypes.SEWING_TYPE.get());

        for (SewingRecipe recipe : all) {
            if (recipe.matches(input, level)) {
                availableRecipes.add(recipe);
            }
        }

        availableRecipes.sort((a, b) -> a.getId().compareTo(b.getId()));

        if (!availableRecipes.isEmpty()) {
            if (selectedRecipe < 0 || selectedRecipe >= availableRecipes.size()) {
                selectedRecipe = 0;
            }
            updateOutputFromSelection();
        } else {
            selectedRecipe = -1;
            output.setItem(0, ItemStack.EMPTY);
        }
        broadcastChanges();
    }

    private void updateOutputFromSelection() {
        if (selectedRecipe >= 0 && selectedRecipe < availableRecipes.size()) {
            ItemStack result = availableRecipes.get(selectedRecipe)
                    .getResultItem(level.registryAccess())
                    .copy();
            output.setItem(0, result);
        } else {
            output.setItem(0, ItemStack.EMPTY);
        }
    }

    public void setSelectedRecipe(int index) {
        if (index >= 0 && index < availableRecipes.size()) {
            selectedRecipe = index;
            updateOutputFromSelection();
        }
    }

    public List<SewingRecipe> getAvailableRecipes() {
        return Collections.unmodifiableList(availableRecipes);
    }

    public int getSelectedRecipe() {
        return selectedRecipe;
    }

    private void consumeInputs() {
        if (selectedRecipe < 0 || selectedRecipe >= availableRecipes.size()) return;

        SewingRecipe recipe = availableRecipes.get(selectedRecipe);

        if (!recipe.getBaseItem().isEmpty()) input.getItem(0).shrink(1);
        if (!recipe.getDye().isEmpty()) input.getItem(1).shrink(1);
        if (!recipe.getPadding().isEmpty()) input.getItem(2).shrink(1);
        if (!recipe.getEmbellish().isEmpty()) input.getItem(3).shrink(1);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col,
                    8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index == 4) {
                if (!this.moveItemStackTo(stackInSlot, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stackInSlot, itemstack);
            } else if (index >= 5 && index < 41) {
                if (!this.moveItemStackTo(stackInSlot, 0, 4, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 0 && index < 4) {
                if (!this.moveItemStackTo(stackInSlot, 5, 41, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.SEWING_TABLE.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            this.clearContainer(player, this.input);
        }
    }
}









