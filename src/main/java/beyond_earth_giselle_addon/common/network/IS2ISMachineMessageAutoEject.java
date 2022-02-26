package beyond_earth_giselle_addon.common.network;

import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import net.minecraft.server.level.ServerPlayer;

public class IS2ISMachineMessageAutoEject extends IS2ISMachineMessageAutoTransfer
{
	public IS2ISMachineMessageAutoEject()
	{
		super();
	}

	public IS2ISMachineMessageAutoEject(ItemStackToItemStackBlockEntityMultiRecipe blockEntity, boolean value)
	{
		super(blockEntity, value);
	}

	@Override
	public void onHandle(ItemStackToItemStackBlockEntityMultiRecipe blockEntity, ServerPlayer sender)
	{
		blockEntity.setAutoEject(this.getValue());
	}

}