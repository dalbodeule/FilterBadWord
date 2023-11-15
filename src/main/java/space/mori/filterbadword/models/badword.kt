package space.mori.filterbadword.models

import org.tensorflow.Graph
import org.tensorflow.Session
import space.mori.filterbadword.FilterBadWord
import java.nio.file.Files

class BadWord {
    lateinit var session: Session

    fun initSession() {
        val tfModelPath = FilterBadWord.instance.dataFolder.toPath().resolveSibling("tfmodel.pb")
        val graph = Graph()
        graph.importGraphDef(Files.readAllBytes(tfModelPath))

        session = Session(graph)
    }

    fun closeSession() {
        if (::session.isInitialized) {
            session.close()
        }
    }
}
