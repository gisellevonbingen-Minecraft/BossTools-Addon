package boss_tools_giselle_addon.compat.jei;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import boss_tools_giselle_addon.common.adapter.OxygenStorageAdapter;
import boss_tools_giselle_addon.common.adapter.OxygenStorageAdapterItemStackCreateEvent;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.config.AddonConfigs;
import boss_tools_giselle_addon.util.ReflectionUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
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
import net.mrscauthd.boss_tools.item.NetheriteSpaceArmorItem;
import net.mrscauthd.boss_tools.item.SpaceArmorItem;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
	public static final ResourceLocation CATEGORY_NASAWORKBENCH = getCategoryUid(WorkbenchJeiCategory.class);
	public static final ResourceLocation CATEGORY_COALGENERATOR = getCategoryUid(GeneratorJeiCategory.class);
	public static final ResourceLocation CATEGORY_BLASTFURNACE = getCategoryUid(BlastingFurnaceJeiCategory.class);
	public static final ResourceLocation CATEGORY_COMPRESSOR = getCategoryUid(CompressorJeiCategory.class);
	public static final ResourceLocation CATEGORY_OXYGENLOADER = getCategoryUid(OxygenMachineJeiCategory.class);
	public static final ResourceLocation CATEGORY_OXYGENGENERATOR = getCategoryUid(OxygenGeneratorJeiCategory.class);

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
		registration.addRecipeTransferHandler(new ElectricBlastFurnaceRecipeTransferInfo());
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		registration.addRecipeCatalyst(new ItemStack(AddonBlocks.ELECTRIC_BLAST_FURNACE.get()), CATEGORY_BLASTFURNACE);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		registration.addGuiContainerHandler(ElectricBlastFurnaceScreen.class, new ElectricBlastFurnaceGuiContainerHandler());
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		if (AddonConfigs.Common.recipes.hideOxygenLoaderRecipes.get() == false)
		{
			registration.addRecipes(this.generateOxygenMachineRecipes(), CATEGORY_OXYGENLOADER);
		}

		if (AddonConfigs.Common.recipes.hideOxygenGeneratorRecipes.get() == false)
		{
			registration.addRecipes(this.generateOxygenGeneratorRecipes(), CATEGORY_OXYGENGENERATOR);
		}

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

	public static ResourceLocation getCategoryUid(Class<?> klass)
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
