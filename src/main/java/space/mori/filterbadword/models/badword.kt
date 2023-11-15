package space.mori.filterbadword.models

import org.tensorflow.Graph
import org.tensorflow.SavedModelBundle
import org.tensorflow.Tensor
import org.tensorflow.ndarray.DoubleNdArray
import org.tensorflow.ndarray.NdArrays
import org.tensorflow.ndarray.Shape
import org.tensorflow.types.TFloat64
import space.mori.filterbadword.FilterBadWord
import space.mori.filterbadword.FilterBadWord.Companion.fastText
import space.mori.filterbadword.FilterBadWord.Companion.instance

class BadWord {
    private val tfModelPath = FilterBadWord.instance.dataFolder.toPath().endsWith("")
    private lateinit var model: SavedModelBundle

    fun initBadWord() {
        instance.logger.info(tfModelPath.toString())
        model = SavedModelBundle.load(tfModelPath.toString(), "serve")
    }

    fun getChatIsBad(str: String): Double {
        try {
            val runner = model.session().runner()
            val input: DoubleNdArray = NdArrays.ofDoubles(Shape.of(Shape.UNKNOWN_SIZE, 200))
            fastText.getCorpusVector(str).forEachIndexed { i, it ->
                input.set(NdArrays.vectorOf(*it.toDoubleArray()), i.toLong())
            }
            val inputTensor: Tensor = TFloat64.tensorOf(input)

            runner.feed("input", inputTensor)
            val outputTensorList = runner.fetch("output").run()

            val outputTensor = outputTensorList[0]

            return outputTensor.asRawTensor().data().asDoubles().getDouble(0)


        } catch (e: Exception) {
            throw e
        }
    }

    fun closeSession() {
        // model.close()
    }
}

