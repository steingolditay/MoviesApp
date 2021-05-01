package com.steingolditay.app.moviesapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.lang.Exception
import javax.inject.Inject

@InstallIn(SingletonComponent::class)
@Module
class NetworkConnectionMonitor

@Inject constructor(@ApplicationContext private val context: Context) {
    private val _liveData = MutableLiveData<Boolean>()
    val liveData: LiveData<Boolean> get() = _liveData

    fun registerNetworkCallback(){
        _liveData.postValue(false)

        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    _liveData.postValue(true)
                }
                override fun onLost(network: Network) {
                    super.onLost(network)
                    _liveData.postValue(false)
                }
            })
        }
        catch (e: Exception){
        }
    }

}