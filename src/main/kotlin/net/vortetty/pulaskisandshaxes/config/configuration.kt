package net.vortetty.pulaskisandshaxes.config

import com.sun.jdi.InvalidTypeException
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigCategory
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry
import me.shedaniel.clothconfig2.gui.entries.TextListEntry
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import org.hjson.JsonArray
import org.hjson.JsonObject
import org.hjson.Stringify
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

fun addIntSlider(config: JsonObject, category: ConfigCategory?=null, entryBuilder: ConfigEntryBuilder, name: String, min: Int, max: Int, default: Int, needsRestart: Boolean=false): IntegerSliderEntry {
    val entry = entryBuilder.startIntSlider(TranslatableText("option.pulaskisandshaxes.slider.$name"), config.getInt(name, default), min, max)
        .setDefaultValue(default)
        .setMin(min)
        .setMax(max)
        .setTooltip(TranslatableText("option.pulaskisandshaxes.slider.$name.tooltip"))
        .setSaveConsumer { newValue: Int -> config.set(name, newValue) }
    var tmp: IntegerSliderEntry? = null

    if (needsRestart){
        tmp = entry.requireRestart().build()
        if (category != null) {
            category.addEntry(
                tmp
            )
        }
    } else {
        tmp = entry.build()
        if (category != null) {
            category.addEntry(
                tmp
            )
        }
    }

    return tmp
}

fun addBool(config: JsonObject, category: ConfigCategory?=null, entryBuilder: ConfigEntryBuilder, name: String, default: Boolean, needsRestart: Boolean=false, yesNoText: ((Boolean) -> Text)?=null): BooleanListEntry {
    val entry = entryBuilder.startBooleanToggle(TranslatableText("option.pulaskisandshaxes.toggle.$name"), config.getBoolean(name, default))
        .setDefaultValue(default)
        .setTooltip(TranslatableText("option.pulaskisandshaxes.toggle.$name.tooltip"))
        .setSaveConsumer { newValue: Boolean -> config.set(name, newValue) }
        .setYesNoTextSupplier(
            if (yesNoText==null)
                { b: Boolean -> if (b)
                    TranslatableText("option.pulaskisandshaxes.defaultYesText")
                else
                    TranslatableText("option.pulaskisandshaxes.defaultNoText") }
            else
                yesNoText
        )
    var tmp: BooleanListEntry? = null

    if (needsRestart){
        tmp = entry.requireRestart().build()
        if (category != null) {
            category.addEntry(
                tmp
            )
        }
    } else {
        tmp = entry.build()
        if (category != null) {
            category.addEntry(
                tmp
            )
        }
    }

    return tmp
}

fun addSpacer(category: ConfigCategory?=null, entryBuilder: ConfigEntryBuilder): TextListEntry {
    val entry = entryBuilder.startTextDescription(TranslatableText("option.pulaskisandshaxes.label.spacerText")).build()
    if (category != null) {
        category.addEntry(entry)
    }
    return entry
}
fun addLabel(category: ConfigCategory?=null, entryBuilder: ConfigEntryBuilder, name: String): TextListEntry {
    val entry = entryBuilder.startTextDescription(TranslatableText("option.pulaskisandshaxes.label.$name")).build()
    if (category != null) {
        category.addEntry(entry)
    }
    return entry
}

fun addSubcategory(category: ConfigCategory?=null, entryBuilder: ConfigEntryBuilder, name: String, vararg entries: AbstractConfigListEntry<*>): SubCategoryListEntry {
    return addSubcategory(category, entryBuilder, name, entries.asList())
}
fun addSubcategory(category: ConfigCategory?=null, entryBuilder: ConfigEntryBuilder, name: String, entries: List<AbstractConfigListEntry<*>>): SubCategoryListEntry {
    val entry = entryBuilder.startSubCategory(TranslatableText("option.pulaskisandshaxes.subcategory.$name"), entries)
        .setTooltip(TranslatableText("option.pulaskisandshaxes.subcategory.$name.tooltip")).build()
    if (category != null) {
        category.addEntry(entry)
    }
    return entry
}

class configuration {
    var config: JsonObject = JsonObject()
    var general_config: JsonObject = JsonObject()
    var random_config: JsonObject = JsonObject()

