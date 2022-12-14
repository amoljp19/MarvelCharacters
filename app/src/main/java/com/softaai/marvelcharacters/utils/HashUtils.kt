package com.softaai.marvelcharacters.utils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
 */

object HashUtils {
    fun md5(stringToHash: String): String {
        val MD5 = "MD5"

        try {
            val digest = MessageDigest.getInstance(MD5)
            digest.update(stringToHash.toByteArray())
            val messageDigest = digest.digest()

            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0" + h
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}