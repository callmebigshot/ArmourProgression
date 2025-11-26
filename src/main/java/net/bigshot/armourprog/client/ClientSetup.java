package net.bigshot.armourprog.client;

import net.bigshot.armourprog.ArmourProg;
import net.bigshot.armourprog.client.screen.SewingTableScreen;
import net.bigshot.armourprog.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ArmourProg.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
                        MenuScreens.register(ModMenuTypes.SEWING_TABLE_MENU.get(), SewingTableScreen::new)
                );
    }
}
