<img src="assets/example-scoreboard.gif" alt="scoreboard example" align="right" />
<h1 align="left">user-interface</h1>

Spigot UI made simple.

## Setup

### Maven
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
    <groupId>org.selyu</groupId>
    <artifactId>user-interface</artifactId>
    <version>VERSION</version>
</dependency>
```

### Gradle
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
	}
}

dependencies {
    implementation 'org.selyu:user-interface:<version>'
}
```

## Scoreboard

### Plugin class

```java
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.selyu.ui.UserInterfaceProvider;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        UserInterfaceProvider userInterfaceProvider = new UserInterfaceProvider(this, 1);
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                userInterfaceProvider.setBoard(event.getPlayer(), new ExampleScoreboardAdapter());
            }
        });
    }
}
```

### Scoreboard Adapter class

```java
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.selyu.ui.scoreboard.adapter.IScoreboardAdapter;
import org.selyu.ui.scoreboard.model.ScoreboardLine;
import org.selyu.ui.scoreboard.model.ScoreboardTitle;

import java.util.List;

public class ExampleScoreboardAdapter implements IScoreboardAdapter {
    @Override
    public ScoreboardTitle getTitle() {
        // This just makes a SICK rainbow :)
        String text = "GAYGAYGAYGAY";
        TextComponent.Builder builder = Component.text();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            double hue = Math.ceil((System.currentTimeMillis() + (250 * i)) / 20.0);
            hue %= 360;

            TextColor color = TextColor.color(Color.getHSBColor((float) hue / 360f, 0.8f, 0.7f).getRGB());
            builder.append(Component.text(c, color));
        }

        return ScoreboardTitle.from(1, builder.build());
    }

    @Override
    public List<ScoreboardLine> getLines() {
        Component a = Component.text("owo").color(TextColor.fromHexString("#ff96e5"));
        Component b = Component.text("uwu").color(TextColor.fromHexString("#ff66d9"));
        Component c = Component.text("owo").color(TextColor.fromHexString("#ff59d6"));
        Component d = Component.text("uwu").color(TextColor.fromHexString("#ff45d1"));
        Component e = Component.text("owo").color(TextColor.fromHexString("#ff33cd"));
        Component f = Component.text("uwu").color(TextColor.fromHexString("#ff19c7"));
        return ScoreboardLine.builder()
                .addLine(3, a, b, c, d, e, f)
                .addLine(1, "&aA", "&bB", "&cC")
                .build();
    }
}
```
