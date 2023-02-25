package cy.jdkdigital.fireproofboats.mixin;

import cy.jdkdigital.fireproofboats.FireproofBoats;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cy.jdkdigital.fireproofboats.FireproofBoats.CRIMSON_TYPE;
import static cy.jdkdigital.fireproofboats.FireproofBoats.WARPED_TYPE;

@Mixin(value = Boat.class)
public abstract class MixinBoat extends Entity
{
    @Shadow private double waterLevel;

    @Shadow public abstract Boat.Type getBoatType();

    @Shadow protected abstract Boat.Status getStatus();

    public MixinBoat(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(at = {@At(value = "HEAD")}, method = {"checkInWater"}, cancellable = true)
    private void checkInFluid(CallbackInfoReturnable<Boolean> callbackInfo) {
        AABB aabb = this.getBoundingBox();
        int i = Mth.floor(aabb.minX);
        int j = Mth.ceil(aabb.maxX);
        int k = Mth.floor(aabb.minY);
        int l = Mth.ceil(aabb.minY + 0.001D);
        int i1 = Mth.floor(aabb.minZ);
        int j1 = Mth.ceil(aabb.maxZ);
        boolean flag = false;
        this.waterLevel = -Double.MAX_VALUE;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    mutableBlockPos.set(k1, l1, i2);
                    FluidState fluidstate = this.level.getFluidState(mutableBlockPos);
                    float f = (float)l1 + fluidstate.getHeight(this.level, mutableBlockPos);
                    this.waterLevel = Math.max(f, this.waterLevel);
                    flag |= aabb.minY < (double)f;
                }
            }
        }
        callbackInfo.setReturnValue(flag);
    }

    @Override
    public boolean fireImmune() {
        return FireproofBoats.isFireproofBoat(this.getBoatType()) || super.fireImmune();
    }

    @Inject(at = {@At(value = "RETURN")}, method = {"getDropItem"}, cancellable = true)
    public void getDropItem(CallbackInfoReturnable<Item> cir) {
        if (this.getBoatType().equals(CRIMSON_TYPE)) {
            cir.setReturnValue(FireproofBoats.CRIMSON_BOAT.get());
        } else if (this.getBoatType().equals(WARPED_TYPE)) {
            cir.setReturnValue(FireproofBoats.WARPED_BOAT.get());
        }
    }
}
