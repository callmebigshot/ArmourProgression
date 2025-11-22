package net.bigshot.armourprog.network;

import net.bigshot.armourprog.menu.custom.SewingTableMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectSewingRecipePacket {

    private final int index;

    public SelectSewingRecipePacket(int index) {
        this.index = index;
    }

    public static void encode(SelectSewingRecipePacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.index);
    }

    public static SelectSewingRecipePacket decode(FriendlyByteBuf buf) {
        int index = buf.readInt();
        return new SelectSewingRecipePacket(index);
    }

    public static void handle(SelectSewingRecipePacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null && player.containerMenu instanceof SewingTableMenu menu) {
                menu.setSelectedRecipe(packet.index);
            }
        });
        ctx.setPacketHandled(true);
    }
}


