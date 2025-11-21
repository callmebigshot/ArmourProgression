package net.bigshot.armourprog.registry;

import net.bigshot.armourprog.ArmourProg;
import net.bigshot.armourprog.menu.custom.SewingTableMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ArmourProg.MOD_ID);

    public static final RegistryObject<MenuType<SewingTableMenu>> SEWING_TABLE_MENU =
            MENUS.register("sewing_table_menu",
                    () -> IForgeMenuType.create((windowId, inv, data) ->
                            new SewingTableMenu(windowId, inv, ContainerLevelAccess.NULL)
                    ));
}
