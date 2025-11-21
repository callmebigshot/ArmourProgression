package net.bigshot.armourprog.block.custom;

import net.bigshot.armourprog.menu.custom.SewingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SewingTableBlock extends Block {
    public SewingTableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        player.openMenu(new SimpleMenuProvider(
                (id, playerInv, p) -> new SewingTableMenu(
                        id, playerInv,
                        ContainerLevelAccess.create(level, pos)
                ),
                Component.translatable("container.armourprog.sewing_table")
        ));

        return InteractionResult.CONSUME;
    }
}
