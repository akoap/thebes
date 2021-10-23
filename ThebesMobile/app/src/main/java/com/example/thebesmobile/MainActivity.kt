package com.example.thebesmobile

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.getTag

class MainActivity : AppCompatActivity() {
    val notfound = "No NFC found"
    val success = "NFC scanned successfully"
    val failure = "Scanning failed"
    var tag: WritableTag? = null
    var tagId: String? = null

    var debugTag = "debug_nfc"

    var adapter: NfcAdapter? = null


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
//        val tagFromIntent = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
//        try {
//            tag = WritableTag(tagFromIntent)
//        } catch (e: FormatException) {
//            Log.e(getTag(), "Unsupported tag tapped", e)
//            return
//        }
//        tagId = tag!!.tagId
//        showToast("Tag tapped: $tagId")

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
    }
}