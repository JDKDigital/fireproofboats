package cy.jdkdigital.fireproofboats.mixin;

import cy.jdkdigital.fireproofboats.FireproofBoats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(value = Entity.class)
public abstract class MixinEntity
{
    @Shadow
    protected boolean firstTick;

    @Shadow public abstract boolean isPassenger();

    @Shadow @Nullable public abstract Entity getVehicle();

    @Inject(at = {@At(value = "HEAD")}, method = {"isInLava"}, cancellable = true)
    public void isInLava(CallbackInfoReturnable<Boolean> cir) {
        if (!this.firstTick && this.isPassenger() && this.getVehicle() instanceof Boat boat && FireproofBoats.isFireproofBoat(boat.getBoatType())) {
            cir.setReturnValue(false);
        }
    }
}
