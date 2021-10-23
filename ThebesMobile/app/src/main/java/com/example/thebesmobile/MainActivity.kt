package com.example.thebesmobile

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var debugTag = "debug_nfc"

    private var adapter: NfcAdapter? = null

    private var client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var nfcAdapter = NfcAdapter.getDefaultAdapter(this)
//        Log.d(tag, "NFC supported: " + (nfcAdapter != null).toString())
//        Log.d(tag, "NFC enabled: " + (nfcAdapter?.isEnabled).toString())
        initNfcAdapter()
    }

    private fun initNfcAdapter() {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        adapter = nfcManager.defaultAdapter
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    private fun enableNfcForegroundDispatch() {
        try {
            val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val nfcPendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            adapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null)
        } catch (ex: IllegalStateException) {
            Log.e(debugTag, "Error enabling NFC foreground dispatch", ex)
        }
    }

    override fun onPause() {
        disableNfcForegroundDispatch()
        super.onPause()
    }

    private fun disableNfcForegroundDispatch() {
        try {
            adapter?.disableForegroundDispatch(this)
        } catch (ex: IllegalStateException) {
            Log.e(debugTag, "Error disabling NFC foreground dispatch", ex)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMsgs != null) {
                onTagTapped(NfcUtils.getUID(intent), NfcUtils.getData(rawMsgs))
            }
        }
    }

    private fun onTagTapped(superTagId: String, superTagData: String) {
        Log.d(debugTag, superTagId)
        Log.d(debugTag, superTagData)

        val request = Request.Builder()
            .url("https://us-east1-thebes-329917.cloudfunctions.net/thebes-get-coupon?chipId=${superTagId}")
            .build()

        val intent = Intent(this@MainActivity, CouponActivity::class.java)


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                if (response.code() == 200) {
                    val gson = Gson()
                    val coupon = gson.fromJson(response.body()?.string(), CouponEntity.data::class.java)

                    intent.putExtra("id", coupon.id)
                    intent.putExtra("name", coupon.name)
                    intent.putExtra("description", coupon.description)
                    intent.putExtra("expiration", coupon.expiration)
                    startActivity(intent)
                }
            }
        })

    }
}
