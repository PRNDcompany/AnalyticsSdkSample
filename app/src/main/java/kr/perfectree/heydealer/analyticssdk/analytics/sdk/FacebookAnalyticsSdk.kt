package kr.perfectree.heydealer.analyticssdk.analytics.sdk

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import kr.perfectree.heydealer.analyticssdk.analytics.Analytics

class FacebookAnalyticsSdk : AnalyticsSdk {

    private lateinit var appEventsLogger: AppEventsLogger

    override fun init(context: Context) {
        appEventsLogger = AppEventsLogger.newLogger(context)
    }

    override fun isInitialized(): Boolean =
        FacebookSdk.isInitialized() && ::appEventsLogger.isInitialized

    override fun setUserId(userId: String) {
        AppEventsLogger.setUserID(userId)
    }

    override fun setUserProperties(properties: Map<String, Any>) {
        val bundle = Bundle().apply {
            properties.forEach { (key, value) ->
                putString(key, value.toString())
            }
        }
        AppEventsLogger.updateUserProperties(bundle) {
            // no-op
        }
    }

    override fun screen(activity: Activity, name: String) {
        // no-op
    }

    override fun event(name: String) {
        internalEvent(name)
    }

    override fun event(name: String, value: String) {
        val bundle = Bundle().apply {
            putString(Analytics.Param.VALUE, value)
        }
        internalEvent(name, bundle)
    }

    override fun event(name: String, dataList: List<Pair<String, String>>) {
        internalEvent(name, getBundle(dataList))
    }

    private fun internalEvent(name: String, bundle: Bundle? = null) {
        appEventsLogger.logEvent(name, bundle)
    }
}