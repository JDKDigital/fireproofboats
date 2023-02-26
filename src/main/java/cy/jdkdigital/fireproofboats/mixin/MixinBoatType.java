package cy.jdkdigital.fireproofboats.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.BoatEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(value = BoatEntity.Type.class)
public class MixinBoatType
{
    @Shadow
    @Final
    @Mutable
    private static BoatEntity.Type[] $VALUES;

    @Invoker("<init>")
    public static BoatEntity.Type invokeInit(String enumName, int internalId, Block wood, String name) {
        throw new AssertionError();
    }

    private static final BoatEntity.Type CRIMSON = addType("CRIMSON", Blocks.CRIMSON_PLANKS, "crimson");
    private static final BoatEntity.Type WARPED = addType("WARPED", Blocks.WARPED_PLANKS, "warped");

    private static BoatEntity.Type addType(String enumName, Block wood, String name) {
        List<BoatEntity.Type> variants = new ArrayList<>(Arrays.asList(MixinBoatType.$VALUES));
        BoatEntity.Type boatType =  MixinBoatType.invokeInit(enumName, variants.get(variants.size() - 1).ordinal() + 1, wood, name);
        variants.add(boatType);
        MixinBoatType.$VALUES = variants.toArray(new BoatEntity.Type[0]);
        return boatType;
    }
}
