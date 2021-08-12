package net.vortetty.pulaskisandshaxes.config

import com.sun.jdi.InvalidTypeException
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigCategory
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import org.hjson.JsonArray
import org.hjson.JsonObject
import org.hjson.Stringify
import java.io.*


/*
@Config(name = "pulaskisandshaxes")
class configuration : PartitioningSerializer.GlobalData() {
    var bedrockBreakerUses = 10
    var allowPushBlockEntities = false
    var allowPushExtendedPiston = false

    @ConfigEntry.Category("module_a")
    @ConfigEntry.Gui.TransitiveObject
    var randomizerConfig: randomizers = randomizers()
}

@Config(name = "pulaskisandshaxes_randomizers")
class randomizers : ConfigData {
    var doRandomDrops = false
    var doRandomPlaces = false
}
*/

fun addIntSlider(config: JsonObject, category: ConfigCategory, entryBuilder: ConfigEntryBuilder, name: String, min: Int, max: Int, default: Int, needsRestart: Boolean=false) {
    val entry = entryBuilder.startIntSlider(TranslatableText("option.pulaskisandshaxes.slider.$name"), config.getInt(name, default), min, max)
        .setDefaultValue(default)
        .setMin(min)
        .setMax(max)
        .setTooltip(TranslatableText("option.pulaskisandshaxes.slider.$name.tooltip"))
        .setSaveConsumer { newValue: Int -> config.set(name, newValue) }

    if (needsRestart){
        category.addEntry(
            entry.requireRestart().build()
        )
    } else {
        category.addEntry(
            entry.build()
        )
    }
}

fun addBool(config: JsonObject, category: ConfigCategory, entryBuilder: ConfigEntryBuilder, name: String, default: Boolean, needsRestart: Boolean=false, yesNoText: ((Boolean) -> Text)?=null) {
    var yesNoTextSupplier: ((Boolean) -> Text)? = { b: Boolean -> if (b) TranslatableText("option.pulaskisandshaxes.defaultYesText") else TranslatableText("option.pulaskisandshaxes.defaultNoText") }
    if (yesNoText!=null)
        yesNoTextSupplier = yesNoText
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

    if (needsRestart){
        category.addEntry(
            entry.requireRestart().build()
        )
    } else {
        category.addEntry(
            entry.build()
        )
    }
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

        addIntSlider(general_config, generalSettings, entryBuilder, "bedrockBreakerUses", 1, 100, 10)
        addBool(general_config, generalSettings, entryBuilder, "allowPushBlockEntities", false)
        addBool(general_config, generalSettings, entryBuilder, "allowPushExtendedPiston", false)

        addBool(random_config, randomSettings, entryBuilder, "doRandomDrops", false)
        addBool(random_config, randomSettings, entryBuilder, "doRandomPlaces", false)
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

    fun initConfigObject() {
        loadConfig()

        general_config = addConfigOptionIfMissing(config, "general_config", JsonObject())!!
        random_config = addConfigOptionIfMissing(config, "random_config", JsonObject())!!

        addConfigOptionIfMissing(general_config, "bedrockBreakerUses", 10)
        addConfigOptionIfMissing(general_config, "allowPushBlockEntities", false)
        addConfigOptionIfMissing(general_config, "allowPushExtendedPiston", false)

        addConfigOptionIfMissing(random_config, "doRandomDrops", false)
        addConfigOptionIfMissing(random_config, "doRandomPlaces", false)


        val fileWriter: FileWriter = try {
            FileWriter("config/pulaskisandshaxes.hjson")
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }
        config.writeTo(fileWriter, Stringify.HJSON)
        fileWriter.close()
    }

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
    }
}