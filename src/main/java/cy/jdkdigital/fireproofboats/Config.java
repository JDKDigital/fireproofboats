package cy.jdkdigital.fireproofboats;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config
{
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_CONFIG;
    public static final Client CLIENT = new Client(CLIENT_BUILDER);

    static {
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static class Client
    {
        public final ForgeConfigSpec.BooleanValue changeLavaRenderType;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("Client");

            changeLavaRenderType = builder
                    .comment("Changes the rendertype for lava to fix lava being visible inside boats. This is a potential performance hit and it's recommended to turn of on low end systems.")
                    .define("changeLavaRenderType", true);

            builder.pop();
        }
    }
}