    fun generateConfigScreen(builder: ConfigBuilder) {
        val entryBuilder = builder.entryBuilder()
        val generalSettings = builder.getOrCreateCategory(TranslatableText("option.pulaskisandshaxes.category.general"))
        val randomSettings = builder.getOrCreateCategory(TranslatableText("option.pulaskisandshaxes.category.random"))

        builder.savingRunnable = Runnable ret@{
            val fileWriter: FileWriter = try {
                FileWriter("config/pulaskisandshaxes.hjson")
            } catch (e: IOException) {
                e.printStackTrace()
                return@ret
            }
            config.writeTo(fileWriter, Stringify.HJSON)
            fileWriter.close()
        }
        builder.setTransparentBackground(true)
        builder.setShouldListSmoothScroll(true)
        builder.setShouldTabsSmoothScroll(true)

        addIntSlider(general_config, generalSettings, entryBuilder, "bedrockBreakerUses", 1, 100, 10)
        addBool(general_config, generalSettings, entryBuilder, "allowPushBlockEntities", false)
        addBool(general_config, generalSettings, entryBuilder, "allowPushExtendedPiston", false)
        addBool(general_config, generalSettings, entryBuilder, "allowNestedShulkerBoxes", false)

        addLabel(randomSettings, entryBuilder, "loot")
        addBool(random_config, randomSettings, entryBuilder, "doAllRandomLoot", false)
        addSubcategory(randomSettings, entryBuilder, "preciseControl",
            addBool(random_config, null, entryBuilder, "doRandomBlockDrops", false),
            addBool(random_config, null, entryBuilder, "doRandomEntityDrops", false)
        )
        addSpacer(randomSettings, entryBuilder)
        addLabel(randomSettings, entryBuilder, "other")
        addBool(random_config, randomSettings, entryBuilder, "doRandomPlaces", false)
        addBool(random_config, randomSettings, entryBuilder, "doRandomPotions", false)
    }

    @Throws(IOException::class)
    fun loadConfig() {
        val configFile: FileReader = try {
            FileReader("config/pulaskisandshaxes.hjson")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return
        }
        config = JsonObject.readHjson(configFile.readText()).asObject()
    }

    private fun initConfigObject() {
        loadConfig()

        general_config = addConfigOptionIfMissing(config, "general_config", JsonObject())!!
        random_config = addConfigOptionIfMissing(config, "random_config", JsonObject())!!

        addConfigOptionIfMissing(general_config, "bedrockBreakerUses", 10)
        addConfigOptionIfMissing(general_config, "allowPushBlockEntities", false)
        addConfigOptionIfMissing(general_config, "allowPushExtendedPiston", false)
        addConfigOptionIfMissing(general_config, "allowNestedShulkerBoxes", false)

        addConfigOptionIfMissing(random_config, "doAllRandomLoot", false)
        addConfigOptionIfMissing(random_config, "doRandomBlockDrops", false)
        addConfigOptionIfMissing(random_config, "doRandomEntityDrops", false)
        addConfigOptionIfMissing(random_config, "doRandomPlaces", false)
        addConfigOptionIfMissing(random_config, "doRandomPotions", false)


        val fileWriter: FileWriter = try {
            FileWriter("config/pulaskisandshaxes.hjson")
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }
        config.writeTo(fileWriter, Stringify.HJSON)
        fileWriter.close()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> addConfigOptionIfMissing(jsonObject: JsonObject, name: String, default: T): T? {
        try {
            when (default) {
                is Boolean     -> { if (jsonObject.get(name) == null) { jsonObject.add(name, default) }; return jsonObject.getBoolean(name, default as Boolean) as T }
                is Double      -> { if (jsonObject.get(name) == null) { jsonObject.add(name, default) }; return jsonObject.getDouble(name, default as Double) as T }
                is Int         -> { if (jsonObject.get(name) == null) { jsonObject.add(name, default) }; return jsonObject.getInt(name, default as Int) as T }
                is Long        -> { if (jsonObject.get(name) == null) { jsonObject.add(name, default) }; return jsonObject.getLong(name, default as Long) as T }
                is Float       -> { if (jsonObject.get(name) == null) { jsonObject.add(name, default) }; return jsonObject.getFloat(name, default as Float) as T }
                is String      -> { if (jsonObject.get(name) == null) { jsonObject.add(name, default) }; return jsonObject.getString(name, default as String) as T }
                is JsonObject  -> {
                    if (jsonObject.get(name) == null) { jsonObject.add(name, default) }
                    return try {
                        jsonObject.get(name).asObject() as T
                    } catch (e: Throwable) {
                        default
                    }
                }
                is JsonArray  -> {
                    if (jsonObject.get(name) == null) { jsonObject.add(name, default) }
                    return try {
                        jsonObject.get(name).asArray() as T
                    } catch (e: Throwable) {
                        default
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            throw InvalidTypeException()
        }
        return null
    }

    init {
        initConfigObject()
    }
}