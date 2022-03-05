package beyond_earth_giselle_addon.common.registries;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipe;
import net.minecraft.core.Registry;
import net.mrscauthd.beyond_earth.crafting.BeyondEarthRecipeType;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipeType;

public class AddonRecipes
{
	public static final ItemStackToItemStackRecipeType<RollingRecipe> ROLLING = create(new ItemStackToItemStackRecipeType<>("rolling"));
	public static final ItemStackToItemStackRecipeType<ExtrudingRecipe> EXTRUDING = create(new ItemStackToItemStackRecipeType<>("extruding"));

	public static void visit()
	{

	}

	private static <T extends BeyondEarthRecipeType<?>> T create(T value)
	{
		Registry.register(Registry.RECIPE_TYPE, BeyondEarthAddon.rl(value.getName()), value);
		return value;
	}

	private AddonRecipes()
	{

	}

}
