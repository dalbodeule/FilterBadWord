package space.mori.filterbadword;

import org.bukkit.plugin.java.JavaPlugin;
import org.tensorflow.Graph
import org.tensorflow.Session
import space.mori.filterbadword.models.BadWord
import space.mori.filterbadword.models.FastText
import java.nio.file.Files
import java.nio.file.Paths

class FilterBadWord: JavaPlugin() {
    companion object {
        lateinit var instance: FilterBadWord
        lateinit var badWord: BadWord
        lateinit var fastText: FastText

    }

    override fun onEnable() {
        instance = this
        badWord = BadWord()
        badWord.initSession()

        fastText = FastText()
        fastText.initFastText()

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
}