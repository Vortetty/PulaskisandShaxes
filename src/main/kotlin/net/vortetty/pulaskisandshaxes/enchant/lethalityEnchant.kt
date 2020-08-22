package net.vortetty.pulaskisandshaxes.enchant

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.MathHelper.nextInt
import java.util.*


class lethalityEnchant : Enchantment(Rarity.RARE, EnchantmentTarget.WEAPON, arrayOf(EquipmentSlot.MAINHAND)) {

    override fun getMinPower(level: Int): Int {
        return level*3
    }

    override fun getMaxLevel(): Int {
        return 10
    }

    override fun onTargetDamaged(user: LivingEntity?, target: Entity, level: Int) {
        if (target is LivingEntity) {
            if (level < 5) {
                target.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, level*20, level))
            } else {
                target.addStatusEffect(StatusEffectInstance(StatusEffects.WITHER, level*20, level))
            }
            target.addScoreboardTag("hitWithLethality")
        }
        super.onTargetDamaged(user, target, level)
    }

    override fun onUserDamaged(user: LivingEntity?, attacker: Entity?, level: Int) {
        if (attacker is LivingEntity) {
            if (user is LivingEntity && attacker.scoreboardTags.contains("hitWithLethality")) {
                user.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, 1, 1))
                attacker.removeScoreboardTag("hitWithLethality")
            }
        }
        super.onUserDamaged(user, attacker, level)
    }

    override fun getName(level: Int): Text? {
        val mutableText: MutableText = TranslatableText(getTranslationKey())
        if (this.isCursed) {
            mutableText.formatted(Formatting.RED)
        } else {
            mutableText.formatted(Formatting.GRAY)
        }
        if (level != 1 || this.maxLevel != 1) {
            mutableText.append(" ").append(TranslatableText("enchantment.level.$level"))
        }
        return mutableText
    }
}