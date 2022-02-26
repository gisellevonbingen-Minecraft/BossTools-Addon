package beyond_earth_giselle_addon.common.network;

import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import net.minecraft.server.level.ServerPlayer;

public class IS2ISMachineMessageAutoPull extends IS2ISMachineMessageAutoTransfer
{
	public IS2ISMachineMessageAutoPull()
	{
		super();
	}

	public IS2ISMachineMessageAutoPull(ItemStackToItemStackBlockEntityMultiRecipe blockEntity, boolean value)
	{
		super(blockEntity, value);
	}

	@Override
	public void onHandle(ItemStackToItemStackBlockEntityMultiRecipe blockEntity, ServerPlayer sender)
	{
		blockEntity.setAutoPull(this.getValue());
	}

}