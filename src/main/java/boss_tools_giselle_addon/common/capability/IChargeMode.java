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

	public ResourceLocation getName();

	public default ITextComponent getDisplayName()
	{
		return new TranslationTextComponent(BossToolsAddon.tl(LANGUGE_CATEGORY_CHARGEMODE, this.getName()));
	}

	public Iterable<ItemStack> getItemStacks(Entity entity);
}
