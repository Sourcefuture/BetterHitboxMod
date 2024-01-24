package com.lcbmasters.betterrender;

import com.lcbmasters.betterrender.command.CommandToggleSwitch;
import com.lcbmasters.betterrender.keyblinding.ModKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = BetterRenderMod.MODID, version = BetterRenderMod.VERSION)
public class BetterRenderMod {
    public static final String MODID = "BetterRenderMod";
    public static final String VERSION = "1.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModKeybindings.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        BetterHitBoxRenderMod betterHitBoxRenderMod = new BetterHitBoxRenderMod(Minecraft.getMinecraft(), Minecraft.getMinecraft().getRenderManager());
        MinecraftForge.EVENT_BUS.register(betterHitBoxRenderMod);
        ClientCommandHandler.instance.registerCommand(new CommandToggleSwitch());
        FMLCommonHandler.instance().bus().register(new ModKeybindings());
    }
}
