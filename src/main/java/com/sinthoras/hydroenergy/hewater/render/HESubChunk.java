package com.sinthoras.hydroenergy.hewater.render;

import com.sinthoras.hydroenergy.proxy.HECommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;

@SideOnly(Side.CLIENT)
public class HESubChunk {

    public int vaoId = -1;
    public int vboId = -1;
    public int numWaterBlocks = 0;

    private void patchBlockLight(int chunkX, int chunkY, int chunkZ, int x, int y, int z, float waterLevel, WorldClient world, Chunk chunk) {
        float diff = Math.min((chunkY << 4) - waterLevel + y, 0);
        int lightVal = (int)(15 + diff * HECommonProxy.blockWaterStill.getLightOpacity());
        lightVal = Math.max(lightVal, 0);
        world.setLightValue(EnumSkyBlock.Sky, chunkX*16+x, chunkY*16+y, chunkZ*16+z, lightVal);
    }
}
