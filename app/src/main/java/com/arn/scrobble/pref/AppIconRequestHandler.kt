package com.arn.scrobble.pref

import android.content.Context
import android.text.TextUtils
import com.arn.scrobble.Stuff
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler


/**
 * Created by arn on 17/09/2017.
 */
class AppIconRequestHandler(context: Context) : RequestHandler() {

    private val pm = context.packageManager
    private val isMIUI = Stuff.isMiui(context)

    override fun canHandleRequest(data: Request): Boolean {
        return data.uri != null && TextUtils.equals(data.uri.scheme, SCHEME_PNAME)
    }

    override fun load(request: Request, networkPolicy: Int): Result? {
        return try {
            val info = pm.getApplicationInfo(request.uri.toString().split(":")[1], 0)
            val b = Stuff.drawableToBitmap(info.loadIcon(pm), request.targetWidth, request.targetHeight, isMIUI)
            Result(b, LoadedFrom.DISK)
        } catch (e: Exception) { //catch miui security exceptions too
            e.printStackTrace()
            null
        }
    }

    companion object {
        const val SCHEME_PNAME = "pname"
    }

}