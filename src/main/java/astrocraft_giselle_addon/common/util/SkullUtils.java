package astrocraft_giselle_addon.common.util;

import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

public class SkullUtils
{
	public static final String SKULL_OWNER = "SkullOwner";

	@Nullable
	public static void getGameProfile(CompoundTag compound, Consumer<GameProfile> callback)
	{
		if (compound == null)
		{

		}
		else if (compound.contains(SKULL_OWNER, Tag.TAG_COMPOUND) == true)
		{
			GameProfile readGameProfile = NbtUtils.readGameProfile(compound.getCompound(SKULL_OWNER));
			callback.accept(readGameProfile);
		}
		else if (compound.contains(SKULL_OWNER, Tag.TAG_STRING) == true)
		{
			GameProfile gameProfile = new GameProfile((UUID) null, compound.getString(SKULL_OWNER));
			SkullBlockEntity.updateGameprofile(gameProfile, callback);
		}

	}

	private SkullUtils()
	{

	}

}
