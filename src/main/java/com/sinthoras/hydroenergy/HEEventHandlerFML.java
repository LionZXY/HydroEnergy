package com.sinthoras.hydroenergy;

import com.sinthoras.hydroenergy.controller.HEDams;
import com.sinthoras.hydroenergy.hewater.HEBlockQueue;
import com.sinthoras.hydroenergy.hewater.render.HERenderManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.event.world.ChunkEvent;

public class HEEventHandlerFML  {

	@SubscribeEvent
	public void onEvent(ServerTickEvent event) {
		if(event.phase == ServerTickEvent.Phase.END)
			HEBlockQueue.instance.onTick(event);
	}
	
	@SubscribeEvent
	public void onEvent(RenderTickEvent event) {
		if(event.phase == RenderTickEvent.Phase.END && HERenderManager.instance != null)
			HERenderManager.instance.onRenderTick();
			
	}
	
	@SubscribeEvent
	public void onEvent(PlayerLoggedInEvent event) {
		HEDams.instance.synchronizeClient(event);
	}
}
