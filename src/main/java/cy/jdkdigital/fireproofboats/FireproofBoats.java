package cy.jdkdigital.fireproofboats;

import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FireproofBoats.MODID)
public class FireproofBoats
{
    public static final String MODID = "fireproofboats";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final BoatEntity.Type CRIMSON_TYPE = BoatEntity.Type.byName("crimson");
    public static final BoatEntity.Type WARPED_TYPE = BoatEntity.Type.byName("warped");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FireproofBoats.MODID);

    public static final RegistryObject<Item> CRIMSON_BOAT = ITEMS.register("crimson_boat", () -> new BoatItem(CRIMSON_TYPE, (new Item.Properties()).stacksTo(1).fireResistant().tab(ItemGroup.TAB_TRANSPORTATION)));
    public static final RegistryObject<Item> WARPED_BOAT = ITEMS.register("warped_boat", () -> new BoatItem(WARPED_TYPE, (new Item.Properties()).stacksTo(1).fireResistant().tab(ItemGroup.TAB_TRANSPORTATION)));

    public FireproofBoats() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
    }

    public static boolean isFireproofBoat(BoatEntity.Type boatType) {
        return ((FireBlock) Blocks.FIRE).getBurnOdd(boatType.getPlanks().defaultBlockState()) == 0;
    }
}
