//
// A layer between kotlin and modmenu to make everything work, errors if this isn't here
//
package net.vortetty.pulaskisandshaxes.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;

public class modmenuIntegration implements ModMenuApi {
    Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("title.pulaskisandshaxes.config"));

        pulaskisandshaxes.Companion.getConfig().generateConfigScreen(builder);

        return builder.build();
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> getConfigScreen(parent);
    }
}
