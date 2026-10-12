package cy.jdkdigital.fireproofboats;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FireproofBoats.MODID)
public class FireproofBoats
{
    public static final String MODID = "fireproofboats";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final Boat.Type CRIMSON_TYPE = Boat.Type.byName("crimson");
    public static final Boat.Type WARPED_TYPE = Boat.Type.byName("warped");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, FireproofBoats.MODID);

    public static final DeferredHolder<Item, Item> CRIMSON_BOAT = ITEMS.register("crimson_boat", () -> new BoatItem(false, CRIMSON_TYPE, (new Item.Properties()).stacksTo(1).fireResistant()));
    public static final DeferredHolder<Item, Item> CRIMSON_CHEST_BOAT = ITEMS.register("crimson_chest_boat", () -> new BoatItem(true, CRIMSON_TYPE, (new Item.Properties()).stacksTo(1).fireResistant()));
    public static final DeferredHolder<Item, Item> WARPED_BOAT = ITEMS.register("warped_boat", () -> new BoatItem(false, WARPED_TYPE, (new Item.Properties()).stacksTo(1).fireResistant()));
    public static final DeferredHolder<Item, Item> WARPED_CHEST_BOAT = ITEMS.register("warped_chest_boat", () -> new BoatItem(true, WARPED_TYPE, (new Item.Properties()).stacksTo(1).fireResistant()));

    public FireproofBoats(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MODID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void buildContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
                event.accept(CRIMSON_BOAT.get());
                event.accept(CRIMSON_CHEST_BOAT.get());
                event.accept(WARPED_BOAT.get());
                event.accept(WARPED_CHEST_BOAT.get());
            }
        }
    }

    public static boolean isFireproofBoat(Boat.Type boatType) {
        return ((FireBlock) Blocks.FIRE).getBurnOdds(boatType.getPlanks().defaultBlockState()) == 0;
    }
}
