package space.mori.filterbadword.models

import com.github.jfasttext.JFastText
import space.mori.filterbadword.FilterBadWord.Companion.instance

class FastText {
    val fastText = JFastText()

    fun initFastText() {
        val fasttextPath = instance.dataFolder.toPath().resolveSibling("${instance.name}/fasttext.bin")
        instance.logger.info(fasttextPath.toString())
        fastText.loadModel(fasttextPath.toString())
    }

    fun getCorpusVector(str: String): MutableList<List<Double>> {
        val list:MutableList<List<Double>> = mutableListOf()

        str.trim().split("\\s+".toRegex()).forEach { it ->
            list.add(fastText.getVector(it).map { it1 -> it1.toDouble() })
        }

        return list
    }

    fun closeSession() {
    }
}