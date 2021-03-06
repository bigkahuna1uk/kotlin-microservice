package com.felipecosta.microservice.frontcontroller.impl

import com.beust.klaxon.JsonArray
import com.beust.klaxon.json
import com.felipecosta.microservice.frontcontroller.SparkFrontCommand
import spark.Request
import spark.Response

class JsonSparkFrontCommand(request: Request, response: Response) : SparkFrontCommand(request, response) {

    override fun process() {

        val jsonBody = json {
            obj(
                    "url" to request.url(),
                    "host" to request.host(),
                    "user-agent" to request.userAgent(),
                    "query-params" to request.queryMap()?.let { queryMap ->
                        array(queryMap.toMap().map { it ->
                            return@map obj(it.key to JsonArray(it.value.map { it }))
                        })
                    }
            )
        }

        render(text = jsonBody.toJsonString())
    }
}
