package org.wunder.interfaces

public interface DownloadListener <T> {
    fun complete(content: T)
    fun error(ex: Exception)
}