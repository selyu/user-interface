package org.selyu.ui.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.selyu.ui.UserInterfaceProvider;

public final class UserInterfaceListener implements Listener {
    private final UserInterfaceProvider userInterfaceProvider;

    public UserInterfaceListener(@NotNull UserInterfaceProvider userInterfaceProvider) {
        this.userInterfaceProvider = userInterfaceProvider;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        userInterfaceProvider.removeBoard(event.getPlayer());
    }
}
