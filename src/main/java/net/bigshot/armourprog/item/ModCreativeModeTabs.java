package net.bigshot.armourprog.item;

import net.bigshot.armourprog.ArmourProg;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArmourProg.MOD_ID);

    public static RegistryObject<CreativeModeTab> ARMOUR_PROG_TAB = CREATIVE_MODE_TABS.register("armourprog_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WOOLCLOTH.get()))
                    .title(Component.translatable("creativetab.armourprog_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.WOOLCLOTH.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }


}
