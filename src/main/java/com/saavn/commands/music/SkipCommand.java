package com.saavn.commands.music;

import com.saavn.audio.GuildMusicManager;
import com.saavn.audio.PlayerManager;
import com.saavn.commandmeta.CommandBase;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

//// TODO: Define something to clear this redundant checking

public class SkipCommand extends CommandBase {
    public SkipCommand(JDA jda) {
        super(jda, "skip", "Skips to the next song", "Music");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        final Member selfMember = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = selfMember.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            event.deferReply().setContent("I'm not playing anything currently.").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            event.deferReply().setContent("You are not connected to a voice channel.").setEphemeral(true).queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            event.deferReply().setContent("You are not connected to the same voice channel as me.").setEphemeral(true).queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        final AudioPlayer audioPlayer = musicManager.getPlayer();

        if (audioPlayer.getPlayingTrack() == null) {
            event.deferReply().setContent("I'm not playing anything currently.").queue();
            return;
        }

        musicManager.getScheduler().nextTrack();
        final AudioTrack nextTrack = audioPlayer.getPlayingTrack();

        event.deferReply().setContent("**✅ | Skipped to the next track**: " + nextTrack.getInfo().title).queue();
    }
}
