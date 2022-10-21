package beyond_earth_giselle_addon.common.network;

import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import net.minecraft.network.FriendlyByteBuf;

public abstract class IS2ISMachineMessageAutoTransfer extends BlockEntityMessage<ItemStackToItemStackBlockEntityMultiRecipe>
{
	private boolean value;

	public IS2ISMachineMessageAutoTransfer()
	{
		super();
	}

	public IS2ISMachineMessageAutoTransfer(ItemStackToItemStackBlockEntityMultiRecipe blockEntity, boolean value)
	{
		super(blockEntity);

		this.value = value;
	}

	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		super.encode(buffer);

		buffer.writeBoolean(this.value);
	}

	@Override
	public void decode(FriendlyByteBuf buffer)
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
