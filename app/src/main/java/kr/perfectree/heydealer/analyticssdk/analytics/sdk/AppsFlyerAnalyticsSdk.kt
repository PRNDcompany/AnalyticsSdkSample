package kr.perfectree.heydealer.analyticssdk.analytics.sdk

import android.app.Activity
import android.content.Context
import com.appsflyer.AppsFlyerLib
import kr.perfectree.heydealer.analyticssdk.R
import kr.perfectree.heydealer.analyticssdk.analytics.Analytics

class AppsFlyerAnalyticsSdk : AnalyticsSdk {

    private lateinit var appsFlyerLib: AppsFlyerLib
    private lateinit var context: Context

    override fun init(context: Context) {
        this.context = context
        appsFlyerLib = AppsFlyerLib.getInstance().apply {
            init(context.getString(R.string.appsflyer_app_id), null, context)
            startTracking(context)
        }
    }

    override fun isInitialized(): Boolean = ::appsFlyerLib.isInitialized

    override fun setUserId(userId: String) {
        appsFlyerLib.setCustomerUserId(userId)
    }

    override fun setUserProperties(properties: Map<String, Any>) {
        val propertiesArray = properties.map { (key, value) -> key to value }.toTypedArray()
        appsFlyerLib.setAdditionalData(hashMapOf(*propertiesArray))
    }

    override fun screen(activity: Activity, name: String) {
        // no-op
    }


    override fun event(name: String) {
        internalEvent(name)
    }

    override fun event(name: String, value: String) {
        val map = HashMap<String, Any>().apply {
            put(Analytics.Param.VALUE, value)
        }
        internalEvent(name, map)
    }

    override fun event(name: String, dataList: List<Pair<String, String>>) {
        val map =
            HashMap<String, Any>().apply {
                dataList.forEach { (key, value) -> put(key, value) }
            }
        internalEvent(name, map)
    }

    private fun internalEvent(name: String, map: Map<String, Any> = emptyMap()) {
        appsFlyerLib.trackEvent(context, name, map)
    }

}