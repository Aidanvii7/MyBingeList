package com.aidanvii.mybingelist.core.logger

object CompositeLogger : CompositeLoggerDelegate {

    private var tagPrefix = ""

    private val delegates = mutableSetOf<LoggerDelegate>()

    val hasLoggers: Boolean get() = delegates.isNotEmpty()

    @Synchronized
    override fun attachDelegate(delegate: LoggerDelegate) {
        delegates.add(delegate)
    }

    @Synchronized
    override fun detachDelegate(delegate: LoggerDelegate) {
        delegates.remove(delegate)
    }

    fun setTagPrefix(tagPrefix: String) {
        CompositeLogger.tagPrefix = tagPrefix
    }

    override fun d(tag: String, message: String) = delegates.forEach { it.d(
        tagPrefix + tag, message) }

    override fun w(tag: String, message: String) = delegates.forEach { it.w(
        tagPrefix + tag, message) }

    override fun v(tag: String, message: String) = delegates.forEach { it.v(
        tagPrefix + tag, message) }
}