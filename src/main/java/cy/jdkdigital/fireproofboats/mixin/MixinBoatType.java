package cy.jdkdigital.fireproofboats.mixin;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Debug(export = true)
@Mixin(value = Boat.Type.class)
public class MixinBoatType
{
    @Shadow
    @Final
    @Mutable
    private static Boat.Type[] $VALUES;

    @Invoker("<init>")
    public static Boat.Type invokeInit(String enumName, int internalId, Block wood, String name) {
        throw new AssertionError();
    }

    private static final Boat.Type CRIMSON = addType("CRIMSON", Blocks.CRIMSON_PLANKS, "crimson");
    private static final Boat.Type WARPED = addType("WARPED", Blocks.WARPED_PLANKS, "warped");

    private static Boat.Type addType(String enumName, Block wood, String name) {
        List<Boat.Type> variants = new ArrayList<>(Arrays.asList(MixinBoatType.$VALUES));
        var boatType =  MixinBoatType.invokeInit(enumName, variants.get(variants.size() - 1).ordinal() + 1, wood, name);
        variants.add(boatType);
        MixinBoatType.$VALUES = variants.toArray(new Boat.Type[0]);
        return boatType;
    }
}
