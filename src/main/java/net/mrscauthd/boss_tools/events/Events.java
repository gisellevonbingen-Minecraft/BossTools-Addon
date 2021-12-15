package net.mrscauthd.boss_tools.events;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.*;
import net.mrscauthd.boss_tools.events.forgeevents.RenderHandItemEvent;
import net.mrscauthd.boss_tools.events.forgeevents.SetupLivingBipedAnimEvent;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId)
public class Events {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level world = player.level;

            //Lander Teleport System
            if (player.getVehicle() instanceof LanderEntity) {
                Methodes.landerTeleportOrbit(player, world);
            }

            //Planet Gui Open
            Methodes.openPlanetGui(player);

            //Oxygen System
            OxygenSystem.OxygenSystem(player);

            //Gravity Methode Call
            Gravity.Gravity(player, Gravity.GravityType.PLAYER, world);

            //Drop Off Hand Item
            Methodes.DropRocket(player);

            //Player orbit Fall Teleport
            if (player.getY() < 1 && !(player.getVehicle() instanceof LanderEntity)) {
                Methodes.playerFalltoPlanet(world, player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEntityTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level world = entity.level;

        Methodes.EntityOxygen(entity,world);

        //Gravity Methode Call
        Gravity.Gravity(entity, Gravity.GravityType.LIVING, world);

        //Venus Rain
        Methodes.VenusRain(entity, new ResourceLocation(BossToolsMod.ModId, "venus"));

        //Venus Fire
        Methodes.VenusFire(entity, new ResourceLocation(BossToolsMod.ModId, "venus"), new ResourceLocation(BossToolsMod.ModId, "mercury"));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void CameraPos(EntityViewRenderEvent.CameraSetup event) {
        Entity ridding = event.getCamera().getEntity().getVehicle();

        if (Methodes.isRocket(ridding) || ridding instanceof LanderEntity) {
            CameraType pointOfView = Minecraft.getInstance().options.getCameraType();

            if (pointOfView.equals(CameraType.THIRD_PERSON_FRONT) || pointOfView.equals(CameraType.THIRD_PERSON_BACK)) {
                event.getCamera().move(-event.getCamera().getMaxZoom(9d), 0d, 0);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void render(RenderPlayerEvent event) {
        if (event.getEntity().getVehicle() instanceof LanderEntity) {
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setupPlayerAngles(SetupLivingBipedAnimEvent.Post event) {
        if (event.getLivingEntity() instanceof Player) {
            Player player = (Player) event.getLivingEntity();
            HumanoidModel model = event.getModel();

            //Player Rocket Sit Rotations
            if (Methodes.isRocket(player.getVehicle())) {
                model.rightLeg.xRot = (float) Math.toRadians(0F);
                model.leftLeg.xRot = (float) Math.toRadians(0F);
                model.leftLeg.yRot = (float) Math.toRadians(3F);
                model.leftLeg.yRot = (float) Math.toRadians(3F);
                // Arms
                model.leftArm.xRot = -0.07f;
                model.rightArm.xRot = -0.07f;
            }

            //Player Hold Vehicles Rotation
            if (!Methodes.isRocket(player.getVehicle())) {
                Item item1 = player.getMainHandItem().getItem();
                Item item2 = player.getOffhandItem().getItem();
                if (item1 == ModInnet.TIER_1_ROCKET_ITEM.get()
                        || item1 == ModInnet.TIER_2_ROCKET_ITEM.get()
                        || item1 == ModInnet.TIER_3_ROCKET_ITEM.get()
                        || item1 == ModInnet.ROVER_ITEM.get()
                        //Off Hand
                        || item2 == ModInnet.TIER_1_ROCKET_ITEM.get()
                        || item2 == ModInnet.TIER_2_ROCKET_ITEM.get()
                        || item2 == ModInnet.TIER_3_ROCKET_ITEM.get()
                        || item2 == ModInnet.ROVER_ITEM.get()) {
                    model.rightArm.xRot = 10;
                    model.leftArm.xRot = 10;
                    model.rightArm.zRot = 0;
                    model.leftArm.zRot = 0;
                    model.rightArm.yRot = 0;
                    model.leftArm.yRot = 0;
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void ItemRender(RenderHandItemEvent.Pre event) {
        if (event.getLivingEntity() instanceof Player) {
            Player player = (Player) event.getLivingEntity();

            if (Methodes.isRocket(player.getVehicle())) {
                event.setCanceled(true);
            }

            Item item1 = player.getMainHandItem().getItem();
            Item item2 = player.getOffhandItem().getItem();

            if (item1 == ModInnet.TIER_1_ROCKET_ITEM.get()
                    || item1 == ModInnet.TIER_2_ROCKET_ITEM.get()
                    || item1 == ModInnet.TIER_3_ROCKET_ITEM.get()
                    || item1 == ModInnet.ROVER_ITEM.get()) {

                if (event.getHandSide() == HumanoidArm.LEFT) {
                    event.setCanceled(true);
                }

            }

            if (item2 == ModInnet.TIER_1_ROCKET_ITEM.get()
                    || item2 == ModInnet.TIER_2_ROCKET_ITEM.get()
                    || item2 == ModInnet.TIER_3_ROCKET_ITEM.get()
                    || item2 == ModInnet.ROVER_ITEM.get()) {

                if (event.getHandSide() == HumanoidArm.RIGHT) {
                    event.setCanceled(true);
                }

            }

        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Level world = event.world;
            ResourceKey<Level> world2 = world.dimension();
            if (world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"moon"))
             || world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"moon_orbit"))
             || world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"mars"))
             || world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"mars_orbit"))
             || world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"mercury"))
             || world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"mercury_orbit"))
             || world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"venus_orbit"))
             || world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"overworld_orbit"))) {
                world.thunderLevel = 0;
                world.rainLevel = 0;
            }
            if (world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId,"venus"))) {
                world.thunderLevel = 0;
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && event.getEntity() instanceof Player) {
            Player entity = (Player) event.getEntity();

            if (Methodes.nethriteSpaceSuitCheck(entity)) {
                if (event.getSource().isFire()) {
                    entity.setRemainingFireTicks(0);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void FishingBobberTick(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) event.getRayTraceResult()).getEntity();
            if (Methodes.AllVehiclesOr(entity)) {
                event.setCanceled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void SpaceSounds(PlaySoundEvent event) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null && Minecraft.getInstance().screen == null && Methodes.isSpaceWorld(Minecraft.getInstance().player.level)) {
            event.setSound(new SpaceSoundSystem(event.getSound()));
        }
    }
}
