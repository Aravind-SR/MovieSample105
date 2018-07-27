package com.example.aravind_pt1748.moviesample105.util

fun convertDate(inputDate : String) : String {
    var readableDate = StringBuilder()
    var tempDate = inputDate
    readableDate.append(tempDate.substring(tempDate.length-2))
    tempDate = tempDate.substring(0,tempDate.length-3)
    readableDate.append(" ")
    readableDate.append( when(tempDate.substring(tempDate.length-2)) {
        "01" -> "Jan"
        "02" -> "Feb"
        "03" -> "Mar"
        "04" -> "Apr"
        "05" -> "May"
        "06" -> "Jun"
        "07" -> "Jul"
        "08" -> "Aug"
        "09" -> "Sep"
        "10" -> "Oct"
        "11" -> "Nov"
        "12" -> "Dec"
        else -> {
            "Mon"
        }
    } )
    readableDate.append(" ")
    tempDate = tempDate.substring(0,tempDate.length-3)
    readableDate.append(tempDate)
    return readableDate.toString()
}