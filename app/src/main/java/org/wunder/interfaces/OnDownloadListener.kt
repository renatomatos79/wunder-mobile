package org.wunder.interfaces

public interface OnDownloadListener <T> {
    fun complete(content: T)
    fun error(ex: Exception)
}