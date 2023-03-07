package com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models

val Sources: Array<Source> = arrayOf(
    Source("bbc-news", "BBC news"),
    Source("google-news", "Google News"),
    Source("abc-news", "ABC News"),
    Source("bleacher-report", "Bleacher Report"),
    Source("aftenposten", "Aftenposten"),
    Source("ansa", "ANSA.it"),
    Source("ary-news", "Ary News"),
    Source("associated-press", "Associated Press"),


)

class Source(val id: String, val name: String) {

}
