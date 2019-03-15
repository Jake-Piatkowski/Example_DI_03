package com.jbpi.exampledi03

import io.reactivex.functions.Consumer
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject

fun main() {

    startKoin(listOf(moduleMain))

    StreamsDownloaderApplication()
}

class StreamsDownloaderApplication : KoinComponent {

    private val streamsDownloader by inject<StreamsDownloader>()

    init {

        streamsDownloader.download(Consumer { apiResponseStreams ->
            System.out.println("Stream count: " + apiResponseStreams.data.size)

            for (apiStream in apiResponseStreams.data) {
                System.out.println(apiStream.title)
            }
        })
    }
}