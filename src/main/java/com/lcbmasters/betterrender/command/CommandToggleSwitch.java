package com.lcbmasters.betterrender.command;

import com.lcbmasters.betterrender.BetterHitBoxRenderMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandToggleSwitch extends CommandBase {



    @Override
    public String getCommandName() {
        return "toggleswitch";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/toggleswitch <enable/disable>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        // 在这里处理指令的逻辑
        if (args.length == 1) {
            if (args[0].equals("enable")) {
                BetterHitBoxRenderMod.isHitBoxEnabled = true;
                sender.addChatMessage(new ChatComponentText("已开启碰撞箱显示"));
            } else if (args[0].equals("disable")) {
                BetterHitBoxRenderMod.isHitBoxEnabled = false;
                sender.addChatMessage(new ChatComponentText("已关闭碰撞箱显示"));
            } else {
                sender.addChatMessage(new ChatComponentText("无效的使用方法"));
                sender.addChatMessage(new ChatComponentText("请使用 /toggleswitch <enable/disable>"));
            }
        } else {
            sender.addChatMessage(new ChatComponentText("无效的使用方法"));
            sender.addChatMessage(new ChatComponentText("请使用 /toggleswitch <enable/disable>"));
        }
    }
}
