package chucknorris

fun main() {
    var operation: String

    do {
        println("Please input operation (encode/decode/exit):")
        operation = readln() ?: ""

        when (operation) {
            "encode" -> {
                println("Input string:")
                val input = readln() ?: ""
                val encodedMessage = encodeMessage(input)
                println("Encoded string:")
                println(encodedMessage)
            }
            "decode" -> {
                println("Input encoded string:")
                val input = readln() ?: ""
                val decodedMessage = decodeMessage(input)
                if (decodedMessage.isEmpty()) {
                    println("Encoded string is not valid.")
                } else {
                    println("Decoded string:")
                    println(decodedMessage)
                }
            }
            "exit" -> println("Bye!")
            else -> println("There is no '$operation' operation")
        }

    } while (operation != "exit")
}

fun encodeMessage(input: String): String {
    val binaryMessage = input.map { it.toInt().toString(2).padStart(7, '0') }.joinToString("")
    val encoded = StringBuilder()

    var currentBit = binaryMessage[0]
    var count = 1

    for (i in 1 until binaryMessage.length) {
        if (binaryMessage[i] == currentBit) {
            count++
        } else {
            val encodedBlock = "${if (currentBit == '1') "0" else "00"} ${"0".repeat(count)}"
            encoded.append(encodedBlock).append(" ")
            currentBit = binaryMessage[i]
            count = 1
        }
    }

    val encodedBlock = "${if (currentBit == '1') "0" else "00"} ${"0".repeat(count)}"
    encoded.append(encodedBlock)

    return encoded.toString()
}

fun decodeMessage(input: String): String {
    val blocks = input.split(" ")
    val binaryBuilder = StringBuilder()

    for (i in blocks.indices step 2) {
        if (i + 1 < blocks.size) {
            val firstBlock = blocks[i]
            val secondBlock = blocks[i + 1]
            val binaryValue = if (firstBlock == "0") {
                "1".repeat(secondBlock.length)
            } else if (firstBlock == "00") {
                "0".repeat(secondBlock.length)
            } else {
                return ""
            }
            binaryBuilder.append(binaryValue)
        }
    }

    val binaryMessage = binaryBuilder.toString()
    if (binaryMessage.length % 7 != 0) {
        return ""
    }

    val decodedMessage = binaryMessage.chunked(7).map { Integer.parseInt(it, 2).toChar() }.joinToString("")

    return decodedMessage
}