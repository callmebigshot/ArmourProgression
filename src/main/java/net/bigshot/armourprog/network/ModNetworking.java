package net.bigshot.armourprog.network;

import net.bigshot.armourprog.ArmourProg;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModNetworking {

    private static final String VERSION = "1";

    public static final net.minecraftforge.network.simple.SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ArmourProg.MOD_ID, "main"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals
    );

    private static int id = 0;
    private static int next() { return id++; }

    public static void register() {

        CHANNEL.registerMessage(
                next(),
                SelectSewingRecipePacket.class,
                SelectSewingRecipePacket::encode,
                SelectSewingRecipePacket::decode,
                SelectSewingRecipePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        CHANNEL.registerMessage(
                next(),
                SyncLayersPacket.class,
                SyncLayersPacket::encode,
                SyncLayersPacket::new,
                SyncLayersPacket::handle
        );

        CHANNEL.registerMessage(
                next(),
                SetLayerSlotPacket.class,
                SetLayerSlotPacket::encode,
                SetLayerSlotPacket::new,
                SetLayerSlotPacket::handle
        );
    }

    public static void sendTo(ServerPlayer player, Object msg) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}




