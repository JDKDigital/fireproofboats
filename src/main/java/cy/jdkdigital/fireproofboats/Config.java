package cy.jdkdigital.fireproofboats;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CLIENT_CONFIG;
    public static final Client CLIENT = new Client(CLIENT_BUILDER);

    static {
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static class Client
    {
        public final ModConfigSpec.BooleanValue changeLavaRenderType;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("Client");

            changeLavaRenderType = builder
                    .comment("Changes the rendertype for lava to fix lava being visible inside boats. This is a potential performance hit and it's recommended to turn of on low end systems.")
                    .define("changeLavaRenderType", true);

            builder.pop();
        }
    }
}