package net.vortetty.pulaskisandshaxes.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = "pulaskisandshaxes")
class configuration : ConfigData {
    var bedrockBreakerUses = 10
    var doRandomDrops = false
    var doRandomPlaces = false
    var allowPushBlockEntities = false
    var allowPushExtendedPiston = false
}