package net.vortetty.pulaskisandshaxes.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class modMenuIntegration implements ModMenuApi {
	@Override
	public String getModId() {
		return "pulaskisandshaxes"; // Return your modid here
	}
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			return (Screen) AutoConfig.getConfigScreen(bedrockBreakerConfig.class, parent).get();
		};
	}
}
