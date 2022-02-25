package boss_tools_giselle_addon.common.capability;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public interface IChargeMode
{
	public static final String LANGUGE_CATEGORY_CHARGEMODE = "chargemode";

	public static ITextComponent createDisplayName(ResourceLocation name)
	{
		return new TranslationTextComponent(BossToolsAddon.tl(LANGUGE_CATEGORY_CHARGEMODE, name));
	}

	public ResourceLocation getName();

	public ITextComponent getDisplayName();

	public Iterable<ItemStack> getItemStacks(Entity entity);
}
