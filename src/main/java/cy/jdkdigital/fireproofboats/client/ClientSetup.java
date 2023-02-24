package cy.jdkdigital.fireproofboats.client;

import cy.jdkdigital.fireproofboats.Config;
import cy.jdkdigital.fireproofboats.FireproofBoats;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FireproofBoats.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup
{
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        if (Config.CLIENT.changeLavaRenderType.get()) {
            ItemBlockRenderTypes.setRenderLayer(Fluids.LAVA, RenderType.translucent());
        }
    }
}
