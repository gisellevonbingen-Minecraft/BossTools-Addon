package net.mrscauthd.boss_tools.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.screens.rover.RoverGui;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class RoverEntity extends PathfinderMob {
    private double speed = 0;
    private boolean forward = false;

    public static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(RocketTier1Entity.class, EntityDataSerializers.INT);

	public static final int FUEL_BUCKETS = 3;

    public RoverEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.entityData.define(FUEL, 0);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected void doPush(Entity p_20971_) {
    }

    @Override
    public void push(Entity p_21294_) {
    }

    @Deprecated
    public boolean canBeRiddenInWater() {
        return true;
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return null;
    }

    @Override
    public SoundEvent getDeathSound() {
        return null;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() - 0.15;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onPassengerTurned(Entity p_20320_) {
        this.applyYawToEntity(p_20320_);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        Vec3[] avector3d = new Vec3[]{getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot()), getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot() - 22.5F), getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot() + 22.5F), getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot() - 45.0F), getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot() + 45.0F)};
        Set<BlockPos> set = Sets.newLinkedHashSet();
        double d0 = this.getBoundingBox().maxY;
        double d1 = this.getBoundingBox().minY - 0.5D;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for(Vec3 vector3d : avector3d) {
            blockpos$mutable.set(this.getX() + vector3d.x, d0, this.getZ() + vector3d.z);

            for(double d2 = d0; d2 > d1; --d2) {
                set.add(blockpos$mutable.immutable());
                blockpos$mutable.move(Direction.DOWN);
            }
        }

        for(BlockPos blockpos : set) {
            if (!this.level.getFluidState(blockpos).is(FluidTags.LAVA)) {
                double d3 = this.level.getBlockFloorHeight(blockpos);
                if (DismountHelper.isBlockFloorValid(d3)) {
                    Vec3 vector3d1 = Vec3.upFromBottomCenterOf(blockpos, d3);

                    for(Pose pose : livingEntity.getDismountPoses()) {
                        AABB axisalignedbb = livingEntity.getLocalBoundsForPose(pose);
                        if (DismountHelper.isBlockFloorValid(this.level.getBlockFloorHeight(blockpos))) {
                            livingEntity.setPose(pose);
                            return vector3d1;
                        }
                    }
                }
            }
        }

        return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
    }

    protected void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        entityToUpdate.yRotO += f1 - f;
        entityToUpdate.setYRot(entityToUpdate.getYRot() + f1 - f);
        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
    }

    @Override
    protected void removePassenger(Entity passenger) {
        if (passenger.isCrouching() && !passenger.level.isClientSide) {
            if (passenger instanceof ServerPlayer) {
                this.setSpeed(0f);
            }
        }
        super.removePassenger(passenger);
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        ItemStack itemStack = new ItemStack(ModInnet.ROVER_ITEM.get(), 1);
        itemStack.getOrCreateTag().putInt(BossToolsMod.ModId + ":fuel", this.entityData.get(FUEL));

        return itemStack;
    }

    @Override
    public void kill() {
        this.spawnRoverItem();
        this.dropEquipment();
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return new AABB(this.getX(),this.getY(),this.getZ(),this.getX(),this.getY(), this.getZ()).inflate(4.5,4.5,4.5);
    }

    @Override
    public boolean hurt(DamageSource source, float p_21017_) {
        if (!source.isProjectile() && source.getEntity() != null && source.getEntity().isCrouching() && !this.isVehicle()) {
            this.spawnRoverItem();
            this.dropEquipment();
            this.remove(RemovalReason.DISCARDED);
        }

        return false;
    }

    protected void spawnRoverItem() {
        if (!level.isClientSide) {
            ItemStack itemStack = new ItemStack(ModInnet.ROVER_ITEM.get(), 1);
            itemStack.getOrCreateTag().putInt(BossToolsMod.ModId + ":fuel", this.getEntityData().get(FUEL));

            ItemEntity entityToSpawn = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), itemStack);
            entityToSpawn.setPickUpDelay(10);
            level.addFreshEntity(entityToSpawn);
        }
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                this.spawnAtLocation(itemstack);
            }
        }
    }

    private final ItemStackHandler inventory = new ItemStackHandler(9) {
        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }
    };

    private final CombinedInvWrapper combined = new CombinedInvWrapper(inventory, new EntityHandsInvWrapper(this), new EntityArmorInvWrapper(this));

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == null) {
            return LazyOptional.of(() -> combined).cast();
        }
        return super.getCapability(capability, side);
    }

    public IItemHandlerModifiable getItemHandler() {
        return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("InventoryCustom", inventory.serializeNBT());

        compound.putInt("fuel", this.getEntityData().get(FUEL));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        Tag inventoryCustom = compound.get("InventoryCustom");
        if (inventoryCustom instanceof CompoundTag) {
            inventory.deserializeNBT((CompoundTag) inventoryCustom);
        }

        this.entityData.set(FUEL, compound.getInt("fuel"));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        super.mobInteract(player, hand);
        InteractionResult retval = InteractionResult.sidedSuccess(this.level.isClientSide);

        if (player instanceof ServerPlayer && player.isCrouching()) {

            NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return RoverEntity.this.getDisplayName();
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                    packetBuffer.writeVarInt(RoverEntity.this.getId());
                    return new RoverGui.GuiContainer(id, inventory, packetBuffer);
                }
            }, buf -> {
                buf.writeVarInt(this.getId());
            });

            return retval;
        }

        player.startRiding(this);
        return retval;
    }

    public boolean getforward() {
        return forward;
    }

    @Override
    public void baseTick() {
        super.baseTick();

        //Fuel Load up
        if (Methodes.tagCheck(FluidUtil2.findBucketFluid(this.inventory.getStackInSlot(0).getItem()), ModInnet.FLUID_VEHICLE_FUEL_TAG)) {

            if (this.entityData.get(FUEL) <= 2000) {
                this.getEntityData().set(FUEL, this.getEntityData().get(FUEL) + 1000);
                this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
            }
        }

        if (this.getPassengers().isEmpty()) {
            return;
        }

        if (!(this.getPassengers().get(0) instanceof Player)) {
            return;
        }

        if (this.isEyeInFluid(FluidTags.WATER)) {
            return;
        }

        Player passanger = (Player) this.getPassengers().get(0);

        if (passanger.zza > 0.01 && this.getEntityData().get(FUEL) != 0) {

            this.entityData.set(FUEL, this.getEntityData().get(FUEL) - 1);
            forward = true;
        } else if (passanger.zza < -0.01 && this.getEntityData().get(FUEL) != 0) {

            this.entityData.set(FUEL, this.getEntityData().get(FUEL) - 1);
            forward = false;
        }
    }

    @Override
    public void travel(Vec3 p_21280_) {

        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof Player) {

            Player passanger = (Player) this.getPassengers().get(0);

            this.flyingSpeed = this.getSpeed() * 0.15F;
            this.maxUpStep = 1.0F;

            double pmovement = passanger.zza;

            if (pmovement == 0 || this.getEntityData().get(FUEL) == 0 || this.isEyeInFluid(FluidTags.WATER)) {
                pmovement = 0;
                this.setSpeed(0f);

                if (speed != 0) {
                    if (speed > 0.02) {
                        speed = speed - 0.02;
                    }
                }
            }

            if (this.forward && this.getEntityData().get(FUEL) != 0) {
                if (this.getSpeed() >= 0.01) {
                    if (speed <= 0.32) {
                        speed = speed + 0.02;
                    }
                }

                if (this.getSpeed() < 0.25) {
                    this.setSpeed(this.getSpeed() + 0.02F);
                }

            }

            if (!this.forward) {

                if (this.getEntityData().get(FUEL) != 0 && !this.isEyeInFluid(FluidTags.WATER)) {

                    if (this.getSpeed() <= 0.04) {
                        this.setSpeed(this.getSpeed() + 0.02f);
                    }
                }

                if (this.getSpeed() >= 0.08) {
                    this.setSpeed(0f);
                }
            }

            if (this.forward) {
                this.setWellRotationPlus(4.0f, 0.4f);
            } else {
                this.setWellRotationMinus(8.0f, 0.8f);
            }

            super.travel(new Vec3(0, 0, pmovement));
            return;
        }

        super.travel(new Vec3(0, 0, 0));
    }

    public void setWellRotationMinus(float rotation1, float rotation2) {
        this.animationSpeedOld = this.animationSpeed;
        double d1 = this.getX() - this.xo;
        double d0 = this.getZ() - this.zo;
        float f1 = -Mth.sqrt((float) (d1 * d1 + d0 * d0)) * rotation1;
        if (f1 > 1.0F)
            f1 = 1.0F;
        this.animationSpeed += (f1 - this.animationSpeed) * rotation2;
        this.animationPosition += this.animationSpeed;
    }

    public void setWellRotationPlus(float rotation1, float rotation2) {
        this.animationSpeedOld = this.animationSpeed;
        double d1 = this.getX() - this.xo;
        double d0 = this.getZ() - this.zo;
        float f1 = Mth.sqrt((float) (d1 * d1 + d0 * d0)) * rotation1;
        if (f1 > 1.0F)
            f1 = 1.0F;
        this.animationSpeed += (f1 - this.animationSpeed) * rotation2;
        this.animationPosition += this.animationSpeed;
    }
}
