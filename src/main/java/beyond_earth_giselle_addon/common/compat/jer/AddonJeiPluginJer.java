package beyond_earth_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.compat.AddonCompatibleManager;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import jeresources.compatibility.CompatBase;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.mrscauthd.beyond_earth.entities.alien.AlienEntity;
import net.mrscauthd.beyond_earth.entities.alien.AlienTrade;
import net.mrscauthd.beyond_earth.registries.EntitiesRegistry;

@mezz.jei.api.JeiPlugin
public class AddonJeiPluginJer implements IModPlugin
{
	private static AddonJeiPluginJer instance;

	public static AddonJeiPluginJer instance()
	{
		return instance;
	}

	public AddonJeiPluginJer()
	{
		instance = this;
	}

	@Override
	public ResourceLocation getPluginUid()
	{
		return BeyondEarthAddon.rl("jer");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		if (AddonCompatibleManager.JER.isLoaded() == true)
		{
			IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
			registration.addRecipeCategories(new AlienCategory(guiHelper));
		}

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		if (AddonCompatibleManager.JER.isLoaded() == true)
		{
			List<AlienWrapper> list = new ArrayList<>();

			for (Entry<VillagerProfession, Int2ObjectMap<ItemListing[]>> entry : AlienTrade.TRADES.entrySet())
			{
				VillagerProfession villagerProfession = entry.getKey();
				Int2ObjectMap<ItemListing[]> values = entry.getValue();

				if (values.values().stream().mapToInt(r -> r.length).sum() > 0)
				{
					AlienEntity alienEntity = (AlienEntity) EntitiesRegistry.ALIEN.get().create(CompatBase.getLevel());
					alienEntity.setVillagerData(alienEntity.getVillagerData().setProfession(villagerProfession));
					AlienEntry alienEntry = new AlienEntry(villagerProfession, values, alienEntity);

					if (alienEntry.tradeList.isEmpty() == false)
					{
						list.add(new AlienWrapper(alienEntry));
					}

				}

			}

			registration.addRecipes(AlienCategory.TYPE, list);
		}

	}

}
