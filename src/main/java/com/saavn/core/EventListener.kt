package com.saavn.core

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import com.saavn.core.SaavnDna

import com.saavn.event.SlashCommandInteraction

class EventListener(bot: SaavnDna) : ListenerAdapter() {
    var bot: SaavnDna = bot

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        SlashCommandInteraction(this.bot, event)
    }
}