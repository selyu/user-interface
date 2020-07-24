package org.selyu.ui.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.selyu.ui.UserInterfaceProvider;

import static java.util.Objects.requireNonNull;

public final class UserInterfaceListener implements Listener {
    private final UserInterfaceProvider userInterfaceProvider;

    public UserInterfaceListener(UserInterfaceProvider userInterfaceProvider) {
        requireNonNull(userInterfaceProvider, "userInterfaceProvider");
        this.userInterfaceProvider = userInterfaceProvider;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        userInterfaceProvider.removeBoard(event.getPlayer());
    }
}
