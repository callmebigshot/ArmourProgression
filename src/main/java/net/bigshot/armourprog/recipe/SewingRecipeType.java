package net.bigshot.armourprog.recipe;

import net.minecraft.world.item.crafting.RecipeType;

public class SewingRecipeType implements RecipeType<SewingRecipe> {
    public static final SewingRecipeType INSTANCE = new SewingRecipeType();
    public static final String ID = "sewing";

    private SewingRecipeType() {}
}
