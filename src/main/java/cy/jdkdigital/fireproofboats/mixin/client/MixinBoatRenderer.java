package cy.jdkdigital.fireproofboats.mixin.client;

import cy.jdkdigital.fireproofboats.FireproofBoats;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cy.jdkdigital.fireproofboats.FireproofBoats.CRIMSON_TYPE;

@Mixin(value = BoatRenderer.class)
public abstract class MixinBoatRenderer
{
    private static final ResourceLocation CRIMSON_BOAT_TEXTURES = new ResourceLocation("textures/entity/boat/crimson.png");
    private static final ResourceLocation WARPED_BOAT_TEXTURES = new ResourceLocation("textures/entity/boat/warped.png");

    @Inject(at = {@At(value = "HEAD")}, method = {"getTextureLocation"}, cancellable = true)
    public void getTextureLocation(BoatEntity boat, CallbackInfoReturnable<ResourceLocation> cir) {
        if (FireproofBoats.isFireproofBoat(boat.getBoatType())) {
            if (boat.getBoatType().equals(CRIMSON_TYPE)) {
                cir.setReturnValue(CRIMSON_BOAT_TEXTURES);
            } else {
                cir.setReturnValue(WARPED_BOAT_TEXTURES);
            }
        }
    }
}
