package com.mhasancse15.cleanmaster.domain.util

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import android.util.Log
import javax.annotation.Nonnull

class NetworkTracker private constructor(@param:Nonnull private val context: Context) :
    BroadcastReceiver() {
    private var networkListener: NetworkListener? = null


    fun setNetworkListener(networkListener: NetworkListener?): NetworkTracker {
        this.networkListener = networkListener
        return this
    }

    interface NetworkListener {
        fun onNetworkChange(isConnected: Boolean, networkType: Type?, activeNetwork: NetworkInfo?)
    }

    enum class Type(val value: String) {
        unknown("unknown"), offline("offline"), twoG("2g"), threeG("3g"), fourG("4g"), fiveG("5g"), wifi(
            "wifi"
        )
    }

    fun startReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(this as BroadcastReceiver, intentFilter)
    }

    fun stopReceiver() {
        context.unregisterReceiver(this)
    }

    override fun onReceive(context: Context, intent: Intent) {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            val activeNetwork = connMgr.activeNetworkInfo
            if (networkListener != null) {
                val netType = getNetworkTypeBy(context, activeNetwork)
                networkListener!!.onNetworkChange(activeNetwork != null, netType, activeNetwork)
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "ACCESS_NETWORK_STATE permission is missing")
        }
    }


    companion object {
        private val TAG: String = NetworkTracker::class.java.simpleName
        fun with(@Nonnull context: Context): NetworkTracker {
            return NetworkTracker(context)
        }

        fun hasNetwork(@Nonnull context: Context): Boolean {
            val connectivity =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val activeNetwork = connectivity.activeNetworkInfo
                return activeNetwork != null
            }
            return false
        }

        private fun getNetworkTypeBy(@Nonnull context: Context, activeNetwork: NetworkInfo?): Type {
            try {
                if (activeNetwork == null) return Type.offline //net work not connected


                //network connected
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    return Type.wifi
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    val mTelephonyManager =
                        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    @SuppressLint("MissingPermission") val networkType =
                        mTelephonyManager.networkType
                    return when (networkType) {
                        TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> Type.twoG
                        TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> Type.threeG
                        TelephonyManager.NETWORK_TYPE_LTE -> Type.fourG
                        else -> Type.unknown
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return Type.unknown
        }

        fun getNetworkType(@Nonnull context: Context): Type {
            try {
                val connMgr =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = connMgr.activeNetworkInfo
                getNetworkTypeBy(context, activeNetwork)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return Type.unknown
        }
    }
}