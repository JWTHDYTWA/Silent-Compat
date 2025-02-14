package com.github.dragoni7.silentcompat.world;

import java.util.List;
import com.github.dragoni7.silentcompat.networking.Networking;
import com.github.dragoni7.silentcompat.networking.PacketVolitileExplosionParticles;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VolitileExplosion extends Explosion {
	
	private final Player player;
	private final Entity attacked;
	private final Level level;
	private final float radius;
	private final double x;
	private final double y;
	private final double z;

	public VolitileExplosion(Player player, Entity entity, DamageSource source, ExplosionDamageCalculator damageCalculator,
			double x, double y, double z, float radius) {
		
		super(player.level, entity, source, damageCalculator, x, y, z, radius, false, Explosion.BlockInteraction.NONE);
		
		this.player = player;
		this.attacked = entity;
		this.level = player.level;
		this.radius = radius;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// override explode just to prevent player damaged
	@Override
	public void explode() {
		this.level.gameEvent(this.attacked, GameEvent.EXPLODE, new Vec3(this.x, this.y, this.z));
		
		float f2 = this.radius * 2.0F;
		int k1 = Mth.floor(this.x - (double) f2 - 1.0D);
		int l1 = Mth.floor(this.x + (double) f2 + 1.0D);
		int i2 = Mth.floor(this.y - (double) f2 - 1.0D);
		int i1 = Mth.floor(this.y + (double) f2 + 1.0D);
		int j2 = Mth.floor(this.z - (double) f2 - 1.0D);
		int j1 = Mth.floor(this.z + (double) f2 + 1.0D);
		
		List<Entity> list = this.level.getEntities(this.attacked,
				new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, this, list, f2);
		Vec3 vec3 = new Vec3(this.x, this.y, this.z);

		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			if (!entity.ignoreExplosion() && !(entity instanceof Player)) {
				double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
				if (d12 <= 1.0D) {
					double d5 = entity.getX() - this.x;
					double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - this.y;
					double d9 = entity.getZ() - this.z;
					double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
					if (d13 != 0.0D) {
						d5 /= d13;
						d7 /= d13;
						d9 /= d13;
						double d14 = (double) getSeenPercent(vec3, entity);
						double d10 = (1.0D - d12) * d14;
						entity.hurt(this.getDamageSource(),
								(float) ((int) ((d10 * d10 + d10) / 2.0D * 6.0D * (double) f2 + 1.0D)));
						double d11 = d10;
						if (entity instanceof LivingEntity) {
							d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
						}

						entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
					}
				}
			}
		}
	}
	
	@Override
	public void finalizeExplosion(boolean particles) {
		
		this.level.playSound(null, this.x, this.y, this.z, SoundEvents.SHULKER_BULLET_HURT, SoundSource.NEUTRAL, 10.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F);

		if (player instanceof ServerPlayer) {
			Networking.sendToClient(new PacketVolitileExplosionParticles(attacked.getId()), (ServerPlayer) player);
		}
	}
}
