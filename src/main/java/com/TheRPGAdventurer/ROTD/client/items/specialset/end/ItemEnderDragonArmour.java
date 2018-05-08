package com.TheRPGAdventurer.ROTD.client.items.specialset.end;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.RealmOfTheDragons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemEnderDragonArmour extends ItemArmor {
	
	private final PotionEffect potionEffect;

	public ItemEnderDragonArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName, PotionEffect potionEffect2) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.potionEffect = potionEffect2;
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(RealmOfTheDragons.MODID, unlocalizedName));
		this.setCreativeTab(RealmOfTheDragons.TAB);
	}
	
	@Override
	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
		if (!player.isPotionActive(potionEffect.getPotion())) { // If the Potion isn't currently active,
			player.addPotionEffect(new PotionEffect(potionEffect)); // Apply a copy of the PotionEffect to the player
		}	
		
		spawnParticles(player);
	}
	
	public void spawnParticles(EntityPlayer player) {
        // spawn generic particles
		Random rand = new Random();
        double px = player.posX + (rand.nextDouble() - 0.3);
        double py = player.posY + (rand.nextDouble() - 0.3);
        double pz = player.posZ + (rand.nextDouble() - 0.3);
        double ox = (rand.nextDouble() - 0.3) * 2;
        double oy = (rand.nextDouble() - 0.3) * 2;
        double oz = (rand.nextDouble() - 0.3) * 2;
        player.world.spawnParticle(EnumParticleTypes.PORTAL, px, py, pz, ox, oy, oz); 
        
	}

}
