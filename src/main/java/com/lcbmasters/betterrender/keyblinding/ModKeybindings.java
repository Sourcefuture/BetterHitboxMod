package com.lcbmasters.betterrender.keyblinding;

import com.lcbmasters.betterrender.BetterHitBoxRenderMod;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ModKeybindings {
    public static KeyBinding enableHitbox;

    public static void init() {
        enableHitbox = new KeyBinding("显示碰撞箱", org.lwjgl.input.Keyboard.KEY_J, "渲染"); // 使用 KEY_C 作为默认按键
        ClientRegistry.registerKeyBinding(enableHitbox);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (enableHitbox.isPressed()) {
            BetterHitBoxRenderMod.isHitBoxEnabled = !BetterHitBoxRenderMod.isHitBoxEnabled;
        }
    }
}
