package com.TheRPGAdventurer.ROTD.server.entity.breeds;


import com.TheRPGAdventurer.ROTD.client.render.BreathWeaponEmitter;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathAffectedArea;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathWeapon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathWeaponNether;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode.Power;

import net.minecraft.init.Biomes;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class DragonBreedNether extends DragonBreed {

    DragonBreedNether() {
        super("nether", 0xe5b81b);
        
        addImmunity(DamageSource.IN_FIRE);
        addImmunity(DamageSource.ON_FIRE);
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);          
        
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {
        dragon.getBrain().setAvoidsWater(true);
    }

    @Override
    public void onDisable(EntityTameableDragon dragon) {
        dragon.getBrain().setAvoidsWater(false);
   }

    @Override
    public void onDeath(EntityTameableDragon dragon) {}
    
    @Override
    public SoundEvent getLivingSound() {
        if (rand.nextInt(2) == 0) {
            return ModSounds.ENTITY_NETHER_DRAGON_GROWL;
        } else {
            return ModSounds.ENTITY_NETHER_DRAGON_GROWL;
        }
    }
    
	@Override
	public boolean canChangeBreed() {
		return false;
	}
	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getBreathAffectedAreaNether().continueBreathing(world, origin, endOfLook, power);
		dragon.getBreathHelper().getBreathAffectedAreaNether().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforNetherDragon(world, power, tickCounter);
    }
    
}
