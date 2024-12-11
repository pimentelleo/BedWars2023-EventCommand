package com.tomkeuper.bedwars.cmds.listeners;

import static com.tomkeuper.bedwars.cmds.Main.getPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.tomkeuper.bedwars.cmds.ConfigPath;
import com.tomkeuper.bedwars.cmds.Main;

public class WinListener implements Listener {

    @EventHandler
    public void onWin(@NotNull GameStateChangeEvent e) {
        if (e.getNewState() == GameState.restarting) {
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                for (Player p : e.getArena().getPlayers()) {
                    for (String s : Main.getCfg().getYml().getStringList(ConfigPath.GAME_WIN_WINNER_CMDS_AS_PLAYER)) {
                        Bukkit.getServer().dispatchCommand(p, "/" + s.replace("{player}", p.getName()
                                .replace("{arena}", e.getArena().getWorldName()))
                                .replace("{arenaDisplay}", e.getArena().getDisplayName())
                        .replace("{group}", e.getArena().getGroup()));
                    }
                    for (String s : Main.getCfg().getYml().getStringList(ConfigPath.GAME_WIN_WINNER_CMDS_AS_CONSOLE)) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", p.getName()
                                .replace("{arena}", e.getArena().getWorldName()))
                                .replace("{arenaDisplay}", e.getArena().getDisplayName())
                                .replace("{group}", e.getArena().getGroup()));
                    }
                }
            }, 10L);
        }
    }
}
