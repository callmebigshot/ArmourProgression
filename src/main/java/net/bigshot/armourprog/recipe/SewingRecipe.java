package net.bigshot.armourprog.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.bigshot.armourprog.registry.ModRecipeTypes;

public class SewingRecipe implements Recipe<Container> {

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.SEWING_SERIALIZER.get();
    }

    private final ResourceLocation id;

    private final Ingredient baseItem;
    private final Ingredient dye;
    private final Ingredient padding;
    private final Ingredient embellish;

    private final ItemStack result;

    public SewingRecipe(ResourceLocation id,
                        Ingredient baseItem,
                        Ingredient dye,
                        Ingredient padding,
                        Ingredient embellish,
                        ItemStack result) {
        this.id = id;
        this.baseItem = baseItem;
        this.dye = dye;
        this.padding = padding;
        this.embellish = embellish;
        this.result = result;
    }

    public Ingredient getBaseItem() {
        return baseItem;
    }

    public Ingredient getDye() {
        return dye;
    }

    public Ingredient getPadding() {
        return padding;
    }

    public Ingredient getEmbellish() {
        return embellish;
    }

    public ItemStack getResult() {
        return result;
    }


    @Override
    public boolean matches(Container container, Level level) {
        return baseItem.test(container.getItem(0))
                && dye.test(container.getItem(1))
                && padding.test(container.getItem(2))
                && embellish.test(container.getItem(3));
    }


    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeType<?> getType() {
        return SewingRecipeType.INSTANCE;
    }
}
