package com.water.animall.Spider.entity;

import com.water.animall.Blaze.entity.TameBlazeEntity;
import com.water.animall.Cow.entity.TameCow;
import com.water.animall.Creeper.entity.SssssEntity;
import com.water.animall.Enderman.entity.TameEnderMan;
import com.water.animall.Ghast.entity.TameGhastEntity;
import com.water.animall.Gholem.entity.TameIronGolem;
import com.water.animall.Phantom.entity.TamePhantomEntity;
import com.water.animall.Slime.entity.TameSlimeEntity;
import com.water.animall.init.AnimallModEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
public class TameSpiderEntity extends Monster {

    @SubscribeEvent
    public static void addLivingEntityToBiomes(BiomeLoadingEvent event) {
        event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AnimallModEntities.BLAZE.get(), 20, 4, 4));
    }

    public TameSpiderEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(AnimallModEntities.SPIDER.get(), world);
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static void init() {
        SpawnPlacements.register(AnimallModEntities.BLAZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos,
                 random) -> (world.getBlockState(pos.below()).getMaterial() == Material.GRASS && world.getRawBrightness(pos, 0) > 8));
    }
    //いかこぴぺ
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(TameSpiderEntity.class, EntityDataSerializers.BYTE);
    private static final float TameSpiderEntity_SPECIAL_EFFECT_CHANCE = 0.1F;

    public TameSpiderEntity(EntityType<? extends TameSpiderEntity> p_33786_, Level p_33787_) {
        super(p_33786_, p_33787_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new TameSpiderEntity.TameSpiderEntityAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new TameSpiderEntity.TameSpiderEntityTargetGoal<>(this, IronGolem.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, false,
                (entity) -> !(entity instanceof SssssEntity)
                        && !(entity instanceof TameCow)
                        && !(entity instanceof TameSlimeEntity)
                        && !(entity instanceof TameEnderMan)
                        && !(entity instanceof TameGhastEntity)
                        &&!(entity instanceof TameIronGolem)
                        && !(entity instanceof TamePhantomEntity)
                        &&!(entity instanceof TameSpiderEntity)
                        && !(entity instanceof TameBlazeEntity)));
    }

    public double getPassengersRidingOffset() {
        return (double)(this.getBbHeight() * 0.5F);
    }

    protected PathNavigation createNavigation(Level p_33802_) {
        return new WallClimberNavigation(this, p_33802_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    public void tick() {
        super.tick();
        this.setInvulnerable(true);

        Player player = this.level.getNearestPlayer(this, 200.0);
        if (player != null) {

            // ゾンビがプレイヤーの背後を追従する
            double distanceX = player.getX() - this.getX();
            double distanceZ = player.getZ() - this.getZ();
            double angle = Math.atan2(distanceZ, distanceX);

            // ゾンビをプレイヤーの半径3マス以内に入らないように移動させる
            double minDistance = 3.0;
            double currentDistance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);


            double distancee = 20.0; // プレイヤーとの距離の閾値
            double teleportDistance = 5.0; // テレポートする距離

            if (this.distanceToSqr(player) > distancee * distancee) {

                this.setPos(player.position().x + 2, player.position().y, player.position().z + 2);
            }
            if (currentDistance < minDistance) {
                double newX = player.getX() - minDistance * Math.cos(angle);
                double newZ = player.getZ() - minDistance * Math.sin(angle);
                this.setPos(newX, this.getY(), newZ);
            } else if (currentDistance > 4.0) {
                // プレイヤーの位置に向かって移動
                this.getNavigation().moveTo(player, 1.7);
            }
        }
        if (!this.level.isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 160000000.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33814_) {
        return SoundEvents.SPIDER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    protected void playStepSound(BlockPos p_33804_, BlockState p_33805_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
        if (!p_33796_.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(p_33796_, p_33797_);
        }

    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public boolean canBeAffected(MobEffectInstance p_33809_) {
        if (p_33809_.getEffect() == MobEffects.POISON) {
            net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, p_33809_);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.canBeAffected(p_33809_);
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean p_33820_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_33820_) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33790_, DifficultyInstance p_33791_, MobSpawnType p_33792_, @Nullable SpawnGroupData p_33793_, @Nullable CompoundTag p_33794_) {
        p_33793_ = super.finalizeSpawn(p_33790_, p_33791_, p_33792_, p_33793_, p_33794_);


        if (p_33793_ == null) {
            p_33793_ = new TameSpiderEntity.TameSpiderEntityEffectsGroupData();
            if (p_33790_.getDifficulty() == Difficulty.HARD && p_33790_.getRandom().nextFloat() < 0.1F * p_33791_.getSpecialMultiplier()) {
                ((TameSpiderEntity.TameSpiderEntityEffectsGroupData)p_33793_).setRandomEffect(p_33790_.getRandom());
            }
        }

        if (p_33793_ instanceof TameSpiderEntity.TameSpiderEntityEffectsGroupData) {
            MobEffect mobeffect = ((TameSpiderEntity.TameSpiderEntityEffectsGroupData)p_33793_).effect;
            if (mobeffect != null) {
                this.addEffect(new MobEffectInstance(mobeffect, Integer.MAX_VALUE));
            }
        }

        return p_33793_;
    }

    protected float getStandingEyeHeight(Pose p_33799_, EntityDimensions p_33800_) {
        return 0.65F;
    }

    static class TameSpiderEntityAttackGoal extends MeleeAttackGoal {
        public TameSpiderEntityAttackGoal(TameSpiderEntity p_33822_) {
            super(p_33822_, 1.0D, true);
        }

        public boolean canUse() {
            return super.canUse() && !this.mob.isVehicle();
        }

        public boolean canContinueToUse() {
            float f = this.mob.getBrightness();
            if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
                this.mob.setTarget((LivingEntity)null);
                return false;
            } else {
                return super.canContinueToUse();
            }
        }

        protected double getAttackReachSqr(LivingEntity p_33825_) {
            return (double)(4.0F + p_33825_.getBbWidth());
        }
    }

    public static class TameSpiderEntityEffectsGroupData implements SpawnGroupData {
        @Nullable
        public MobEffect effect;

        public void setRandomEffect(Random p_33830_) {
            int i = p_33830_.nextInt(5);
            if (i <= 1) {
                this.effect = MobEffects.MOVEMENT_SPEED;
            } else if (i <= 2) {
                this.effect = MobEffects.DAMAGE_BOOST;
            } else if (i <= 3) {
                this.effect = MobEffects.REGENERATION;
            } else if (i <= 4) {
                this.effect = MobEffects.INVISIBILITY;
            }

        }
    }

    static class TameSpiderEntityTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public TameSpiderEntityTargetGoal(TameSpiderEntity p_33832_, Class<T> p_33833_) {
            super(p_33832_, p_33833_, true);
        }

        public boolean canUse() {
            float f = this.mob.getBrightness();
            return f >= 0.5F ? false : super.canUse();
        }
    }

}
