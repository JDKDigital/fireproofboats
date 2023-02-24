package cy.jdkdigital.fireproofboats;

import cy.jdkdigital.fireproofboats.mixin.MixinBoatType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
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

    public static final RegistryObject<Item> CRIMSON_BOAT = ITEMS.register("crimson_boat", () -> new BoatItem(false, CRIMSON_TYPE, (new Item.Properties()).stacksTo(1).fireResistant().tab(CreativeModeTab.TAB_TRANSPORTATION)));
    public static final RegistryObject<Item> CRIMSON_CHEST_BOAT = ITEMS.register("crimson_chest_boat", () -> new BoatItem(true, CRIMSON_TYPE, (new Item.Properties()).stacksTo(1).fireResistant().tab(CreativeModeTab.TAB_TRANSPORTATION)));
    public static final RegistryObject<Item> WARPED_BOAT = ITEMS.register("warped_boat", () -> new BoatItem(false, WARPED_TYPE, (new Item.Properties()).stacksTo(1).fireResistant().tab(CreativeModeTab.TAB_TRANSPORTATION)));
    public static final RegistryObject<Item> WARPED_CHEST_BOAT = ITEMS.register("warped_chest_boat", () -> new BoatItem(true, WARPED_TYPE, (new Item.Properties()).stacksTo(1).fireResistant().tab(CreativeModeTab.TAB_TRANSPORTATION)));

    public FireproofBoats() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
    }
}
