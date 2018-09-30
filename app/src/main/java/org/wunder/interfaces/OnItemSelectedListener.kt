package org.wunder.interfaces

public interface OnItemSelectedListener<T : Any> {
    fun select(item: T)
}