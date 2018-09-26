package com.lamblin.core

import com.lamblin.core.model.HandlerMethod
import com.lamblin.core.model.HandlerMethodParameter
import com.lamblin.core.model.HttpMethod
import com.lamblin.core.model.HttpMethod.GET
import com.lamblin.core.model.HttpMethod.PATCH
import com.lamblin.core.model.HttpMethod.POST
import com.lamblin.core.model.HttpMethod.PUT
import com.lamblin.core.model.annotation.Endpoint
import org.slf4j.LoggerFactory
import java.lang.reflect.Method
import kotlin.reflect.KCallable

internal interface HandlerMethodFactory {
    fun method(method: Method, controllerClass: Class<out Any>): HandlerMethod

    companion object {
        internal fun default() = DefaultHandlerMethodFactory
    }
}

private val LOGGER = LoggerFactory.getLogger(DefaultHandlerMethodFactory::class.java)

/**
 * Defines the mechanism for creating [HandlerMethod] instances after inspecting
 * the details(annotations) of a [KCallable].
 */
internal object DefaultHandlerMethodFactory: HandlerMethodFactory {

    /** Creates a [HandlerMethod] instance using the details(annotations and parameters) of the input [KCallable]. */
    override fun method(method: Method, controllerClass: Class<out Any>): HandlerMethod {
        val endpointAnnotation = method.annotations.find { it is Endpoint } as? Endpoint

        return when (endpointAnnotation?.method) {
            POST -> createHandlerMethod(POST, endpointAnnotation.path, method, controllerClass)
            GET -> createHandlerMethod(GET, endpointAnnotation.path, method, controllerClass)
            PUT -> createHandlerMethod(PUT, endpointAnnotation.path, method, controllerClass)
            PATCH -> createHandlerMethod(PATCH, endpointAnnotation.path, method, controllerClass)
            else -> throw IllegalArgumentException("Http Method ${endpointAnnotation?.method} not supported.")
        }
    }

    private fun createHandlerMethod(
            httpMethod: HttpMethod,
            path: String,
            method: Method,
            controllerClass: Class<out Any>): HandlerMethod {

        val paramNameToParam = method.parameters.asSequence()
                .filter { !it.annotations.isEmpty() }
                .map { it.name!! to HandlerMethodParameter.of(it.name!!, it.javaClass , it.annotations[0]) }
                .toMap()

        LOGGER.debug("Handler method created for [{}] in [{}]", controllerClass.canonicalName, method.name)

        return HandlerMethod(path, httpMethod, paramNameToParam, method, controllerClass)
    }
}
