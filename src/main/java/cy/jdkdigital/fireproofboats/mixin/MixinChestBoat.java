package cy.jdkdigital.fireproofboats.mixin;

import cy.jdkdigital.fireproofboats.FireproofBoats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cy.jdkdigital.fireproofboats.FireproofBoats.CRIMSON_TYPE;
import static cy.jdkdigital.fireproofboats.FireproofBoats.WARPED_TYPE;

//@Debug(export = true)
@Mixin(value = ChestBoat.class)
public abstract class MixinChestBoat extends Boat
{
    public MixinChestBoat(EntityType<? extends Boat> type, Level level) {
        super(type, level);
    }

    @Inject(at = {@At(value = "RETURN")}, method = {"getDropItem"}, cancellable = true)
    public void getDropItem(CallbackInfoReturnable<Item> cir) {
        if (this.getBoatType().equals(CRIMSON_TYPE)) {
            cir.setReturnValue(FireproofBoats.CRIMSON_CHEST_BOAT.get());
        } else if (this.getBoatType().equals(WARPED_TYPE)) {
            cir.setReturnValue(FireproofBoats.WARPED_CHEST_BOAT.get());
        }
    }
}
