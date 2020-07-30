package net.vortetty.pulaskisandshaxes.config

import io.github.prospector.modmenu.api.ConfigScreenFactory
import io.github.prospector.modmenu.api.ModMenuApi
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen

@Environment(EnvType.CLIENT)
class modMenuIntegration : ModMenuApi {
    override fun getModId(): String {
        return "pulaskisandshaxes" // Return your modid here
    }

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent -> AutoConfig.getConfigScreen(bedrockBreakerConfig::class.java, parent).get() as Screen }
    }
}