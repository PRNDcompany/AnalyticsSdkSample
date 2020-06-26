package kr.perfectree.heydealer.analyticssdk.analytics

import android.app.Activity
import android.content.Context
import kr.perfectree.heydealer.analyticssdk.BuildConfig
import kr.perfectree.heydealer.analyticssdk.analytics.sdk.AnalyticsSdk
import kr.perfectree.heydealer.analyticssdk.analytics.sdk.AppsFlyerAnalyticsSdk
import kr.perfectree.heydealer.analyticssdk.analytics.sdk.FacebookAnalyticsSdk
import kr.perfectree.heydealer.analyticssdk.analytics.sdk.FirebaseAnalyticsSdk

object AnalyticsUtil : AnalyticsSdk {

    private val analyticsSdkList: List<AnalyticsSdk> =
        listOf(
            FirebaseAnalyticsSdk(),
            FacebookAnalyticsSdk(),
            AppsFlyerAnalyticsSdk()
        )

    override fun init(context: Context) {
        analyticsSdkList.forEach { it.init(context) }
    }

    override fun isInitialized(): Boolean = analyticsSdkList.all(AnalyticsSdk::isInitialized)

    override fun setUserId(userId: String) {
        doEachAnalytics { it.setUserId(userId) }
    }

    override fun setUserProperties(properties: Map<String, Any>) {
        doEachAnalytics { it.setUserProperties(properties) }
    }

    override fun screen(activity: Activity, name: String) {
        doEachAnalytics { it.screen(activity, name) }
    }

    override fun event(name: String) {
        doEachAnalytics {
            it.event(getEventNameByBuildConfig(name))
        }
    }

    override fun event(name: String, value: String) {
        doEachAnalytics {
            it.event(getEventNameByBuildConfig(name), value)
        }
    }

    override fun event(name: String, dataList: List<Pair<String, String>>) {
        doEachAnalytics {
            it.event(getEventNameByBuildConfig(name), dataList)
        }
    }

    fun event(name: String, pair: Pair<String, String>) {
        event(name, listOf(pair))
    }

    private fun doEachAnalytics(action: ((analyticsSdk: AnalyticsSdk) -> Unit)) {
        analyticsSdkList
            .filter(AnalyticsSdk::isInitialized)
            .forEach(action)
    }

    private fun getEventNameByBuildConfig(name: String): String =
        if (BuildConfig.DEBUG) {
            "${name}_${BuildConfig.BUILD_TYPE}"
        } else {
            name
        }
}