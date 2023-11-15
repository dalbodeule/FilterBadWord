package space.mori.filterbadword.models

import com.github.jfasttext.JFastText
import space.mori.filterbadword.FilterBadWord

class FastText {
    lateinit var fastText: JFastText

    fun initFastText() {
        val fasttextPath = FilterBadWord.instance.dataFolder.toPath().resolveSibling("fasttext.bib")

        fastText = JFastText()
        fastText.loadModel(fasttextPath.toString());
    }

    fun closeSession() {
        if(::fastText.isInitialized) {
            /* todo: closing sessions. */
        }
    }
}