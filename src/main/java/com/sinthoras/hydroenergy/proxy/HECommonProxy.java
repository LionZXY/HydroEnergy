package com.sinthoras.hydroenergy.proxy;

import com.sinthoras.hydroenergy.HE;
import com.sinthoras.hydroenergy.HEEventHandlerEVENT_BUS;
import com.sinthoras.hydroenergy.HEEventHandlerFML;
import com.sinthoras.hydroenergy.commands.HECommandDebug;
import com.sinthoras.hydroenergy.commands.HECommandSetWater;
import com.sinthoras.hydroenergy.controller.HEControllerBlock;
import com.sinthoras.hydroenergy.controller.HEControllerTileEntity;
import com.sinthoras.hydroenergy.controller.HEDams;
import com.sinthoras.hydroenergy.hewater.HEBlockQueue;
import com.sinthoras.hydroenergy.hewater.HEWaterDynamic;
import com.sinthoras.hydroenergy.hewater.HEWaterStatic;
import com.sinthoras.hydroenergy.network.HEPacketDebug;
import com.sinthoras.hydroenergy.network.HEPacketSynchronize;
import com.sinthoras.hydroenergy.network.HEPacketUpdate;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
//import net.bytebuddy.ByteBuddy;
//import net.bytebuddy.implementation.FixedValue;
//import net.bytebuddy.matcher.ElementMatchers;
import net.minecraftforge.common.MinecraftForge;

public class HECommonProxy {
	
	//public static HEWater[] water_instances;
	public static HEWaterStatic blockWaterStill = new HEWaterStatic();
	public static HEWaterDynamic blockWaterFlowing = new HEWaterDynamic();
	public static HEControllerBlock controller = new HEControllerBlock();
	
	// preInit "Run before anything else. Read your config, create blocks, items, 
	// etc, and register them with the GameRegistry."
	public void fmlLifeCycleEvent(FMLPreInitializationEvent event)
	{
		HEBlockQueue.instance = new HEBlockQueue();
		
    	HE.network = NetworkRegistry.INSTANCE.newSimpleChannel("hydroenergy");
    	HE.network.registerMessage(HEPacketUpdate.Handler.class, HEPacketUpdate.class, 0, Side.CLIENT);
    	HE.network.registerMessage(HEPacketDebug.Handler.class, HEPacketDebug.class, 1, Side.CLIENT);
    	HE.network.registerMessage(HEPacketSynchronize.Handler.class, HEPacketSynchronize.class, 2, Side.CLIENT);

    	/*HE.LOG.info("The subsequent " + 16 + " liquid errors are intendend. Please ignore...");  // TODO: move to config
    	water_instances = new HEWater[16];  // TODO: move to config
    	for(int i=0;i<16;i++)  // TODO: move to config
    	{
    		try
    		{
    			water_instances[i] = new ByteBuddy()
						.subclass(HEWater.class)
						.name("HEWater" + i)
						.method(ElementMatchers.named("getId"))
						.intercept(FixedValue.value(i))
						.make()
						.load(getClass().getClassLoader())
						.getLoaded()
						.newInstance();
				GameRegistry.registerBlock(water_instances[i], water_instances[i].getUnlocalizedName() + i);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
    	}*/
    	GameRegistry.registerBlock(blockWaterStill, blockWaterStill.getUnlocalizedName() + 0);
    	
		GameRegistry.registerBlock(controller, controller.getUnlocalizedName());
		
		GameRegistry.registerTileEntity(HEControllerTileEntity.class, "he_controller_tile_entity");
	}
	
	// load "Do your mod setup. Build whatever data structures you care about. Register recipes."
	public void fmlLifeCycleEvent(FMLInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(new HEEventHandlerFML());
		MinecraftForge.EVENT_BUS.register(new HEEventHandlerEVENT_BUS());
	}
	
	// postInit "Handle interaction with other mods, complete your setup based on this."
	public void fmlLifeCycleEvent(FMLPostInitializationEvent event)
	{
		
	}
	
	public void fmlLifeCycleEvent(FMLServerAboutToStartEvent event)
	{

	}

	// register server commands in this event handler
	public void fmlLifeCycleEvent(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new HECommandSetWater());
		event.registerServerCommand(new HECommandDebug());
		if(event.getSide() == Side.SERVER || event.getServer().isSinglePlayer())
		{
			HEDams.instance = HEDams.load(event.getServer().worldServers[0]);
		}
	}
	
	public void fmlLifeCycleEvent(FMLServerStartedEvent event)
	{
		
	}
	
	public void fmlLifeCycleEvent(FMLServerStoppingEvent event)
	{
		
	}
	
	public void fmlLifeCycleEvent(FMLServerStoppedEvent event)
	{
		
	}

	public static Block getWaterBlock(int id)
	{
		return blockWaterStill;
	}
}
