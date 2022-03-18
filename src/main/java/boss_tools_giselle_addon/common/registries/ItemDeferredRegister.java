package boss_tools_giselle_addon.common.registries;

import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemDeferredRegister extends DeferredRegisterWrapper<Item>
{
	public ItemDeferredRegister(String modid)
	{
		super(modid, ForgeRegistries.ITEMS);
	}

}
