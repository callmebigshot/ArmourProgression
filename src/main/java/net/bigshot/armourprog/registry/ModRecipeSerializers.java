package net.bigshot.armourprog.registry;

import net.bigshot.armourprog.ArmourProg;
import net.bigshot.armourprog.recipe.SewingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArmourProg.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> SEWING_SERIALIZER =
            SERIALIZERS.register("sewing", () -> SewingRecipeSerializer.INSTANCE);

}
