package net.bigshot.armourprog.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class SewingRecipeSerializer implements RecipeSerializer<SewingRecipe> {

    public static final SewingRecipeSerializer INSTANCE = new SewingRecipeSerializer();

    @Override
    public SewingRecipe fromJson(ResourceLocation id, JsonObject json) {
        Ingredient baseItem = Ingredient.fromJson(json.get("base_item"));
        Ingredient dye = Ingredient.fromJson(json.get("dye"));
        Ingredient padding = Ingredient.fromJson(json.get("padding"));
        Ingredient embellish = Ingredient.fromJson(json.get("embellish"));

        if (!json.has("result")) {
            throw new JsonSyntaxException("Missing 'result' object for sewing recipe: " + id);
        }

        JsonObject resultObj = GsonHelper.getAsJsonObject(json, "result");
        ItemStack result = ShapedRecipe.itemStackFromJson(resultObj);

        return new SewingRecipe(id, baseItem, dye, padding, embellish, result);
    }

    @Override
    public SewingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        Ingredient baseItem = Ingredient.fromNetwork(buf);
        Ingredient dye = Ingredient.fromNetwork(buf);
        Ingredient padding = Ingredient.fromNetwork(buf);
        Ingredient embellish = Ingredient.fromNetwork(buf);

        ItemStack result = buf.readItem();

        return new SewingRecipe(id, baseItem, dye, padding, embellish, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, SewingRecipe recipe) {
        recipe.getBaseItem().toNetwork(buf);
        recipe.getDye().toNetwork(buf);
        recipe.getPadding().toNetwork(buf);
        recipe.getEmbellish().toNetwork(buf);

        buf.writeItem(recipe.getResult());
    }
}