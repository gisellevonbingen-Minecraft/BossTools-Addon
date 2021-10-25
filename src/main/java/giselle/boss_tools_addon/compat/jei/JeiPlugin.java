package giselle.boss_tools_addon.compat.jei;

import java.util.ArrayList;
import java.util.List;

import giselle.boss_tools_addon.BossToolsAddon;
import giselle.boss_tools_addon.common.adapter.OxygenStorageAdapter;
import giselle.boss_tools_addon.common.adapter.OxygenStorageAdapterItemStackCreateEvent;
import giselle.boss_tools_addon.common.block.AddonBlocks;
import giselle.boss_tools_addon.config.AddonConfigs;
import giselle.boss_tools_addon.util.ReflectionUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.JeiPlugin.BlastingFurnaceJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.CompressorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.GeneratorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.OxygenGeneratorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.OxygenGeneratorJeiCategory.OxygenGeneratorRecipeWrapper;
import net.mrscauthd.boss_tools.JeiPlugin.OxygenMachineJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.OxygenMachineJeiCategory.OxygenMachineRecipeWrapper;
import net.mrscauthd.boss_tools.JeiPlugin.WorkbenchJeiCategory;
import net.mrscauthd.boss_tools.gui.BlastFurnaceGUIGuiWindow;
import net.mrscauthd.boss_tools.gui.CompressorGuiGuiWindow;
import net.mrscauthd.boss_tools.gui.GeneratorGUIGuiWindow;
import net.mrscauthd.boss_tools.gui.NasaWorkbenchGuiWindow;
import net.mrscauthd.boss_tools.gui.OxygenBulletGeneratorGUIGuiWindow;
import net.mrscauthd.boss_tools.gui.OxygenLoaderGuiGuiWindow;
import net.mrscauthd.boss_tools.item.NetheriteSpaceArmorItem;
import net.mrscauthd.boss_tools.item.SpaceArmorItem;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
	public static IJeiHelpers jeiHelper;

	public JeiPlugin()
	{

	}

	@Override
	public ResourceLocation getPluginUid()
	{
		return BossToolsAddon.rl("jei");
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration)
	{

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		registration.addRecipeClickArea(NasaWorkbenchGuiWindow.class, 108, 49, 14, 14, this.getCategoryUid(WorkbenchJeiCategory.class));
		registration.addRecipeClickArea(GeneratorGUIGuiWindow.class, 78, 52, 13, 13, this.getCategoryUid(GeneratorJeiCategory.class));
		registration.addRecipeClickArea(BlastFurnaceGUIGuiWindow.class, 73, 38, 22, 15, this.getCategoryUid(BlastingFurnaceJeiCategory.class));
		registration.addRecipeClickArea(CompressorGuiGuiWindow.class, 61, 39, 22, 15, this.getCategoryUid(CompressorJeiCategory.class));
		registration.addRecipeClickArea(OxygenLoaderGuiGuiWindow.class, 76, 42, 14, 12, this.getCategoryUid(OxygenMachineJeiCategory.class));
		registration.addRecipeClickArea(OxygenBulletGeneratorGUIGuiWindow.class, 76, 30, 14, 12, this.getCategoryUid(OxygenGeneratorJeiCategory.class));
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		if (AddonConfigs.Common.recipes.hideOxygenLoaderRecipes.get() == false)
		{
			registration.addRecipes(this.generateOxygenMachineRecipes(), this.getCategoryUid(OxygenMachineJeiCategory.class));
		}

		if (AddonConfigs.Common.recipes.hideOxygenGeneratorRecipes.get() == false)
		{
			registration.addRecipes(this.generateOxygenGeneratorRecipes(), this.getCategoryUid(OxygenGeneratorJeiCategory.class));
		}

		this.addIngredientInfo(registration, AddonBlocks.OXYGEN_ACCEPTER.get());
		this.addIngredientInfo(registration, AddonBlocks.FUEL_LOADER.get(), AddonConfigs.Common.machines.fuelloader_range.get());
	}

	public List<OxygenGeneratorRecipeWrapper> generateOxygenGeneratorRecipes()
	{
		List<OxygenGeneratorRecipeWrapper> recipes = new ArrayList<>();

		for (Item item : ItemTags.LEAVES.getValues())
		{
			if (item == Items.OAK_LEAVES)
			{
				continue;
			}

			ArrayList<ItemStack> inputs = new ArrayList<>();
			inputs.add(new ItemStack(item));
			recipes.add(new OxygenGeneratorRecipeWrapper(inputs));
		}

		return recipes;
	}

	public List<OxygenMachineRecipeWrapper> generateOxygenMachineRecipes()
	{
		List<Item> spacesuits = new ArrayList<>();
		spacesuits.add(SpaceArmorItem.body);
		spacesuits.add(NetheriteSpaceArmorItem.body);

		List<OxygenMachineRecipeWrapper> recipes = new ArrayList<>();

		for (Item item : spacesuits)
		{
			ItemStack itemStack = new ItemStack(item);
			OxygenStorageAdapter<? extends ItemStack> adapter = new OxygenStorageAdapterItemStackCreateEvent(itemStack).resolve();

			if (adapter != null)
			{
				adapter.setStoredOxygen(adapter.getOxygenCapacity());
			}

			recipes.addAll(this.generateOxygenMachineRecipes(itemStack));
		}

		return recipes;
	}

	public List<OxygenMachineRecipeWrapper> generateOxygenMachineRecipes(ItemStack spacesuit)
	{
		List<OxygenMachineRecipeWrapper> recipes = new ArrayList<>();

		for (Item item : ItemTags.LEAVES.getValues())
		{
			if (item == Items.OAK_LEAVES)
			{
				continue;
			}

			ArrayList<ItemStack> inputs = new ArrayList<>();
			inputs.add(spacesuit);
			inputs.add(new ItemStack(item));
			recipes.add(new OxygenMachineRecipeWrapper(inputs));
		}

		return recipes;
	}

	public void addIngredientInfo(IRecipeRegistration registration, IItemProvider itemProvider, Object... objects)
	{
		registration.addIngredientInfo(new ItemStack(itemProvider), VanillaTypes.ITEM, new TranslationTextComponent("jei.info." + itemProvider.asItem().getRegistryName().getPath(), objects));
	}

	public ResourceLocation getCategoryUid(Class<?> klass)
	{
		try
		{
			return (ResourceLocation) ReflectionUtil.getDeclaredAccessibleField(klass, "Uid").get(klass);
		}
		catch (SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		}

	}

}
