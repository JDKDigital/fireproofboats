package cy.jdkdigital.fireproofboats.mixin;

import cy.jdkdigital.fireproofboats.FireproofBoats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.extensions.IForgeBoat;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cy.jdkdigital.fireproofboats.FireproofBoats.CRIMSON_TYPE;
import static cy.jdkdigital.fireproofboats.FireproofBoats.WARPED_TYPE;

//@Debug(export = true)
@Mixin(value = Boat.class)
public abstract class MixinBoat extends Entity implements IForgeBoat
{
    @Shadow public abstract Boat.Type getVariant();

    public MixinBoat(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean fireImmune() {
        return isFireproofBoat(this.getVariant()) || super.fireImmune();
    }

    @Inject(at = {@At(value = "RETURN")}, method = {"getDropItem"}, cancellable = true)
    public void getDropItem(CallbackInfoReturnable<Item> cir) {
        if (this.getVariant().equals(CRIMSON_TYPE)) {
            cir.setReturnValue(FireproofBoats.CRIMSON_BOAT.get());
        } else if (this.getVariant().equals(WARPED_TYPE)) {
            cir.setReturnValue(FireproofBoats.WARPED_BOAT.get());
        }
    }

    @Override
    public boolean canBoatInFluid(FluidState state) {
        return (state.getFluidType().equals(Fluids.LAVA.getFluidType()) && isFireproofBoat(this.getVariant())) || IForgeBoat.super.canBoatInFluid(state);
    }

    @Override
    public boolean canBoatInFluid(FluidType type) {
        return (type.equals(Fluids.LAVA.getFluidType()) && isFireproofBoat(this.getVariant())) || IForgeBoat.super.canBoatInFluid(type);
    }

    private static boolean isFireproofBoat(Boat.Type boatType) {
        return ((FireBlock) Blocks.FIRE).getBurnOdds(boatType.getPlanks().defaultBlockState()) == 0;
    }
}
