package net.masterquentus.hexcraftmod.screen;

import net.masterquentus.hexcraftmod.HexcraftMod;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HexcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void ClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(()-> {
            MenuScreens.register(HexcraftMenuTypes.WITCHES_OVEN_MENU.get(), WitchesOvenScreen::new);
        });
    }
}
