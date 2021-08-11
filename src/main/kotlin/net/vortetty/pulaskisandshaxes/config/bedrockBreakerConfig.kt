package net.vortetty.pulaskisandshaxes.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = "pulaskisandshaxes")
class bedrockBreakerConfig : ConfigData {
    var bedrockBreakerUses = 10
    var doRandomDrops = false
}