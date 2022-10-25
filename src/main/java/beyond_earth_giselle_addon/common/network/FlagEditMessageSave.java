package beyond_earth_giselle_addon.common.network;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.mrscauthd.beyond_earth.common.blocks.entities.FlagBlockEntity;

public class FlagEditMessageSave extends BlockEntityMessage<FlagBlockEntity>
{
	private GameProfile gameProfile;

	public FlagEditMessageSave()
	{
		super();
		this.setGameProfile(null);
	}

	public FlagEditMessageSave(FlagBlockEntity blockEntity, GameProfile gameProfile)
	{
		super(blockEntity);
		this.setGameProfile(gameProfile);
	}

	@Override
	public void decode(FriendlyByteBuf buffer)
	{
		super.decode(buffer);

		CompoundTag compound = buffer.readNbt();
		this.setGameProfile(NbtUtils.readGameProfile(compound));
	}

	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		super.encode(buffer);

		buffer.writeNbt(NbtUtils.writeGameProfile(new CompoundTag(), this.getGameProfile()));
	}

	@Override
	public void onHandle(FlagBlockEntity blockEntity, ServerPlayer sender)
	{
		blockEntity.setOwner(this.getGameProfile());
		sender.getLevel().getChunkSource().blockChanged(blockEntity.getBlockPos());
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
