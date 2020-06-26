package kr.perfectree.heydealer.analyticssdk.analytics.sdk

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import kr.perfectree.heydealer.analyticssdk.analytics.Analytics

class FirebaseAnalyticsSdk : AnalyticsSdk {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun init(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    override fun isInitialized(): Boolean = ::firebaseAnalytics.isInitialized

    override fun setUserId(userId: String) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperties(properties: Map<String, Any>) {
        properties.forEach { (key, value) ->
            firebaseAnalytics.setUserProperty(key, value.toString())
        }
    }

    override fun screen(activity: Activity, name: String) {
        firebaseAnalytics.setCurrentScreen(activity, name, null)
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

    private fun internalEvent(name: String, bundle: Bundle = Bundle()) {
        firebaseAnalytics.logEvent(name, bundle)
    }

}