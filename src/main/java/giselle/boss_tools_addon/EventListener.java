package giselle.boss_tools_addon;

import giselle.boss_tools_addon.common.adapter.FuelAdapterBossToolsEntity;
import giselle.boss_tools_addon.common.adapter.FuelAdapterCreateEntityEvent;
import giselle.boss_tools_addon.common.adapter.OxygenMachineAdapterBossToolsTileEntity;
import giselle.boss_tools_addon.common.adapter.OxygenMachineAdapterCreateTileEvent;
import giselle.boss_tools_addon.common.adapter.OxygenStorageAdapterBossToolsSpaceArmor;
import giselle.boss_tools_addon.common.adapter.OxygenStorageAdapterItemStackCreateEvent;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.block.FuelBlock;
import net.mrscauthd.boss_tools.block.OxygenGeneratorBlock;
import net.mrscauthd.boss_tools.block.OxygenMachineBlock;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.item.FuelBucketBigItem;
import net.mrscauthd.boss_tools.item.NetheriteSpaceArmorItem;
import net.mrscauthd.boss_tools.item.SpaceArmorItem;

public class EventListener
{
	@SubscribeEvent
	public void onFuelAdapterCreateEntity(FuelAdapterCreateEntityEvent e)
	{
		Entity taget = e.getTaget();

		if (taget instanceof RoverEntity.CustomEntity)
		{
			e.setAdapter(FuelAdapterBossToolsEntity::new, FuelBlock.bucket);
		}
		else if (taget instanceof RocketEntity.CustomEntity)
		{
			e.setAdapter(FuelAdapterBossToolsEntity::new, FuelBlock.bucket);
		}
		else if (taget instanceof RocketTier2Entity.CustomEntity)
		{
			e.setAdapter(FuelAdapterBossToolsEntity::new, FuelBucketBigItem.block);
		}
		else if (taget instanceof RocketTier3Entity.CustomEntity)
		{
			e.setAdapter(FuelAdapterBossToolsEntity::new, FuelBucketBigItem.block);
		}

	}

	@SubscribeEvent
	public void onOxygenMachineAdapterCreateTile(OxygenMachineAdapterCreateTileEvent e)
	{
		TileEntity taget = e.getTaget();

		if (taget instanceof OxygenGeneratorBlock.CustomTileEntity)
		{
			e.setAdapter(OxygenMachineAdapterBossToolsTileEntity::new, 400.0D);
		}
		else if (taget instanceof OxygenMachineBlock.CustomTileEntity)
		{
			e.setAdapter(OxygenMachineAdapterBossToolsTileEntity::new, 200.0D);
		}

	}

	@SubscribeEvent
	public void onOxygenStorageAdapterItemStackCreate(OxygenStorageAdapterItemStackCreateEvent e)
	{
		Item item = e.getTaget().getItem();

		if (item == SpaceArmorItem.body || item == NetheriteSpaceArmorItem.body)
		{
			e.setAdapter(OxygenStorageAdapterBossToolsSpaceArmor::new);
		}

	}

}
