package com.example.interstitialadskotlin

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {

    private lateinit var adView: AdView

    var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adView = findViewById(R.id.adView)

        //Step 1
        MobileAds.initialize(this)

        //Step 2
        val adRequest = AdRequest.Builder().build()

        //Step 3
        adView.loadAd(adRequest)

        //For Interstitial_Ads Steps are follows.
        //Step 1
        InterstitialAd.load(
            this,
            getString(R.string.inter_ad_unit_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)

                    //Step 2
                    mInterstitialAd = interstitialAd

                    //Step 3
                    mInterstitialAd!!.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                super.onAdFailedToShowFullScreenContent(adError)
                            }

                            override fun onAdImpression() {
                                super.onAdImpression()
                            }

                            override fun onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent()
                                mInterstitialAd = null
                            }
                        }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    Log.d("Error", loadAdError.toString())
                }
            })

        //Step 4
        Handler().postDelayed({
            if (mInterstitialAd != null) mInterstitialAd!!.show(this@MainActivity) else Log.e(
                "AdPending",
                "Ad is not Ready"
            )
        }, 5000)
    }
}