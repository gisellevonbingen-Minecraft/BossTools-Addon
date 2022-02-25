package boss_tools_giselle_addon.common.network;

import boss_tools_giselle_addon.common.tile.ItemStackToItemStackTileEntityMultiRecipe;
import net.minecraft.entity.player.ServerPlayerEntity;

public class IS2ISMachineMessageAutoPull extends IS2ISMachineMessageAutoTransfer
{
	public IS2ISMachineMessageAutoPull()
	{
		super();
	}

	public IS2ISMachineMessageAutoPull(ItemStackToItemStackTileEntityMultiRecipe tileEntity, boolean value)
	{
		super(tileEntity, value);
	}

	@Override
	public void onHandle(ItemStackToItemStackTileEntityMultiRecipe tileEntity, ServerPlayerEntity sender)
	{
		tileEntity.setAutoPull(this.getValue());
	}

}
