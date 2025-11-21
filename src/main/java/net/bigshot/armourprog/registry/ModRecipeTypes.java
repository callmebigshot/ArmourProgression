package net.bigshot.armourprog.registry;

import net.bigshot.armourprog.ArmourProg;
import net.bigshot.armourprog.recipe.SewingRecipe;
import net.bigshot.armourprog.recipe.SewingRecipeSerializer;
import net.bigshot.armourprog.recipe.SewingRecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArmourProg.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ArmourProg.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SewingRecipe>> SEWING_SERIALIZER =
            SERIALIZERS.register("sewing", () -> SewingRecipeSerializer.INSTANCE);

    public static final RegistryObject<RecipeType<SewingRecipe>> SEWING_TYPE =
            TYPES.register("sewing", () -> SewingRecipeType.INSTANCE);
}