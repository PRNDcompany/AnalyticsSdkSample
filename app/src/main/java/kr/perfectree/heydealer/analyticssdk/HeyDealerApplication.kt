package kr.perfectree.heydealer.analyticssdk

import android.app.Application
import kr.perfectree.heydealer.analyticssdk.analytics.AnalyticsUtil

class HeyDealerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AnalyticsUtil.init(this)
    }
}