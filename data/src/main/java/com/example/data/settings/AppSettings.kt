package com.example.data.settings

interface AppSettings {

    /**
     * Get last tab user visit.
     * @return tab name [String]
     */
    fun getLastTab(): String?

    /**
     * Set last tab user visit.
     * @param tabName [String]
     */
    fun setLastTab(tabName: String)

}