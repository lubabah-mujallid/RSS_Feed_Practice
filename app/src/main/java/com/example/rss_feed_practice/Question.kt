package com.example.rss_feed_practice

data class Question(val title: String?, val summary: String?) {
    override fun toString(): String = title!!
}