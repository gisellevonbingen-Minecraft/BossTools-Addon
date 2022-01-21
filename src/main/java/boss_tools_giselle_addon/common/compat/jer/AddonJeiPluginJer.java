package boss_tools_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import jeresources.compatibility.CompatBase;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade.ITrade;

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
		return BossToolsAddon.rl("jer");
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

			for (Entry<VillagerProfession, Int2ObjectMap<ITrade[]>> entry : AlienTrade.VILLAGER_DEFAULT_TRADES.entrySet())
			{
				VillagerProfession villagerProfession = entry.getKey();
				Int2ObjectMap<ITrade[]> values = entry.getValue();

				if (values.values().stream().mapToInt(r -> r.length).sum() > 0)
				{
					AlienEntity alienEntity = (AlienEntity) ModInnet.ALIEN.get().create(CompatBase.getWorld());
					alienEntity.setVillagerData(alienEntity.getVillagerData().setProfession(villagerProfession));
					AlienEntry alienEntry = new AlienEntry(villagerProfession, values, alienEntity);

					if (alienEntry.tradeList.isEmpty() == false)
					{
						list.add(new AlienWrapper(alienEntry));
					}

				}

			}

			registration.addRecipes(list, AlienCategory.Uid);
		}

	}

}
