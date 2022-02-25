package boss_tools_giselle_addon.common.network;

import boss_tools_giselle_addon.common.tile.ItemStackToItemStackTileEntityMultiRecipe;
import net.minecraft.network.PacketBuffer;

public abstract class IS2ISMachineMessageAutoTransfer extends TileEntityMessage<ItemStackToItemStackTileEntityMultiRecipe>
{
	private boolean value;

	public IS2ISMachineMessageAutoTransfer()
	{
		super();
	}

	public IS2ISMachineMessageAutoTransfer(ItemStackToItemStackTileEntityMultiRecipe tileEntity, boolean value)
	{
		super(tileEntity);

		this.value = value;
	}
	
	@Override
	public void encode(PacketBuffer buffer)
	{
		super.encode(buffer);
		
		buffer.writeBoolean(this.value);
	}
	
	@Override
	public void decode(PacketBuffer buffer)
	{
		super.decode(buffer);
		
		this.value = buffer.readBoolean();
	}

	public boolean getValue()
	{
		return this.value;
	}

	public void setValue(boolean value)
	{
		this.value = value;
	}

}
