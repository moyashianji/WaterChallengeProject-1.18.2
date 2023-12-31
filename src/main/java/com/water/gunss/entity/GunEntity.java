
package com.water.gunss.entity;

import com.water.animall.Blaze.entity.TameBlazeEntity;
import com.water.gunss.init.GunssModEntities;
import com.water.gunss.init.GunssModItems;
import com.water.gunss.procedures.GunFeibiDaoJugaenteiteiniDangtatutatokiProcedure;
import com.water.tamemobitem.init.TamemobModItems;
import com.water.teamitem.init.TeamitemModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;


import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class GunEntity extends AbstractArrow implements ItemSupplier {
	public GunEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(GunssModEntities.GUN.get(), world);
	}

	public GunEntity(EntityType<? extends GunEntity> type, Level world) {
		super(type, world);
	}

	public GunEntity(EntityType<? extends GunEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public GunEntity(EntityType<? extends GunEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return new ItemStack(GunssModItems.GUN.get());
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		Level _level = entityHitResult.getEntity().getLevel();

		if(!(entityHitResult.getEntity() instanceof Player)) {

			if(!(entityHitResult.getEntity().level.isClientSide)) {
				ServerLevel level = (ServerLevel) _level;

				for (int i = 0; i < 360; i++) { // 必要な数だけ繰り返す

					double xOffset = entityHitResult.getEntity().getX() + (_level.getRandom().nextDouble() - 0.5) * 2.0;
					double yOffset = entityHitResult.getEntity().getY() + entityHitResult.getEntity().getBbHeight() * 0.5;
					double zOffset = entityHitResult.getEntity().getZ() + (_level.getRandom().nextDouble() - 0.5) * 2.0;
					level.sendParticles(ParticleTypes.COMPOSTER, xOffset, yOffset, zOffset, 1, 0.0, 0.0, 0.0, 0.0);
				}
			}
			if(entityHitResult.getEntity() instanceof Blaze){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMEBLAZE.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}
			if(entityHitResult.getEntity() instanceof Cow){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMECOW.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}

			if(entityHitResult.getEntity() instanceof Creeper){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMECREEPER.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}
			if(entityHitResult.getEntity() instanceof EnderMan){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMEENDER.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}

			if(entityHitResult.getEntity() instanceof Phantom){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMEFHANTOM.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}

			if(entityHitResult.getEntity() instanceof Ghast){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMEGHAST.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}

			if(entityHitResult.getEntity() instanceof IronGolem){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMEGOLEM.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}
			if(entityHitResult.getEntity() instanceof Slime){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMESLIME.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}
			if(entityHitResult.getEntity() instanceof Piglin){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMEPIGLIN.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}

			if(entityHitResult.getEntity() instanceof Spider){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TamemobModItems.TAMESPIDER.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}

			if(entityHitResult.getEntity() instanceof Wolf){

				if (!_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, entityHitResult.getEntity().getX(), entityHitResult.getEntity().getY(), entityHitResult.getEntity().getZ(), new ItemStack(TeamitemModItems.WOLF.get()));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				entityHitResult.getEntity().discard();
			}
		}
		GunFeibiDaoJugaenteiteiniDangtatutatokiProcedure.execute(

		);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.inGround)
			this.discard();
	}

	public static GunEntity shoot(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
		GunEntity entityarrow = new GunEntity(GunssModEntities.GUN.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
				ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.snowball.throw")), SoundSource.PLAYERS, 1,
				1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static GunEntity shoot(LivingEntity entity, LivingEntity target) {
		GunEntity entityarrow = new GunEntity(GunssModEntities.GUN.get(), entity, entity.level);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 3f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(0);
		entityarrow.setKnockback(0);
		entityarrow.setCritArrow(false);
		entity.level.addFreshEntity(entityarrow);
		entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
				ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.snowball.throw")), SoundSource.PLAYERS, 1,
				1f / (new Random().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
