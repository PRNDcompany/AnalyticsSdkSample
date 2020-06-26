package kr.perfectree.heydealer.analyticssdk.analytics.sdk

import android.app.Activity
import android.content.Context
import android.os.Bundle

interface AnalyticsSdk {
    fun init(context: Context)
    fun isInitialized(): Boolean
    fun setUserId(userId: String)
    fun setUserProperties(properties: Map<String, Any>)
    fun screen(activity: Activity, name: String)
    fun event(name: String)
    fun event(name: String, value: String)
    fun event(name: String, dataList: List<Pair<String, String>>)

    fun getBundle(dataList: List<Pair<String, String>>) =
        Bundle().apply {
            dataList.forEach { (key, value) ->
                putString(key, value)
            }
        }
}