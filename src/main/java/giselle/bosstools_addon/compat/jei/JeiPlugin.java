package giselle.bosstools_addon.compat.jei;

import giselle.bosstools_addon.BossToolsAddon;
import giselle.bosstools_addon.common.block.AddonBlocks;
import giselle.bosstools_addon.util.ReflectionUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.JeiPlugin.BlastingFurnaceJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.CompressorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.GeneratorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.OxygenGeneratorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.OxygenMachineJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.WorkbenchJeiCategory;
import net.mrscauthd.boss_tools.gui.BlastFurnaceGUIGuiWindow;
import net.mrscauthd.boss_tools.gui.CompressorGuiGuiWindow;
import net.mrscauthd.boss_tools.gui.GeneratorGUIGuiWindow;
import net.mrscauthd.boss_tools.gui.NasaWorkbenchGuiWindow;
import net.mrscauthd.boss_tools.gui.OxygenBulletGeneratorGUIGuiWindow;
import net.mrscauthd.boss_tools.gui.OxygenLoaderGuiGuiWindow;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
	public static IJeiHelpers jeiHelper;

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

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		registration.addIngredientInfo(new ItemStack(AddonBlocks.OXYGEN_ACCEPTER.get()), VanillaTypes.ITEM, new TranslationTextComponent("jei.info.oxygen_accepter"));
	}

}
