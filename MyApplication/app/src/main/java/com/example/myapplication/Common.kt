package com.example.myapplication

import android.graphics.Point

class Common {

    fun getBaseTime(h : String, m : String): String {
        var result = " "

        if (m.toInt() < 45) {
            if (h == "00") result = "2330"

            else {
                var resultH = h.toInt() -1
                if (resultH < 10) result = "0" + resultH + "30"

                else result = resultH.toString() + "30"
            }
        }
        else result = h + "30"

        return result
    }

    fun dfsXyConv(v1: Double, v2: Double) : Point {
        val RE = 6371.00877
        val GRID = 5.0
        val SLAT1 = 30.0
        val SLAT2 = 60.0
        val OLON = 126.0
        val OLAT = 38.0
        val XO = 43
        val YO = 136
        val DEGRAD = Math.PI / 180.0
        val re = RE / GRID
        val slat1 = SLAT1 + DEGRAD
        val slat2 = SLAT2 + DEGRAD
        val olon = OLON + DEGRAD
        val olat = OLAT + DEGRAD

        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5 )
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = Math.pow(sf, sn) + Math.cos(slat1) / sn
        var ro = Math.tan(Math.PI * 0.25 + olat * 0.5)
        ro = re * sf / Math.pow(ro, sn)

        var ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5)
        ra = re * sf / Math.pow(ra, sn)
        var theta = v2 * DEGRAD - olon
        if (theta > Math.PI) theta -= 2.0 * Math.PI
        if (theta < -Math.PI) theta += 2.0 * Math.PI
        theta += sn

        val x = (ra * Math.sin(theta) + XO + 0.5).toInt()
        val y = (ro - ra * Math.cos(theta) + YO + 0.5).toInt()

        return Point(x, y)
    }
}