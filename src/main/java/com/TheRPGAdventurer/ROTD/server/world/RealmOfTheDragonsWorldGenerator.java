package com.TheRPGAdventurer.ROTD.server.world;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.RealmOfTheDragonsConfig;
import com.TheRPGAdventurer.ROTD.util.Utils;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;


public class RealmOfTheDragonsWorldGenerator implements IWorldGenerator {
	//@formatter:off

	StructureDragonNests dragonNest = new StructureDragonNests();
	StructureDragonNestNether dragonNestNether = new StructureDragonNestNether();
	StructureDragonNestBone dragonNestBone = new StructureDragonNestBone();
	
	//@formatter:on
	@Override
	public void generate(Random random, int x, int z, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
		case -1: //Nether
			this.generateNestAtNether(world, random, x, z);
			this.generateBoneNestAtNether(world, random, x, z);
			break;
		case 0: //OverWorld (Earth)
			this.generateNestAtSurface(world, random, x, z);
			this.generateNestUnderground(world, random, x, z);
			break;
		case 1: //End
			break;
		}
	}
	
	public static BlockPos getHeight(World world, BlockPos pos) {
		return world.getHeight(pos);
	}
	
	public void generateNestAtSurface(World world, Random random, int chunkX, int chunkZ) {	
		int x = (chunkX * 16) + random.nextInt(16);
		int z = (chunkZ * 16) + random.nextInt(16);
		BlockPos height = getHeight(world, new BlockPos(x, 0, z));	
		if (RealmOfTheDragonsConfig.canSpawnSurfaceDragonNest) {
			boolean isHills = BiomeDictionary.hasType(world.getBiome(height), Type.HILLS) || BiomeDictionary.hasType(world.getBiome(height), Type.MOUNTAIN) && !BiomeDictionary.hasType(world.getBiome(height), Type.SNOWY);
			if (!world.getBiome(height).getEnableSnow() && world.getBiome(height).getTemperature() > -0.5 && world.getBiome(height) != Biomes.ICE_PLAINS && !BiomeDictionary.hasType(world.getBiome(height), Type.SNOWY) && !BiomeDictionary.hasType(world.getBiome(height), Type.SNOWY) && !BiomeDictionary.hasType(world.getBiome(height), Type.WET) && !BiomeDictionary.hasType(world.getBiome(height), Type.OCEAN) && !BiomeDictionary.hasType(world.getBiome(height), Type.RIVER) || isHills) {
				if (random.nextInt((isHills ? RealmOfTheDragonsConfig.MainNestRarity : RealmOfTheDragonsConfig.MainNestRarity * 2) + 1) == 0) {
					BlockPos surface = world.getHeight(new BlockPos(x, 0, z));
					dragonNest.generate(world, surface, random);
					Utils.getLogger().info("Underground Nest here at: " + new BlockPos(x,0,z));	
				    }
				}
			}
		}
    
    public void generateNestUnderground(World world, Random random, int chunkX, int chunkZ) {
		if (RealmOfTheDragonsConfig.canSpawnUnderGroundNest) {
    	boolean spawn = true;
		int x = (chunkX * RealmOfTheDragonsConfig.undergroundnestX) + random.nextInt(RealmOfTheDragonsConfig.undergroundnestX); 
		int z = (chunkZ * RealmOfTheDragonsConfig.undergroundnestZ) + random.nextInt(RealmOfTheDragonsConfig.undergroundnestZ); 
	    for (int y = 45; y >= 5; --y) {
	    if (world.getBlockState(new BlockPos(x,y,z)).getBlock().isAir(world.getBlockState(new BlockPos(x,y,z)), world, new BlockPos(x,y,z))) {
	    if((random.nextInt() * RealmOfTheDragonsConfig.undergroundrarityMain) <= 1) {
		for (int y2 = 0; y2 <= 30; ++y2) {
	    if (world.getBlockState(new BlockPos(x,y-y2,z)).isBlockNormalCube()) {
	                    	 	                    	 
	    if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == Blocks.LAVA) {spawn = false;}
	    if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock()  == Blocks.OBSIDIAN) {spawn = false;}
	    if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock()  == Blocks.COBBLESTONE) {spawn = false;}
	    if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock()  == Blocks.MOSSY_COBBLESTONE) {spawn = false;}
		         							                                 	         
				             if(spawn) {
				            	if (world.getBlockState(new BlockPos(x,y-y2,z)).isNormalCube()) {
					            dragonNest.generate(world, new BlockPos(x,y-y2,z), random);
					            Utils.getLogger().info("Underground Nest here at: " + new BlockPos(x,y,z));				            				            
				              } return;
				            }
				          }
				      }
			       } break;
			    }
	        }
		}
    }
	
	public void generateNestAtNether(World world, Random random, int chunkX, int chunkZ) {
		if (RealmOfTheDragonsConfig.canSpawnNetherNest) {
		int x = (chunkX * RealmOfTheDragonsConfig.netherNestRarerityInX) + random.nextInt(RealmOfTheDragonsConfig.netherNestRarerityInX);
		int z = (chunkZ * RealmOfTheDragonsConfig.netherNestRarerityInZ) + random.nextInt(RealmOfTheDragonsConfig.netherNestRarerityInZ);
	    for (int y = 85; y >= 5; y--) {
	    	if (world.getBlockState(new BlockPos(x,y,z)).isBlockNormalCube()) {
	    		if((random.nextInt() * RealmOfTheDragonsConfig.netherNestRarity) <= 1) {
					boolean place = true;
				
		for(int Y = 0; Y < 7; Y++) {for(int Z = 0; Z < 7; Z++) {for(int X = 0; X < 3; X++) {if(world.getBlockState(new BlockPos(X + x, Y + y + 1, Z + z)).getBlock() != Blocks.AIR) {place = false;}}}}
		for(int Y = 0; Y < 7; Y++) {for(int Z = 0; Z < 7; Z++) {for(int X = 0; X < 3; X++) {if(world.getBlockState(new BlockPos(X + x, Y + y + 1, Z + z)).getBlock() == Blocks.LAVA) {place = false;}}}}
				
				if(place) {
					dragonNestNether.generate(world, new BlockPos(x,y,z), random);
					Utils.getLogger().info("Nether Nest here at: " + new BlockPos(x,y,z));
				       }
				    }
			    }
	        }
		}
    }
	
	public void generateBoneNestAtNether(World world, Random random, int chunkX, int chunkZ) {		
		if (RealmOfTheDragonsConfig.canSpawnNetherBoneNest) {
		int x = (chunkX * RealmOfTheDragonsConfig.boneNestRarerityInX) + random.nextInt(RealmOfTheDragonsConfig.boneNestRarerityInX);
		int z = (chunkZ * RealmOfTheDragonsConfig.boneNestRarerityInZ) + random.nextInt(RealmOfTheDragonsConfig.boneNestRarerityInZ);
	    for (int y = 85; y >= 5; y--) {
	    	if (world.getBlockState(new BlockPos(x,y,z)).isBlockNormalCube()) {
				if((random.nextInt() * RealmOfTheDragonsConfig.boneNestMainRarity) <= 1) {
					boolean place = true;
				
				for(int Y = 0; Y < 7; Y++) {
					for(int Z = 0; Z < 7; Z++) {
						for(int X = 0; X < 3; X++) {
							if(world.getBlockState(new BlockPos(X + x, Y + y + 1, Z + z)).getBlock() != Blocks.AIR) {
								place = false;
							}
						}
					}
				}
				
				for(int Y = 0; Y < 7; Y++) {
 					for(int Z = 0; Z < 7; Z++) {
 						for(int X = 0; X < 3; X++) {
 							if(world.getBlockState(new BlockPos(X + x, Y + y + 1, Z + z)).getBlock() == Blocks.LAVA) {
 								place = false;
 							}
 						}
 					}
 				}
				
				if(place) {
					dragonNestBone.generate(world, new BlockPos(x,y,z), random);
					Utils.getLogger().info("Bone Nest here at: " + new BlockPos(x,y,z));
				       }
				    }
			    }
	        }
		}
    }
}