package boss_tools_giselle_addon.common.network;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

public class FlagEditMessageSave extends TileEntityMessage<FlagTileEntity>
{
	private GameProfile gameProfile;

	public FlagEditMessageSave()
	{
		super();
		this.setGameProfile(null);
	}

	public FlagEditMessageSave(FlagTileEntity tileEntity, GameProfile gameProfile)
	{
		super(tileEntity);
		this.setGameProfile(gameProfile);
	}

	@Override
	public void decode(PacketBuffer buffer)
	{
		super.decode(buffer);

		CompoundNBT compound = buffer.readNbt();
		this.setGameProfile(NBTUtil.readGameProfile(compound));
	}

	@Override
	public void encode(PacketBuffer buffer)
	{
		super.encode(buffer);

		buffer.writeNbt(NBTUtil.writeGameProfile(new CompoundNBT(), this.getGameProfile()));
	}

	@Override
	public void onHandle(FlagTileEntity tileEntity, ServerPlayerEntity sender)
	{
		tileEntity.setPlayerProfile(this.getGameProfile());
		sender.getLevel().getChunkSource().blockChanged(tileEntity.getBlockPos());
	}

	public GameProfile getGameProfile()
	{
		return this.gameProfile;
	}

	public void setGameProfile(GameProfile gameProfile)
	{
		this.gameProfile = gameProfile;
	}

}
