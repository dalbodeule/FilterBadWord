package space.mori.filterbadword;

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
import space.mori.filterbadword.models.BadWord
import space.mori.filterbadword.models.FastText

class FilterBadWord: JavaPlugin(), Listener {
    companion object {
        lateinit var instance: FilterBadWord
        lateinit var badWord: BadWord
        lateinit var fastText: FastText
    }

    override fun onEnable() {
        instance = this

        fastText = FastText()
        fastText.initFastText()

        badWord = BadWord()
        badWord.initBadWord()

        server.pluginManager.registerEvents(this, this)

        // command initialize
        //server.getPluginCommand("discord")?.run {
        //    this.setExecutor(DiscordCommand)
        //    this.tabCompleter = DiscordCommand
        //}

        //server.pluginManager.registerEvents(Discord, this)
    }

    override fun onDisable() {
        //Discord.disable()

        //Config.save()
        //UUIDtoDiscordID.save()
        //Language.save()

        badWord.closeSession()
        fastText.closeSession()
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val ratio = badWord.getChatIsBad(event.message)
        if(ratio > 0.8) {
            event.isCancelled = true
        }
        instance.logger.info("player: ${event.player.displayName}, chat: ${event.message}, ratio: ${ratio}")
    }
}