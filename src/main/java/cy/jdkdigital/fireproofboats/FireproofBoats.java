package cy.jdkdigital.fireproofboats;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FireproofBoats.MODID)
public class FireproofBoats
{
    public static final String MODID = "fireproofboats";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final Boat.Type CRIMSON_TYPE = Boat.Type.byName("crimson");
    public static final Boat.Type WARPED_TYPE = Boat.Type.byName("warped");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FireproofBoats.MODID);

    public static final RegistryObject<Item> CRIMSON_BOAT = ITEMS.register("crimson_boat", () -> new BoatItem(false, CRIMSON_TYPE, (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> CRIMSON_CHEST_BOAT = ITEMS.register("crimson_chest_boat", () -> new BoatItem(true, CRIMSON_TYPE, (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> WARPED_BOAT = ITEMS.register("warped_boat", () -> new BoatItem(false, WARPED_TYPE, (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> WARPED_CHEST_BOAT = ITEMS.register("warped_chest_boat", () -> new BoatItem(true, WARPED_TYPE, (new Item.Properties()).stacksTo(1)));

    public FireproofBoats() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::tabs);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
    }

    public void tabs(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(CRIMSON_BOAT.get());
            event.accept(CRIMSON_CHEST_BOAT.get());
            event.accept(WARPED_BOAT.get());
            event.accept(WARPED_CHEST_BOAT.get());
        }
    }

    public static boolean isFireproofBoat(Boat.Type boatType) {
        return ((FireBlock) Blocks.FIRE).getBurnOdds(boatType.getPlanks().defaultBlockState()) == 0;
    }
}
