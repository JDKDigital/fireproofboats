package cy.jdkdigital.fireproofboats.mixin;

import cy.jdkdigital.fireproofboats.FireproofBoats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cy.jdkdigital.fireproofboats.FireproofBoats.CRIMSON_TYPE;
import static cy.jdkdigital.fireproofboats.FireproofBoats.WARPED_TYPE;

@Mixin(value = BoatEntity.class)
public abstract class MixinBoat extends Entity
{
    @Shadow private double waterLevel;

    @Shadow public abstract BoatEntity.Type getBoatType();

    public MixinBoat(EntityType<? extends BoatEntity> type, World level) {
        super(type, level);
    }

    @Inject(at = {@At(value = "HEAD")}, method = {"checkInWater"}, cancellable = true)
    private void checkInFluid(CallbackInfoReturnable<Boolean> callbackInfo) {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.ceil(axisalignedbb.minY + 0.001D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        boolean flag = false;
        this.waterLevel = Double.MIN_VALUE;
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();

        for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    mutableBlockPos.set(k1, l1, i2);
                    FluidState fluidstate = this.level.getFluidState(mutableBlockPos);
                    float f = (float)l1 + fluidstate.getHeight(this.level, mutableBlockPos);
                    this.waterLevel = Math.max(f, this.waterLevel);
                    flag |= axisalignedbb.minY < (double)f;
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
