package com.valer.sergey.imageprocessor.utils

import android.content.Context

class ResourceManagerImpl(
        private val context: Context
) : ResourceManager {
    override fun getString(id: Int): String = context.getString(id)
}

interface ResourceManager {
    fun getString(id: Int): String
}